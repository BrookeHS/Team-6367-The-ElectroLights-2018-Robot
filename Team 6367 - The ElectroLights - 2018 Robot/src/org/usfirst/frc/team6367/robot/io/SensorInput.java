package org.usfirst.frc.team6367.robot.io;

import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.SPI;

public class SensorInput {

	private static SensorInput instance;
	
	AHRS ahrs;
	
	private SensorInput() {
		try {
			ahrs = new AHRS(SPI.Port.kMXP);
		} catch(RuntimeException e) {
			DriverStation.reportError("Error instantiating navX MXP: "+e.getMessage(), true);
		}
	}
	
	public static SensorInput getInstance() {
		if(instance==null) {
			instance = new SensorInput();
		}
		return instance;
	}
	
	
	
}
