package step1_objects.transactions;

import basicmethods.BasicDateInt;
import basicmethods.BasicString;
import basicmethods.BasicTime;
import step0_static.DesignationStatic.Column;
import step0_static.UOBStaticColumn.UOBColumn;
import step1_objects.uobaccount.UOBAccount;

public class UOBTransaction implements Comparable<UOBTransaction> {

	
	protected UOBTransaction(UOBTransactionManager _sUOBTransactionManager,
			UOBAccount _sUOBAccount,
			int _sValueDate,
			int _sDate,
			long _sTime,
			String _sDescription,
			String _sYourReference,
			String _sOurReference,
			String _sChequeNumber,
			String _sRemarks,
			double _sDeposit,
			double _sWithdrawal,
			double _sLedgerBalance) {
		mUOBTransactionManager = _sUOBTransactionManager;
		mUOBAccount = _sUOBAccount;
		mValueDate = _sValueDate;
		mDate = _sDate;
		mTime = _sTime;
		mDescription = _sDescription;
		mYourReference = _sYourReference;
		mOurReference = _sOurReference;
		mChequeNumber = _sChequeNumber;
		mRemarks = _sRemarks;
		mDeposit = _sDeposit;
		mWithdrawal = _sWithdrawal;
		mLedgerBalance = _sLedgerBalance;
		/*
		 * 
		 */
		mKeyStr = getUniqueKey(_sUOBAccount.getmNumber(), _sValueDate, _sDate, _sTime, _sDescription, _sYourReference, _sOurReference, _sChequeNumber, _sRemarks, _sDeposit, _sWithdrawal, _sLedgerBalance);
		/*
		 * We reorder the transaction into a list of UOBTransaction for each value
		 */
		UOBValueInColumnManager lUOBValueInColumnManager = mUOBTransactionManager.getmUOBMainManager().getmUOBValueInColumnManager();		
		lUOBValueInColumnManager.declareNewUOBTransaction(this, UOBColumn.Account, mUOBAccount.getmNumber());
		lUOBValueInColumnManager.declareNewUOBTransaction(this, UOBColumn.ValueDate, mValueDate);
		lUOBValueInColumnManager.declareNewUOBTransaction(this, UOBColumn.Date, mDate);
		lUOBValueInColumnManager.declareNewUOBTransaction(this, UOBColumn.Time, mTime);
		lUOBValueInColumnManager.declareNewUOBTransaction(this, UOBColumn.Description, mDescription);
		lUOBValueInColumnManager.declareNewUOBTransaction(this, UOBColumn.YourReference, mYourReference);
		lUOBValueInColumnManager.declareNewUOBTransaction(this, UOBColumn.OurReference, mOurReference);
		lUOBValueInColumnManager.declareNewUOBTransaction(this, UOBColumn.ChequeNumber, mChequeNumber);
		lUOBValueInColumnManager.declareNewUOBTransaction(this, UOBColumn.Remarks, mRemarks);
		lUOBValueInColumnManager.declareNewUOBTransaction(this, UOBColumn.Deposit, mDeposit);
		lUOBValueInColumnManager.declareNewUOBTransaction(this, UOBColumn.Withdrawal, mWithdrawal);
	}
	
	/*
	 * Intrinsic from UOB
	 */
	private UOBAccount mUOBAccount;
	private int mValueDate;
	private int mDate;
	private long mTime;
	private String mDescription;
	private String mYourReference;
	private String mOurReference;
	private String mChequeNumber;
	private String mRemarks;
	private double mDeposit;
	private double mWithdrawal;
	private double mLedgerBalance;
	/*
	 * Data
	 */
	private String mAccount;
	private String mBKIncome;
	private String mComment;
	private UOBTransactionManager mUOBTransactionManager;
	private String mFileUOBOrigin;
	private String mLineInFileUOBOrigin;
	private String mKeyStr;
	private String mFileLinkedCompta;
	private String mLineInFileLinkedCompta;
	
