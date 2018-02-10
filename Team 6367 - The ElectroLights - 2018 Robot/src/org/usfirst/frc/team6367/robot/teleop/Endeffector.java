package org.usfirst.frc.team6367.robot.teleop;

public class Endeffector {
	private static Endeffector instance;
	public void  deployBox() {
		
	}
	public void Intake() {
		
	}
	public void Stop() {
		
	}
	public static Endeffector getInstance() {
		if(instance==null) {
			instance = new Endeffector();
		}
		return instance;
	}
	}

