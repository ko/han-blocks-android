package com.relurori.hanblocks;

import rom.relurori.hanblocks.db.spreadsheet.AbstractGetNameTask;
import rom.relurori.hanblocks.db.spreadsheet.GetNameInForeground;
import rom.relurori.hanblocks.db.spreadsheet.SpreadsheetGdataActivity;
import android.accounts.Account;
import android.accounts.AccountManager;
import android.app.Activity;
import android.content.Context;

import com.google.android.gms.auth.GoogleAuthUtil;

public class OauthUtils {

	public static String[] getAccountNames(Context context,
			AccountManager mAccountManager) {
		mAccountManager = AccountManager.get(context);
		Account[] accounts = mAccountManager
				.getAccountsByType(GoogleAuthUtil.GOOGLE_ACCOUNT_TYPE);
		String[] names = new String[accounts.length];
		for (int i = 0; i < names.length; i++) {
			names[i] = accounts[i].name;
		}
		return names;
	}

	/**
	 * Note: This approach is for demo purposes only. Clients would normally not
	 * get tokens in the background from a Foreground activity.
	 */
	public static AbstractGetNameTask getTask(Activity activity,
										String email, String scope, int requestCode) {
		return new GetNameInForeground(activity, email, scope, requestCode);
	}
}
