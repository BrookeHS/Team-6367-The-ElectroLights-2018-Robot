package org.usfirst.frc.team6367.robot.auto;

import org.usfirst.frc.team6367.robot.LightDrive.LightDrive;
import org.usfirst.frc.team6367.robot.io.RobotOutput;

import io.github.robotpy.magicbot.MagicInject;
import io.github.robotpy.magicbot.sm.AutonomousStateMachine;
import io.github.robotpy.magicbot.sm.State;
import io.github.robotpy.magicbot.sm.TimedState;

public class SimpleDeposit extends AutonomousStateMachine {
	@MagicInject
	RobotOutput robotOut;
	@MagicInject
	LightDrive lightDrive;

	@State
	public void driving() {
		robotOut.driveDistance(DIST);
		if (robotOut.atPosition()) {
			nextState("rotating");
		}
	}

	@State
	public void rotating() {
		robotOut.rotateToAngle(ANGLE);
		if (robotOut.toAngle()) {
			nextState("elevator");
		}
	}

	@State
	public void elevator() {
		elevator.goToScale();
		if (robotOut.atScale()) {
			nextState("endEffector");
		}

	}

	@TimedState(duration = 4)
	public void endEffector() {
		robotOut.deployBox();
	}

}
