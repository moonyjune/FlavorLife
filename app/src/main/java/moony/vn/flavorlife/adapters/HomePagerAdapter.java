package moony.vn.flavorlife.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import moony.vn.flavorlife.fragments.InstructionFragment;
import moony.vn.flavorlife.fragments.UserCookbooksFragment;
import moony.vn.flavorlife.fragments.UserRecipesFragment;

/**
 * Created by moony on 3/4/15.
 */
public class HomePagerAdapter extends FragmentPagerAdapter {
    private static final int NUMBER_ITEM = 2;
    private int mPosition;

    public enum TypeTabIndicator{
        USER_RECIPES,
        USER_COOKBOOKS
    }

    public TypeTabIndicator getTypeTabIndicator(){
        switch (mPosition){
            case 0:
                return TypeTabIndicator.USER_RECIPES;
            case 1:
                return TypeTabIndicator.USER_COOKBOOKS;
        }
        return null;
    }

    public HomePagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        mPosition = position;
        switch (getTypeTabIndicator()){
            case USER_RECIPES:
                return UserRecipesFragment.newInstance(1);
            case USER_COOKBOOKS:
                return UserCookbooksFragment.newInstance(1);
        }
        return null;
    }

    @Override
    public int getCount() {
        return NUMBER_ITEM;
    }
}
