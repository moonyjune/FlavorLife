package moony.vn.flavorlife.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import moony.vn.flavorlife.R;
import moony.vn.flavorlife.api.model.DfeGetFollowers;
import moony.vn.flavorlife.api.model.DfeGetNewRecipes;
import moony.vn.flavorlife.api.model.FlPaginatedList;

/**
 * Created by moony on 3/1/15.
 */
public class TabFollowersFragment extends FlListFragment {
    private DfeGetFollowers mDfeGetFollowers;
    private TextView mNoData;

    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_tab_follows;
    }

    @Override
    protected FlPaginatedList getFlPaginatedList() {
        return mDfeGetFollowers = new DfeGetFollowers(mApi);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mActionbar.syncActionBar(this);
        syncNoDataView();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mNoData = (TextView) view.findViewById(R.id.footer_no_data);
    }

    @Override
    public void onDataChanged() {
        super.onDataChanged();
        syncNoDataView();
    }

    private void syncNoDataView() {
        if (mDfeGetFollowers != null && mDfeGetFollowers.isReady() && mDfeGetFollowers.getCount() == 0) {
            mNoData.setVisibility(View.VISIBLE);
        } else {
            mNoData.setVisibility(View.GONE);
        }
    }
}
