package org.usfirst.frc.team6367.robot.auto;

public class AutoControl {
	
	private static AutoControl instance;
	
	private AutoControl() {
		
	}
	
	public static AutoControl getInstance() {
		if(instance==null) {
			instance = new AutoControl();
		}
		return instance;
	}
	
}
/*
 What we need to do:
1st we need the encoders
2nd we need to research the encoders
3rd We need to create a method called straight distance which makes the robot go a specified distance.

Constructing an Encoder object
SAMPLE CODE:

Java
Encoder enc;
enc = new Encoder(0, 1, false, Encoder.EncodingType.k4X);

Encoder sampleEncoder = new Encoder(0, 1, false, Encoder.EncodingType.k4X);
sampleEncoder.setMaxPeriod(.1);
sampleEncoder.setMinRate(10);
sampleEncoder.setDistancePerPulse(5);
sampleEncoder.setReverseDirection(true);
sampleEncoder.setSamplesToAverage(7);

This is how we will be able to calculate the distance. 
Which is helpful on programming the robot to go a certain distance.
After that we need to say to go to where are side is using the information given to us for the game.
However, we need to then talk about which directions to go and at what occasions it will need to 
go with a certain distance

During this mode there is only 15 seconds on where we are able to do what we need to do.
Our robot is already holding one power cube. So with that cube we can use the encoders and the 
directions to then use the end effectors to shoot out the cubes and onto the switch.
If we have enough time then we can maybe get more cubes and place them on the switch to gain more 
ownership.
 */