	public final String toString() {
		return "Date= " + mValueDate
				+ "; Comment= " + getmComment()
				+ "; Amount= " + getmAmount() + " " + mUOBAccount.getmCurrency();
	}
	
	public final String toString2() {
		return "Date= " + mValueDate
				+ "; mDescription= " + mDescription
				+ "; mYourReference= " + mYourReference
				+ "; mOurReference= " + mOurReference
				+ "; mRemarks= " + mRemarks
				+ "; Amount= " + getmAmount() + " " + mUOBAccount.getmCurrency();
	}
	
	@Override
	public int compareTo(UOBTransaction _sUOBTransaction) {
		int lKey1 = Integer.compare(mDate, _sUOBTransaction.mDate);
		if (lKey1 != 0) {
			return lKey1;
		}
		return Long.compare(mTime, _sUOBTransaction.mTime);
	}	
	
	/**
	 * @return Cash flow
	 */
	public final double getmAmount() {
		return mDeposit - mWithdrawal;
	}
	
	/**
	 * Unique Key for Map of the manager
	 * @return
	 */
	public static String getUniqueKey(long _sUOBAccountNumber,
			int _sValueDate,
			int _sDate,
			long _sTime,
			String _sDescription,
			String _sYourReference,
			String _sOurReference,
			String _sChequeNumber,
			String _sRemarks,
			double _sDeposit,
			double _sWithdrawal,
			double _sLedgerBalance) {
		return getStrFromValueAccountNumber(_sUOBAccountNumber) 
				+ ";;" + getStrFromValueDate(_sValueDate)
				+ ";;" + getStrFromValueDate(_sDate)
				+ ";;" + getStrFromValueTime(_sTime)
				+ ";;" + _sDescription
				+ ";;" + _sYourReference
				+ ";;" + _sOurReference
				+ ";;" + _sChequeNumber
				+ ";;" + _sRemarks
				+ ";;" + getStrFromValueDollar(_sDeposit)
				+ ";;" + getStrFromValueDollar(_sWithdrawal)
				+ ";;" + getStrFromValueDollar(_sLedgerBalance);
	}
	
	/**
	 * 
	 * @param _sAccountNumberStr
	 * @return
	 */
	public static long getValueFromStrAccountNumber(String _sAccountNumberStr) {
		return BasicString.getLong(_sAccountNumberStr.trim());
	}
	
	/**
	 * 
	 * @param _sAccountNumber
	 * @return
	 */
	public static String getStrFromValueAccountNumber(long _sAccountNumber) {
		return "" + _sAccountNumber;
	}
	
	/**
	 * 
	 * @param _sDateStr
	 * @return
	 */
	public static int getValueFromStrDate(String _sDateStr) {
		return BasicDateInt.getmDateFromString(_sDateStr.trim().replaceAll(" ", ""), false);
	}
	
	/**
	 * 
	 * @param _sDate
	 * @return
	 */
	public static String getStrFromValueDate(int _sDate) {
		return "" + _sDate;
	}
	
	/**
	 * 
	 * @param _sTimeStr
	 * @return
	 */
	public static long getValueFromStrTime(String _sTimeStr) {
		return BasicTime.getHeureJavaFromStringHHMMSSsss(_sTimeStr.replaceAll(" ", ""));
	}
	
	/**
	 * 
	 * @param _sTime
	 * @return
	 */
	public static String getStrFromValueTime(long _sTime) {
		return BasicTime.getHeureTexteHHMMSSFromLong(_sTime);
	}
	
	/**
	 * 
	 * @param _sDollarStr
	 * @return
	 */
	public static double getValueFromStrDollar(String _sDollarStr) {
		return BasicString.getDouble(_sDollarStr.replaceAll(",", ""));
	}
	
