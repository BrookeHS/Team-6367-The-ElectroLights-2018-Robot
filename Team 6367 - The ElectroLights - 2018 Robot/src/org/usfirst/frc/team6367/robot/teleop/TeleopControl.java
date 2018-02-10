package org.usfirst.frc.team6367.robot.teleop;

import org.usfirst.frc.team6367.robot.LightDrive.LightDrive;
import org.usfirst.frc.team6367.robot.io.DriverInput;
import edu.wpi.first.wpilibj.Joystick;

public class TeleopControl {
	
	private static TeleopControl instance;
	DriverInput humanDriver;
	LightDrive lightDrive;
	Elevator elevator;
	Endeffector endEffector;
	
	private TeleopControl() {
		humanDriver = DriverInput.getInstance();
		lightDrive = LightDrive.getInstance();
		elevator = Elevator.getInstance();
		endEffector = Endeffector.getInstance();
	}
	
	public static TeleopControl getInstance() {
		if(instance==null) {
			instance = new TeleopControl();
		}
		return instance;
	}
	
	public void teleopTasks() {
		Joystick a = humanDriver.getDriverStick();
		if(a.getRawButton(5)) {
			lightDrive.driveStraight();
		} else {
			lightDrive.turnController.disable();
			lightDrive.robotOut.arcadeDrive(a);
		}
       	if(a.getTrigger()){
    		endEffector.deployBox();
       	} else if(a.getRawButton(2)){
            endEffector.intake();
    	}else{
      		endEffector.stop();
        }
       	if(a.getRawButton(6)) {
       		elevator.goToScale();
       	}
       	else {
       		
       	}
       	if(a.getRawButton(4)) {
       		elevator.goToSwitch();
       	}
       	else {
       	
       	}
       	if(a.getRawButton(3)) {
       		elevator.goToGround();
       	}
       	else {
       		
       	}
	}
	
	
}
