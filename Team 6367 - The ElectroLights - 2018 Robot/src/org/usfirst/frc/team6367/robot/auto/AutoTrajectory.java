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

	public static final int kENCODERTICKSPERREV = 1024;
	public static final double kWHEELDIAMETER = 0.1524;
	public static final double kP_ENCODER = 0.8;
	public static final double kI_ENCODER = 0.0;
	public static final double kD_ENCODER = 0.0;
	public static final double kMAXVELOCITY = 1.7;

	EncoderFollower left;
	EncoderFollower right;

	@MagicInject
	SensorInput sensors;

	@MagicInject
	RobotOutput robotOut;

	public void calculateTrajectory(int startingLocation, boolean scaleSide) {

		Trajectory.Config config = new Trajectory.Config(Trajectory.FitMethod.HERMITE_CUBIC,
				Trajectory.Config.SAMPLES_HIGH, 0.02, AutoTrajectory.kMAXVELOCITY, 2.0, 60.0);

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
		Waypoint[] points;
		if (startingLocation == 0 && scaleSide == true) {
			points = new Waypoint[] { new Waypoint(-4, -1, Pathfinder.d2r(-45)), new Waypoint(-2, -2, 0),
					new Waypoint(0, 0, 0) };
		} else if (startingLocation == 0 && scaleSide == false) {
			points = new Waypoint[] { new Waypoint(-4, -1, Pathfinder.d2r(-45)), new Waypoint(-2, -2, 0),
					new Waypoint(0, 0, 0) };
		} else if (startingLocation == 1 && scaleSide == true) {
			points = new Waypoint[] { new Waypoint(-4, -1, Pathfinder.d2r(-45)), new Waypoint(-2, -2, 0),
					new Waypoint(0, 0, 0) };
		} else if (startingLocation == 1 && scaleSide == false) {
			points = new Waypoint[] { new Waypoint(-4, -1, Pathfinder.d2r(-45)), new Waypoint(-2, -2, 0),
					new Waypoint(0, 0, 0) };
		} else if (startingLocation == 2 && scaleSide == true) {
			points = new Waypoint[] { new Waypoint(-4, -1, Pathfinder.d2r(-45)), new Waypoint(-2, -2, 0),
					new Waypoint(0, 0, 0) };
		} else if (startingLocation == 2 && scaleSide == false) {
			points = new Waypoint[] { new Waypoint(-4, -1, Pathfinder.d2r(-45)), new Waypoint(-2, -2, 0),
					new Waypoint(0, 0, 0) };
		} else {
			points = new Waypoint[] { new Waypoint(-4, -1, Pathfinder.d2r(-45)), new Waypoint(-2, -2, 0),
					new Waypoint(0, 0, 0) };
		}

		// After you generate the waypoints, define the trajectory.
		Trajectory trajectory = Pathfinder.generate(points, config);

		// Modifies trajectory based on how wide the wheels are.
		// Wheelbase Width = 0.5969
		TankModifier modifier = new TankModifier(trajectory).modify(0.5969);

		// Creates two EncoderFollower objects for the left and the right side of the
		// robot.
		left = new EncoderFollower(modifier.getLeftTrajectory());
		left.configureEncoder(robotOut.getEncoderLeftSide(), AutoTrajectory.kENCODERTICKSPERREV,
				AutoTrajectory.kWHEELDIAMETER);
		left.configurePIDVA(AutoTrajectory.kP_ENCODER, AutoTrajectory.kI_ENCODER, AutoTrajectory.kD_ENCODER,
				1 / AutoTrajectory.kMAXVELOCITY, 0);

		right = new EncoderFollower(modifier.getRightTrajectory());
		right.configureEncoder(robotOut.getEncoderRightSide(), AutoTrajectory.kENCODERTICKSPERREV,
				AutoTrajectory.kWHEELDIAMETER);
		right.configurePIDVA(AutoTrajectory.kP_ENCODER, AutoTrajectory.kI_ENCODER, AutoTrajectory.kD_ENCODER,
				1 / AutoTrajectory.kMAXVELOCITY, 0);
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