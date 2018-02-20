package org.usfirst.frc.team6367.robot;

import org.usfirst.frc.team6367.robot.LightDrive.LightDrive;
import org.usfirst.frc.team6367.robot.auto.AutoTrajectory;
import org.usfirst.frc.team6367.robot.auto.DriveStraight;
import org.usfirst.frc.team6367.robot.auto.PickerMode;
import org.usfirst.frc.team6367.robot.auto.SimpleDeposit;
import org.usfirst.frc.team6367.robot.io.DriverInput;
import org.usfirst.frc.team6367.robot.io.RobotOutput;
import org.usfirst.frc.team6367.robot.io.SensorInput;
import org.usfirst.frc.team6367.robot.teleop.Elevator;
import org.usfirst.frc.team6367.robot.teleop.EndEffector;
import org.usfirst.frc.team6367.robot.teleop.TeleopControl;

import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import io.github.robotpy.magicbot.MagicRobot;

public class Robot extends MagicRobot {
	
	public enum AutonomousChoice {
		AutoModeLeft,
		AutoModeRight,
		AutoModeMiddle,
	}
	
	public SendableChooser<AutonomousChoice> startingPos;
	public DriverInput driverIn;	
	public Elevator elevator;
	public EndEffector endEffector;
	public LightDrive lightDrive;
	public RobotOutput robotOut;
	public SensorInput sensors;
	public TeleopControl teleopControl;
	public org.usfirst.frc.team6367.robot.auto.AutoTrajectory autoTrajectory;
	
	private static final String kDriveStraight = "Drive Straight";
	private static final String kPicker = "Picker";
	private static final String kSimpleDeposit = "Simple Deposit";
	

	// Initialization of the robot at the beginning of the match.
	@Override
	public void createObjects() {
<<<<<<< HEAD
		//addAutonomous(kPicker, new PickerMode());
=======
		
		addAutonomous(kPicker, new PickerMode());
>>>>>>> b8aab2d906f33153f982b08e2c867c54295ccc9a
		this.driverIn = new DriverInput();
		this.elevator = new Elevator();
		this.endEffector = new EndEffector();
		this.lightDrive = new LightDrive();
		this.robotOut = new RobotOutput();
		this.sensors = new SensorInput();
		this.teleopControl = new TeleopControl();
		this.autoTrajectory = new AutoTrajectory();
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

	 public void createChoices(){
	  	startingPos.addObject("Left", AutonomousChoice.AutoModeLeft);
	    startingPos.addObject("Right", AutonomousChoice.AutoModeRight);
	    startingPos.addObject("Middle", AutonomousChoice.AutoModeMiddle);
	    SmartDashboard.putData("Trajectory",startingPos);
	  }
}
