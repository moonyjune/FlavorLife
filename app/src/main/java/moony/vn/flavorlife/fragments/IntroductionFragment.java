package moony.vn.flavorlife.fragments;

import android.os.Bundle;
import android.view.View;

import com.ntq.fragments.NFragmentSwitcher;

import moony.vn.flavorlife.R;
import moony.vn.flavorlife.entities.Recipe;

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
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mNavigationManager.showPage(RecipeDetailFragment.newInstance(new Recipe()));
            }
        });
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_introduction;
    }

    @Override
    protected void requestData() {

    }
}
