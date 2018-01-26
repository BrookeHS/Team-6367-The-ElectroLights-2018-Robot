package org.usfirst.frc.team6367.robot.io;

import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.SPI;

public class SensorInput {

	private static SensorInput instance;
	
	public AHRS ahrs;
	public Limelight camera;
	
	// Constructor for SensorInput, creates an AHRS for the NavX-MXP.
	private SensorInput() {
		try {
			ahrs = new AHRS(SPI.Port.kMXP);
		} catch(RuntimeException e) {
			DriverStation.reportError("Error instantiating navX MXP: "+e.getMessage(), true);
		}
		camera = new Limelight();
	}
	
	public static SensorInput getInstance() {
		if(instance==null) {
			instance = new SensorInput();
		}
		return instance;
	}
	
	
	
}
