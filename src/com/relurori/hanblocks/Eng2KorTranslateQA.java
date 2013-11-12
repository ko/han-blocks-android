package com.relurori.hanblocks;

import android.util.Log;

public class Eng2KorTranslateQA implements QuestionAnswerInterface {

	private static final String TAG = Eng2KorTranslateQA.class.getSimpleName();
	
	public static boolean isValid(CharSequence q, CharSequence a) {
		Log.d(TAG,"Question=" + q);
		Log.d(TAG,"Answer=" + a);
		return false;
	}

}
