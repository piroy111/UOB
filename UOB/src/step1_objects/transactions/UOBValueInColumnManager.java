package step1_objects.transactions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import step0_static.UOBStaticColumn.UOBColumn;

public class UOBValueInColumnManager {

	public UOBValueInColumnManager() {
		/*
		 * 
		 */
		mMapKeyStrToUOBValueInColumn = new HashMap<>();
		mMapUOBValueInColumnToListUOBTransaction = new HashMap<>();
	}


	/*
	 * Data
	 */
	private Map<String, UOBValueInColumn> mMapKeyStrToUOBValueInColumn;
	private Map<UOBValueInColumn, List<UOBTransaction>> mMapUOBValueInColumnToListUOBTransaction;

	/**
	 * Classic get or create
	 * @param _sUOBColumn
	 * @param _sValueStr
	 * @return
	 */
	public final UOBValueInColumn getmOrCreateUOBValueInColumn(UOBColumn _sUOBColumn, String _sValueStr) {
		String lKeyStr = UOBValueInColumn.getKeyStr(_sUOBColumn, _sValueStr);
		UOBValueInColumn lUOBValueInColumn = mMapKeyStrToUOBValueInColumn.get(lKeyStr);
		if (lUOBValueInColumn == null) {
			lUOBValueInColumn = new UOBValueInColumn(_sUOBColumn, _sValueStr);
			mMapKeyStrToUOBValueInColumn.put(lKeyStr, lUOBValueInColumn);
		}
		return lUOBValueInColumn;
	}

	/**
	 * 
	 * @param _sUOBTransaction
	 * @param _sUOBColumn
	 * @param _sValueStr
	 */
	public final void declareNewUOBTransaction(UOBTransaction _sUOBTransaction, UOBColumn _sUOBColumn, Object _sValue) {
		String lValueStr = _sValue.toString();
		List<UOBTransaction> lListUOBTransaction = getmListUOBTransaction(_sUOBColumn, lValueStr);
		if (!lListUOBTransaction.contains(_sUOBTransaction)) {
			lListUOBTransaction.add(_sUOBTransaction);
		}
	}
	
	/**
	 * 
	 * @param _sUOBColumn
	 * @param _sValueStr
	 * @return
	 */
	public final List<UOBTransaction> getmListUOBTransaction(UOBColumn _sUOBColumn, String _sValueStr) {
		UOBValueInColumn lUOBValueInColumn = getmOrCreateUOBValueInColumn(_sUOBColumn, _sValueStr);
		List<UOBTransaction> lListUOBTransaction = mMapUOBValueInColumnToListUOBTransaction.get(lUOBValueInColumn);
		if (lListUOBTransaction == null) {
			lListUOBTransaction = new ArrayList<>();
			mMapUOBValueInColumnToListUOBTransaction.put(lUOBValueInColumn, lListUOBTransaction);
		}
		return lListUOBTransaction;
	}
	
	/**
	 * Give the list of transaction which have the values. If there is no transaction at all which has a value, then we skip it.<br>
	 * @param _sAccountNumber
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
	 * @return
	 */
	public final List<UOBTransaction> getmGuessMatch(long _sAccountNumber, 
			int _sValueDate, int _sDate, long _sTime, String _sDescription,
			String _sYourReference, String _sOurReference, String _sChequeNumber,
			String _sRemarks, double _sDeposit, double _sWithdrawal) {
		List<UOBTransaction> lListUOBTransactionRetained = null;
		lListUOBTransactionRetained = computeListUOBTransactionRetained(lListUOBTransactionRetained, UOBColumn.Account, UOBTransaction.getStrFromValueAccountNumber(_sAccountNumber));
		lListUOBTransactionRetained = computeListUOBTransactionRetained(lListUOBTransactionRetained, UOBColumn.ValueDate, UOBTransaction.getStrFromValueDate(_sValueDate));
		lListUOBTransactionRetained = computeListUOBTransactionRetained(lListUOBTransactionRetained, UOBColumn.Date, "" + UOBTransaction.getStrFromValueDate(_sDate));
		lListUOBTransactionRetained = computeListUOBTransactionRetained(lListUOBTransactionRetained, UOBColumn.Time, UOBTransaction.getStrFromValueTime(_sTime));
		lListUOBTransactionRetained = computeListUOBTransactionRetained(lListUOBTransactionRetained, UOBColumn.Description, _sDescription);
		lListUOBTransactionRetained = computeListUOBTransactionRetained(lListUOBTransactionRetained, UOBColumn.YourReference, _sYourReference);
		lListUOBTransactionRetained = computeListUOBTransactionRetained(lListUOBTransactionRetained, UOBColumn.OurReference, _sOurReference);
		lListUOBTransactionRetained = computeListUOBTransactionRetained(lListUOBTransactionRetained, UOBColumn.ChequeNumber, _sChequeNumber);
		lListUOBTransactionRetained = computeListUOBTransactionRetained(lListUOBTransactionRetained, UOBColumn.Remarks, _sRemarks);
		lListUOBTransactionRetained = computeListUOBTransactionRetained(lListUOBTransactionRetained, UOBColumn.Deposit, UOBTransaction.getStrFromValueDollar(_sDeposit));
		lListUOBTransactionRetained = computeListUOBTransactionRetained(lListUOBTransactionRetained, UOBColumn.Withdrawal, UOBTransaction.getStrFromValueDollar(_sWithdrawal));
		return lListUOBTransactionRetained;
	}

	/**
	 * 
	 * @param _sListUOBTransactionRetained
	 * @return
	 */
	private List<UOBTransaction> computeListUOBTransactionRetained(List<UOBTransaction> _sListUOBTransactionRetained, 
			UOBColumn _sUOBColumn, String _sValueStr) {
		List<UOBTransaction> lListUOBTransactionChallenge = getmListUOBTransaction(_sUOBColumn, _sValueStr);
		if (_sListUOBTransactionRetained == null || _sListUOBTransactionRetained.size() == 0) {
			return new ArrayList<>(lListUOBTransactionChallenge);
		} else if (lListUOBTransactionChallenge.size() == 0) {
			return _sListUOBTransactionRetained;
		} else {
			List<UOBTransaction> lListUOBTransactionToRemove = new ArrayList<>();
			for (UOBTransaction lUOBTransaction : _sListUOBTransactionRetained) {
				if (!lListUOBTransactionChallenge.contains(lUOBTransaction)) {
					lListUOBTransactionToRemove.add(lUOBTransaction);
				}
			}
			_sListUOBTransactionRetained.removeAll(lListUOBTransactionToRemove);
			return _sListUOBTransactionRetained;
		}
	}
	
	
	
	
	
	
	
	
	
	
}
