package pg;

import java.io.File;

public class PathfinderLoader {
	
	/**
	 * Magic that allows pathfinder to work on a PC
	 */
	public static void load() {
		if (System.getProperty("os.name").startsWith("Windows")) {
			File soFile = new File("lib/pathfinderjava.dll");
			System.load(soFile.getAbsolutePath());
		} else {
			File soFile = new File("lib/libpathfinderjava.so");
			System.load(soFile.getAbsolutePath());
		}
	}

}
