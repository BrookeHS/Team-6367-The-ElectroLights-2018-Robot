package org.usfirst.frc.team6367.robot.io;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.VictorSP;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.drive.MecanumDrive;

public class RobotOutput {

	private static RobotOutput instance;
	
	private TalonSRX driveLeftFront;
	private TalonSRX driveLeftRear;
	private TalonSRX driveRightFront;
	private TalonSRX driveRightRear;
	private VictorSPX elevator;
	private VictorSPX endEffectorLeft;
	private VictorSPX endEffectorRight;
	private VictorSPX climber;
	/*
	 * There needs to be 4 talon srxs
	 * there need to be victor spx
	 * 
	 * 
	 */
	
	
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
		this.driveLeftFront 	= new TalonSRX(4);
		this.driveLeftRear		= new TalonSRX(3);
		this.driveRightFront	= new TalonSRX(2);
		this.driveRightRear		= new TalonSRX(1);
		this.elevator			= new VictorSPX(6);
		this.endEffectorLeft	= new VictorSPX(5);
		this.endEffectorRight	= new VictorSPX(8);
		this.climber			= new VictorSPX(7);
		
		
	    //SpeedControllerGroup drive_left = new SpeedControllerGroup(driveLeftFront, driveLeftRear);
		//SpeedControllerGroup drive_right = new SpeedControllerGroup(driveRightFront, driveRightRear);
		//this.light_drive = new DifferentialDrive(drive_left, drive_right);
		//this.light_drive2 = new MecanumDrive(driveLeftFront ,driveLeftRear ,driveRightFront ,driveRightRear );
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
		light_drive.arcadeDrive(driveStick.getY(), driveStick.getX(), true);
	}
	
	public void tankDrive(Joystick driveStick1, Joystick driveStick2) {
		light_drive.tankDrive(driveStick1.getY(),driveStick2.getY(),true);
	}
	
	public void mecanumDrive(Joystick driveStick) {
		light_drive2.driveCartesian(driveStick.getY(), driveStick.getX(), driveStick.getZ());
	}
	
}
