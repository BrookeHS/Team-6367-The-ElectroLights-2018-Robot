package org.usfirst.frc.team6367.robot.LightDrive;

public class LightDrive {

	private static LightDrive instance;
	
	private LightDrive() {
		
	}
	
	public static LightDrive getInstance() {
		if(instance==null) {
			instance = new LightDrive();
		}
		return instance;
	}
	
	public void driveStraight() {
		
		
	}
	
	public void rotateToAngle(double targetAngle) {
		
		
	}
	
	public void zeroYaw() {
		
	}
	
	public void ninetyYaw() {
		
	}
}
