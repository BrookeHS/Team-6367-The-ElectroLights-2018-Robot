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
			new Waypoint(5.33399982931201,6.85799978054401,0),
			new Waypoint(7.13231977176577,5.94359980980481,0)
		);

		generateAndWrite("robotLScaleR",
			new Waypoint(0.487679984394241,6.70559978542081,0),
			new Waypoint(4.571999853696,7.01039977566721,0),
			new Waypoint(7.13231977176577,2.285999926848,0)
      
		);
		
		generateAndWrite("robotMScaleL",
			new Waypoint(0.487679984394241,3.6575998829568,0),
			new Waypoint(3.3527998927104,7.01039977566721,0),
			new Waypoint(7.13231977176577,5.94359980980481,0)
		);
		
		generateAndWrite("robotMScaleR",
			new Waypoint(0.487679984394241,3.6575998829568,0),
			new Waypoint(4.2671998634496,1.523999951232,0),
			new Waypoint(7.13231977176577,2.285999926848,0)
		);

		generateAndWrite("robotRScaleL",
			new Waypoint(0,0.914399970739201,0),
			new Waypoint(4.571999853696,0.609599980492801,0),
			new Waypoint(6.40079979517441,3.047999902464,-1.570797),
			new Waypoint(6.40079979517441,6.40079979517441,-1.570797),
			new Waypoint(7.01039977566721,7.61999975616001,0),
			new Waypoint(8.22959973665281,1.523999951232,1.570797)
                
		);

		generateAndWrite("robotRScaleR",
			new Waypoint(0.487679984394241,1.523999951232,0),
			new Waypoint(3.9623998732032,1.523999951232,0),
			new Waypoint(6.09599980492801,3.047999902464,-1.570797),
			new Waypoint(7.19327976981505,5.79119981468161,0)			
		);	

  	generateAndWrite("default",
	        new Waypoint(0,0,0),		
	        new Waypoint(1,0,0)		
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
