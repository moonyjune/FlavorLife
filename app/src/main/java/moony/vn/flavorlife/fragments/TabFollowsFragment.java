package moony.vn.flavorlife.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import moony.vn.flavorlife.R;
import moony.vn.flavorlife.api.model.DfeGetFollowers;
import moony.vn.flavorlife.api.model.DfeGetFollows;
import moony.vn.flavorlife.api.model.DfeGetNewRecipes;
import moony.vn.flavorlife.api.model.FlPaginatedList;

/**
 * Created by moony on 3/1/15.
 */
public class TabFollowsFragment extends FlListFragment {
    private DfeGetFollows mDfeGetFollows;
    private TextView mNoData;

    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_tab_follows;
    }

    @Override
    protected FlPaginatedList getFlPaginatedList() {
        return mDfeGetFollows = new DfeGetFollows(mApi);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mNoData = (TextView) view.findViewById(R.id.footer_no_data);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mActionbar.syncActionBar(this);
        syncNoDataView();
    }

    @Override
    public void onDataChanged() {
        super.onDataChanged();
        syncNoDataView();
    }

    private void syncNoDataView() {
        if (mDfeGetFollows != null && mDfeGetFollows.isReady() && mDfeGetFollows.getCount() == 0) {
            mNoData.setVisibility(View.VISIBLE);
        } else {
            mNoData.setVisibility(View.GONE);
        }
    }
}
