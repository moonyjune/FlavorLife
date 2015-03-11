package moony.vn.flavorlife.activities;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v4.app.Fragment;
import android.view.View;

import moony.vn.flavorlife.fragments.SplashFragment;

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
}
