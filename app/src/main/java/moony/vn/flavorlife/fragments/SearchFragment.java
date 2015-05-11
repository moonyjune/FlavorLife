package moony.vn.flavorlife.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ntq.fragments.NFragment;

import java.util.ArrayList;
import java.util.List;

import moony.vn.flavorlife.R;
import moony.vn.flavorlife.adapters.SearchPagerAdapter;
import moony.vn.flavorlife.layout.SimpleTabIndicator;
import moony.vn.flavorlife.layout.TabIndicator;

/**
 * Created by moony on 5/11/15.
 */
public class SearchFragment extends NFragment {
    private SimpleTabIndicator mTabIndicator;
    private ViewPager mViewPager;
    private SearchPagerAdapter mSearchPagerAdapter;

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mActionbar.syncActionBar(this);
        List<Fragment> fragmentList = new ArrayList<Fragment>();
        fragmentList.add(new SearchPeopleFragment());
        fragmentList.add(new SearchRecipeFragment());

        mSearchPagerAdapter = new SearchPagerAdapter(getChildFragmentManager(), fragmentList);
        mViewPager.setAdapter(mSearchPagerAdapter);
        mTabIndicator.setViewPager(mViewPager, getTabNames());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_search, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mTabIndicator = (SimpleTabIndicator) view.findViewById(R.id.vp_tab_indicator);
        mViewPager = (ViewPager) view.findViewById(R.id.view_content);
    }

    public List<String> getTabNames() {
        List<String> tabNames = new ArrayList<String>();
        tabNames.add("Users");
        tabNames.add("Recipes");
        return tabNames;
    }

}
