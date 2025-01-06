package step2_read_files_uob;

import java.util.ArrayList;
import java.util.List;

import basicmethods.AMNumberTools;
import basicmethods.BasicDateInt;
import basicmethods.BasicPrintMsg;
import basicmethods.BasicString;
import basicmethods.ReadFile;
import step0_static.UOBStatic;
import step1_objects.transactions.UOBTransaction;

public class UOBFile  implements Comparable<UOBFile> {

	protected UOBFile(UOBFileManager _sUOBFileManager, ReadFile _sReadFile) {
		mUOBFileManager = _sUOBFileManager;
		mReadFile = _sReadFile;
		/*
		 * Initiate
		 */
		mListUOBTransaction = new ArrayList<UOBTransaction>();
		computeDate();
	}

	/*
	 * Data
	 */
	private ReadFile mReadFile;
	private String mNameFile;
	private List<UOBTransaction> mListUOBTransaction;
	private double mFinalLedger;
	private boolean mIsAscendingOrDescending;
	private int mDate;
	private String mCurrency;
	private UOBFile mUOBFilePrevious;
	private double mLedgerBalancePreviousFile;
	private String mNameCurrencyFull;
	private UOBFileManager mUOBFileManager;

	/**
	 * 
	 * @param _sUOBTransaction
	 */
	protected final void addNewUOBTransaction(UOBTransaction _sUOBTransaction) {
		mListUOBTransaction.add(_sUOBTransaction);
	}


	/**
	 * Compute Date of file
	 */
	protected final void computeDate() {
		mNameFile = mReadFile.getmNameFile();
		String[] lArray = mNameFile.split("_", -1);
		int lYear = BasicString.getInt(lArray[0]);
		int lMonth = BasicString.getInt(lArray[1]);
		mCurrency = lArray[2].substring(0, 3);
		mNameCurrencyFull = lArray[2];
		mDate = BasicDateInt.getmEndOfMonth(BasicDateInt.getmDateInt(lYear, lMonth, 1));
	}

	/**
	 * 
	 */
	protected final void run() {
		/*
		 * Look for two consecutive UOBTransactions which are not equal
		 */
		int lIdx = -1;
		UOBTransaction lUOBTransaction0 = null;
		UOBTransaction lUOBTransaction1 = null;
		while(++lIdx < mListUOBTransaction.size() - 1) {
			lUOBTransaction0 = mListUOBTransaction.get(lIdx);
			lUOBTransaction1 = mListUOBTransaction.get(lIdx + 1);
			if (AMNumberTools.isEqual(lUOBTransaction0.getmAmount(), lUOBTransaction1.getmAmount())) {
				lUOBTransaction0 = null;
				lUOBTransaction1 = null;
			} else {
				break;
			}
		}
		/*
		 * Check Ascending or Descending
		 */
		if (lUOBTransaction0 == null) {
			mIsAscendingOrDescending = mDate < UOBStatic.getDATE_FILE_UOB_SORT_DESCENDING();
		} else {
			if (AMNumberTools.isEqual(lUOBTransaction1.getmLedgerBalance(), 
					lUOBTransaction0.getmLedgerBalance() - lUOBTransaction0.getmAmount())) {
				mIsAscendingOrDescending = false;
			} else if (AMNumberTools.isEqual(lUOBTransaction1.getmLedgerBalance(), 
					lUOBTransaction0.getmLedgerBalance() + lUOBTransaction1.getmAmount())) {
				mIsAscendingOrDescending = true;
			} else {
				BasicPrintMsg.error("Check file ledge consistency\nFile= " + mNameFile
						+ "\nTransactions are both in ascending and descending orders");
			}
		}
		if (mIsAscendingOrDescending != mDate  < UOBStatic.getDATE_FILE_UOB_SORT_DESCENDING()) {
			BasicPrintMsg.error("UOB changed its sens again !"
					+ "\nName file= " + mNameFile);
		}


	}

	/**
	 * Search for the way the file has been written: ascending transactions or descending ones
	 */
	protected final void computeAscendingOrDescending() {
		if (mListUOBTransaction.size() <= 1) {
			mIsAscendingOrDescending = true;
		} else {
			/*
			 * Get the ledger balance of the previous file
			 */
			mLedgerBalancePreviousFile = 0.;
			if (mUOBFilePrevious != null) {
				mLedgerBalancePreviousFile = mUOBFilePrevious.getmFinalLedger();
			}
			/*
			 * Initiate
			 */
			boolean lIsAscending = true;
			boolean lIsDescending = true;
			UOBTransaction lUOBTransactionPreviousAscending = null;
			UOBTransaction lUOBTransactionPreviousDescending = null;
			/*
			 * Loop on the transactions
			 */
			for (int lIdx = 0; lIdx < mListUOBTransaction.size(); lIdx++) {
				/*
				 * Case Ascending
				 */
				if (lIsAscending) {
					UOBTransaction lUOBTransaction = mListUOBTransaction.get(lIdx);
					lUOBTransactionPreviousAscending = computeIfItWorks(lUOBTransactionPreviousAscending, lUOBTransaction);
					lIsAscending = lUOBTransactionPreviousAscending != null;
				}
				/*
				 * Case Descending
				 */
				if (lIsDescending) {
					UOBTransaction lUOBTransaction = mListUOBTransaction.get(mListUOBTransaction.size() - 1 - lIdx);
					lUOBTransactionPreviousDescending = computeIfItWorks(lUOBTransactionPreviousDescending, lUOBTransaction);
					lIsDescending = lUOBTransactionPreviousDescending != null;
				}
			}
			/*
			 * Deduction
			 */
			if ((lIsAscending && lIsDescending) || (!lIsAscending && !lIsDescending)) {
				BasicPrintMsg.display(this, "I cannot determine wether the UOB file is ascending or descending"
						+ "\n lIsAscending= " + lIsAscending + "; lIsDescending= " + lIsDescending
						+ "\n File= " + mNameFile
						+ "\n LedgerPreviousFile= " + mLedgerBalancePreviousFile
						+ "\n mUOBFilePrevious= " + mUOBFilePrevious);
				mIsAscendingOrDescending = false;
			} else {
				mIsAscendingOrDescending = lIsAscending;
			}
			/*
			 * Double Check
			 */
			if (mDate < UOBStatic.getDATE_FILE_UOB_SORT_DESCENDING() && !mIsAscendingOrDescending) {
				BasicPrintMsg.error("This is not supposed to happen. Issue with the 'UOBStatic.getDATE_FILE_UOB_SORT_DESCENDING()'");
			}
		}
	}

