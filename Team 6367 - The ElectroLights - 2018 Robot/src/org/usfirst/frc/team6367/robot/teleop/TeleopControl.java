package org.usfirst.frc.team6367.robot.teleop;

import org.usfirst.frc.team6367.robot.LightDrive.LightDrive;
import org.usfirst.frc.team6367.robot.io.DriverInput;
import org.usfirst.frc.team6367.robot.io.RobotOutput;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Servo;
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
	
	
	
	
	
	public void teleopTasks() {
		Joystick a = driverIn.getDriverStick();
/*		if(a.getRawButton(6)) {
			lightDrive.driveStraight();
		} else {
			lightDrive.turnController.disable();
			robotOut.arcadeDrive(a);
		}*/
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
       		robotOut.turnServo(0.0);
       	}  	
       	else if(a.getRawButton(8)) {
       		robotOut.turnServo(0.99);
       	}  	
       	robotOut.arcadeDrive(a);
	}
}
	
	

