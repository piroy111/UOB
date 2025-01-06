package step4_launchme;

import basicmethods.BasicDetectDir;

public class UOBLaunchMe {

	public static void main(String[] _sArgs) {
		new UOBMainManager(BasicDetectDir.getMainStrFromArgsOfBatFile(_sArgs)).run();
	}
	
	
	
	
	
}
