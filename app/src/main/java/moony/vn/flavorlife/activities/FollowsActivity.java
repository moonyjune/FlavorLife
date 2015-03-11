package moony.vn.flavorlife.activities;

import android.support.v4.app.Fragment;

import moony.vn.flavorlife.fragments.FollowsFragment;

/**
 * Created by moony on 3/11/15.
 */
public class FollowsActivity extends BaseActivity {
    @Override
    public Fragment getRootFragment() {
        return new FollowsFragment();
    }
}
