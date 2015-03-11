package moony.vn.flavorlife.gcm;

import android.content.Context;
import android.util.Log;

import moony.vn.flavorlife.utils.AppPrefers;

import com.ntq.utils.Utils;

public class GcmUtils {
    private static final String TAG = "GcmUtils";

    /**
     * Gets the current registration ID for application on GCM service.
     * <p/>
     * If result is empty, the app needs to register.
     *
     * @return registration ID, or empty string if there is no existing
     * registration ID.
     */
    public static String getRegistrationId(Context context) {

        String registrationId = AppPrefers.getInstance().getRegistrationId();
        if (registrationId.isEmpty()) {
            return "";
        }
        // Check if app was updated; if so, it must clear the registration ID
        // since the existing regID is not guaranteed to work with the new
        // app version.
        int registeredVersion = AppPrefers.getInstance().getAppVersionCode();
        int currentVersion = Utils.getVersionCode(context);
        if (registeredVersion != currentVersion) {
            return "";
        }
        return registrationId;
    }

    /**
     * Stores the registration ID and app versionCode in the application's
     * {@code SharedPreferences}.
     *
     * @param context application's context.
     * @param regId   registration ID
     */
    public static void storeRegistrationId(Context context, String regId) {
        int appVersion = Utils.getVersionCode(context);
        Log.i(TAG, "Saving regId on app version " + appVersion);
        AppPrefers.getInstance().saveAppVersionCode(appVersion);
        AppPrefers.getInstance().saveRegistrationId(regId);
    }
}
