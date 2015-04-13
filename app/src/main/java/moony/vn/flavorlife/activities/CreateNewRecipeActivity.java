package moony.vn.flavorlife.activities;

import android.content.IntentFilter;
import android.support.v4.app.Fragment;

import moony.vn.flavorlife.fragments.CreateRecipeFragment;
import moony.vn.flavorlife.gcm.GcmIntentService;

/**
 * Created by moony on 3/11/15.
 */
public class CreateNewRecipeActivity extends BaseActivity {
    @Override
    public Fragment getRootFragment() {
        return new CreateRecipeFragment();
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
