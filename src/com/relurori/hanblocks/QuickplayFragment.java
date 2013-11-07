package com.relurori.hanblocks;

import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class QuickplayFragment extends Fragment {

	private static final String TAG = QuickplayFragment.class.getSimpleName();
	
	public static final String DRAWER_ITEM_KEY = "DRAWER_ITEM_KEY";

	protected static final int BUTTON_1 = 1;
	protected static final int BUTTON_2 = 2;
	
	private int drawerValId = 0;
	private Button tempButton = null;
	private View mView = null;
	private TextView tempTextView = null;
	private CharSequence tempString = null;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		Bundle bundle = this.getArguments();
		drawerValId = bundle.getInt(this.DRAWER_ITEM_KEY);
		
		Log.d(TAG, "DRAWER_ITEM_NUMBER=" + DRAWER_ITEM_KEY);
		Log.d(TAG, "drawerValId=" + drawerValId);
		
		mView = inflater.inflate(R.layout.fragment_quickplay, container, false);
		
		onCreateSetupTextView();
		onCreateSetupButtons();
		
		return mView;
	}

	private void onCreateSetupTextView() {
		tempTextView = (TextView)mView.findViewById(R.id.textView1);
	}

	private void onCreateSetupButtons() {
		tempButton = (Button)mView.findViewById(R.id.button1);
		tempButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Log.d(TAG,"button1");
				Toast.makeText(getActivity().getApplicationContext(), "button1", Toast.LENGTH_SHORT).show();
				submitAnswer(((Button) v).getText());
			}
		});
		tempButton.setText("Button1");
		
		tempButton = (Button)mView.findViewById(R.id.button2);
		tempButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Log.d(TAG,"button2");
				Toast.makeText(getActivity().getApplicationContext(), "button2", Toast.LENGTH_SHORT).show();
				submitAnswer(((Button) v).getText());
			}
		});
	}

	protected void submitAnswer(CharSequence answer) {
		boolean result;
		
		tempString = tempTextView.getText();
		/* TODO STUFF */
		result = Eng2KorTranslateQA.isValid(tempString,answer);

		Toast.makeText(getActivity().getApplicationContext(), "Correct? " + result, Toast.LENGTH_SHORT).show();
	}
}
