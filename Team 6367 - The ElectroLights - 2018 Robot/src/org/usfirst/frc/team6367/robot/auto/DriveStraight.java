package org.usfirst.frc.team6367.robot.auto;

import org.usfirst.frc.team6367.robot.LightDrive.LightDrive;

/*
Drive Straight( Kelvin and Janeya)
	1.Drive Straight
	2.Stop
   	- After N Seconds
   	- Or after distance
 */

public class DriveStraight {
	LightDrive lightdrive1;
	public void driveStraight() {
		lightdrive1 = LightDrive.getInstance();
		lightdrive1.driveStraight();
	}
	public void stop() {
		lightdrive1.stop();
	}
}
