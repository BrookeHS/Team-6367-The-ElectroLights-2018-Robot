package org.usfirst.frc.team6367.robot.io;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.VictorSP;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.drive.MecanumDrive;

public class RobotOutput {

	private static RobotOutput instance;
	
	private WPI_TalonSRX driveLeftFront;
	// Follows driveLeftFront
	private WPI_VictorSPX driveLeftRear;
	
	private WPI_TalonSRX driveRightFront;
	// Follows driveRightFront
	private WPI_VictorSPX driveRightRear;
	
	private WPI_TalonSRX elevator;
	private WPI_VictorSPX endEffectorLeft;
	private WPI_VictorSPX endEffectorRight;
	private WPI_TalonSRX climber;
	/*
	 * There needs to be 4 talon srxs
	 * there need to be victor spx
	 * 
	 * 
	 */
	
	
	protected DifferentialDrive light_drive;
	protected MecanumDrive light_drive2;
	public static final double DEADBAND = .3;
	
	
	/*
	 * Constructor Method for RobotOutput
	 * Private
	 * Instantiates each motor controller with PWM channels 0-7.
	 * 	Drive-train VictorSPs are 0-3
	 * 	Mechanism VictorSPs are 4-7
	 * Instantiates Differential Drive for skid-steer control.
	 */
	private RobotOutput() {
		this.driveLeftFront 	= new WPI_TalonSRX(6);
		this.driveLeftRear		= new WPI_VictorSPX(1);
		this.driveRightFront	= new WPI_TalonSRX(3);
		this.driveRightRear		= new WPI_VictorSPX(2);
		this.elevator			= new WPI_TalonSRX(5);
		this.endEffectorLeft	= new WPI_VictorSPX(7);
		this.endEffectorRight	= new WPI_VictorSPX(4);
		this.climber			= new WPI_TalonSRX(8);
		
		
	    SpeedControllerGroup drive_left = new SpeedControllerGroup(driveLeftFront, driveLeftRear);
		SpeedControllerGroup drive_right = new SpeedControllerGroup(driveRightFront, driveRightRear);
		this.light_drive = new DifferentialDrive(drive_left, drive_right);
	}
	
	public static RobotOutput getInstance() {
		if(instance == null) {
			instance = new RobotOutput();
		}
		return instance;
	}
	
	public void setDriveLeft(double output) {
		this.driveLeftFront.set(ControlMode.PercentOutput,-output);
		this.driveLeftRear.set(ControlMode.PercentOutput,-output);
	}
	
	public void setDriveRight(double output) {
		this.driveRightFront.set(ControlMode.PercentOutput,output);
		this.driveRightRear.set(ControlMode.PercentOutput,output);
	}
	
	public void setElevator(double output) {
		this.elevator.set(ControlMode.PercentOutput,output);
	}
	
	public void setEndEffector(double output) {
		this.endEffectorLeft.set(ControlMode.PercentOutput, output);
		this.endEffectorRight.set(ControlMode.PercentOutput,-output);
	}
	
	public void setClimber(double output) {
		this.climber.set(ControlMode.PercentOutput,0);
	}
	
	public void stopAll() {
		this.setDriveLeft(0);
		this.setDriveRight(0);
		this.setElevator(0);
		this.setEndEffector(0);
		this.setClimber(0);
	}
	
	public void arcadeDrive(Joystick driveStick) {
		light_drive.arcadeDrive(compDeadBand(driveStick.getY()), compDeadBand(driveStick.getX()), true);
	}
	
	public void tankDrive(Joystick driveStick1, Joystick driveStick2) {
		light_drive.tankDrive(driveStick1.getY(),driveStick2.getY(),true);
	}	
	
	public double compDeadBand(double input) {
		if(Math.abs(input)<DEADBAND) {
			return 0;
		}
		return Math.copySign((Math.abs(input) - DEADBAND)/(1 - DEADBAND), input);
	}
}
