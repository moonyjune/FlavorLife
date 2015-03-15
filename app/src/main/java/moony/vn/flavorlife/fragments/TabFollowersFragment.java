package moony.vn.flavorlife.fragments;

import android.os.Bundle;

import moony.vn.flavorlife.R;
import moony.vn.flavorlife.api.model.DfeGetFollowers;
import moony.vn.flavorlife.api.model.DfeGetNewRecipes;
import moony.vn.flavorlife.api.model.FlPaginatedList;

/**
 * Created by moony on 3/1/15.
 */
public class TabFollowersFragment extends FlListFragment {

    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_tab_follows;
    }

    @Override
    protected FlPaginatedList getFlPaginatedList() {
        return new DfeGetFollowers(mApi);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mActionbar.syncActionBar(this);
    }
}
