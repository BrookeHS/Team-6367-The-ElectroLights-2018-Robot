package org.usfirst.frc.team6367.robot.teleop;

import org.usfirst.frc.team6367.robot.io.RobotOutput;

import com.ctre.phoenix.motorcontrol.ControlMode;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import io.github.robotpy.magicbot.MagicComponent;
import io.github.robotpy.magicbot.MagicInject;

public class Elevator implements MagicComponent {
	
	enum LatchState {
		LATCHED,
		UNLATCHED
	}
	
	@MagicInject
	RobotOutput robotOut;
	
	public final static double  kswitch = 1.25;
	public final static double  kscale  = 6.1;
	public final static double kground = 0;
	
	LatchState latchState = LatchState.LATCHED;
	Timer latchTimer = new Timer();
	
	public void upPosition() {
		robotOut.elevatorMotor.set(ControlMode.Position, -20.4*(1024));
	}
	public void downPosition() {
		robotOut.elevatorMotor.set(ControlMode.Position, 0);
	}
	public void midPosition() {
		robotOut.elevatorMotor.set(ControlMode.Position, -14*(1024));		
	}
	
	public double getPosition() {
		return robotOut.elevatorMotor.getSensorCollection().getQuadraturePosition() * 1024.0;
	}
	
	public boolean upFinished() {
		return Math.abs(robotOut.elevatorMotor.getClosedLoopError(0)) <= 300
				&& Math.abs(robotOut.elevatorMotor.getClosedLoopError(0)) >= 70;
	}
	
	public void unlatchArm() {
		latchState = LatchState.UNLATCHED;
	}
	
	public void latchArm() {
		latchState = LatchState.LATCHED;
	}
	
	@Override
	public void onEnabled() {
		latchTimer.start();
		latchTimer.reset();
	}
	
	@Override
	public void execute() {
		if (latchTimer.get() < 1) {
			latchState = LatchState.UNLATCHED;
		} else if (latchTimer.get() < 2) {
			latchState = LatchState.LATCHED;
		}
		
		switch (latchState) {
		case LATCHED:
			robotOut.dropServo.set(0.0);
			break;
		case UNLATCHED:
			robotOut.dropServo.set(0.99);
			break;
		}
		
	}
}
