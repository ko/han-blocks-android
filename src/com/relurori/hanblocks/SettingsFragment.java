package com.relurori.hanblocks;

import com.google.android.gms.auth.GoogleAuthUtil;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.accounts.AccountManagerCallback;
import android.accounts.AccountManagerFuture;
import android.app.Fragment;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.CheckBoxPreference;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceCategory;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


public class SettingsFragment extends PreferenceFragment {

	public static final String TAG = SettingsFragment.class.getSimpleName();
	public static final String DRAWER_ITEM_KEY = "DRAWER_ITEM_KEY";
	
	private int drawerValId;
	
    private AccountManager mAccountManager;

    static final int REQUEST_CODE_RECOVER_FROM_AUTH_ERROR = 1001;
    static final int REQUEST_CODE_RECOVER_FROM_PLAY_SERVICES_ERROR = 1002;
    
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// Load the preferences from an XML resource
        addPreferencesFromResource(R.xml.preferences);

        
        ListPreference listPreference = (ListPreference) findPreference("account_list_preference");
        
        CharSequence[] charArray = OauthUtils.getAccountNames(getActivity(),mAccountManager);
		listPreference.setEntries(charArray);
		listPreference.setEntryValues(charArray);
		
		listPreference.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
			
			private static final String SCOPE = "oauth2:https://www.googleapis.com/auth/userinfo.profile https://spreadsheets.google.com/feeds https://www.google.com/calendar/feeds/";
		    
			@Override
			public boolean onPreferenceChange(Preference preference, Object newValue) {
				Log.d(TAG,"newValue=" + newValue);
				OauthUtils.getTask(getActivity(), (String)newValue, SCOPE, 
								   REQUEST_CODE_RECOVER_FROM_AUTH_ERROR)
								   .execute();
				return false;
			}
		});

		
	}
	
	



}
