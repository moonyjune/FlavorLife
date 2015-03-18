package moony.vn.flavorlife.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ntq.fragments.NFragment;

import java.util.ArrayList;
import java.util.List;

import moony.vn.flavorlife.R;
import moony.vn.flavorlife.adapters.RecipePagerAdapter;
import moony.vn.flavorlife.layout.TabIndicator;

/**
 * Created by moony on 3/1/15.
 */
public class CreateRecipeFragment extends NFragment {
    private ViewPager mRecipeViewPager;
    private TabIndicator mTabIndicator;
    private RecipePagerAdapter mRecipePagerAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_create_recipe, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mRecipeViewPager = (ViewPager) view.findViewById(R.id.cnrf_view_content);
        mTabIndicator = (TabIndicator) view.findViewById(R.id.cnr_vp_tab_indicator);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mActionbar.syncActionBar(this);
        mRecipePagerAdapter = new RecipePagerAdapter(getChildFragmentManager());
        mRecipeViewPager.setAdapter(mRecipePagerAdapter);
        mTabIndicator.setViewPager(mRecipeViewPager, getListTabName());
    }

    private List<String> getListTabName() {
        List<String> listTabName = new ArrayList<>();
        listTabName.add("Ingredients");
        listTabName.add("Instruction");
        listTabName.add("Introduction");
        return listTabName;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }
}
