package uff.simonalzheimer.main;

import uff.simonalzheimer.SDDLServer;
import uff.simonalzheimer.simulators.SensorsSimulator;

public class Main {

	public static void main(String[] args) throws Exception {
		SDDLServer.main(args);
		Thread.sleep(1000);
		SensorsSimulator.main(args);
	}
}
