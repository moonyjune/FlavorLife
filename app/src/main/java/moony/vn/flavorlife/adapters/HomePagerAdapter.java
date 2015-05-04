package moony.vn.flavorlife.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

import moony.vn.flavorlife.fragments.InstructionFragment;
import moony.vn.flavorlife.fragments.UserCookbooksFragment;
import moony.vn.flavorlife.fragments.UserRecipesFragment;

/**
 * Created by moony on 3/4/15.
 */
public class HomePagerAdapter extends FragmentPagerAdapter {
    private List<Fragment> listPager;
    private final int NUMBER_ITEM = 2;
    private int mPosition;

    public enum TypeTabIndicator {
        USER_RECIPES,
        USER_COOKBOOKS
    }

    public TypeTabIndicator getTypeTabIndicator() {
        switch (mPosition) {
            case 0:
                return TypeTabIndicator.USER_RECIPES;
            case 1:
                return TypeTabIndicator.USER_COOKBOOKS;
        }
        return null;
    }

    public HomePagerAdapter(FragmentManager fm, List<Fragment> listPager) {
        super(fm);
        this.listPager = listPager;
    }

    @Override
    public Fragment getItem(int position) {
//        mPosition = position;
//        switch (getTypeTabIndicator()){
//            case USER_RECIPES:
//                return UserRecipesFragment.newInstance(1);
//            case USER_COOKBOOKS:
//                return UserCookbooksFragment.newInstance(1);
//        }
//        return null;
        return listPager.get(position);
    }

    @Override
    public int getCount() {
//        return NUMBER_ITEM;
        return listPager.size();
    }

    public List<Fragment> getListPager() {
        return listPager;
    }

    public void setListPager(List<Fragment> listPager) {
        this.listPager = listPager;
    }
}
