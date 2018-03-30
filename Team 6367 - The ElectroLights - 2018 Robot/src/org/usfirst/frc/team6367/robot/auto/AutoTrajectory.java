
package org.usfirst.frc.team6367.robot.auto;

import java.io.File;

import org.usfirst.frc.team6367.robot.Robot.AutonomousChoice;
import org.usfirst.frc.team6367.robot.io.RobotOutput;
import org.usfirst.frc.team6367.robot.io.SensorInput;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import io.github.robotpy.magicbot.MagicInject;
import jaci.pathfinder.Pathfinder;
import jaci.pathfinder.Trajectory;
import jaci.pathfinder.Trajectory.Segment;
import jaci.pathfinder.followers.EncoderFollower;
import jaci.pathfinder.modifiers.TankModifier;

public class AutoTrajectory {

	public static final int kEncodersTicksPerRev = 1420;
	public static final double kWheelDiameter = 0.1524;
	public static final double kP_Encoder = 1;
	public static final double kI_Encoder = 0.0;
	public static final double kD_Encoder = 0.01;
	public static final double kMaxVelocity = 1.7;
	public static final double gp = 0.02;
	public static final double gd = 0.0025;
	public static final double dt = 0.02;
	
	double angle_error = 0.0;

	@MagicInject
	SensorInput sensors;

	EncoderFollower left;
	EncoderFollower right;

	@MagicInject
	RobotOutput robotOut;

	/*
	 * Changes to implement: Read from CSV file for trajectory.
	 */

	
	public void calculateTrajectory(AutonomousChoice startingLocation, String scaleSide) {
		this.angle_error=0.0;
		sensors.ahrs.reset();
		File myFile;
		if (startingLocation == AutonomousChoice.AutoModeLeftScale && scaleSide.charAt(1) == 'R') {
			myFile = new File("/home/lvuser/trajectories/robotLScaleR.csv");
		} else if (startingLocation == AutonomousChoice.AutoModeLeftScale && scaleSide.charAt(1) == 'L') {
			myFile = new File("/home/lvuser/trajectories/robotLScaleL.csv");
		} else if (startingLocation == AutonomousChoice.AutoModeMiddleScale && scaleSide.charAt(1) == 'R') {
			myFile = new File("/home/lvuser/trajectories/robotMScaleR.csv");
		} else if (startingLocation == AutonomousChoice.AutoModeMiddleScale && scaleSide.charAt(1) == 'L') {
			myFile = new File("/home/lvuser/trajectories/robotMScaleL.csv");
		} else if (startingLocation == AutonomousChoice.AutoModeRightScale && scaleSide.charAt(1) == 'R') {
			myFile = new File("/home/lvuser/trajectories/robotRScaleR.csv");
		} else if (startingLocation == AutonomousChoice.AutoModeRightScale && scaleSide.charAt(1) == 'L') {
			myFile = new File("/home/lvuser/trajectories/robotRScaleL.csv");
		}
		
		else if (startingLocation == AutonomousChoice.AutoModeLeftSwitch && scaleSide.charAt(0) == 'R') {
			myFile = new File("/home/lvuser/trajectories/robotLSwitchR.csv");
		} else if (startingLocation == AutonomousChoice.AutoModeLeftSwitch && scaleSide.charAt(0) == 'L') {
			myFile = new File("/home/lvuser/trajectories/robotLSwitchL.csv");
		} else if (startingLocation == AutonomousChoice.AutoModeMiddleSwitch && scaleSide.charAt(0) == 'R') {
			myFile = new File("/home/lvuser/trajectories/robotMSwitchR.csv");
		} else if (startingLocation == AutonomousChoice.AutoModeMiddleSwitch && scaleSide.charAt(0) == 'L') {
			myFile = new File("/home/lvuser/trajectories/robotMSwitchL.csv");
		} else if (startingLocation == AutonomousChoice.AutoModeRightSwitch && scaleSide.charAt(0) == 'R') {
			myFile = new File("/home/lvuser/trajectories/robotRSwitchR.csv");
		} else if (startingLocation == AutonomousChoice.AutoModeRightSwitch && scaleSide.charAt(0) == 'L') {
			myFile = new File("/home/lvuser/trajectories/robotRSwitchL.csv");
		}
		
		else {
			myFile = new File("/home/lvuser/trajectories/default.csv");
		}
		
		System.out.println("Using trajectory " + myFile.getAbsolutePath());
		
		Trajectory trajectory = Pathfinder.readFromCSV(myFile);

		// Modifies trajectory based on how wide the wheels are.
		// Wheelbase Width = 0.6048375
		TankModifier modifier = new TankModifier(trajectory).modify(0.6048375);

		// Creates two EncoderFollower objects for the left and the right side of the
		// robot.
		left = new EncoderFollower(modifier.getLeftTrajectory());
		left.configureEncoder(robotOut.getEncoderLeftSide(), AutoTrajectory.kEncodersTicksPerRev,
				AutoTrajectory.kWheelDiameter);
		left.configurePIDVA(AutoTrajectory.kP_Encoder, AutoTrajectory.kI_Encoder, AutoTrajectory.kD_Encoder,
				1 / AutoTrajectory.kMaxVelocity, 0);

		right = new EncoderFollower(modifier.getRightTrajectory());
		right.configureEncoder(robotOut.getEncoderRightSide(), AutoTrajectory.kEncodersTicksPerRev,
				AutoTrajectory.kWheelDiameter);
		right.configurePIDVA(AutoTrajectory.kP_Encoder, AutoTrajectory.kI_Encoder, AutoTrajectory.kD_Encoder,
				1 / AutoTrajectory.kMaxVelocity, 0);
	}

