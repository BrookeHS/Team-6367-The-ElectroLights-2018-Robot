package pg;

import java.io.File;

import jaci.pathfinder.Pathfinder;
import jaci.pathfinder.Trajectory;
import jaci.pathfinder.Waypoint;

public class PathGenerator {
	
	public static final double kMaxVelocity = 1.7;

	public static void main(String[] args) {
		PathfinderLoader.load();
		// Test trajectory
		generateAndWrite("testTrajectory",
				new Waypoint(0, 0, Pathfinder.d2r(0)),
				new Waypoint(1, 1, Pathfinder.d2r(45))
		);
		// Vehicle starts on the left side, and the scale is on the right side.
		generateAndWrite("vehicleLeftScaleRight",
				new Waypoint(0,0,0),
				new Waypoint(7.2296, 0 , Pathfinder.d2r(45))
//				new Waypoint(0, 8.2296, Pathfinder.d2r(80)),
//				new Waypoint(0.762, 8.2296, Pathfinder.d2r(90))		
		);
		/*
		// Vehicle starts on the left side, and the scale is on the left side.
		generateAndWrite("vehicleLeftScaleLeft",
				new Waypoint(0, 0, 0), 
				new Waypoint(21.5, 0, Pathfinder.d2r(-90)),
				new Waypoint(21.5, -19, 0), 
				new Waypoint(27, -19, Pathfinder.d2r(90)),
				new Waypoint(27, -17, Pathfinder.d2r(90))
		);
		// Vehicle starts in the middle, and the scale is on the right side.
		generateAndWrite("vehicleMiddleScaleRight",
				new Waypoint(0, 0, 0), 
				new Waypoint(6.5, 0, Pathfinder.d2r(90)),
				new Waypoint(6.5, 12, 0), 
				new Waypoint(27, 12, Pathfinder.d2r(-90)),
				new Waypoint(27, 9.5, Pathfinder.d2r(-90))
		);
		// Vehicle starts in the middle, and the scale is on the left side.
		generateAndWrite("vehicleMiddleScaleLeft",
				new Waypoint(0, 0, 0), 
				new Waypoint(6.5, 0, Pathfinder.d2r(-90)),
				new Waypoint(6.5, -12, 0), 
				new Waypoint(27, -12, Pathfinder.d2r(90)),
				new Waypoint(27, -9.5, Pathfinder.d2r(90))
		);
		// Vehicle starts on the right side, and the scale is on the right side.
		generateAndWrite("vehicleRightScaleRight",
				new Waypoint(0, 0, 0), 
				new Waypoint(21.5, 0, Pathfinder.d2r(90)),
				new Waypoint(21.5, 19, 0), 
				new Waypoint(27, 19, Pathfinder.d2r(-90)),
				new Waypoint(27, 17, Pathfinder.d2r(-90))
		);
		// Vehicle starts on the right side, and the scale is on the left side.
		generateAndWrite("vehicleRightScaleLeft",
				new Waypoint(0, 0, 0), 
				new Waypoint(27, 0, Pathfinder.d2r(90)),
				new Waypoint(27, 2.5, Pathfinder.d2r(90))
		);
	
*/	
		
		
		// TODO: generate more of these things
	}
	
	public static void generateAndWrite(String fileName, Waypoint... points) {
		
		Trajectory.Config config = new Trajectory.Config(Trajectory.FitMethod.HERMITE_CUBIC,
				Trajectory.Config.SAMPLES_HIGH, 0.02, kMaxVelocity, 2.0, 60.0);
		
		// After you generate the waypoints, define the trajectory.
		Trajectory trajectory = Pathfinder.generate(points, config);
		
		// Write it to the named file
		File file = new File(fileName + ".csv");
		Pathfinder.writeToCSV(file, trajectory);
		System.out.println(file + ": trajectory length of " + trajectory.length());
		
	}
	
	
	

}
