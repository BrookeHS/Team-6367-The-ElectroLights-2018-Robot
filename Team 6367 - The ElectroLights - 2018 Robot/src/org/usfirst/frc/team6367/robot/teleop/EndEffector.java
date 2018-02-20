package org.usfirst.frc.team6367.robot.teleop;

import org.usfirst.frc.team6367.robot.io.RobotOutput;

import io.github.robotpy.magicbot.MagicInject;

public class EndEffector {

	@MagicInject
	RobotOutput robotOut;
	
	public void  deployBox() {
		robotOut.setEndEffector(.5);
	}
	
	public void intake() {
		robotOut.setEndEffector(-.5);
	}
	
	public void stop() {
		robotOut.setEndEffector(0);
	}	
}

