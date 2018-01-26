package org.usfirst.frc.team6367.robot.io;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.VictorSP;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.drive.MecanumDrive;

public class RobotOutput {

	private static RobotOutput instance;
	
	private VictorSP driveLeftFront;
	private VictorSP driveLeftRear;
	private VictorSP driveRightFront;
	private VictorSP driveRightRear;
	private VictorSP elevator;
	private VictorSP endEffectorLeft;
	private VictorSP endEffectorRight;
	private VictorSP climber;
	
	protected DifferentialDrive light_drive;
	protected MecanumDrive light_drive2;
	
	
	/*
	 * Constructor Method for RobotOutput
	 * Private
	 * Instantiates each motor controller with PWM channels 0-7.
	 * 	Drive-train VictorSPs are 0-3
	 * 	Mechanism VictorSPs are 4-7
	 * Instantiates Differential Drive for skid-steer control.
	 * Instantiates Mecanum drive.
	 */
	private RobotOutput() {
		this.driveLeftFront 	= new VictorSP(0);
		this.driveLeftRear		= new VictorSP(1);
		this.driveRightFront	= new VictorSP(2);
		this.driveRightRear		= new VictorSP(3);
		this.elevator			= new VictorSP(4);
		this.endEffectorLeft	= new VictorSP(5);
		this.endEffectorRight	= new VictorSP(6);
		this.climber			= new VictorSP(7);
		
		SpeedControllerGroup drive_left = new SpeedControllerGroup(driveLeftFront, driveLeftRear);
		SpeedControllerGroup drive_right = new SpeedControllerGroup(driveRightFront, driveRightRear);
		this.light_drive = new DifferentialDrive(drive_left, drive_right);
		this.light_drive2 = new MecanumDrive(driveLeftFront ,driveLeftRear ,driveRightFront ,driveRightRear );
	}
	
	public static RobotOutput getInstance() {
		if(instance == null) {
			instance = new RobotOutput();
		}
		return instance;
	}
	
	public void setDriveLeft(double output) {
		this.driveLeftFront.set(-output);
		this.driveLeftRear.set(-output);
	}
	
	public void setDriveRight(double output) {
		this.driveRightFront.set(output);
		this.driveRightRear.set(output);
	}
	
	public void setElevator(double output) {
		this.elevator.set(output);
	}
	
	public void setEndEffector(double output) {
		this.endEffectorLeft.set(output);
		this.endEffectorRight.set(-output);
	}
	
	public void setClimber(double output) {
		this.climber.set(0);
	}
	
	public void stopAll() {
		this.setDriveLeft(0);
		this.setDriveRight(0);
		this.setElevator(0);
		this.setEndEffector(0);
		this.setClimber(0);
	}
	
	public void arcadeDrive(Joystick driveStick) {
		light_drive.arcadeDrive(driveStick.getY(), driveStick.getX(), true);
	}
	
	public void tankDrive(Joystick driveStick1, Joystick driveStick2) {
		light_drive.tankDrive(driveStick1.getY(),driveStick2.getY(),true);
	}
	
	public void mecanumDrive(Joystick driveStick) {
		light_drive2.driveCartesian(driveStick.getY(), driveStick.getX(), driveStick.getZ());
	}
	
}
