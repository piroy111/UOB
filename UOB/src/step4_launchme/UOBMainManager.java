package step4_launchme;

import basicmethods.BasicPrintMsg;
import step0_com.ComManager;
import step0_static.UOBStatic;
import step1_objects.transactions.UOBTransactionManager;
import step1_objects.transactions.UOBValueInColumnManager;
import step1_objects.uobaccount.UOBAccountManager;
import step2_read_files_uob.UOBFileManager;
import step3_write_files_uob.WriteAllUOBTransactions;

public class UOBMainManager {

	public UOBMainManager(String _sPathStrMain) {
		/*
		 * 
		 */
		UOBStatic.setDIR(_sPathStrMain);
		BasicPrintMsg.display(this, "Drive detected= '" + UOBStatic.getDIR() + "'");
		/*
		 * 
		 */
		mUOBTransactionManager = new UOBTransactionManager(this);
		mUOBValueInColumnManager = new UOBValueInColumnManager();
		mComManager = new ComManager(this);
		mUOBAccountManager = new UOBAccountManager(this);
		mUOBFileManager = new UOBFileManager(this);
		mWriteAllUOBTransactions = new WriteAllUOBTransactions(this);
	}
	
	/*
	 * Data
	 */
	private ComManager mComManager;
	private UOBTransactionManager mUOBTransactionManager;
	private UOBValueInColumnManager mUOBValueInColumnManager;
	private UOBAccountManager mUOBAccountManager;
	private UOBFileManager mUOBFileManager;
	private WriteAllUOBTransactions mWriteAllUOBTransactions;
	
	/**
	 * 
	 */
	public final void run() {
		mUOBFileManager.run();
		mWriteAllUOBTransactions.run();
	}

	/*
	 * Getters & Setters
	 */
	public final UOBTransactionManager getmUOBTransactionManager() {
		return mUOBTransactionManager;
	}
	public final UOBValueInColumnManager getmUOBValueInColumnManager() {
		return mUOBValueInColumnManager;
	}
	public final ComManager getmComManager() {
		return mComManager;
	}
	public final UOBAccountManager getmUOBAccountManager() {
		return mUOBAccountManager;
	}
	public final UOBFileManager getmUOBFileManager() {
		return mUOBFileManager;
	}
	
	
	
}
