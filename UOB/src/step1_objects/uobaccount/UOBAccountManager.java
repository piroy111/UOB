package step1_objects.uobaccount;

import java.util.HashMap;
import java.util.Map;

import step4_launchme.UOBMainManager;

public class UOBAccountManager {

	public UOBAccountManager(UOBMainManager _sUOBMainManager) {
		mUOBMainManager = _sUOBMainManager;
		/*
		 * 
		 */
		mMapIDNumberToUOBAccount = new HashMap<>();
	}
	
	/*
	 * Data
	 */
	private UOBMainManager mUOBMainManager;
	private Map<Long, UOBAccount> mMapIDNumberToUOBAccount;

	/**
	 * @return Classic get or create
	 * @param _sNumber : number of the account
	 * @param _sCurrency : currency of the account
	 */
	public final UOBAccount getmOrCreateUOBAccount(long _sNumber, String _sCurrency) {
		UOBAccount lUOBAccount = mMapIDNumberToUOBAccount.get(_sNumber);
		if (lUOBAccount == null) {
			lUOBAccount = new UOBAccount(this, _sCurrency, _sNumber);
			mMapIDNumberToUOBAccount.put(_sNumber, lUOBAccount);
		}
		return lUOBAccount;
	}

	/*
	 * Getters & Setters
	 */
	public final Map<Long, UOBAccount> getmMapIDNumberToUOBAccount() {
		return mMapIDNumberToUOBAccount;
	}
	public final UOBMainManager getmUOBMainManager() {
		return mUOBMainManager;
	}
	

	
}
