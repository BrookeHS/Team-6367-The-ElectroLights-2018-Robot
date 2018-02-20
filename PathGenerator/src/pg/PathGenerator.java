package pg;

import java.io.File;

import jaci.pathfinder.Pathfinder;
import jaci.pathfinder.Trajectory;
import jaci.pathfinder.Waypoint;

public class PathGenerator {
	
	public static final double kMaxVelocity = 1.7;

	public static void main(String[] args) {
		PathfinderLoader.load();
		
		generateAndWrite("robotLScaleL",
			new Waypoint(0,0,0)
		);

		generateAndWrite("robotLScaleR",
				new Waypoint(0,0,0)
		);
		
		generateAndWrite("robotMScaleL",
				new Waypoint(0,0,0)
		);
		
		generateAndWrite("robotMScaleR",
				new Waypoint(0,0,0)
		);

		generateAndWrite("robotRScaleL",
				new Waypoint(0,0,0)
		);

		generateAndWrite("robotRScaleR",
				new Waypoint(0,0,0)
		);	
		
		
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
