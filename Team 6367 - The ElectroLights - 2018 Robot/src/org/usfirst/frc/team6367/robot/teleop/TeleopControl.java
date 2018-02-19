package org.usfirst.frc.team6367.robot.teleop;

import org.usfirst.frc.team6367.robot.LightDrive.LightDrive;
import org.usfirst.frc.team6367.robot.io.DriverInput;
import org.usfirst.frc.team6367.robot.io.RobotOutput;

import edu.wpi.first.wpilibj.Joystick;
import io.github.robotpy.magicbot.MagicInject;

public class TeleopControl {
	
	@MagicInject
	RobotOutput robotOut;
	
	@MagicInject
	DriverInput humanDriver;
	
	@MagicInject
	LightDrive lightDrive;
	
	@MagicInject
	Elevator elevator;
	
	@MagicInject
	Endeffector endEffector;
	
	
	public void teleopTasks() {
		Joystick a = humanDriver.getDriverStick();
		if(a.getRawButton(5)) {
			lightDrive.driveStraight();
		} else {
			lightDrive.turnController.disable();
			robotOut.arcadeDrive(a);
		}
       	if(a.getTrigger()){
    		endEffector.deployBox();
       	} else if(a.getRawButton(2)){
            endEffector.intake();
    	}else{
      		endEffector.stop();
        }
       	if(a.getRawButton(5)) {
       		elevator.goUp();
       	} else if(a.getRawButton(8)) {
       		elevator.goDown();
       	}
       	else {
       		elevator.stop();
       	}
	}
}
	
	

