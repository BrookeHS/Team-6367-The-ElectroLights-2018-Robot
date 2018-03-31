
from pathfinder import Waypoint

def paths():
		
		p = {}
		
		def generateAndWrite(n, *w):
			p[n] = w
		generateAndWrite("robotLScaleL",
				Waypoint(0.502919983906561,6.70559978542081,0),
				Waypoint(3.6575998829568,7.61999975616001,0),
				Waypoint(7.31519976591361,7.16279977079041,-0.872665)
				);

		generateAndWrite("robotLScaleR",
				Waypoint(0.502919983906561,6.70559978542081,0),
				Waypoint(4.87679984394241,6.70559978542081,-0.7853985),
				Waypoint(5.48639982443521,1.8287999414784,-1.570797),
				Waypoint(6.09599980492801,0.609599980492801,0),
				Waypoint(7.01039977566721,0.975359968788481,0.872665)
				);
		
		generateAndWrite("robotMScaleL",
				Waypoint(0.502919983906561,3.6575998829568,0),
				Waypoint(3.36803989222272,7.61999975616001,0),
				Waypoint(4.73963984833153,7.61999975616001,0),
				Waypoint(7.31519976591361,7.16279977079041,-0.872665)
				);
		
		generateAndWrite("robotMScaleR",
				Waypoint(0.502919983906561,3.6575998829568,0),
				Waypoint(3.36803989222272,1.2191999609856,0),
				Waypoint(4.58723985320833,1.2191999609856,0),
				Waypoint(7.01039977566721,0.975359968788481,0.872665)
		);

		generateAndWrite("robotRScaleL",
				Waypoint(0.502919983906561,1.2191999609856,0),
				Waypoint(5.48639982443521,1.8287999414784,1.047198),
				Waypoint(5.48639982443521,6.40079979517441,1.570797),
				Waypoint(6.09599980492801,7.61999975616001,0),
				Waypoint(7.31519976591361,7.16279977079041,-0.872665)
		);

		generateAndWrite("robotRScaleR",
				Waypoint(0.502919983906561,1.523999951232,0),
				Waypoint(4.58723985320833,1.523999951232,0),
				Waypoint(7.01039977566721,0.975359968788481,0.872665)
		);

		
		# Start switch code
		generateAndWrite("robotLSwitchL",
				Waypoint(0.502919983906561,6.70559978542081,0),
				Waypoint(3.3527998927104,6.85799978054401,-0.872665)
				);

		generateAndWrite("robotLSwitchR",
				Waypoint(0.502919983906561,6.70559978542081,0),
				Waypoint(1.6763999463552,4.2671998634496,-1.570797),
				Waypoint(1.3715999561088,2.1335999317248,-1.047198),
				Waypoint(3.3527998927104,1.3715999561088,0.872665)
				);
		
		generateAndWrite("robotMSwitchL",
				Waypoint(0.502919983906561,3.6575998829568,0),
				Waypoint(1.8287999414784,6.70559978542081,1.3089975),
				Waypoint(2.4383999219712,7.01039977566721,0),
				Waypoint(3.3527998927104,6.85799978054401,-0.872665)
				);
		
		generateAndWrite("robotMSwitchR",
				Waypoint(0.502919983906561,3.6575998829568,0),
				Waypoint(2.1335999317248,2.7431999122176,0)
		);

		generateAndWrite("robotRSwitchL",
				Waypoint(0.502919983906561,1.523999951232,0),
				Waypoint(1.2191999609856,2.1335999317248,0.7853985),
				Waypoint(1.2191999609856,6.40079979517441,1.396264),
				Waypoint(3.3527998927104,6.85799978054401,-0.872665)
		);

		generateAndWrite("robotRSwitchR",
				Waypoint(0.502919983906561,1.523999951232,0),
				Waypoint(3.3527998927104,1.3715999561088,0.872665)
		);

		
		generateAndWrite("default",
		    Waypoint(0,0,0),
		    Waypoint(3.5,0,0)
		);
		
		return p
