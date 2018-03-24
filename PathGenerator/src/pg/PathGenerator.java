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
				new Waypoint(0.487679984394241,6.55319979029761,0),
				new Waypoint(3.6575998829568,7.31519976591361,0),
				new Waypoint(7.61999975616001,7.01039977566721,-1.047198)
				);

		generateAndWrite("robotLScaleR",
				new Waypoint(0.487679984394241,6.55319979029761,0),
				new Waypoint(4.72439984881921,7.01039977566721,-0.7853985),
				new Waypoint(5.33399982931201,1.8287999414784,-1.570797),
				new Waypoint(6.09599980492801,0.609599980492801,0),
				new Waypoint(7.61999975616001,1.2191999609856,1.047198)
				);
		
		generateAndWrite("robotMScaleL",
				new Waypoint(0.487679984394241,3.6575998829568,0),
				new Waypoint(3.3527998927104,7.61999975616001,0),
				new Waypoint(4.72439984881921,7.61999975616001,0),
				new Waypoint(7.61999975616001,7.01039977566721,-1.047198)
				);
		
		generateAndWrite("robotMScaleR",
				new Waypoint(0.487679984394241,3.6575998829568,0),
				new Waypoint(3.3527998927104,1.2191999609856,0),
				new Waypoint(4.571999853696,1.2191999609856,0),
				new Waypoint(7.61999975616001,1.2191999609856,1.047198)
		);

		generateAndWrite("robotRScaleL",
				new Waypoint(0.487679984394241,1.2191999609856,0),
				new Waypoint(5.33399982931201,1.8287999414784,1.047198),
				new Waypoint(5.33399982931201,6.40079979517441,1.570797),
				new Waypoint(6.09599980492801,7.61999975616001,0),
				new Waypoint(7.61999975616001,7.01039977566721,-1.047198)
		);

		generateAndWrite("robotRScaleR",
				new Waypoint(0.487679984394241,1.523999951232,0),
				new Waypoint(4.571999853696,1.523999951232,0),
				new Waypoint(7.61999975616001,1.2191999609856,1.047198)
		);	

  	generateAndWrite("default",
	        new Waypoint(0,0,0),		
	        new Waypoint(3.5,0,0)		
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