	/**
	 * 
	 * @param _sDollar
	 * @return
	 */
	public static String getStrFromValueDollar(double _sDollar) {
		String lDollarStr = "" + _sDollar;
		if (lDollarStr.contains(".")) {
			int lIdxPoint = lDollarStr.indexOf('.');
			int lNbDecimals = lDollarStr.length() - 1 - lIdxPoint;
			int lNbDecimalsToDelete = lNbDecimals - Math.min(5,  lNbDecimals);
			if (lNbDecimalsToDelete > 0) {
				lDollarStr = lDollarStr.substring(0, lDollarStr.length() - lNbDecimalsToDelete);
			}
		}
		return lDollarStr;
	}

	/**
	 * 
	 * @param _sUOBTransaction
	 * @return
	 */
	public String getmValueInColumn(Column _sColumn) {
		String lDesignationStr = null;
		switch (_sColumn) {
		case Remarks : lDesignationStr = mRemarks; break;
		case YourReference : lDesignationStr = mYourReference; break;
		case OurReference : lDesignationStr = mOurReference; break;
		case Description : lDesignationStr = mDescription; break;
		}
		if (lDesignationStr != null) {
			lDesignationStr = lDesignationStr.trim();
		}
		return lDesignationStr;
	}

	/**
	 * Replace all the ',' into ';' so it does not pollute a future CSV where we will write the comment
	 * @param mComment
	 */
	public final void setmComment(String _sComment) {
		String lComment = "Description= '" + getmDescription() + "'"
				+ "| Your Reference= '" + getmYourReference() + "'"
				+ "| Our Reference= '" + getmOurReference() + "'"
				+ "| Remarks= '" + getmRemarks() + "'";
		mComment = lComment + "| " + _sComment.replaceAll(",", ";");
		mComment = mComment.replaceAll(",", "");
	}

	
	/*
	 * Getters & Setters
	 */
	public final UOBAccount getmUOBAccount() {
		return mUOBAccount;
	}
	public final int getmValueDate() {
		return mValueDate;
	}
	public final int getmDate() {
		return mDate;
	}
	public final long getmTime() {
		return mTime;
	}
	public final String getmDescription() {
		return mDescription;
	}
	public final String getmYourReference() {
		return mYourReference;
	}
	public final String getmOurReference() {
		return mOurReference;
	}
	public final String getmChequeNumber() {
		return mChequeNumber;
	}
	public final String getmRemarks() {
		return mRemarks;
	}
	public final double getmDeposit() {
		return mDeposit;
	}
	public final double getmWithdrawal() {
		return mWithdrawal;
	}
	public final double getmLedgerBalance() {
		return mLedgerBalance;
	}
	public final String getmBKIncome() {
		return mBKIncome;
	}
	public final void setmBKincome(String mCategory) {
		this.mBKIncome = mCategory;
	}
	public final String getmAccount() {
		return mAccount;
	}
	public final void setmAccount(String mAccount) {
		this.mAccount = mAccount;
	}
	public final String getmComment() {
		return mComment;
	}
	public final UOBTransactionManager getmUOBTransactionManager() {
		return mUOBTransactionManager;
	}
	public final String getmFileUOBOrigin() {
		return mFileUOBOrigin;
	}
	public final void setmFileUOBOrigin(String mFileUOBOrigin) {
		this.mFileUOBOrigin = mFileUOBOrigin;
	}
	public final String getmLineInFileUOBOrigin() {
		return mLineInFileUOBOrigin;
	}
	public final void setmLineInFileUOBOrigin(String mLineInFileUOBOrigin) {
		this.mLineInFileUOBOrigin = mLineInFileUOBOrigin;
	}
	public final String getmKeyStr() {
		return mKeyStr;
	}

	public final String getmFileLinkedCompta() {
		return mFileLinkedCompta;
	}

	public final void setmFileLinkedCompta(String mFileLinkedCompta) {
		this.mFileLinkedCompta = mFileLinkedCompta;
	}

	public final String getmLineInFileLinkedCompta() {
		return mLineInFileLinkedCompta;
	}

	public final void setmLineInFileLinkedCompta(String mLineInFileLinkedCompta) {
		this.mLineInFileLinkedCompta = mLineInFileLinkedCompta;
	}
	
	
}
