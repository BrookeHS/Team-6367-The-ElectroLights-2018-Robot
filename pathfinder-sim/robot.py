#!/usr/bin/env python3
#
# This example demonstrates using robotpy-pathfinder in the pyfrc simulator
# with a working physics modules such that you can see the path being traced
#
# Note that the pyfrc physics aren't particularly realistic (in particular,
# friction and momentum are ignored), so performance of these exact parameters
# on a real robot won't be so great.
#

import math
import wpilib

import pathfinder as pf
from pathfinder import Waypoint

from paths import paths

from networktables.util import ntproperty

class MyRobot(wpilib.TimedRobot):
    '''Main robot class'''
    
    mode = ntproperty('/robot/mode', 'disabled', True)
    
    # Robot attributes
    WHEEL_DIAMETER = 0.1524 # 6 inches
    ENCODER_COUNTS_PER_REV = 360
    
    # Pathfinder constants
    MAX_VELOCITY = 1.7 # ft/s
    MAX_ACCELERATION = 2
    
    def robotInit(self):
        '''Robot-wide initialization code should go here'''
        
        self.lstick = wpilib.Joystick(0)
        
        self.l_motor = wpilib.Spark(1)
        self.r_motor = wpilib.Spark(2)
        
        # Position gets automatically updated as robot moves
        self.gyro = wpilib.AnalogGyro(1)
        
        self.robot_drive = wpilib.RobotDrive(self.l_motor, self.r_motor)
        
        self.l_encoder = wpilib.Encoder(0, 1)
        self.l_encoder.setDistancePerPulse((math.pi * self.WHEEL_DIAMETER) / self.ENCODER_COUNTS_PER_REV)
        
        self.r_encoder = wpilib.Encoder(2, 3)
        self.r_encoder.setDistancePerPulse((math.pi * self.WHEEL_DIAMETER) / self.ENCODER_COUNTS_PER_REV)
        
        self.paths = paths()
        self.chooser = wpilib.SendableChooser()
        for k, v in self.paths.items():
            if k == 'robotLScaleR':
                self.chooser.addDefault(k, v)
            else:
                self.chooser.addObject(k, v)
            
        wpilib.SmartDashboard.putData('Autonomous Mode', self.chooser)
    
    def autonomousInit(self):
        self.mode = 'auto'
        
        # Set up the trajectory
        points = self.chooser.getSelected()
        
        
        info, trajectory = pf.generate(points, pf.FIT_HERMITE_CUBIC, pf.SAMPLES_HIGH,
                                       dt=self.getPeriod(),
                                       max_velocity=self.MAX_VELOCITY,
                                       max_acceleration=self.MAX_ACCELERATION,
                                       max_jerk=60.0)

        # Wheelbase Width = 2 ft
        modifier = pf.modifiers.TankModifier(trajectory).modify(0.5969)

        # Do something with the new Trajectories...
        left = modifier.getLeftTrajectory()
        right = modifier.getRightTrajectory()
        
        kv = 1 / self.MAX_VELOCITY
        
        leftFollower = pf.followers.EncoderFollower(left)
        leftFollower.configureEncoder(self.l_encoder.get(), self.ENCODER_COUNTS_PER_REV, self.WHEEL_DIAMETER)
        leftFollower.configurePIDVA(1.0, 0.0, 0.01, kv, 0)
        
        rightFollower = pf.followers.EncoderFollower(right)
        rightFollower.configureEncoder(self.r_encoder.get(), self.ENCODER_COUNTS_PER_REV, self.WHEEL_DIAMETER)
        rightFollower.configurePIDVA(1.0, 0.0, 0.01, kv, 0)
        
        self.leftFollower = leftFollower
        self.rightFollower = rightFollower
        
        self.angle_error = 0.0
        
        # This code renders the followed path on the field in simulation (requires pyfrc 2018.2.0+)
        if wpilib.RobotBase.isSimulation():
            from pyfrc.sim import get_user_renderer
            renderer = get_user_renderer()
            if renderer:
                scale = (3.28084, 3.28084)
                
                renderer.draw_pathfinder_trajectory(left, color='#0000ff', offset=(-1,0), scale=scale)
                renderer.draw_pathfinder_trajectory(modifier.source, color='#00ff00', show_dt=1.0, dt_offset=0.0, scale=scale)
                renderer.draw_pathfinder_trajectory(right, color='#0000ff', offset=(1,0), scale=scale)
        
    def autonomousPeriodic(self):
        l_encoder = self.l_encoder.get()
        r_encoder = self.r_encoder.get()
        
        try:
            lSegment = self.leftFollower.getSegment()
            rSegment = self.rightFollower.getSegment()
        except IndexError:
            # end of trajectory
            self.robot_drive.tankDrive(0, 0)
            return
        
        l = self.leftFollower.calculate(l_encoder)
        r = self.rightFollower.calculate(r_encoder)
        
        l_distance_covered = ((l_encoder - 0) / self.ENCODER_COUNTS_PER_REV) * math.pi * self.WHEEL_DIAMETER;
        r_distance_covered = ((r_encoder - 0) / self.ENCODER_COUNTS_PER_REV) * math.pi * self.WHEEL_DIAMETER;
        
    #     double ang = -sensors.ahrs.getAngle(); // Assuming the gyro is giving a value in degrees
	# 	double desired_heading = Pathfinder.r2d(left.getHeading()); // Should also be in degrees
	# 	double angleDifference = Pathfinder.boundHalfDegrees(desired_heading - ang);
    #
	# 	// Interpolate the angleDifference (-180 to 180) to a range between (-1 and 1)
	# 	// Multiply that again by a factor of 0.8 to slow it down a bit.
	# //	double turn = 0.8 * (-1.0 / 80.0) * angleDifference;
	# 	double turn = AutoTrajectory.gp * angleDifference +
	# 			(AutoTrajectory.gd *((angleDifference-this.angle_error)
	# 					/AutoTrajectory.dt));
	# 	angle_error = angleDifference;
	# 	robotOut.setDriveLeft(outputLeftEncoder - turn);
	# 	robotOut.setDriveRight(outputRightEncoder +turn);

        gyro_heading = -self.gyro.getAngle()    # Assuming the gyro is giving a value in degrees
        desired_heading = pf.r2d(self.leftFollower.getHeading())   # Should also be in degrees

        # This is a poor man's P controller
        angleDifference = pf.boundHalfDegrees(desired_heading - gyro_heading)
        #turn = 5 * (-1.0/80.0) * angleDifference
        
        gp = 0.02
        gd = 0.0025
        dt = 0.02
        
        turn = gp * angleDifference + (gd *((angleDifference-self.angle_error)/dt))
        
        l = l - turn
        r = r + turn

        # -1 is forward, so invert both values
        self.robot_drive.tankDrive(-l, -r)
        
        # debugging
        data = [
            wpilib.Timer.getFPGATimestamp(),
            
            l,
            l_encoder,
            l_distance_covered,
            lSegment.position,
            lSegment.velocity,
            
            r,
            r_encoder,
            r_distance_covered,
            rSegment.position,
            rSegment.velocity,
            
            gyro_heading,
            desired_heading,
            angleDifference,
            
            lSegment.x,
            lSegment.y,
            rSegment.x,
            rSegment.y,
        ]
        
        # 0 Timer.getFPGATimestamp(),
    #
    # 1 outputLeftEncoder,
    # 2 l_encoder,
    # 3 l_distance_covered,
    # 4 lSegment.position,
    # 5 lSegment.velocity,
    #
    # 6 outputRightEncoder,
    # 7 r_encoder,
    # 8 r_distance_covered,
    # 9 rSegment.position,
    # 10 rSegment.velocity,
    #
    # 11 ang,
    # 12 desired_heading
    
    # 13
    # 14
    # 15
    # 16
        
        # tm, l_last_error,
        wpilib.SmartDashboard.putNumberArray('pfdebug', data)
    
    def disabledInit(self):
        self.mode = 'disabled'
    
    def teleopInit(self):
        self.mode = 'teleop'
    
    def teleopPeriodic(self):
        self.robot_drive.arcadeDrive(self.lstick)

if __name__ == '__main__':
    wpilib.run(MyRobot)
