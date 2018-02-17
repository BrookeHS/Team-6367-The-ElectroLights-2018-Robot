package org.usfirst.frc.team6367.robot.teleop;

import org.usfirst.frc.team6367.robot.io.RobotOutput;

import io.github.robotpy.magicbot.MagicInject;

public class Elevator {
	
	@MagicInject
	RobotOutput RobotOut;
	
	public final static double  kswitch = 1.25;
	public final static double  kscale  = 6.1;
	public final static double kground = 0;
	
	public void goToSwitch() {
	// make it rise 1.25 feet
		RobotOut.setElevator(kswitch);
	}
	
	public void goToGround() {
		RobotOut.setElevator(kground);
	}
	
	public void goToScale() {	
	// make it rise about 6.1 feet
		RobotOut.setElevator(kscale);
	}
}
