package org.usfirst.frc.team6367.robot.teleop;

import org.usfirst.frc.team6367.robot.io.RobotOutput;

import io.github.robotpy.magicbot.MagicInject;

public class Elevator {
	
	@MagicInject
	RobotOutput robotOut;
	
	public final static double  kswitch = 1.25;
	public final static double  kscale  = 6.1;
	public final static double kground = 0;
	
	public void goUp() {
		robotOut.elevator.set(1);
	}
	
	public void goDown() {
		robotOut.elevator.set(-.8);
	}
	
	public void stop() {
		robotOut.elevator.set(0);
	}
}
