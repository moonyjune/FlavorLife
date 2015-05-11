package moony.vn.flavorlife.activities;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import moony.vn.flavorlife.R;
import moony.vn.flavorlife.fragments.PeopleFragment;

/**
 * Created by moony on 3/11/15.
 */
public class FollowsActivity extends BaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fragment fragment = getRootFragment();
        if (fragment == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.content_layout, new PeopleFragment()).commit();
        } else {
            mActionbar.syncActionBar(fragment);
        }
    }
}
