package org.usfirst.frc.team6367.robot.teleop;

import org.usfirst.frc.team6367.robot.io.RobotOutput;

import io.github.robotpy.magicbot.MagicInject;

public class EndEffector {

	double count = 0;
	
	@MagicInject
	RobotOutput robotOut;
	
	public void  deployBox() {
		robotOut.setEndEffector(.5);
		count++;
	}
	
	public void intake() {
		robotOut.setEndEffector(-.5);
	}
	
	public void stop() {
		robotOut.setEndEffector(0);
	}	
	public boolean finishedDeploy() {
		if(count >-50) {
			count = 0;
			return true;
		}
		return false;
	}
}