	/**
	 * 
	 * @param _sUOBTransactionPrevious
	 * @param _sUOBTransaction
	 * @return
	 */
	private UOBTransaction computeIfItWorks(UOBTransaction _sUOBTransactionPrevious, UOBTransaction _sUOBTransaction) {
		/*
		 * Initiate
		 */
		double lLedgerBalancePrevious;
		int lDatePrevious;
		/*
		 * Find the previous data
		 */
		if (_sUOBTransactionPrevious != null) {
			lLedgerBalancePrevious = _sUOBTransactionPrevious.getmLedgerBalance();
			lDatePrevious = _sUOBTransactionPrevious.getmDate();
		} else {
			lLedgerBalancePrevious = mLedgerBalancePreviousFile;
			lDatePrevious = 0;
		}
		/*
		 * Test versus the date
		 */
		if (_sUOBTransaction.getmDate() < lDatePrevious) {
			return null;
		}
		/*
		 * Test versus the ledger balance
		 */
		else {
			double lLedgerBalanceTheoritical = lLedgerBalancePrevious + _sUOBTransaction.getmDeposit() - _sUOBTransaction.getmWithdrawal();
			if (AMNumberTools.isEqual(lLedgerBalanceTheoritical, _sUOBTransaction.getmLedgerBalance())) {
				return _sUOBTransaction;
			} else {
				return null;
			}
		}
	}

	/**
	 * Compute final ledger
	 */
	protected final void computeFinalLedger() {
		if (mIsAscendingOrDescending) {
			mFinalLedger = mListUOBTransaction.get(mListUOBTransaction.size() - 1).getmLedgerBalance();
		} else {
			mFinalLedger = mListUOBTransaction.get(0).getmLedgerBalance();
		}
	}

	@Override public int compareTo(UOBFile _sUOBFile) {
		int lCompare = mNameCurrencyFull.compareTo(_sUOBFile.mNameCurrencyFull);
		if (lCompare == 0) {
			lCompare = Integer.compare(mDate, _sUOBFile.mDate);
		}
		return lCompare;
	}

	/**
	 * Classic toString
	 */
	public final String toString() {
		return mNameFile;
	}

	/**
	 * Set the previous UOBFile. Check it is the same currency
	 * @param _sUOBFile
	 */
	protected final void declareUOBFilePrevious(UOBFile _sUOBFile) {
		if (_sUOBFile != null && _sUOBFile.mNameCurrencyFull.equals(mNameCurrencyFull)) {
			mUOBFilePrevious = _sUOBFile;
			/*
			 * Check the date
			 */
			if (!mUOBFilePrevious.getmNameFile().startsWith("0000")
					&& BasicDateInt.getmNumberMonths(mUOBFilePrevious.getmDate(), mDate) > 1) {
				BasicPrintMsg.error("The previous file is more than one month before. Check there is not a missing file"
						+ "\nNameFile current= " + mNameFile
						+ "\nNameFile previous= " + mUOBFilePrevious.getmNameFile());
			}
		}
	}

	/**
	 * Stop program and send an error message
	 * @param _sErroMSg
	 */
	public final void errorInFile(String _sErroMSg) {
		mUOBFileManager.getmUOBMainManager().getmComManager().displayFatalError(this, 
				"There is an error when reading the file"
						+ "\nFile= '" + mReadFile.getmDirPlusNameFile() + "'"
						+ "\n" + _sErroMSg);
	}


	/*
	 * Getters & Setters
	 */
	public final ReadFile getmReadFile() {
		return mReadFile;
	}
	public final String getmNameFile() {
		return mNameFile;
	}
	public final List<UOBTransaction> getmListUOBTransaction() {
		return mListUOBTransaction;
	}
	public final double getmFinalLedger() {
		return mFinalLedger;
	}
	public final boolean getmIsAscendingOrDescending() {
		return mIsAscendingOrDescending;
	}
	public final int getmDate() {
		return mDate;
	}
	public final String getmCurrency() {
		return mCurrency;
	}






}
