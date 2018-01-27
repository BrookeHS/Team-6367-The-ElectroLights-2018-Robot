package org.usfirst.frc.team6367.robot.teleop;

import org.usfirst.frc.team6367.robot.LightDrive.LightDrive;
import org.usfirst.frc.team6367.robot.io.DriverInput;

import edu.wpi.first.wpilibj.Joystick;

public class TeleopControl {
	
	private static TeleopControl instance;
	DriverInput humanDriver;
	LightDrive lightDrive;
	
	private TeleopControl() {
		humanDriver = DriverInput.getInstance();
		lightDrive = LightDrive.getInstance();
	}
	
	public static TeleopControl getInstance() {
		if(instance==null) {
			instance = new TeleopControl();
		}
		return instance;
	}
	
	public void teleopTasks() {
		Joystick a = humanDriver.getDriverStick();
		if(a.getTrigger()==true) {
			lightDrive.driveStraight();
		}
		else {
			lightDrive.robotOut.arcadeDrive(a);
		}
	}
	
	
}
