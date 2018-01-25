package org.usfirst.frc.team6367.robot.auto;

public class AutoControl {
	
	private static AutoControl instance;
	
	private AutoControl() {
		
	}
	
	public static AutoControl getInstance() {
		if(instance==null) {
			instance = new AutoControl();
		}
		return instance;
	}
	
}
