package com.ntq.utils;

import android.content.Context;
import android.location.LocationManager;

/**
 * 
 * @author TUNGDX
 *
 */

/**
 * 
 * All utils for Location Service.
 * 
 */
public class LocationUtils {

	/**
	 * 
	 * @param context
	 * @return true if gps enabled or location network service enabled.
	 */
	public static boolean isLocationServiceEnabled(Context context) {
		boolean gps_enabled = false, network_enabled = false;
		LocationManager lm = (LocationManager) context
				.getSystemService(Context.LOCATION_SERVICE);
		try {
			gps_enabled = lm.isProviderEnabled(LocationManager.GPS_PROVIDER);
			network_enabled = lm
					.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
		} catch (Exception ex) {
			ex.printStackTrace();
			return false;
		}
		return (gps_enabled || network_enabled);
	}

	/**
	 * 
	 * @param context
	 * @return true if gps enabled.
	 */
	public static boolean isGPSEnabled(Context context) {
		LocationManager lm = (LocationManager) context
				.getSystemService(Context.LOCATION_SERVICE);
		try {
			return lm.isProviderEnabled(LocationManager.GPS_PROVIDER);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return false;
	}

	/**
	 * 
	 * @param context
	 * @return true if location service network enabled.
	 */
	public static boolean isLocationServiceNetworkEnabled(Context context) {
		LocationManager lm = (LocationManager) context
				.getSystemService(Context.LOCATION_SERVICE);
		try {
			return lm.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return false;
	}
}
