package com.ntq.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * 
 * @author TUNGDX
 *
 */

/**
 * 
 * All utils for network.
 * 
 */
public class NetworkUtils {
	/**
	 * 
	 * @param context
	 * @return true if network conntected, false otherwise.
	 */
	public static boolean isNetworkConnected(Context context) {
		ConnectivityManager connectivityManager = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
		if (networkInfo != null && networkInfo.isConnected()) {
			return true;
		}
		return false;
	}

	/**
	 * 
	 * @param context
	 * @return true if network connectivity exists or is in the process of being
	 *         established, false otherwise.
	 */
	public static boolean isNetworkConnectedOrConnecting(Context context) {
		ConnectivityManager connectivityManager = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
		if (networkInfo != null && networkInfo.isConnectedOrConnecting()) {
			return true;
		}
		return false;
	}
}
