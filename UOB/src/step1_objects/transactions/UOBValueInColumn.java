package step1_objects.transactions;

import step0_static.UOBStaticColumn.UOBColumn;

public class UOBValueInColumn {

	public UOBValueInColumn(UOBColumn _sUOBColumn, String _sValueStr) {
		mUOBColumn = _sUOBColumn;
		mValueStr = _sValueStr;
		mKeyStr = getKeyStr(_sUOBColumn, _sValueStr);
	}
	
	/*
	 * Data
	 */
	private UOBColumn mUOBColumn;
	private String mValueStr;
	private String mKeyStr;
	
	/**
	 * Unique Key for Map
	 * @param _sUOBColumn
	 * @param _sValueStr
	 * @param _sValue
	 * @return
	 */
	public static String getKeyStr(UOBColumn _sUOBColumn, String _sValueStr) {
		return _sUOBColumn.toString() + "= " + _sValueStr.trim();
	}	
	
	/**
	 * 
	 */
	public String toString() {
		return mKeyStr;
	}
	
	/*
	 * Getters & Setters
	 */
	public final UOBColumn getmUOBColumn() {
		return mUOBColumn;
	}
	public final String getmValueStr() {
		return mValueStr;
	}
	public final String getmKeyStr() {
		return mKeyStr;
	}

}
