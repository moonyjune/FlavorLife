package com.ntq.utils;

import android.os.Build;

/**
 * 
 * @author TUNGDX
 * 
 */

/**
 * 
 * All utils relative to Operating System.
 * 
 */
public class OSUtils {

	/**
	 * Uses static final constants to detect if the device's platform version is
	 * Gingerbread or later.
	 */
	public static boolean hasGingerbread() {
		return Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD;
	}

	/**
	 * Uses static final constants to detect if the device's platform version is
	 * Honeycomb or later.
	 */
	public static boolean hasHoneycomb() {
		return Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB;
	}

	/**
	 * Uses static final constants to detect if the device's platform version is
	 * Honeycomb MR1 or later.
	 */
	public static boolean hasHoneycombMR1() {
		return Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR1;
	}

	/**
	 * Uses static final constants to detect if the device's platform version is
	 * ICS or later.
	 */
	public static boolean hasICS() {
		return Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH;
	}

	public static boolean hasJELLY() {
		return Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN;
	}
	
	public static boolean hasKITKAT() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;
    }
}
