package moony.vn.flavorlife.activities;

import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.app.Fragment;

import moony.vn.flavorlife.R;
import moony.vn.flavorlife.fragments.CreateRecipeFragment;
import moony.vn.flavorlife.gcm.GcmIntentService;

/**
 * Created by moony on 3/11/15.
 */
public class CreateNewRecipeActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fragment fragment = getRootFragment();
        if (fragment == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.content_layout, new CreateRecipeFragment()).commit();
        } else {
            mActionbar.syncActionBar(fragment);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        registerReceiver(broadcastReceiver, new IntentFilter(GcmIntentService.ACTION_SEND_NOTIFICATION));
    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(broadcastReceiver);
    }

}

