package org.usfirst.frc.team6367.robot.io;

import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;

	public class Limelight {

	private static NetworkTableInstance lime_table = null;
	
	public static enum LightMode{
		lightOn, lightOff, lightBlink;
	}
	
	public static enum CameraMode{
		camVision, camDriver;
	}
	
	NetworkTable table = NetworkTableInstance.getDefault().getTable("limelight");
	NetworkTableEntry tx = table.getEntry("tx");
	NetworkTableEntry ty = table.getEntry("ty");
	NetworkTableEntry ta = table.getEntry("ta");
	double x = tx.getDouble(0);
	double y = ty.getDouble(0);
	double area = ta.getDouble(0);
	
	
}
