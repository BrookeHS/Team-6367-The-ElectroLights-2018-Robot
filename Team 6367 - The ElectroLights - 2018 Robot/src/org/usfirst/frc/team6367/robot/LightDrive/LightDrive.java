package org.usfirst.frc.team6367.robot.LightDrive;

import org.usfirst.frc.team6367.robot.io.RobotOutput;
import org.usfirst.frc.team6367.robot.io.SensorInput;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDOutput;

public class LightDrive implements PIDOutput{

	public static LightDrive instance;
	
	static final double kP = 0.03;
	static final double kI = 0.00;
	static final double kD = 0.00;
	static final double kF = 0.00;
	static final double kToleranceDegrees = 2.0f;
	static final double kTargetAngleDegrees = 90.0f;
	
	public PIDController turnController;
	double rotateToAngleRate;
	SensorInput sensors;
	public RobotOutput robotOut;
	
	// Constructor 
	private LightDrive() {
		this.sensors = SensorInput.getInstance();
		this.robotOut = RobotOutput.getInstance();
		this.turnController = new PIDController(kP, kI, kD, kF, sensors.ahrs, this );
		this.turnController.setInputRange(-180.0f,  180.0f);
		this.turnController.setOutputRange(-1.0, 1.0);
		this.turnController.setAbsoluteTolerance(kToleranceDegrees);
		this.turnController.setContinuous(true);
		this.turnController.disable();
		enc.setDistancePerPulse(6 * Math.PI); // in feet
	}

	public Encoder enc = new Encoder(0,1,false,Encoder.EncodingType.k4X);
	
	public static LightDrive getInstance() {
		if(instance==null) {
			instance = new LightDrive();
		}
		return instance;
	}
	
	public void driveStraight() {
		if(!turnController.isEnabled()) {
			turnController.setSetpoint(kTargetAngleDegrees);
			rotateToAngleRate = 0;
			turnController.enable();
		}
		robotOut.setDriveLeft(0.8*rotateToAngleRate);
		robotOut.setDriveRight(0.8*rotateToAngleRate);
	}
	  
	public void stop() {
		turnController.disable();
		robotOut.setDriveLeft(0);
		robotOut.setDriveRight(0);
	}
	
	public void driveDistance(double distance, double targetFT) {
		if(distance >= targetFT) {
			stop();
		} else {
			driveStraight();
		}
	}

	
	public void rotateToAngle(double targetAngle) {
		if(!turnController.isEnabled()) {
			turnController.setSetpoint(targetAngle);
			rotateToAngleRate = 0;
			turnController.enable();
		}
		robotOut.setDriveLeft(0.8*rotateToAngleRate);
		robotOut.setDriveRight(-0.8*rotateToAngleRate);
	}
	
	public void zeroYaw() {
		this.rotateToAngle(0);
	}
	public void ninetyYaw() {
		this.rotateToAngle(90);
	}
	public void oneEightyYaw() {
		this.rotateToAngle(180);
	}
	public void twoSeventyYaw() {
		this.rotateToAngle(270);
	}
	
	@Override
	public void pidWrite(double output) {
		rotateToAngleRate = output;
	}
}
