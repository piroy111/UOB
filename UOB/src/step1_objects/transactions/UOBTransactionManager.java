package step1_objects.transactions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import step1_objects.uobaccount.UOBAccount;
import step4_launchme.UOBMainManager;

public class UOBTransactionManager {

	public UOBTransactionManager(UOBMainManager _sMainManager) {
		UOBMainManager = _sMainManager;
		/*
		 * 
		 */
		mMapKeyToUOBTransaction = new HashMap<>();
		mListUOBTransaction = new ArrayList<>();
		mMapDateToListUOBTransaction = new HashMap<>();
	}
	
	/*
	 * Data
	 */
	private UOBMainManager UOBMainManager;
	private Map<String, UOBTransaction> mMapKeyToUOBTransaction;
	private List<UOBTransaction> mListUOBTransaction;
	private Map<Integer, List<UOBTransaction>> mMapDateToListUOBTransaction;
	
	/**
	 * Classic get or create. Note the unique key contains double, and we use a rounding method to get the String
	 * @param _sUOBAccount
	 * @param _sValueDate
	 * @param _sDate
	 * @param _sTime
	 * @param _sDescription
	 * @param _sYourReference
	 * @param _sOurReference
	 * @param _sChequeNumber
	 * @param _sRemarks
	 * @param _sDeposit
	 * @param _sWithdrawal
	 * @param _sLedgerBalance
	 * @return
	 */
	public final UOBTransaction getmOrCreateUOBTransaction(UOBAccount _sUOBAccount,
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
		String lKeyStr = UOBTransaction.getUniqueKey(_sUOBAccount.getmNumber(), _sValueDate, _sDate, _sTime, _sDescription, _sYourReference, _sOurReference, _sChequeNumber, _sRemarks, _sDeposit, _sWithdrawal, _sLedgerBalance);
		UOBTransaction lUOBTransaction = mMapKeyToUOBTransaction.get(lKeyStr);
		if (lUOBTransaction == null) {
			lUOBTransaction = new UOBTransaction(this, _sUOBAccount, _sValueDate, _sDate, _sTime, _sDescription, _sYourReference, _sOurReference, _sChequeNumber, _sRemarks, _sDeposit, _sWithdrawal, _sLedgerBalance);
			mMapKeyToUOBTransaction.put(lKeyStr, lUOBTransaction);
			mListUOBTransaction.add(lUOBTransaction);
			getmOrCreateListUOBTransactionForDate(lUOBTransaction.getmDate()).add(lUOBTransaction);
		}
		return lUOBTransaction;
	}
	
	/**
	 * 
	 * @param _sDate
	 * @return
	 */
	public final List<UOBTransaction> getmOrCreateListUOBTransactionForDate(int _sDate) {
		List<UOBTransaction> lListUOBTransaction = mMapDateToListUOBTransaction.get(_sDate);
		if (lListUOBTransaction == null) {
			lListUOBTransaction = new ArrayList<>();
			mMapDateToListUOBTransaction.put(_sDate, lListUOBTransaction);
		}
		return lListUOBTransaction;
	}
	
	/*
	 * Getters & Setters
	 */
	public final UOBMainManager getmUOBMainManager() {
		return UOBMainManager;
	}
	public final Map<String, UOBTransaction> getmMapKeyToUOBTransaction() {
		return mMapKeyToUOBTransaction;
	}
	public final List<UOBTransaction> getmListUOBTransaction() {
		return mListUOBTransaction;
	}
	public final Map<Integer, List<UOBTransaction>> getmMapDateToListUOBTransaction() {
		return mMapDateToListUOBTransaction;
	}

}
