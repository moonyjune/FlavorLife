package moony.vn.flavorlife.fragments;

import android.os.Bundle;

import com.ntq.fragments.NFragmentSwitcher;

import moony.vn.flavorlife.R;

/**
 * Created by moony on 3/4/15.
 */
public class IntroductionFragment extends NFragmentSwitcher {
    public static IntroductionFragment newInstance() {
        IntroductionFragment introductionFragment = new IntroductionFragment();
        Bundle bundle = new Bundle();
        introductionFragment.setArguments(bundle);
        return introductionFragment;
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_introduction;
    }

    @Override
    protected void requestData() {

    }
}
