package org.usfirst.frc.team6367.robot.teleop;

import org.usfirst.frc.team6367.robot.io.RobotOutput;

import com.ctre.phoenix.motorcontrol.ControlMode;

import io.github.robotpy.magicbot.MagicInject;
public class Elevator {
	
	@MagicInject
	RobotOutput robotOut;
	
	public final static double  kswitch = 1.25;
	public final static double  kscale  = 6.1;
	public final static double kground = 0;
	
	public void upPosition() {
		robotOut.elevator.set(ControlMode.Position, -20.0*(1024));
	}
	public void downPosition() {
		robotOut.elevator.set(ControlMode.Position, 0);
	}
	public void midPosition() {
		robotOut.elevator.set(ControlMode.Position, -14*(1024));		
	}
	
	public boolean upFinished() {
		return robotOut.elevator.get() == 0;
	}
}
