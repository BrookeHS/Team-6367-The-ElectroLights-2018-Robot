package org.usfirst.frc.team6367.robot.io;

import edu.wpi.first.wpilibj.Joystick;

public class DriverInput {

	private Joystick driverStick;
	private Joystick mechanismStick;
	
	
	public DriverInput() {
		driverStick = new Joystick(0);
		mechanismStick = new Joystick(1);
	}
	
	public Joystick getDriverStick() {
		return driverStick;
	}
	
	public Joystick getMechanismStick() {
		return mechanismStick;
	}
	
}
