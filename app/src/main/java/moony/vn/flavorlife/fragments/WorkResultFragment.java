package moony.vn.flavorlife.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.ntq.fragments.NFragmentSwitcher;

import java.util.ArrayList;
import java.util.List;

import moony.vn.flavorlife.R;
import moony.vn.flavorlife.adapters.HomePagerAdapter;
import moony.vn.flavorlife.entities.User;
import moony.vn.flavorlife.layout.TabIndicator;

/**
 * Created by moony on 5/9/15.
 */
public class WorkResultFragment extends NFragmentSwitcher {
    private static final String KEY_USER = "user";
    private TabIndicator mTabIndicator;
    private ViewPager mHomePager;
    private HomePagerAdapter mHomePagerAdapter;
    private User mUser;

    public static WorkResultFragment newInstance(User user) {
        WorkResultFragment workResultFragment = new WorkResultFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable(KEY_USER, user);
        workResultFragment.setArguments(bundle);
        return workResultFragment;
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
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mTabIndicator = (TabIndicator) view.findViewById(R.id.hf_vp_tab_indicator);
        mHomePager = (ViewPager) view.findViewById(R.id.hf_view_content);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        List<Fragment> listPager = new ArrayList<Fragment>();
        listPager.add(UserRecipesFragment.newInstance(mUser.getId()));
        listPager.add(UserCookbooksFragment.newInstance(mUser.getId()));
        mHomePagerAdapter = new HomePagerAdapter(getChildFragmentManager(), listPager);
        mHomePager.setAdapter(mHomePagerAdapter);
        mTabIndicator.setViewPager(mHomePager, getTabNames());
    }

    public List<String> getTabNames() {
        List<String> tabNames = new ArrayList<String>();
        tabNames.add("Recipes");
        tabNames.add("Cookbooks");
        return tabNames;
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_workresult;
    }

    @Override
    protected void requestData() {

    }

    @Override
    protected boolean isDataReady() {
        return false;
    }

}