	public void move() {
		// Figure out how fast the left side moves and figure out how fast the right
		// side moves.
		int l_encoder = robotOut.getEncoderLeftSide();
		int r_encoder = robotOut.getEncoderRightSide();
		
		Segment lSegment = left.getSegment();
		Segment rSegment = right.getSegment();
		
		double outputLeftEncoder = left.calculate(l_encoder); 
		double outputRightEncoder = right.calculate(r_encoder);
		// Calculates the difference between our current angle and the desired angle.
		double ang = -sensors.ahrs.getAngle(); // Assuming the gyro is giving a value in degrees
		double desired_heading = Pathfinder.r2d(left.getHeading()); // Should also be in degrees
		double angleDifference = Pathfinder.boundHalfDegrees(desired_heading - ang);

		// Interpolate the angleDifference (-180 to 180) to a range between (-1 and 1)
		// Multiply that again by a factor of 0.8 to slow it down a bit.
	//	double turn = 0.8 * (-1.0 / 80.0) * angleDifference;
		double turn = AutoTrajectory.gp * angleDifference + 
				(AutoTrajectory.gd *((angleDifference-this.angle_error)
						/AutoTrajectory.dt));
		angle_error = angleDifference;
		robotOut.setDriveLeft(outputLeftEncoder - turn);
		robotOut.setDriveRight(outputRightEncoder +turn);
       	SmartDashboard.putNumber("outputLeftEncoder", outputLeftEncoder);
       	SmartDashboard.putNumber("outputRightEncoder", outputRightEncoder);
       	SmartDashboard.putNumber("turn", turn);
       	
       	double l_distance_covered = ((double)(l_encoder - 0) / kEncodersTicksPerRev)
                * Math.PI * kWheelDiameter;
       	double r_distance_covered = ((double)(r_encoder - 0) / kEncodersTicksPerRev)
                * Math.PI * kWheelDiameter;
       	
        SmartDashboard.putNumberArray("pfdebug", new double[] {
        		Timer.getFPGATimestamp(),
        		
        		outputLeftEncoder,
        		l_encoder,
        		l_distance_covered,
        		lSegment.position,
        		lSegment.velocity,
        		
        		outputRightEncoder,
        		r_encoder,
        		r_distance_covered,
        		rSegment.position,
        		rSegment.velocity,
        		
        		ang,
        		desired_heading,
        		
        		lSegment.x,
        		lSegment.y,
        		rSegment.x,
        		rSegment.y,
        });
       	
       	
       	SmartDashboard.updateValues();
       	LiveWindow.updateValues();

	}

	public boolean isFinished() {
		if (left.isFinished() || right.isFinished()) {
			left.reset();
			right.reset();
			return true;
		} else {
			return false;
		}
	}
}