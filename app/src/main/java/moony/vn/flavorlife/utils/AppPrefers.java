package moony.vn.flavorlife.utils;

import android.content.Context;

import moony.vn.flavorlife.FlavorLifeApplication;

import com.ntq.utils.BasePrefers;

public class AppPrefers extends BasePrefers {
    private static AppPrefers sAppPrefers;

    public static AppPrefers getInstance() {
        if (sAppPrefers == null) {
            sAppPrefers = new AppPrefers(FlavorLifeApplication.get());
        }
        return sAppPrefers;
    }

    private AppPrefers(Context context) {
        super(context);
    }

    @Override
    protected String getFileNamePrefers() {
        // TODO shoud place in params in template.
        return "FlavorLife";
    }

    public void saveAppVersionCode(int versionCode) {
        mEditor.putInt("version_code", versionCode).commit();
    }

    public int getAppVersionCode() {
        return mPreferences.getInt("version_code", Integer.MIN_VALUE);
    }

    /**
     * Save registrationId for GCM.
     *
     * @param regId
     */
    public void saveRegistrationId(String regId) {
        mEditor.putString("registration_id", regId).commit();
    }

    /**
     * @return registrationId for GCM, null if not found.
     */
    public String getRegistrationId() {
        return mPreferences.getString("registration_id", null);
    }

    private boolean containKey(String key) {
        return mPreferences.contains(key);
    }

    public boolean containRegistrationKey() {
        return containKey("registration_id");
    }

    public void saveNumberNotifications(int num) {
        mEditor.putInt("number_notification", num).commit();
    }

    public int getNumberNotifications() {
        if (!containKey("number_notification")) return 0;
        return mPreferences.getInt("number_notification", 0);
    }

}
