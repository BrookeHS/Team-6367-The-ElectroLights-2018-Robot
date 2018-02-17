package org.usfirst.frc.team6367.robot.auto;

import org.usfirst.frc.team6367.robot.LightDrive.LightDrive;
import org.usfirst.frc.team6367.robot.io.RobotOutput;

import edu.wpi.first.wpilibj.DriverStation;
import io.github.robotpy.magicbot.MagicAutonomous;

public class PickerMode implements MagicAutonomous {
	RobotOutput robotOut;
	LightDrive light;
	public static final double DIST = 8;

	// What happens periodically (20ms) during autonomous mode.
	@Override
	public void autonomousPeriodic() {
	}

	// This method is called once at the beginning of autonomous mode.
	@Override
	public void onEnabled() {
		String gameData = DriverStation.getInstance().getGameSpecificMessage();
		if (gameData.length() > 0) {
			if (gameData.charAt(1) == 'R') {
				robotOut.driveDistance(DIST);
				if(robotOut.toPosition()) {
					light.rotateToAngle(90);
				}
			} else if (gameData.charAt(1) == 'L') {
				robotOut.driveDistance(DIST);
				if(robotOut.toPosition()) {
					light.rotateToAngle(270);
				}
			}

		}
	}
}
/*
Get closed loop error. 
Shows how far off you are the target.
Use it to create a delay for rotate to angle.

Create a function called at position.
*/