package org.usfirst.frc.team6367.robot.LightDrive;

import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDOutput;

public class LightDrive implements PIDOutput{

	private static LightDrive instance;
	
	PIDController turnController;
	double rotateToAngleRate;
	
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
	
	@Override
	public void pidWrite(double output) {
		rotateToAngleRate = output;
	}
}
