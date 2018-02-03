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
	Encoder encode;
	double startingPos;
// constructor
	public DriveStraight(){
		lightdrive1 = LightDrive.getInstance();
		encode = lightdrive1.enc;// the encoder
		startingPos = encode.get();
	}
	public void run() {
		lightdrive1.driveDistance(encode.get() - startingPos, 15);
	}
}
