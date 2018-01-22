package org.usfirst.frc.team6367.robot.io;

import edu.wpi.first.wpilibj.Joystick;

public class DriverInput {

	private Joystick driverStick;
	private Joystick mechanismStick;
	
	private static DriverInput instance;
	
	private DriverInput() {
		driverStick = new Joystick(0);
		mechanismStick = new Joystick(1);
	}
	
	public static DriverInput getInstance() {
		if(instance==null) {
			instance = new DriverInput();
		}
		return instance;
	}
	
	public Joystick getDriverStick() {
		return driverStick;
	}
	
}
