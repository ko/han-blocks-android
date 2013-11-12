package com.relurori.hanblocks;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.accounts.AccountManagerCallback;
import android.accounts.AccountManagerFuture;
import android.os.AsyncTask;
import android.os.Bundle;

public class SpreadsheetUtil {
	
	private static Account account;
	
	public static AccountManagerCallback<Bundle> doneCallback = new AccountManagerCallback<Bundle>() {

		@Override
		public void run(AccountManagerFuture<Bundle> arg0) {
			
			String name;
			String type;
			Bundle b;
			
			try {
				b = arg0.getResult();
				name = b.getString(AccountManager.KEY_ACCOUNT_NAME);
				type = b.getString(AccountManager.KEY_ACCOUNT_TYPE);
				
				account = new Account(name,type);
				
				new SampleTask().execute();
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		}
	};
	
	static class  SampleTask extends AsyncTask<Void,Void,Void> {

		@Override
		protected Void doInBackground(Void... params) {

			TokenSupplier supplier = new TokenSupplier() {
                @Override
                public void invalidateToken(String token) {
                    AccountManager.get(getActivity()).invalidateAuthToken("com.google", token);
                }
                @Override
                public String getToken(String authTokenType) {
                    try {
                        return AccountManager.get(getActivity()).blockingGetAuthToken(account, authTokenType, true);
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                }
            };
            
            SpreadsheetsService service = new SpreadsheetsService("hanblocks-app", supplier);
            
			return null;
		}
		
	}
}
