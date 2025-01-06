package step0_static;

public class UOBStatic {

	/*
	 * DIR
	 */
	private static String DIR;
	private static String RAW_FILES = "01_Raw_files_from_UOB";
	private static String OUTPUT_FILES = "02_Files_output_int_compta_format";
	/*
	 * Constant
	 */
	private static int DATE_FILE_UOB_SORT_DESCENDING = 20180401;
	private static String NAME_FILE_UOB_ACCOUNT_STATEMENT = "_AccountStatement.csv";
	private static String NAME_OUTPUT_TRANSACTIONS = "_UOB_Transactions.csv";

	/*
	 * Getters & Setters
	 */
	public static final String getDIR() {
		return DIR;
	}
	public static final void setDIR(String _sDIR) {
		DIR = _sDIR;
	}
	public static final String getRAW_FILES() {
		return DIR + RAW_FILES;
	}
	public static final String getOUTPUT_FILES() {
		return DIR + OUTPUT_FILES;
	}
	public static final String getNAME_FILE_UOB_ACCOUNT_STATEMENT() {
		return NAME_FILE_UOB_ACCOUNT_STATEMENT;
	}
	public static final int getDATE_FILE_UOB_SORT_DESCENDING() {
		return DATE_FILE_UOB_SORT_DESCENDING;
	}
	public static final String getNAME_OUTPUT_TRANSACTIONS() {
		return NAME_OUTPUT_TRANSACTIONS;
	}
	
	
}
