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
		robotOut.elevator.set(ControlMode.Position, 400);
	}
	public void downPosition() {
		robotOut.elevator.set(ControlMode.Position, 0);
	}
	public void midPosition() {
		robotOut.elevator.set(ControlMode.Position, 200);		
	}
	
	public void goUp() {
		robotOut.elevator.set(1);
	}
	
	public void goDown() {
		robotOut.elevator.set(-.8);
	}
<<<<<<< HEAD
	
	public void stop() {
		robotOut.elevator.set(0);
	}
	public boolean upFinished() {
=======
	  public boolean upFinished() {
>>>>>>> parent of c4e9fa7... Bug Fixes to Elevator
		return robotOut.elevator.get() == 0;
	}
}
