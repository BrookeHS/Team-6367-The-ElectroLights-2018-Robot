package org.usfirst.frc.team6367.robot.auto;

import org.usfirst.frc.team6367.robot.LightDrive.LightDrive;
import org.usfirst.frc.team6367.robot.io.RobotOutput;

import edu.wpi.first.wpilibj.DriverStation;
import io.github.robotpy.magicbot.MagicInject;
import io.github.robotpy.magicbot.sm.AutonomousStateMachine;
import io.github.robotpy.magicbot.sm.State;
import io.github.robotpy.magicbot.sm.TimedState;

public class SimpleDeposit extends AutonomousStateMachine {
    String gameData = DriverStation.getInstance().getGameSpecificMessage();
	
	public static final double ANGLE = 90;
	public static final double DIST = 8;
	
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
		lightDrive.rotateToAngle(ANGLE);
        if(gameData.charAt(1) == 'L') {
        	lightDrive.rotateToAngle(270);
         } else if(gameData.charAt(1) == 'R'){
        	lightDrive.rotateToAngle(90);
         }
		if (lightDrive.atAngle()) {
			nextState("elevator");
		}
	}

	@State
	public void elevator() {
		Elevator.goToScale();
		if (robotOut.toScale()) {
			nextState("endEffector");
		}

	}

	@TimedState(duration = 4)
	public void endEffector() {
		robotOut.deployBox();
	}

}
