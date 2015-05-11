package moony.vn.flavorlife.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.ListFragment;

import java.util.List;

import moony.vn.flavorlife.fragments.TabFollowersFragment;
import moony.vn.flavorlife.fragments.TabFollowsFragment;

/**
 * Created by moony on 3/4/15.
 */
public class SearchPagerAdapter extends FragmentPagerAdapter {
    private List<Fragment> listFragment;

    public SearchPagerAdapter(FragmentManager fm, List<Fragment> listFragment) {
        super(fm);
        this.listFragment = listFragment;
    }

    @Override
    public Fragment getItem(int position) {
        return listFragment.get(position);
    }

    @Override
    public int getCount() {
        return listFragment.size();
    }
}
