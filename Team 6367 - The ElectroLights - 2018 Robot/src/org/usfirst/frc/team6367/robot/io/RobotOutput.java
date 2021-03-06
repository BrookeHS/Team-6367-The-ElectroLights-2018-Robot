package org.usfirst.frc.team6367.robot.io;

import org.usfirst.frc.team6367.robot.Robot;
import org.usfirst.frc.team6367.robot.teleop.Elevator;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Servo;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.VictorSP;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.drive.MecanumDrive;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import io.github.robotpy.magicbot.MagicInject;

public class RobotOutput {

	private WPI_TalonSRX driveLeftFront;
	// Follows driveLeftFront
	private WPI_VictorSPX driveLeftRear;

	private WPI_TalonSRX driveRightFront;
	// Follows driveRightFront
	private WPI_VictorSPX driveRightRear;

	public WPI_TalonSRX elevatorMotor;
	private WPI_VictorSPX endEffectorLeft;
	private WPI_VictorSPX endEffectorRight;
	private WPI_TalonSRX climber;

	public Servo dropServo;
	
	@MagicInject
	Elevator elevator;
	
	/*
	 * There needs to be 4 talon srxs there need to be victor spx
	 * 
	 * 
	 */

	protected DifferentialDrive light_drive;
	protected MecanumDrive light_drive2;
	public static final double DEADBAND = .3;
	public static final double kENCODERPERFOOT = (6 * Math.PI) / 1024;

	public static final int CLOSEENOUGH = (int) Math.round(.5 / kENCODERPERFOOT);
	public static final int CONFIGCLOSEENOUGH = CLOSEENOUGH / 2;
	public static final double ELEVATORPERFOOT = ((6 * Math.PI) / 1024);
	public static final int ELEVATORTOLERANCE = 240;
	public static final int CONFIGELEVATORTOLERANCE = ELEVATORTOLERANCE / 2;

	/*
	 * Constructor Method for RobotOutput Private Instantiates each motor controller
	 * with PWM channels 0-7. Drive-train VictorSPs are 0-3 Mechanism VictorSPs are
	 * 4-7 Instantiates Differential Drive for skid-steer control.
	 */
	public RobotOutput() {
		dropServo = new Servo(0);

		this.driveLeftFront = new WPI_TalonSRX(6); //
		this.driveLeftRear = new WPI_VictorSPX(1);
		this.driveRightFront = new WPI_TalonSRX(3); //
		this.driveRightRear = new WPI_VictorSPX(2);
		this.elevatorMotor = new WPI_TalonSRX(8);
		this.endEffectorLeft = new WPI_VictorSPX(7);
		this.endEffectorRight = new WPI_VictorSPX(4);
		this.climber = new WPI_TalonSRX(5);

		driveLeftFront.setInverted(true);
		driveLeftRear.setInverted(true);
		driveRightFront.setInverted(true);
		driveRightRear.setInverted(true);
		driveRightRear.setNeutralMode(NeutralMode.Brake);
		driveRightFront.setNeutralMode(NeutralMode.Brake);
		driveLeftFront.setNeutralMode(NeutralMode.Brake);
		driveLeftRear.setNeutralMode(NeutralMode.Brake);
		
		// default ramp for everyone
		driveLeftFront.configOpenloopRamp(0, 0);
		driveRightFront.configOpenloopRamp(0, 0);
		driveLeftRear.configOpenloopRamp(0, 0);
		driveRightRear.configOpenloopRamp(0, 0);
		
		driveLeftRear.follow(driveLeftFront);
		driveRightRear.follow(driveRightFront);
		
		

		// SpeedControllerGroup drive_left = new SpeedControllerGroup(driveLeftFront,
		// driveLeftRear);
		// SpeedControllerGroup drive_right = new SpeedControllerGroup(driveRightFront,
		// driveRightRear);
		this.light_drive = new DifferentialDrive(driveLeftFront, driveRightFront);
		this.light_drive.setSafetyEnabled(false);
		
		
		elevatorMotor.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder, 0, 0);
		elevatorMotor.config_kP(0, 0.6, 0);
		elevatorMotor.config_kI(0, 0, 0);
		elevatorMotor.config_kD(0, 0.1, 0);
		elevatorMotor.config_kF(0, 0, 0);
		elevatorMotor.configAllowableClosedloopError(0, (int) Math.round(.5 / kENCODERPERFOOT), 0);
		elevatorMotor.configClosedloopRamp(0.5, 0);
		elevatorMotor.getSensorCollection().setQuadraturePosition(0, 0);

