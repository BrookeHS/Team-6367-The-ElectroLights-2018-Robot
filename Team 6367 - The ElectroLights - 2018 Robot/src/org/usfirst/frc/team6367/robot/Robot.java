package org.usfirst.frc.team6367.robot;

import org.usfirst.frc.team6367.robot.io.DriverInput;
import org.usfirst.frc.team6367.robot.io.RobotOutput;
import org.usfirst.frc.team6367.robot.teleop.TeleopControl;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Robot extends IterativeRobot {
	
	private RobotOutput robotOut;
	private DriverInput driverIn;
	private TeleopControl teleopControl;
	
	private static final String kDefaultAuto = "Default";
	private static final String kCustomAuto = "My Auto";
	private String m_autoSelected;
	private SendableChooser<String> m_chooser = new SendableChooser<>();

	// Initialization of the robot at the beginning of the match.
	@Override
	public void robotInit() {
		m_chooser.addDefault("Default Auto", kDefaultAuto);
		m_chooser.addObject("My Auto", kCustomAuto);
		SmartDashboard.putData("Auto choices", m_chooser);
		this.robotOut = RobotOutput.getInstance();
		this.driverIn = DriverInput.getInstance();
		this.teleopControl = TeleopControl.getInstance();
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
	
	// Initialization of code for autonomous.
	@Override
	public void autonomousInit() {
		m_autoSelected = m_chooser.getSelected();
		// autoSelected = SmartDashboard.getString("Auto Selector",
		// defaultAuto);
		System.out.println("Auto selected: " + m_autoSelected);
	}

	// Code that runs when your robot is in autonomous.
	@Override
	public void autonomousPeriodic() {
		switch (m_autoSelected) {
			case kCustomAuto:
				// Put custom auto code here
				break;
			case kDefaultAuto:
			default:
				// Put default auto code here
				break;
		}
	}

	@Override
	public void teleopInit() {
		
	}
	
	@Override
	public void teleopPeriodic() {
		robotOut.mecanumDrive(driverIn.getDriverStick());
	}

	@Override
	public void testPeriodic() {
	}
}
