package org.usfirst.frc.team6367.robot.teleop;

import org.usfirst.frc.team6367.robot.io.RobotOutput;

public class Elevator {
	RobotOutput RobotOut;
	private static Elevator instance;
	public Elevator() {
		RobotOut = RobotOutput.getInstance();
	}
	
	public void goToSwitch() {
		
	}
	
	public void goToGround() {
		
	}
	
	public void goToScale() {
		
	}
	

	public static Elevator getInstance() {
		if(instance==null) {
			instance = new Elevator();
		}
		return instance;
	}


}
