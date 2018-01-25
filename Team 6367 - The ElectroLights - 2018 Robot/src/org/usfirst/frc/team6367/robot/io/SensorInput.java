package org.usfirst.frc.team6367.robot.io;

public class SensorInput {

	private static SensorInput instance;
	
	private SensorInput() {

	}
	
	public static SensorInput getInstance() {
		if(instance==null) {
			instance = new SensorInput();
		}
		return instance;
	}
	
}
