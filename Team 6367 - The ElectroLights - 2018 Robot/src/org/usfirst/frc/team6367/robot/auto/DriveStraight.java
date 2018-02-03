package org.usfirst.frc.team6367.robot.auto;

import org.usfirst.frc.team6367.robot.LightDrive.LightDrive;

import edu.wpi.first.wpilibj.Encoder;

/*
Drive Straight( Kelvin and Janeya)
	1.Drive Straight
	2.Stop
   	- After N Seconds
   	- Or after distance
 */

public class DriveStraight implements Runnable {
	LightDrive lightdrive1;
	Encoder enc;
	


	public void driveStraight() {
		lightdrive1 = LightDrive.getInstance();
		lightdrive1.driveStraight();
	}
	
	public void stop() {
		lightdrive1.stop();
	}
	

	public void run() {
		enc = new Encoder(0,1,false,Encoder.EncodingType.k4X);
		double distance = enc.getDistance();
		lightdrive1.driveDistance(distance);
	}
}
