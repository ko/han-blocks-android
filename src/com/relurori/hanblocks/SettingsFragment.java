package com.relurori.hanblocks;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.accounts.AccountManagerCallback;
import android.accounts.AccountManagerFuture;
import android.app.Fragment;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.ListPreference;
import android.preference.PreferenceFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


public class SettingsFragment extends PreferenceFragment {

	public static final String TAG = SettingsFragment.class.getSimpleName();
	public static final String DRAWER_ITEM_KEY = "DRAWER_ITEM_KEY";
	
	private int drawerValId;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// Load the preferences from an XML resource
        addPreferencesFromResource(R.xml.preferences);

	}

}
