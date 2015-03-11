package com.ntq.utils;

import android.content.Context;
import android.content.SharedPreferences;

public abstract class BasePrefers {
	protected SharedPreferences.Editor mEditor;
	protected SharedPreferences mPreferences;
	protected Context mContext;

	public BasePrefers(Context context) {
		mContext = context.getApplicationContext();
		mPreferences = mContext.getSharedPreferences(getFileNamePrefers(),
				Context.MODE_PRIVATE);
		mEditor = mPreferences.edit();
	}

	protected abstract String getFileNamePrefers();

	public SharedPreferences getSharedPreference() {
		return mPreferences;
	}

}
