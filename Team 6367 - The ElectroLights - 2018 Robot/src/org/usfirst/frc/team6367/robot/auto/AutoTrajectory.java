package org.usfirst.frc.team6367.robot.auto;

import org.usfirst.frc.team6367.robot.io.RobotOutput;
import org.usfirst.frc.team6367.robot.io.SensorInput;

import io.github.robotpy.magicbot.MagicInject;
import jaci.pathfinder.Pathfinder;
import jaci.pathfinder.Trajectory;
import jaci.pathfinder.Waypoint;
import jaci.pathfinder.followers.EncoderFollower;
import jaci.pathfinder.modifiers.TankModifier;

public class AutoTrajectory {
	 EncoderFollower left;
	  EncoderFollower right;
	  @MagicInject
		RobotOutput robotOut;
	  @MagicInject
	  SensorInput sensor;
	  
	  public void calculateTrajectory( int startingLocation, boolean scaleSide ){
	    Trajectory[] toReturn = new Trajectory[2];
			Trajectory.Config config = new Trajectory.Config(Trajectory.FitMethod.HERMITE_CUBIC, Trajectory.Config.SAMPLES_HIGH, 0.02, 1.7, 2.0, 60.0);
	    
	    /* Determine waypoints based on robot starting location and the side with
	     * the scale.
	     * Location -> left and Scale -> left.
	     *   Move forward and drop cube.
	     * Location -> left and Scale -> right.
	     *   Move forward, turn right, move forward, and then drop cube.
	     * Location -> middle and Scale -> left.
	     *   Turn left, move forward, turn right, move forward, drop cube.
	     * Location -> middle and Scale -> right.
	     *   Turn right, move forward, turn left, move forward, drop cube.
	     * Location -> right and Scale -> left.
	     *   Move forward and drop cube.
	     * Location -> right and Scale -> right.
	     *   Move forward, turn left, move forward, and then drop cube.
	     */
	    Waypoint[] points;
	    if( startingLocation==0&&scaleSide==true ){
	      points = new Waypoint[] {
	        new Waypoint(-4, -1, Pathfinder.d2r(-45)),
	        new Waypoint(-2, -2, 0),
	        new Waypoint(0, 0, 0)
	      };
	    }
	    else if(startingLocation==0&&scaleSide==false){
	      points = new Waypoint[] {
	        new Waypoint(-4, -1, Pathfinder.d2r(-45)),
	        new Waypoint(-2, -2, 0),
	        new Waypoint(0, 0, 0)
	      };
	    }
	    else if(startingLocation==1&&scaleSide==true){
	      points = new Waypoint[] {
	        new Waypoint(-4, -1, Pathfinder.d2r(-45)),
	        new Waypoint(-2, -2, 0),
	        new Waypoint(0, 0, 0)
	      };
	    }
	    else if(startingLocation==1&&scaleSide==false){
	      points = new Waypoint[] {
	        new Waypoint(-4, -1, Pathfinder.d2r(-45)),
	        new Waypoint(-2, -2, 0),
	        new Waypoint(0, 0, 0)
	      };
	    }
	    else if(startingLocation==2&&scaleSide==true ){
	      points = new Waypoint[] {
	        new Waypoint(-4, -1, Pathfinder.d2r(-45)),
	        new Waypoint(-2, -2, 0),
	        new Waypoint(0, 0, 0)
	      };
	    }
	    else if(startingLocation==2&&scaleSide==false ){
	      points = new Waypoint[] {
	        new Waypoint(-4, -1, Pathfinder.d2r(-45)),
	        new Waypoint(-2, -2, 0),
	        new Waypoint(0, 0, 0)
	      };
	    }
	    else{
	        points = new Waypoint[] {
	        new Waypoint(-4, -1, Pathfinder.d2r(-45)),
	        new Waypoint(-2, -2, 0),
	        new Waypoint(0, 0, 0)
	      };
	    }

	    Trajectory trajectory = Pathfinder.generate(points, config);

	    // Wheelbase Width = 0.5969m
	    TankModifier modifier = new TankModifier(trajectory).modify(0.5969);

	    // Visit : https://github.com/JacisNonsense/Pathfinder/wiki/Pathfinder-for-FRC---Java
	    // Figure out to setup encoders.
	   	EncoderFollower left = new EncoderFollower(modifier.getLeftTrajectory());
			EncoderFollower right = new EncoderFollower(modifier.getRightTrajectory());
		  left.configureEncoder(robotOut.getEncoderLeftSide(), 1000, .1524);
	    right.configureEncoder(robotOut.getEncoderRightSide(), 1000, .1524);
			left.configurePIDVA(1.0, 0.0, 0.0, 1 / 1.7, 0);
	  	right.configurePIDVA(1.0, 0.0, 0.0, 1 / 1.7, 0);
	  	double outputLeftEncoder = left.calculate(robotOut.getEncoderLeftSide());
	  	double outputRightEncoder = right.calculate(robotOut.getEncoderRightSide());
	  	double l = left.calculate(robotOut.getEncoderLeftSide());
		double r = right.calculate(robotOut.getEncoderRightSide());

		double ang = sensor.ahrs.getAngle(); // Assuming the gyro is giving a value in degrees
		double desired_heading = Pathfinder.r2d(left.getHeading());  // Should also be in degrees

		double angleDifference = Pathfinder.boundHalfDegrees(desired_heading - ang);
	  double turn = 0.8 * (-1.0/80.0) * angleDifference;

	robotOut.setDriveLeft(l + turn);
	robotOut.setDriveRight(r - turn);
	  }
	  
	  
	}