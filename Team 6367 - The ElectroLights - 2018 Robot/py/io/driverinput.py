import wpilib

class DriverInput:
    
    def __init__(self):
        self.driverStick = wpilib.Joystick(0)
        self.mechanismStick = wpilib.Joystick(1)
    
    def getDriverStick(self) -> wpilib.Joystick:
        return self.driverStick
    
    def getMechanismStick(self) -> wpilib.Joystick:
        return self.mechanismStick
