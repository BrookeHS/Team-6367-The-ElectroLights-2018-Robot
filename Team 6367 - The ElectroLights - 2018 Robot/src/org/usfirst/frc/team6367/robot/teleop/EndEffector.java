package org.usfirst.frc.team6367.robot.teleop;

import org.usfirst.frc.team6367.robot.io.RobotOutput;
import org.usfirst.frc.team6367.robot.io.SensorInput;

import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import io.github.robotpy.magicbot.MagicComponent;
import io.github.robotpy.magicbot.MagicInject;

public class EndEffector implements MagicComponent {

	public double count = 0;
	
	@MagicInject
	RobotOutput robotOut;
	@MagicInject
	SensorInput sensors;
	
	NetworkTableEntry accelLimit = SmartDashboard.getEntry("accelLimit");
	
	static final double kDefaultAccel = 40.0;
	
	enum EffectorState {
		DEPLOY,
		INTAKE,
		EINTAKE,
		STOP
	}
	
	EffectorState state = EffectorState.STOP;
	
	public EndEffector() {
		accelLimit.setNumber(kDefaultAccel);
	}
	
	public void deployBox() {
		state = EffectorState.DEPLOY;
	}
	
	public double returnCount() {
		return count;
	}
	
	public void intake() {
		state = EffectorState.INTAKE;
	}
	
	public void stop() {
		state = EffectorState.STOP;
	}
	
	public boolean finishedDeploy() {
		if(count >=50) {
			count = 0;
			return true;
		}
		return false;
	}

	@Override
	public void execute() {
		// if rotational acceleration is greater than N, force intake to happen
		double accel = sensors.ahrs.getRawGyroZ();

		SmartDashboard.putNumber("accel_x", sensors.ahrs.getRawGyroX());
		SmartDashboard.putNumber("accel_y", sensors.ahrs.getRawGyroY());
		SmartDashboard.putNumber("accel_z", sensors.ahrs.getRawGyroZ());
		
		if (Math.abs(accel) > accelLimit.getDouble(kDefaultAccel)) {
			state = EffectorState.EINTAKE;
		}
		
		switch (state) {
		case DEPLOY:
			//robotOut.setEndEffector(0.7);
			robotOut.setEndEffector(-0.4);
			count++;
			break;
		case INTAKE:
			//robotOut.setEndEffector(-.7);
			robotOut.setEndEffector(.5);
			break;
		case EINTAKE:
			//robotOut.setEndEffector(-.7);
			robotOut.setEndEffector(.5);
			break;
		case STOP:
			robotOut.setEndEffector(0);
			break;
		}
		
		state = EffectorState.STOP;
	}
}

