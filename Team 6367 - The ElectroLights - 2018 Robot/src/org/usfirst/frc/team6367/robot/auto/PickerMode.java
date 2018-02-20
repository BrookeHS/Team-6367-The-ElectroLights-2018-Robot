package org.usfirst.frc.team6367.robot.auto;

import org.usfirst.frc.team6367.robot.Robot.AutonomousChoice;
import org.usfirst.frc.team6367.robot.LightDrive.LightDrive;
import org.usfirst.frc.team6367.robot.io.RobotOutput;
import org.usfirst.frc.team6367.robot.teleop.Elevator;
import org.usfirst.frc.team6367.robot.teleop.EndEffector;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import io.github.robotpy.magicbot.MagicAutonomous;
import io.github.robotpy.magicbot.MagicInject;
import io.github.robotpy.magicbot.sm.AutonomousStateMachine;
import io.github.robotpy.magicbot.sm.State;

public class PickerMode extends AutonomousStateMachine {

	String gameData = DriverStation.getInstance().getGameSpecificMessage();
	AutonomousChoice choiceNum;

	@MagicInject
	RobotOutput robotOut;
	@MagicInject
	AutoTrajectory autoTrajectory;
	@MagicInject
	SendableChooser<AutonomousChoice> startingPos;
	@MagicInject
	Elevator elevator;
	@MagicInject
	EndEffector effector;

	int startingLocation;
	boolean scaleSide;

	// This method is called once at the beginning of autonomous mode.
	@Override
	public void onEnabled() {
		choiceNum = startingPos.getSelected();
		autoTrajectory.calculateTrajectory(choiceNum, gameData);

	}
	
	@State(first=true)
	public void driving() {
		autoTrajectory.move();
		if (autoTrajectory.isFinished()) {
			nextState("liftElevator");
		}
	}
	
	public void liftElevator() {
		elevator.upPosition();
		if(elevator.upFinished()) {
			nextState("deployBox");
		}
	}
	
	public void deployBox() {
		effector.deployBox();
		if(effector.finishedDeploy()) {
			effector.stop();
		}
	}
	
	
}

/*
 * Get closed loop error. Shows how far off you are the target. Use it to create
 * a delay for rotate to angle.
 * 
 * Create a function called at position.
 */
