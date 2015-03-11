package moony.vn.flavorlife.actionbar;

import android.app.Activity;

import moony.vn.flavorlife.activities.MainActivity;

public class CustomActionBarFactory {
    public static CustomActionbar getInstance(Activity activity) {
        CustomActionbar actionBar = null;
        if (activity instanceof MainActivity) {
            actionBar = new NativeActionbar();
        }
        return actionBar;
    }
}
