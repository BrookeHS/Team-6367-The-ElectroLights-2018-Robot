package org.usfirst.frc.team6367.robot.auto;

import org.usfirst.frc.team6367.robot.Robot.AutonomousChoice;
import org.usfirst.frc.team6367.robot.LightDrive.LightDrive;
import org.usfirst.frc.team6367.robot.io.RobotOutput;
import org.usfirst.frc.team6367.robot.teleop.Elevator;
import org.usfirst.frc.team6367.robot.teleop.EndEffector;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import io.github.robotpy.magicbot.MagicAutonomous;
import io.github.robotpy.magicbot.MagicInject;
import io.github.robotpy.magicbot.sm.AutonomousStateMachine;
import io.github.robotpy.magicbot.sm.State;
import io.github.robotpy.magicbot.sm.TimedState;

public class PickerMode extends AutonomousStateMachine {

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
	EndEffector endEffector;

	int startingLocation;
	boolean scaleSide;
	String gameData;

	// This method is called once at the beginning of autonomous mode.
	@Override
	public void onEnabled() {
		super.onEnabled();
		choiceNum = startingPos.getSelected();
		gameData = DriverStation.getInstance().getGameSpecificMessage();
		autoTrajectory.calculateTrajectory(choiceNum, gameData);
	}
	
	@State(first=true)
	public void driving() {
		autoTrajectory.move();
		if (autoTrajectory.isFinished()) {
			robotOut.stopDriving();
			if(choiceNum.isSwitch()==true) {
				nextState("liftElevatorSwitch");
			}
			else {
				nextState("liftElevatorScale");
			}
		}
	}
	
	
	@TimedState(duration=5.0, nextState="deployScale")
	public void liftElevatorScale(double tm) {
		elevator.upPosition();
		if(tm > 1 && elevator.posFinished()) {
			nextState("deployScale");
		}
	}
	
	@State
	public void deployScale() {
		endEffector.deployBox();
		robotOut.stopDriving();
       	SmartDashboard.putNumber("shooter", endEffector.returnCount());
       	SmartDashboard.updateValues();
       	LiveWindow.updateValues();
       	if(endEffector.finishedDeploy()) {
			endEffector.stop();
			elevator.downPosition();
			this.done();
		}
	}
	
	@TimedState(duration=3.0, nextState="driveForward")
	public void liftElevatorSwitch(double tm) {
		elevator.midPosition();
		if(tm > 1 && elevator.posFinished()) {
			nextState("driveForward");
		}
	}
	
	@TimedState(duration=1.5,nextState="deploySwitch")
	public void driveForward() {
		robotOut.setDriveLeft(0.55);
		robotOut.setDriveRight(0.55);	
	}
	
	@State
	public void deploySwitch() {
		endEffector.deployBox();
		robotOut.stopDriving();
       	if(endEffector.finishedDeploy()) {
			endEffector.stop();
			nextState("backup");
		}
	}
	
	@TimedState(duration=1.5, nextState="stopState")
	public void backup() {
		robotOut.setDriveLeft(-0.5);
		robotOut.setDriveRight(-0.5);	
	}
	
	@State
	public void stopState() {
		robotOut.stopDriving();
		done();
	}
}

/*
 * Get closed loop error. Shows how far off you are the target. Use it to create
 * a delay for rotate to angle.
 * 
 * Create a function called at position.
 */
