package moony.vn.flavorlife.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

import moony.vn.flavorlife.entities.Ingredient;
import moony.vn.flavorlife.entities.SectionIngredient;
import moony.vn.flavorlife.fragments.IngredientFragment;
import moony.vn.flavorlife.fragments.InstructionFragment;
import moony.vn.flavorlife.fragments.IntroductionFragment;

/**
 * Created by moony on 3/4/15.
 */
public class RecipePagerAdapter extends FragmentPagerAdapter {
    private static final int NUMBER_ITEM = 3;
    private int mPosition;
    private List<Fragment> mFragments;

    public RecipePagerAdapter(FragmentManager fm, List<Fragment> fragments) {
        super(fm);
        mFragments = fragments;
    }

    public enum TypeTabIndicator {
        INGREDIENT,
        INSTRUCTION,
        INTRODUCTION
    }

    public TypeTabIndicator getTypeTabIndicator() {
        switch (mPosition) {
            case 0:
                return TypeTabIndicator.INGREDIENT;
            case 1:
                return TypeTabIndicator.INSTRUCTION;
            case 2:
                return TypeTabIndicator.INTRODUCTION;
        }
        return null;
    }

    public RecipePagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
//        mPosition = position;
//        switch (getTypeTabIndicator()) {
//            case INGREDIENT:
//                return IngredientFragment.newInstance();
//            case INSTRUCTION:
//                return InstructionFragment.newInstance();
//            case INTRODUCTION:
//                return IntroductionFragment.newInstance();
//        }
//        return null;
        return mFragments.get(position);
    }

    @Override
    public int getCount() {
//        return NUMBER_ITEM;
        return mFragments.size();
    }
}
