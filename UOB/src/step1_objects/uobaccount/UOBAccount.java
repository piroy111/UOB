package step1_objects.uobaccount;

public class UOBAccount {

	protected UOBAccount(UOBAccountManager _sUOBAccountManager, String _sCurrency, long _sNumber) {
		mUOBAccountManager = _sUOBAccountManager;
		mCurrency = _sCurrency;
		mNumber = _sNumber;
	}
	
	/*
	 * Data
	 */
	private String mCurrency;
	private long mNumber;
	private UOBAccountManager mUOBAccountManager;
	private String mNameFileFromPrevious;

	/**
	 * Check there is not a change in currency in the file UOB (very unlikely)
	 * @param _sNameFileFrom
	 * @param _sCurrency
	 */
	public final void checkCurrency(String _sNameFileFrom, String _sCurrency) {
		if (mNameFileFromPrevious == null) {
			mNameFileFromPrevious = _sNameFileFrom;
		} else {
			if (!_sCurrency.equals(mCurrency)) {
				mUOBAccountManager.getmUOBMainManager().getmComManager().display(this, 
						"One UOB account has two different currencies from two different files"
						+ "\nUOBAccount ID number= " + mNumber
						+ "\nFile= '" + mNameFileFromPrevious + "' --> Currency= " + mCurrency
						+ "\nFile= '" + _sNameFileFrom + "' --> Currency= " + _sCurrency);
			}
		}
	}
	
	
	/*
	 * Getters & Setters
	 */
	public final String getmCurrency() {
		return mCurrency;
	}
	public final long getmNumber() {
		return mNumber;
	}
	public final UOBAccountManager getmUOBAccountManager() {
		return mUOBAccountManager;
	}
	
}
