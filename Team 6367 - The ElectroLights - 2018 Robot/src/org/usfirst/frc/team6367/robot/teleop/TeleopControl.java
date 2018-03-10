package org.usfirst.frc.team6367.robot.teleop;

import org.usfirst.frc.team6367.robot.LightDrive.LightDrive;
import org.usfirst.frc.team6367.robot.Robot.AutonomousChoice;
import org.usfirst.frc.team6367.robot.auto.AutoTrajectory;
import org.usfirst.frc.team6367.robot.io.DriverInput;
import org.usfirst.frc.team6367.robot.io.RobotOutput;

import com.ctre.phoenix.motorcontrol.ControlMode;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Servo;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import io.github.robotpy.magicbot.MagicInject;

public class TeleopControl {
	
	@MagicInject
	RobotOutput robotOut;
	
	@MagicInject
	DriverInput driverIn;
	
//	@MagicInject
//	LightDrive lightDrive;
	
	@MagicInject
	Elevator elevator;
	
	@MagicInject
	EndEffector endEffector;
	
	@MagicInject
	AutoTrajectory autoTrajectory;
	

	public void teleopTasks() {
		Joystick a = driverIn.getDriverStick();
       	if(a.getTrigger()){
    		endEffector.deployBox();
       	} else if(a.getRawButton(2)){
            endEffector.intake();
    	}else{
      		endEffector.stop();
        }
       	if(a.getRawButton(5)) {
       		elevator.upPosition();
       	} else if(a.getRawButton(3)) {
       		elevator.midPosition();
       	}
       	else if(a.getRawButton(4)){
       		elevator.downPosition();
       	}
       	if(a.getRawButton(7)) {
       		elevator.latchArm();
       	}  	
       	else if(a.getRawButton(8)) {
       		elevator.unlatchArm();
       	}
       	
       	if (!DriverStation.getInstance().isFMSAttached() && a.getRawButton(11)) {
       		robotOut.elevatorMotor.set(ControlMode.PercentOutput, a.getZ());
       	}
       	
       	robotOut.arcadeDrive(a);
       	
       	SmartDashboard.putNumber("leftEncoder", robotOut.getEncoderLeftSide());
       	SmartDashboard.putNumber("rightEncoder", robotOut.getEncoderRightSide());
       	SmartDashboard.putNumber("elevatorError", robotOut.elevatorMotor.getClosedLoopError(0));
       	
       	SmartDashboard.putNumber("elevatorHeight", elevator.getPosition());
       	
       	SmartDashboard.updateValues();
       	LiveWindow.updateValues();
	}
}
	
	

