import org.usfirst.frc.team6367.robot.LightDrive.LightDrive
import org.usfirst.frc.team6367.robot.Robot.AutonomousChoice
import org.usfirst.frc.team6367.robot.auto.AutoTrajectory
import org.usfirst.frc.team6367.robot.io.DriverInput
import org.usfirst.frc.team6367.robot.io.RobotOutput

import com.ctre.phoenix.motorcontrol.ControlMode

import wpilib
import driverinput

class TeleopControl :
	
	robotOut = RobotOutput
	
	driverIn = DriverInput 
	
    # @MagicInject
	# LightDrive lightDrive	

	elevator = Elevator 
	
	endEffector = EndEffector 
	
	autoTrajectory = AutoTrajectory  

	def teleopTasks(self):
		Joystick a = driverIn.getDriverStick()
			if a.getRawButton(2):
				endEffector.deployBox()
			elif a.getTrigger():
				endEffector.intake()
			else:
				endEffector.stop()
			if a.getRawButton(5):
				elevator.upPosition()
			elif a.getRawButton(3):
				elevator.midPosition()
			elif a.getRawButton(3):
				elevator.midPosition()
			elif a.getRawButton(4):
				elevator.downPosition()
			elif a.getRawButton(6):
				elevator.upperMiddlePosition()
			if a.getRawButton(7):
				elevator.setElevatorSpeed(0.2)
			if a.getRawButton(8):
				elevator.setElevatorSpeed(0)
			

			if not DriverStation.getInstance().isFMSAttached() and a.getRawButton(11):
				robotOut.elevatorMotor.set(ControlMode.PercentOutput, a.getZ())
		
		robotOut.arcadeDrive(a)

       	SmartDashboard.putNumber("leftEncoder", robotOut.getEncoderLeftSide())
       	SmartDashboard.putNumber("rightEncoder", robotOut.getEncoderRightSide())
       	SmartDashboard.putNumber("elevatorError", robotOut.elevatorMotor.getClosedLoopError(0))
       	
       	SmartDashboard.putNumber("elevatorHeight", elevator.getPosition())
       	
       	SmartDashboard.updateValues()
       	LiveWindow.updateValues()
	