		driveRightFront.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder, 0, 0);
		driveRightFront.config_kP(0, 0.8, 0);
		driveRightFront.config_kI(0, 0, 0);
		driveRightFront.config_kD(0, 0, 0);
		driveRightFront.config_kF(0, 0, 0);
		driveRightFront.configAllowableClosedloopError(0, (int) Math.round(.5 / kENCODERPERFOOT), 0);
		driveRightFront.configClosedloopRamp(0.5, 0);

		driveLeftFront.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder, 0, 0);
		driveLeftFront.config_kP(0, 0.8, 0);
		driveLeftFront.config_kI(0, 0, 0);
		driveLeftFront.config_kD(0, 0, 0);
		driveLeftFront.config_kF(0, 0, 0);
		driveLeftFront.configAllowableClosedloopError(0, (int) Math.round(.5 / kENCODERPERFOOT), 0);
		driveLeftFront.configClosedloopRamp(0.5, 0);
	}
	

	public void resetDriveEncoders() {
		this.driveRightFront.setSelectedSensorPosition(0, 0, 0);
		this.driveLeftFront.setSelectedSensorPosition(0, 0, 0);
	}

	public int getEncoderLeftSide() {
		return -1*driveLeftFront.getSelectedSensorPosition(0);
	}

	public int getEncoderRightSide() {
		return driveRightFront.getSelectedSensorPosition(0);
	}
	


	public void driveDistance(double position) {
		this.driveLeftFront.set(ControlMode.Position, position / kENCODERPERFOOT);
		this.driveRightFront.set(ControlMode.Position, position / kENCODERPERFOOT);
	}

	public void setDriveLeft(double output) {
		this.driveLeftFront.set(ControlMode.PercentOutput, -output);
	}

	public boolean atPosition() {
		return Math.abs(driveLeftFront.getClosedLoopError(0)) <= CLOSEENOUGH
				&& Math.abs(driveRightFront.getClosedLoopError(0)) <= CLOSEENOUGH;
	}

	public boolean atScale() {
		return Math.abs(elevatorMotor.getClosedLoopError(0)) <= ELEVATORTOLERANCE
				&& Math.abs(elevatorMotor.getClosedLoopError(0)) <= ELEVATORTOLERANCE;
	}

	public void setDriveRight(double output) {
		this.driveRightFront.set(ControlMode.PercentOutput, output);
	}

	public void setElevator(double position) {
		this.elevatorMotor.set(ControlMode.Position, position / ELEVATORPERFOOT);
	}

	public void setEndEffector(double output) {
		this.endEffectorLeft.set(ControlMode.PercentOutput, output);
		this.endEffectorRight.set(ControlMode.PercentOutput, -output);
	}

	public void setClimber(double output) {
		this.climber.set(ControlMode.PercentOutput, 0);
	}

	public void stopAll() {
		this.setDriveLeft(0);
		this.setDriveRight(0);
		this.setElevator(0);
		this.setEndEffector(0);
		this.setClimber(0);
	}

	public void arcadeDrive(Joystick driveStick) {
		
		double x = driveStick.getZ();
		double y = driveStick.getY();
		
		if (elevator.getPosition() < SmartDashboard.getNumber("accelElevatorHeight", Robot.kElevator)) {
			x = x * .8;
			y = y * .7;
		}
		
		double twitchy = SmartDashboard.getNumber("twitchy", Robot.kTwitchy);
		light_drive.arcadeDrive(compDeadBand(y), -compDeadBand(x*twitchy), true);
	}
	
	public void stopDriving() {
		this.driveLeftFront.set(0);
		this.driveRightFront.set(0);
	}
	
	public void setRamp(double val) {
		// only configure the leaders, don't need to config the followers
		driveLeftFront.configOpenloopRamp(val, 0);
		driveRightFront.configOpenloopRamp(val, 0);
	}

	public double compDeadBand(double input) {
		if (Math.abs(input) < DEADBAND) {
			return 0;
		}
		return Math.copySign((Math.abs(input) - DEADBAND) / (1 - DEADBAND), input);
	}

}
