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
		new Waypoint(0,7.31519976591361,0),
     	new Waypoint(7.01039977566721,7.31519976591361,0),
     	new Waypoint(8.22959973665281,6.40079979517441,-1.570797)
		);

		generateAndWrite("robotLScaleR",
		new Waypoint(0,7.31519976591361,0),
		new Waypoint(4.571999853696,7.61999975616001,0),
      	new Waypoint(6.40079979517441,5.18159983418881,-1.570797),
      	new Waypoint(6.40079979517441,1.8287999414784,-1.570797),
      	new Waypoint(7.01039977566721,0.609599980492801,0),
      	new Waypoint(8.22959973665281,1.523999951232,1.570797)
      
		);
		
		generateAndWrite("robotMScaleL",
		new Waypoint(0,3.962399873,0),
		new Waypoint(0.3047999902,3.962399873,0),
		new Waypoint(4.267199863,7.315199766,0),
		new Waypoint(7.315199766,7.315199766,0),
		new Waypoint(8.229599737,6.400799795,-1.570797)
		);
		
		generateAndWrite("robotMScaleR",
		new Waypoint(0,3.9623998732032,0),
		new Waypoint(2.7431999122176,0.914399970739201,0),
		new Waypoint(7.01039977566721,0.609599980492801,0),
		new Waypoint(8.22959973665281,1.8287999414784,1.570797)
		);

		generateAndWrite("robotRScaleL",
        new Waypoint(0,0.914399970739201,0),
        new Waypoint(4.571999853696,0.609599980492801,0),
        new Waypoint(6.40079979517441,3.047999902464,-1.570797),
        new Waypoint(6.40079979517441,6.40079979517441,-1.570797),
        new Waypoint(7.01039977566721,7.61999975616001,0),
        new Waypoint(8.22959973665281,6.40079979517441,1.570797)
                
		);

		generateAndWrite("robotRScaleR",
        new Waypoint(5.18159983418881,0.609599980492801,0),		
        new Waypoint(7.31519976591361,0.609599980492801,0),		
        new Waypoint(8.22959973665281,1.8287999414784,-1.570797)			
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
