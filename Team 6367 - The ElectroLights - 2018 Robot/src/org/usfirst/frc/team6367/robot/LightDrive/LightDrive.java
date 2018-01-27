package org.usfirst.frc.team6367.robot.LightDrive;

import org.usfirst.frc.team6367.robot.io.RobotOutput;
import org.usfirst.frc.team6367.robot.io.SensorInput;

import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDOutput;

public class LightDrive implements PIDOutput{

	private static LightDrive instance;
	
	static final double kP = 0.03;
	static final double kI = 0.00;
	static final double kD = 0.00;
	static final double kF = 0.00;
	static final double kToleranceDegrees = 2.0f;
	static final double kTargetAngleDegrees = 90.0f;
	
	PIDController turnController;
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
	}
	
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
		else {
			turnController.disable();
		}
		robotOut.setDriveLeft(rotateToAngleRate);
		robotOut.setDriveRight(rotateToAngleRate);
	}
	
	public void rotateToAngle(double targetAngle) {
		
	}
	
	public void zeroYaw() {
		this.rotateToAngle(0);
	}
	
	@Override
	public void pidWrite(double output) {
		rotateToAngleRate = output;
	}
}
