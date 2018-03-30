package org.usfirst.frc.team6367.robot;

import org.usfirst.frc.team6367.robot.LightDrive.LightDrive;

import edu.wpi.cscore.UsbCamera;
import edu.wpi.first.wpilibj.CameraServer;
import org.usfirst.frc.team6367.robot.Robot.AutonomousChoice;
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

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.MotorSafety;
import edu.wpi.first.wpilibj.MotorSafetyHelper;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import io.github.robotpy.magicbot.MagicRobot;

public class Robot extends MagicRobot {
	
	public enum AutonomousChoice {
		AutoModeLeft,
		AutoModeRight,
		AutoModeMiddle,
		AutoModeDefault,
	}
	
	public SendableChooser<AutonomousChoice> startingPos;
	public DriverInput driverIn;	
	public Elevator elevator;
	public EndEffector endEffector;
	public LightDrive lightDrive;
	public RobotOutput robotOut;
	public SensorInput sensors;
	public TeleopControl teleopControl;
	public AutoTrajectory autoTrajectory;
	public CameraServer server;
	public UsbCamera camera;
	
	private static final String kDriveStraight = "Drive Straight";
	private static final String kPicker = "Picker";
	private static final String kSimpleDeposit = "Simple Deposit";
	
	public static final double kTwitchy = 0.75;
	public static final double kElevator = -14;
	

	// Initialization of the robot at the beginning of the match.
	@Override
	public void createObjects() {
		
		startingPos = new SendableChooser<AutonomousChoice>();
		startingPos.addObject("Left", AutonomousChoice.AutoModeLeft);
	    startingPos.addObject("Right", AutonomousChoice.AutoModeRight);
	    startingPos.addObject("Middle", AutonomousChoice.AutoModeMiddle);
	    startingPos.addObject("Default", AutonomousChoice.AutoModeDefault);
	    SmartDashboard.putData("Trajectory",startingPos);
		
	    SmartDashboard.putNumber("twitchy", kTwitchy);
	    SmartDashboard.putNumber("accelElevatorHeight", kElevator);
	    
		addAutonomous(kPicker, new PickerMode(),true);
		
	    this.driverIn = new DriverInput();
		this.elevator = new Elevator();
		this.endEffector = new EndEffector();
		this.lightDrive = new LightDrive();
		this.robotOut = new RobotOutput();
		this.sensors = new SensorInput();
		this.teleopControl = new TeleopControl();
		this.autoTrajectory = new AutoTrajectory();
		
		addComponent(this.endEffector);
		addComponent(this.elevator);
		server = CameraServer.getInstance();		
		camera = server.startAutomaticCapture();
		camera.setFPS(30);
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
	protected void autonomousInit() {
		robotOut.setRamp(0);
	}
	
	@Override
	public void teleopInit() {
		robotOut.setRamp(0.15);
	}
	
	@Override
	public void teleopPeriodic() {
		teleopControl.teleopTasks();
	}
}
