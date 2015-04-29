package moony.vn.flavorlife.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.View;

import com.ntq.activities.NActivity;
import com.ntq.imageloader.NImageLoader;

import moony.vn.flavorlife.R;
import moony.vn.flavorlife.actionbar.CustomActionbar;
import moony.vn.flavorlife.analytics.AppAnalytics;
import moony.vn.flavorlife.api.Api;
import moony.vn.flavorlife.fragments.SplashFragment;
import moony.vn.flavorlife.navigationmanager.NavigationManager;

/**
 * Created by moony on 3/11/15.
 */
public class SplashActivity extends NActivity {

    private Handler mHandler;
    private boolean mHandlerPosted = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_splash);

        mHandler = new Handler();
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (mHandlerPosted) return;
                mHandlerPosted = true;
                //TODO xac nhan tinh trang dang nhap
                MainActivity.startMainActivity(SplashActivity.this, MainActivity.EXTRA_OPEN_LOGIN);
                finish();
            }
        }, 1000);
    }

    @Override
    protected void onPause() {
        super.onPause();
        mHandler.removeCallbacksAndMessages(null);
        mHandler = null;
        if (!mHandlerPosted) finish();
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
