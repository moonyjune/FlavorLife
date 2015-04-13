package moony.vn.flavorlife.activities;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v4.app.Fragment;

import moony.vn.flavorlife.fragments.NewRecipesFragment;
import moony.vn.flavorlife.gcm.GcmBroadcastReceiver;
import moony.vn.flavorlife.gcm.GcmIntentService;

/**
 * Created by moony on 3/11/15.
 */
public class NewRecipesActivity extends BaseActivity {

    @Override
    public Fragment getRootFragment() {
        return new NewRecipesFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
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
