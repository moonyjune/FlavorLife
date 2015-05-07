package moony.vn.flavorlife.activities;

import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.app.Fragment;

import moony.vn.flavorlife.R;
import moony.vn.flavorlife.entities.Recipe;
import moony.vn.flavorlife.fragments.CreateRecipeFragment;
import moony.vn.flavorlife.gcm.GcmIntentService;

/**
 * Created by moony on 3/11/15.
 */
public class CreateNewRecipeActivity extends BaseActivity {
    public static final String DATA = "data";
    public static final String RECIPE = "recipe";
    public static final String FLAG = "flag_recipe";
    private Recipe mRecipe;
    private int mFlag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState == null) {
            Bundle bundle = getIntent().getBundleExtra(DATA);
            mFlag = CreateRecipeFragment.FLAG_CREATE;
            if (bundle != null) {
                mRecipe = (Recipe) bundle.getSerializable(RECIPE);
                mFlag = bundle.getInt(FLAG);
            }
        } else {
            mRecipe = (Recipe) savedInstanceState.getSerializable(RECIPE);
            mFlag = savedInstanceState.getInt(FLAG);
        }

        Fragment fragment = getRootFragment();
        if (fragment == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.content_layout, CreateRecipeFragment.newInstance(mRecipe, mFlag)).commit();
        } else {
            mActionbar.syncActionBar(fragment);
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (mRecipe != null)
            outState.putSerializable(RECIPE, mRecipe);
        outState.putInt(FLAG, mFlag);
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

