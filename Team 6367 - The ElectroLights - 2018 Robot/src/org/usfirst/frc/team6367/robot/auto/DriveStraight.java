package org.usfirst.frc.team6367.robot.auto;

import org.usfirst.frc.team6367.robot.io.RobotOutput;

/*
Drive Straight( Kelvin and Janeya)
	1.Drive Straight
	2.Stop
   	- After N Seconds
   	- Or after distance
 */

public class DriveStraight implements Runnable {
	RobotOutput robotOut;
	double startingPos;
// constructor
	  
	public DriveStraight(){
		robotOut = RobotOutput.getInstance();
		robotOut.resetDriveEncoders();
	}
	public void run() {
		robotOut.driveDistance(15);
	}
}
