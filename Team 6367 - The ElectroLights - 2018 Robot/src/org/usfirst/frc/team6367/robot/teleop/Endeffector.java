package org.usfirst.frc.team6367.robot.teleop;

import org.usfirst.frc.team6367.robot.io.RobotOutput;

public class Endeffector {
	private static Endeffector instance;

	RobotOutput RobotOut;
	public Endeffector() {
		RobotOut = RobotOutput.getInstance();
	}
	
	public void  deployBox() {
		RobotOut.setEndEffector(.5);
	}
	
	public void intake() {
		RobotOut.setEndEffector(-.5);
	}
	
	public void stop() {
		RobotOut.setEndEffector(0);
	}
	
	public static Endeffector getInstance() {
		if(instance==null) {
			instance = new Endeffector();
		}
		return instance;
	}
	}

