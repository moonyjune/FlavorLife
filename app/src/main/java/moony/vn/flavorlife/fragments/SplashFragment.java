package moony.vn.flavorlife.fragments;

import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ntq.fragments.NFragment;

import moony.vn.flavorlife.FlavorLifeApplication;
import moony.vn.flavorlife.R;
import moony.vn.flavorlife.entities.User;

/**
 * Created by moony on 3/1/15.
 */
public class SplashFragment extends NFragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_splash, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mActionbar.hide();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (getActivity() == null) return;
                if (FlavorLifeApplication.get().getUser().getState() == User.State.LOGGED_OUT) {
                    mNavigationManager.showPageWithoutStack(new LoginFragment());
                } else {
                    mNavigationManager.showNewRecipes();
                }
            }
        }, 1000);
    }
}
