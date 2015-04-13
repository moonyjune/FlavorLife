package moony.vn.flavorlife.activities;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;

import com.ntq.activities.NActivity;
import com.ntq.imageloader.NImageLoader;
import com.ntq.utils.Utils;

import moony.vn.flavorlife.actionbar.CustomActionbar;
import moony.vn.flavorlife.analytics.AppAnalytics;
import moony.vn.flavorlife.api.Api;
import moony.vn.flavorlife.gcm.GcmRegisterService;
import moony.vn.flavorlife.gcm.GcmUtils;
import moony.vn.flavorlife.navigationmanager.NavigationManager;

public class DemoGcmActivity extends NActivity {
    private final static int PLAY_SERVICES_RESOLUTION_REQUEST = 9000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // check google play service
    }

    @Override
    protected void onResume() {
        super.onResume();
        Utils.checkPlayServices(this, PLAY_SERVICES_RESOLUTION_REQUEST);
    }

    @Override
    public NImageLoader getImageLoader() {
        return null;
    }

    @Override
    public Api getDfeApi() {
        return null;
    }

    @Override
    public NavigationManager getNavigationManager() {
        return null;
    }

    @Override
    public CustomActionbar getActionbar() {
        return null;
    }

    @Override
    public AppAnalytics getAnalytics() {
        return null;
    }

}
