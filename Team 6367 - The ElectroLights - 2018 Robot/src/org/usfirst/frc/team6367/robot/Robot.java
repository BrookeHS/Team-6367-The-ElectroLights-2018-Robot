package org.usfirst.frc.team6367.robot;

import org.usfirst.frc.team6367.robot.LightDrive.LightDrive;
import org.usfirst.frc.team6367.robot.auto.DriveStraight;
import org.usfirst.frc.team6367.robot.auto.PickerMode;
import org.usfirst.frc.team6367.robot.auto.SimpleDeposit;
import org.usfirst.frc.team6367.robot.io.DriverInput;
import org.usfirst.frc.team6367.robot.io.RobotOutput;
import org.usfirst.frc.team6367.robot.io.SensorInput;
import org.usfirst.frc.team6367.robot.teleop.Elevator;
import org.usfirst.frc.team6367.robot.teleop.Endeffector;
import org.usfirst.frc.team6367.robot.teleop.TeleopControl;

import io.github.robotpy.magicbot.MagicRobot;

public class Robot extends MagicRobot {
	
	public DriverInput driverIn;	
	public Elevator elevator;
	public Endeffector endEffector;
	public LightDrive lightDrive;
	public RobotOutput robotOut;
	public SensorInput sensors;
	public TeleopControl teleopControl;
	
	private static final String kDriveStraight = "Drive Straight";
	private static final String kPicker = "Picker";
	private static final String kSimpleDeposit = "Simple Deposit";
	

	// Initialization of the robot at the beginning of the match.
	@Override
	public void createObjects() {
		
		addAutonomous(kDriveStraight, new DriveStraight());
		addAutonomous(kPicker, new PickerMode());
		addAutonomous(kSimpleDeposit, new SimpleDeposit());
		
		this.driverIn = new DriverInput();
		this.elevator = new Elevator();
		this.endEffector = new Endeffector();
		this.lightDrive = new LightDrive();
		this.robotOut = new RobotOutput();
		this.sensors = new SensorInput();
		this.teleopControl = new TeleopControl();
	}

	// Initialization of code for the Disabled portion of the match.
	@Override
	public void disabledInit() {
		this.robotOut.stopAll();
	}

	// Code that runs when your robot is disabled.
	@Override
	public void disabledPeriodic() {
		
	}
	
	@Override
	public void teleopInit() {
		
	}
	
	@Override
	public void teleopPeriodic() {
		teleopControl.teleopTasks();
	}
}
