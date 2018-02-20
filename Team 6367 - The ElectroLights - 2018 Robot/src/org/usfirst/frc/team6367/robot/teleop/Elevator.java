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
<<<<<<< HEAD
		robotOut.elevator.set(ControlMode.Position, 400);
=======
		robotOut.elevator.set(ControlMode.Position, 7*(1024));
>>>>>>> parent of f769174... Elevator Final Position and PID Values
	}
	public void downPosition() {
		robotOut.elevator.set(ControlMode.Position, 0);
		robotOut.elevator.setSelectedSensorPosition(0, 0, 0);
	}
	public void midPosition() {
<<<<<<< HEAD
		robotOut.elevator.set(ControlMode.Position, 200);		
=======
		robotOut.elevator.set(ControlMode.Position, 4*(1024));		
>>>>>>> parent of f769174... Elevator Final Position and PID Values
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
