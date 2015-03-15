package moony.vn.flavorlife.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import moony.vn.flavorlife.fragments.IngredientFragment;
import moony.vn.flavorlife.fragments.InstructionFragment;
import moony.vn.flavorlife.fragments.IntroductionFragment;
import moony.vn.flavorlife.fragments.TabFollowersFragment;
import moony.vn.flavorlife.fragments.TabFollowsFragment;

/**
 * Created by moony on 3/4/15.
 */
public class FollowsPagerAdapter extends FragmentPagerAdapter {
    private static final int NUMBER_ITEM = 2;
    private int mPosition;

    public enum TypeTabIndicator {
        FOLLOWS,
        FOLLOWERS
    }

    public TypeTabIndicator getTypeTabIndicator() {
        switch (mPosition) {
            case 0:
                return TypeTabIndicator.FOLLOWS;
            case 1:
                return TypeTabIndicator.FOLLOWERS;
        }
        return null;
    }

    public FollowsPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        mPosition = position;
        switch (getTypeTabIndicator()) {
            case FOLLOWS:
                return new TabFollowsFragment();
            case FOLLOWERS:
                return new TabFollowersFragment();
        }
        return null;
    }

    @Override
    public int getCount() {
        return NUMBER_ITEM;
    }
}
