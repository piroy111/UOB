package step3_write_files_uob;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import basicmethods.BasicDateInt;
import basicmethods.BasicFichiers;
import basicmethods.BasicPrintMsg;
import step0_static.UOBStatic;
import step1_objects.transactions.UOBTransaction;
import step4_launchme.UOBMainManager;

public class WriteAllUOBTransactions {

	
	public WriteAllUOBTransactions(UOBMainManager _sUOBMainManager) {
		pUOBMainManager = _sUOBMainManager;
		/*
		 * 
		 */
		BasicFichiers.getOrCreateDirectory(UOBStatic.getOUTPUT_FILES());
	}
	
	/*
	 * Data
	 */
	private UOBMainManager pUOBMainManager;
	
	/**
	 * 
	 */
	public final void run() {
		BasicPrintMsg.displayTitle(this, "Write files output");
		List<UOBTransaction> lListUOBTransaction = pUOBMainManager.getmUOBTransactionManager().getmListUOBTransaction();
		Collections.sort(lListUOBTransaction);
		List<String> lListLineToWrite = new ArrayList<>();
		for (UOBTransaction lUOBTransaction : lListUOBTransaction) {
			String lComment = "OurRef= '" + lUOBTransaction.getmOurReference().replaceAll(",", ";") + "'"
					+ "; YourRef= '" + lUOBTransaction.getmYourReference().replaceAll(",", ";") + "'"
					+ "; Remarks= '" + lUOBTransaction.getmRemarks().replaceAll(",", ";") + "'"
					+ "; From file '" + lUOBTransaction.getmFileUOBOrigin() + "'";
			String lLine = lUOBTransaction.getmDate()
					+ "," + lUOBTransaction.getmUOBAccount().getmCurrency()
					+ "," + lComment
					+ "," + lUOBTransaction.getmAmount()
					+ "," + "NaN"
					+ ",,";
			lListLineToWrite.add(lLine);
		}
		String lHeader = "Date of the transaction,BKASset (as per names in the conf file 'Prices_histo_assets.csv'),Comment,Quantity,Price (for physical assets write NaN; for paper assets we must have a price executed),BKAccount (email as per the emails in the conf file 'Accounts and currency.csv'),BKIncome (as per names in the conf file 'BKIncome.csv')";
		String lDir = UOBStatic.getOUTPUT_FILES();
		String lNameFile = BasicDateInt.getmToday() + UOBStatic.getNAME_OUTPUT_TRANSACTIONS();
		BasicFichiers.writeFile(lDir, lNameFile, lHeader, lListLineToWrite);
		BasicPrintMsg.display(this, "File writtent= '" + lDir + lNameFile + "'");
	}
	
}
