package com.relurori.hanblocks;

import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class StudyFragment extends Fragment {

	private static final String TAG = StudyFragment.class.getSimpleName();

	public static final String DRAWER_ITEM_KEY = "DRAWER_ITEM_KEY";

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		Log.d(TAG, "DRAWER_ITEM_NUMBER=" + DRAWER_ITEM_KEY);

		View view = inflater.inflate(R.layout.fragment_study, container, false);

		Log.d(TAG, "setup tv");
		TextView tv = (TextView) view.findViewById(R.id.textView1);
		String token = ((MainActivity) getActivity()).getToken();
		if (token == null) {
			Log.d(TAG, "token null");
		} else {
			Log.d(TAG,"token=" + token);
			tv.setText(token);
		}

		return view;
	}
}
