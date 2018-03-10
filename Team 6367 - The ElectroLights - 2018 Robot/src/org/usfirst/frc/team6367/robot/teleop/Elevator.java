package org.usfirst.frc.team6367.robot.teleop;

import org.usfirst.frc.team6367.robot.io.RobotOutput;

import com.ctre.phoenix.motorcontrol.ControlMode;

import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import io.github.robotpy.magicbot.MagicInject;
public class Elevator {
	
	@MagicInject
	RobotOutput robotOut;
	
	public final static double  kswitch = 1.25;
	public final static double  kscale  = 6.1;
	public final static double kground = 0;
	
	public void upPosition() {
		robotOut.elevator.set(ControlMode.Position, -20.4*(1024));
	}
	public void downPosition() {
		robotOut.elevator.set(ControlMode.Position, 0);
	}
	public void midPosition() {
		robotOut.elevator.set(ControlMode.Position, -14*(1024));		
	}
	
	public boolean upFinished() {
		return Math.abs(robotOut.elevator.getClosedLoopError(0)) <= 300
				&& Math.abs(robotOut.elevator.getClosedLoopError(0)) >= 70;
	}
}
