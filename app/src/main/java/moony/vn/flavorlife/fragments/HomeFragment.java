package moony.vn.flavorlife.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ntq.fragments.NFragment;

import java.util.ArrayList;
import java.util.List;

import moony.vn.flavorlife.FlavorLifeApplication;
import moony.vn.flavorlife.R;
import moony.vn.flavorlife.adapters.HomePagerAdapter;
import moony.vn.flavorlife.entities.User;
import moony.vn.flavorlife.layout.TabIndicator;

/**
 * Created by moony on 3/1/15.
 */
public class HomeFragment extends NFragment {
    private static final String KEY_USER = "user";
    private TabIndicator mTabIndicator;
    private ViewPager mHomePager;
    private HomePagerAdapter mHomePagerAdapter;
    private User mUser;

    public static HomeFragment newInstance(User user) {
        HomeFragment homeFragment = new HomeFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable(KEY_USER, user);
        homeFragment.setArguments(bundle);
        return homeFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState == null) {
            mUser = (User) getArguments().getSerializable(KEY_USER);
        } else {
            mUser = (User) savedInstanceState.getSerializable(KEY_USER);
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (mUser != null)
            outState.putSerializable(KEY_USER, mUser);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mTabIndicator = (TabIndicator) view.findViewById(R.id.hf_vp_tab_indicator);
        mHomePager = (ViewPager) view.findViewById(R.id.hf_view_content);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mActionbar.syncActionBar(this);
        mHomePagerAdapter = new HomePagerAdapter(getChildFragmentManager());
        mHomePager.setAdapter(mHomePagerAdapter);
        mTabIndicator.setViewPager(mHomePager, getTabNames());
    }

    public List<String> getTabNames() {
        List<String> tabNames = new ArrayList<String>();
        tabNames.add("My Recipes");
        tabNames.add("My Cookbooks");
        return tabNames;
    }

    public boolean isOwner() {
        if (mUser.getId() == FlavorLifeApplication.get().getUser().getId()) {
            return true;
        } else {
            return false;
        }
    }
}
