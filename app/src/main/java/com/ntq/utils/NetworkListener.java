package com.ntq.utils;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v4.app.Fragment;

/**
 * 
 * @author TUNGDX
 *
 */

/**
 * 
 * Use for register network listener in {@link Activity} or in {@link Fragment}
 * 
 */
public class NetworkListener extends BroadcastReceiver {
	public interface OnNetworkListener {
		public void onNetworkConnected();

		public void onNetworkDisconnected();
	}

	private ConnectivityManager connectivityManager = null;
	private NetworkInfo activeNetInfo = null;
	private Activity mActivity;
	private OnNetworkListener mOnNetworkListener;

	public NetworkListener(Activity activity, OnNetworkListener networkListener) {
		mActivity = activity;
		mOnNetworkListener = networkListener;
	}

	/**
	 * register network listener.
	 */
	public void register() {
		if (mActivity == null)
			return;
		IntentFilter filter = new IntentFilter(
				ConnectivityManager.CONNECTIVITY_ACTION);
		mActivity.registerReceiver(this, filter);

	}

	/**
	 * unregister network listener. Must call this method when not need check
	 * network state avoid memory leak.
	 */
	public void unRegister() {
		if (mActivity == null)
			return;
		mActivity.unregisterReceiver(this);
		mActivity = null;
	}

	@Override
	public void onReceive(Context context, Intent intent) {
		if (mActivity == null)
			return;
		connectivityManager = (ConnectivityManager) mActivity
				.getApplicationContext().getSystemService(
						Context.CONNECTIVITY_SERVICE);
		if (connectivityManager != null) {
			activeNetInfo = connectivityManager.getActiveNetworkInfo();
		}
		if (activeNetInfo != null && activeNetInfo.isConnected()) {
			if (mOnNetworkListener != null)
				mOnNetworkListener.onNetworkConnected();
		} else {
			if (mOnNetworkListener != null)
				mOnNetworkListener.onNetworkDisconnected();
		}

	}
}
