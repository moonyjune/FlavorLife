package moony.vn.flavorlife.activities;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.ntq.utils.Utils;

import moony.vn.flavorlife.Config;
import moony.vn.flavorlife.fragments.NewRecipesFragment;
import moony.vn.flavorlife.fragments.SplashFragment;
import moony.vn.flavorlife.gcm.GcmBroadcastReceiver;
import moony.vn.flavorlife.gcm.GcmIntentService;
import moony.vn.flavorlife.gcm.GcmRegisterService;
import moony.vn.flavorlife.gcm.GcmUtils;

/**
 * Created by moony on 3/11/15.
 */
public class MainActivity extends BaseActivity {

    @Override
    public Fragment getRootFragment() {
        return new SplashFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mTabWidget.setVisibility(View.GONE);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

}
