package moony.vn.flavorlife.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.view.View;

import com.ntq.fragments.NFragment;

/**
 * Created by moony on 3/1/15.
 */
public abstract class TabRootDefaultFragment extends NFragment implements FragmentManager.OnBackStackChangedListener {
    private View mTabRootFrame;

    public abstract int getTabRootId();

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mTabRootFrame = view.findViewById(getTabRootId());
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mActionbar.syncActionBar(this);
        mNavigationManager.addOnBackStackChangedListener(this);
    }

    @Override
    public void onBackStackChanged() {
        int count = mNavigationManager.getBackStackEntryCount(getTabRootId());
        if (count > 0) {
            mTabRootFrame.setBackgroundResource(android.R.color.white);
            mTabRootFrame.setClickable(true);
            onVisible(false);
        } else {
            mTabRootFrame.setBackgroundResource(android.R.color.transparent);
            mTabRootFrame.setClickable(false);
            onVisible(true);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mNavigationManager.removeOnBackStackChangedListener(this);
    }

    protected void onVisible(boolean visible) {
        //do nothing -> use for child
    }
}
