package step0_com;

import basicmethods.BasicPrintMsg;
import step4_launchme.UOBMainManager;

public class ComManager {

	public ComManager(UOBMainManager _sMainManager) {
		mMainManager = _sMainManager;
	}
	
	/*
	 * Static
	 */
	private static int JUSTIFICATION = 50;
	/*
	 * Data
	 */
	private UOBMainManager mMainManager;

	/**
	 * 
	 * @param _sSender
	 * @param _sMessage
	 */
	public final void display(Object _sSender, String _sMessage) {
		BasicPrintMsg.display(_sSender, _sMessage, JUSTIFICATION);
	}
	
	/**
	 * Display the error message in a visible way and kill the program
	 */
	public final void displayFatalError(Object _sSender, String _sMessage) {
		String lBanderole0 = "!!!!!!!!!!!!!!!!!!!!! Fatal Error !!!!!!!!!!!!!!!!!!!!!!!!";
		String lBanderole1 = "!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!";
		System.out.println();
		BasicPrintMsg.display(_sSender, lBanderole0, JUSTIFICATION);
		displayMultipleLines(_sSender, _sMessage);
		BasicPrintMsg.display(_sSender, lBanderole1, JUSTIFICATION);
		System.out.println();
		System.exit(0);
	}
	
	/**
	 * Display the error message in a visible way, but the process still continues
	 */
	public final void displayWarning(Object _sSender, String _sMessage) {
		String lBanderole0 = "!!!!!!!!!!!!!!! Warning !!!!!!!!!!!!!!!!!!!!!!!!";
		String lBanderole1 = "!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!";
		System.out.println();
		BasicPrintMsg.display(_sSender, lBanderole0, JUSTIFICATION);
		displayMultipleLines(_sSender, _sMessage);
		BasicPrintMsg.display(_sSender, lBanderole1, JUSTIFICATION);
		System.out.println();
	}
	
	/**
	 * 
	 * @param _sSender
	 * @param _sMessage
	 */
	private void displayMultipleLines(Object _sSender, String _sMessage) {
		String[] lArrayLines = _sMessage.split("\n", -1);
		for (String lLine : lArrayLines) {
			BasicPrintMsg.display(_sSender, lLine, JUSTIFICATION);
		}
	}

	/**
	 * 
	 * @param _sSender
	 * @param _sTitle
	 */
	public final void displayTitle(Object _sSender, String _sTitle) {
		BasicPrintMsg.displayTitle(_sSender, _sTitle);
	}
	
	/**
	 * 
	 * @param _sSender
	 * @param _sTitle
	 */
	public final void displaySuperTitle(Object _sSender, String _sTitle) {
		BasicPrintMsg.displaySuperTitle(_sSender, _sTitle);
	}

	
	/*
	 * Getters & Setters
	 */
	public final UOBMainManager getmMainManager() {
		return mMainManager;
	}
	
}
