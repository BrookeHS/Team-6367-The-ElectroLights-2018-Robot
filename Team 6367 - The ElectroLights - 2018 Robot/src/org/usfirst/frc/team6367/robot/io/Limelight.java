package org.usfirst.frc.team6367.robot.io;

import edu.wpi.first.networktables.NetworkTableInstance;

public class Limelight {

	private static NetworkTableInstance lime_table = null;
	
	public static enum LightMode{
		lightOn, lightOff, lightBlink;
	}
	
	public static enum CameraMode{
		camVision, camDriver;
	}
	
}
