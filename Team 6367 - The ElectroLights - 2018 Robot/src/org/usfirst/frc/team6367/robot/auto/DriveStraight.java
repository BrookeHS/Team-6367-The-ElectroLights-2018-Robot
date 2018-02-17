package org.usfirst.frc.team6367.robot.auto;

import org.usfirst.frc.team6367.robot.io.RobotOutput;

import io.github.robotpy.magicbot.MagicAutonomous;
import io.github.robotpy.magicbot.MagicInject;

/*
Drive Straight( Kelvin and Janeya)
	1.Drive Straight
	2.Stop
   	- After N Seconds
   	- Or after distance
 */

public class DriveStraight implements MagicAutonomous {
	
	@MagicInject
	RobotOutput robotOut;
	
	double startingPos;
	
	
	@Override
	public void onEnabled() {
		robotOut.resetDriveEncoders();
	}
	
	@Override
	public void autonomousPeriodic() {
		robotOut.driveDistance(15);
	}
}
