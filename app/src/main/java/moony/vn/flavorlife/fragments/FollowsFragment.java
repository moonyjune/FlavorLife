package moony.vn.flavorlife.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ntq.fragments.NFragment;
import com.ntq.fragments.NFragmentSwitcher;

import java.util.ArrayList;
import java.util.List;

import moony.vn.flavorlife.R;
import moony.vn.flavorlife.adapters.FollowsPagerAdapter;
import moony.vn.flavorlife.gcm.GcmBroadcastReceiver;
import moony.vn.flavorlife.layout.TabIndicator;

/**
 * Created by moony on 3/1/15.
 */
public class FollowsFragment extends NFragment {
    private TabIndicator mTabIndicator;
    private ViewPager mViewPager;
    private FollowsPagerAdapter mFollowsPagerAdapter;

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mActionbar.syncActionBar(this);
        mFollowsPagerAdapter = new FollowsPagerAdapter(getChildFragmentManager());
        mViewPager.setAdapter(mFollowsPagerAdapter);
        mTabIndicator.setViewPager(mViewPager, getTabNames());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_follows, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mTabIndicator = (TabIndicator) view.findViewById(R.id.vp_tab_indicator);
        mViewPager = (ViewPager) view.findViewById(R.id.view_content);
    }

    public List<String> getTabNames() {
        List<String> tabNames = new ArrayList<String>();
        tabNames.add("Follows");
        tabNames.add("Followers");
        return tabNames;
    }

}
