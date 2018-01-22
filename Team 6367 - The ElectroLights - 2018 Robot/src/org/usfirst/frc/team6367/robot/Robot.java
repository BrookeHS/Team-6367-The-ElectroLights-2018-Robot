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

	@Override
	public void robotInit() {
		m_chooser.addDefault("Default Auto", kDefaultAuto);
		m_chooser.addObject("My Auto", kCustomAuto);
		SmartDashboard.putData("Auto choices", m_chooser);
		this.robotOut = RobotOutput.getInstance();
		this.driverIn = DriverInput.getInstance();
		this.teleopControl = TeleopControl.getInstance();
	}

	@Override
	public void disabledInit() {
		this.robotOut.stopAll();
	}

	@Override
	public void disabledPeriodic() {
		
	}
	
	@Override
	public void autonomousInit() {
		m_autoSelected = m_chooser.getSelected();
		// autoSelected = SmartDashboard.getString("Auto Selector",
		// defaultAuto);
		System.out.println("Auto selected: " + m_autoSelected);
	}

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
		robotOut.arcadeDrive(driverIn.getDriverStick());
	}

	@Override
	public void testPeriodic() {
	}
}
