package com.ntq.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.hardware.Camera;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Looper;
import android.provider.Settings;
import android.text.TextUtils;
import android.telephony.TelephonyManager;
import android.util.DisplayMetrics;
import android.util.Log;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;

/**
 *
 * @author TUNGDX
 *
 */

/**
 * All common of utils.
 */
public class Utils {
    /**
     * Get version code of app.
     *
     * @param context
     * @return version code.
     */
    public static int getVersionCode(Context context) {
        PackageInfo info;
        try {
            info = context.getPackageManager().getPackageInfo(
                    context.getPackageName(), 0);
            return info.versionCode;
        } catch (NameNotFoundException e) {
            e.printStackTrace();
        }
        // shoud be never happen.
        return 0;
    }

    /**
     * Get version name of app.
     *
     * @param context
     * @return version name.
     */
    public static String getVersionName(Context context) {
        PackageInfo info;
        try {
            info = context.getPackageManager().getPackageInfo(
                    context.getPackageName(), 0);
            return info.versionName;
        } catch (NameNotFoundException e) {
            e.printStackTrace();
        }
        // shoud be never happen.
        return null;
    }

    /**
     * Md5
     *
     * @param input
     * @return data after md5
     */
    public static final String md5(final String input) {
        final String MD5 = "MD5";
        try {
            // Create MD5 Hash
            MessageDigest digest = java.security.MessageDigest.getInstance(MD5);
            digest.update(input.getBytes());
            byte messageDigest[] = digest.digest();

            // Create Hex String
            StringBuilder hexString = new StringBuilder();
            for (byte aMessageDigest : messageDigest) {
                String h = Integer.toHexString(0xFF & aMessageDigest);
                while (h.length() < 2)
                    h = "0" + h;
                hexString.append(h);
            }
            return hexString.toString();

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * Check sim card in device.
     *
     * @param context
     * @return true if sim card exist. false otherwise.
     */
    public static boolean isSimReady(Context context) {
        TelephonyManager telephonyManager = (TelephonyManager) context
                .getSystemService(Context.TELEPHONY_SERVICE);
        if (telephonyManager.getSimState() == TelephonyManager.SIM_STATE_READY) {
            return true;
        }
        return false;
    }

    /**
     * {@linkplain TelephonyManager#getDeviceId()}
     *
     * @param context
     * @return
     */
    public static String getDeviceId(Context context) {
        TelephonyManager telephonyManager = (TelephonyManager) context
                .getSystemService(Context.TELEPHONY_SERVICE);
        String deviceId = telephonyManager.getDeviceId();
        if (TextUtils.isEmpty(deviceId)) {
            WifiManager wimanager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
            deviceId = wimanager.getConnectionInfo().getMacAddress();
        }
        if (TextUtils.isEmpty(deviceId)) {
            deviceId = Settings.Secure.ANDROID_ID;
        }
        return deviceId;
    }

    public static boolean isCameraUseByOtherApp() {
        Camera camera = null;
        try {
            camera = Camera.open();
        } catch (RuntimeException e) {
            return true;
        } finally {
            if (camera != null)
                camera.release();
        }
        return false;
    }

    // refers: http://stackoverflow.com/a/7167086/2128392
    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public static int getActionbarHeight(Activity activity) {
        int attr;
        if (OSUtils.hasHoneycomb()) {
            attr = android.R.attr.actionBarSize;
        } else {
            attr = android.support.v7.appcompat.R.attr.actionBarSize;
        }
        final TypedArray styledAttributes = activity.getTheme()
                .obtainStyledAttributes(new int[]{attr});
        int actionbarSize = (int) styledAttributes.getDimension(0, 0);
        styledAttributes.recycle();
        return actionbarSize;
    }

    /**
     * Check the device to make sure it has the Google Play Services APK. If it
     * doesn't, display a dialog that allows users to download the APK from the
     * Google Play Store or enable it in the device's system settings.
     *
     * @param activity    Activity that check.
     * @param requestCode Request code pass from activity.
     */
    public static boolean checkPlayServices(Activity activity, int requestCode) {
        int resultCode = GooglePlayServicesUtil
                .isGooglePlayServicesAvailable(activity);
        if (resultCode != ConnectionResult.SUCCESS) {
            if (GooglePlayServicesUtil.isUserRecoverableError(resultCode)) {
                GooglePlayServicesUtil.getErrorDialog(resultCode, activity,
                        requestCode).show();
            } else {
                Log.w("GooglePlayServices", "This device is not supported.");
            }
            return false;
        }
        return true;
    }

    public static void ensureOnMainThread() {
        if (Looper.myLooper() != Looper.getMainLooper())
            throw new IllegalStateException(
                    "This method must be called from the UI thread.");
    }

    public static float convertDpToPx(Context context, int dp) {
        return context.getResources().getDisplayMetrics().density * dp;
    }

    public static final int getDimensionInDp(Context context, int resId) {
        Resources resources = context.getResources();
        return (int) (resources.getDimension(resId) / resources.getDisplayMetrics().density);
    }

    public static final int getScreenWidth(Activity activity) {
        DisplayMetrics displaymetrics = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        return displaymetrics.widthPixels;
    }
}
