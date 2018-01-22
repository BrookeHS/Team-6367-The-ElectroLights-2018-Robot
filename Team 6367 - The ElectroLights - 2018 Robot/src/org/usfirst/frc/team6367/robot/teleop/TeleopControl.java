package org.usfirst.frc.team6367.robot.teleop;

public class TeleopControl {
	private static TeleopControl instance;
	
	private TeleopControl() {
		
	}
	
	public static TeleopControl getInstance() {
		if(instance==null) {
			instance = new TeleopControl();
		}
		return instance;
	}
	
}
