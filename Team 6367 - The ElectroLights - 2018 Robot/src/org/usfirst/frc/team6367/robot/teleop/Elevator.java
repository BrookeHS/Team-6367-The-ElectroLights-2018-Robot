package org.usfirst.frc.team6367.robot.teleop;

import org.usfirst.frc.team6367.robot.io.RobotOutput;

import io.github.robotpy.magicbot.MagicInject;

public class Elevator {
	
	@MagicInject
	RobotOutput RobotOut;
	
	public final static double  kswitch = 1.25;
	public final static double  kscale  = 6.1;
	public final static double kground = 0;
	
public void goDown() {
	// make it rise 1.25 feet
		RobotOut.elevator.set(.5);
	}
	
	public void goUp() {
		RobotOut.elevator.set(-.5);
	}
	
	public void stop() {
		RobotOut.elevator.set(0);
	}
}
