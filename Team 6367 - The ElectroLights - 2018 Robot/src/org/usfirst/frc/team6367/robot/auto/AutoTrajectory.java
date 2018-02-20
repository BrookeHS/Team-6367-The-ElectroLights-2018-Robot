
package org.usfirst.frc.team6367.robot.auto;

import org.usfirst.frc.team6367.robot.Robot.AutonomousChoice;
import org.usfirst.frc.team6367.robot.io.RobotOutput;
import org.usfirst.frc.team6367.robot.io.SensorInput;

import io.github.robotpy.magicbot.MagicInject;
import jaci.pathfinder.Pathfinder;
import jaci.pathfinder.Trajectory;
import jaci.pathfinder.Waypoint;
import jaci.pathfinder.followers.EncoderFollower;
import jaci.pathfinder.modifiers.TankModifier;

public class AutoTrajectory {

	public static final int kEncodersTicksPerRev = 1024;
	public static final double kWheelDiameter = 0.1524;
	public static final double kP_Encoder = 0.8;
	public static final double kI_Encoder = 0.0;
	public static final double kD_Encoder = 0.0;
	public static final double kMaxVelocity = 1.7;

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

		Trajectory.Config config = new Trajectory.Config(Trajectory.FitMethod.HERMITE_CUBIC,
				Trajectory.Config.SAMPLES_HIGH, 0.02, AutoTrajectory.kMaxVelocity, 2.0, 60.0);

		/*
		 * Determine waypoints based on robot starting location and the side with the
		 * scale. Location -> left and Scale -> left. Move forward and drop cube.
		 * Location -> left and Scale -> right. Move forward, turn right, move forward,
		 * and then drop cube. Location -> middle and Scale -> left. Turn left, move
		 * forward, turn right, move forward, drop cube. Location -> middle and Scale ->
		 * right. Turn right, move forward, turn left, move forward, drop cube. Location
		 * -> right and Scale -> left. Move forward and drop cube. Location -> right and
		 * Scale -> right. Move forward, turn left, move forward, and then drop cube.
		 */
		Waypoint[] points = null;
		if (startingLocation == AutonomousChoice.AutoModeLeft && scaleSide.charAt(1) == 'R') {
			points = new Waypoint[] { new Waypoint(0, 0, 0), new Waypoint(27, 0, Pathfinder.d2r(-90)),
					new Waypoint(27, -2.5, Pathfinder.d2r(-90)), };
		} else if (startingLocation == AutonomousChoice.AutoModeLeft && scaleSide.charAt(1) == 'L') {
			points = new Waypoint[] { new Waypoint(0, 0, 0), new Waypoint(21.5, 0, Pathfinder.d2r(-90)),
					new Waypoint(21.5, -19, 0), new Waypoint(27, -19, Pathfinder.d2r(90)),
					new Waypoint(27, -17, Pathfinder.d2r(90)), };
		}

		else if (startingLocation == AutonomousChoice.AutoModeMiddle && scaleSide.charAt(1) == 'R') {
			points = new Waypoint[] { new Waypoint(0, 0, 0), new Waypoint(6.5, 0, Pathfinder.d2r(90)),
					new Waypoint(6.5, 12, 0), new Waypoint(27, 12, Pathfinder.d2r(-90)),
					new Waypoint(27, 9.5, Pathfinder.d2r(-90)), };
		} else if (startingLocation == AutonomousChoice.AutoModeMiddle && scaleSide.charAt(1) == 'L') {
			points = new Waypoint[] { new Waypoint(0, 0, 0), new Waypoint(6.5, 0, Pathfinder.d2r(-90)),
					new Waypoint(6.5, -12, 0), new Waypoint(27, -12, Pathfinder.d2r(90)),
					new Waypoint(27, -9.5, Pathfinder.d2r(90)), };
		}

		else if (startingLocation == AutonomousChoice.AutoModeRight && scaleSide.charAt(1) == 'R') {
			points = new Waypoint[] { new Waypoint(0, 0, 0), new Waypoint(21.5, 0, Pathfinder.d2r(90)),
					new Waypoint(21.5, 19, 0), new Waypoint(27, 19, Pathfinder.d2r(-90)),
					new Waypoint(27, 17, Pathfinder.d2r(-90)), };
		} else if (startingLocation == AutonomousChoice.AutoModeRight && scaleSide.charAt(1) == 'L') {
			points = new Waypoint[] { new Waypoint(0, 0, 0), new Waypoint(27, 0, Pathfinder.d2r(90)),
					new Waypoint(27, 2.5, Pathfinder.d2r(90)), };
		}

		// After you generate the waypoints, define the trajectory.
		Trajectory trajectory = Pathfinder.generate(points, config);

		// Modifies trajectory based on how wide the wheels are.
		// Wheelbase Width = 0.5969
		TankModifier modifier = new TankModifier(trajectory).modify(0.5969);

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
		double outputLeftEncoder = left.calculate(robotOut.getEncoderLeftSide()); // incorrect
		double outputRightEncoder = right.calculate(robotOut.getEncoderRightSide()); // incorrect

		// Calculates the difference between our current angle and the desired angle.
		double ang = sensors.ahrs.getAngle(); // Assuming the gyro is giving a value in degrees
		double desired_heading = Pathfinder.r2d(left.getHeading()); // Should also be in degrees
		double angleDifference = Pathfinder.boundHalfDegrees(desired_heading - ang);

		// Interpolate the angleDifference (-180 to 180) to a range between (-1 and 1)
		// Multiply that again by a factor of 0.8 to slow it down a bit.
		double turn = 0.8 * (-1.0 / 180.0) * angleDifference;

		//
		robotOut.setDriveLeft(outputLeftEncoder + turn);
		robotOut.setDriveRight(outputRightEncoder - turn);
	}

}