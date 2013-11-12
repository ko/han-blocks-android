package com.relurori.hanblocks;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.accounts.AccountManagerCallback;
import android.accounts.AccountManagerFuture;
import android.app.Fragment;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


public class SettingsFragment extends Fragment {

	public static final String TAG = SettingsFragment.class.getSimpleName();
	public static final String DRAWER_ITEM_KEY = "DRAWER_ITEM_KEY";
	
	private View mView;
	private Account account;
	private int drawerValId;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		Bundle bundle = this.getArguments();
		drawerValId = bundle.getInt(this.DRAWER_ITEM_KEY);
		mView = inflater.inflate(R.layout.fragment_settings, container, false);
		
		onCreateSetupButtons();
		
		return mView;
	}

	private void onCreateSetupButtons() {
		Button b = (Button) mView.findViewById(R.id.settingsAuth);
		b.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				AccountManager.get(getActivity())
							  .getAuthTokenByFeatures("com.google", "wise", null, 
									  				  getActivity(), null, null, 
									  				  SpreadsheetUtil.doneCallback, null);
			}
		});
	}
}
