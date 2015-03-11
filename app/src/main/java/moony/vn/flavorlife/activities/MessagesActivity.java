package moony.vn.flavorlife.activities;

import android.support.v4.app.Fragment;

import moony.vn.flavorlife.fragments.MessagesFragment;

/**
 * Created by moony on 3/11/15.
 */
public class MessagesActivity extends BaseActivity {
    @Override
    public Fragment getRootFragment() {
        return new MessagesFragment();
    }
}
