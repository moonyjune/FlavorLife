package com.ntq.activities;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;

import com.ntq.fragments.NFragmentHost;

/**
 * 
 * @author TUNGDX
 * 
 */

/**
 * This is base activity for NTQ
 * 
 */
public abstract class NActivity extends ActionBarActivity implements
		NFragmentHost {
	protected static final String TAG = "NTQActivity";
	protected boolean mSaveInstanceStateCalled;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mSaveInstanceStateCalled = false;
	}

	@Override
	protected void onStart() {
		super.onStart();
		mSaveInstanceStateCalled = false;
	}

	@Override
	protected void onResume() {
		super.onResume();
		mSaveInstanceStateCalled = false;
	}

	public boolean canChangeFragmentManagerState() {
		return mSaveInstanceStateCalled || this == null ? false : true;
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		mSaveInstanceStateCalled = true;
	}

	public boolean isStateSaved() {
		return mSaveInstanceStateCalled;
	}
}
