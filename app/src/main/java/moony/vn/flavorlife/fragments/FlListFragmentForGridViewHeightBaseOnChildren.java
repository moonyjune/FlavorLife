package moony.vn.flavorlife.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ListView;

import com.ntq.fragments.NFragmentSwitcher;

import moony.vn.flavorlife.R;
import moony.vn.flavorlife.adapters.FlListAdapterForGridView;
import moony.vn.flavorlife.api.model.FlPaginatedList;

/**
 * Created by moony on 3/4/15.
 */
public abstract class FlListFragmentForGridViewHeightBaseOnChildren extends NFragmentSwitcher implements SwipeRefreshLayout.OnRefreshListener {
    //TODO nghien cuu cach set theo children
    private GridView mGridView;
    protected FlListAdapterForGridView mFlListAdapter;
    protected FlPaginatedList mFlPaginatedList;
    private AdapterView.OnItemClickListener mItemClickListener;
    private SwipeRefreshLayout mSwipeRefreshLayout;

    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_list;
    }

    protected abstract FlPaginatedList getFlPaginatedList();

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (!isDataReady()) {
            switchToLoading();
            requestData();
        } else {
            if (mSwipeRefreshLayout != null) mSwipeRefreshLayout.setEnabled(true);
        }
        rebindAdapter();
    }

    @Override
    protected void requestData() {
        if (mFlPaginatedList == null) {
            mFlPaginatedList = getFlPaginatedList();
            mFlPaginatedList.addErrorListener(this);
            mFlPaginatedList.addDataChangedListener(this);
        }
        mFlPaginatedList.makeRequest();
    }

    @Override
    public void onDataChanged() {
        super.onDataChanged();
        mFlPaginatedList.removeDataChangedListener(this);
        mFlPaginatedList.removeErrorListener(this);
        rebindAdapter();
        if (mSwipeRefreshLayout != null) {
            mSwipeRefreshLayout.setEnabled(true);
            mSwipeRefreshLayout.setRefreshing(false);
        }
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (mDataView instanceof SwipeRefreshLayout) {
            mSwipeRefreshLayout = (SwipeRefreshLayout) mDataView;
        } else {
            mSwipeRefreshLayout = (SwipeRefreshLayout) mDataView.findViewById(R.id.swipe_refresh);
        }
        if (mSwipeRefreshLayout != null) {
            mSwipeRefreshLayout.setOnRefreshListener(this);
            mSwipeRefreshLayout.setEnabled(false);
            mSwipeRefreshLayout.setColorSchemeResources(R.color.holo_blue_bright, R.color.holo_green_light, R.color.holo_orange_light, R.color.holo_red_light);
        }
        if (mDataView instanceof ListView) {
            mGridView = (GridView) mDataView;
        } else {
            mGridView = (GridView) mDataView.findViewById(R.id.list);
        }
        mGridView.setOnItemClickListener(mItemClickListener);
    }

    public void rebindAdapter() {
        if (mDataView == null || !isDataReady()) return;
        if (mFlListAdapter == null) {
            mFlListAdapter = new FlListAdapterForGridView(mContext, mImageLoader, mNavigationManager, mAppAnalytics, mFlPaginatedList);
        }
        mGridView.setAdapter(mFlListAdapter);
    }

    public boolean isDataReady() {
        return mFlPaginatedList != null && mFlPaginatedList.isReady();
    }

    public GridView getListView() {
        return mGridView;
    }

    public void setItemClickListener(AdapterView.OnItemClickListener itemClickListener) {
        this.mItemClickListener = itemClickListener;
    }

    @Override
    public void onRefresh() {
        mFlListAdapter.clearAll();
        mFlListAdapter.triggerNoneMode();
        mFlListAdapter = null;
        mFlPaginatedList.unregisterAll();
        mFlPaginatedList = null;

        //request new data
        mFlPaginatedList = getFlPaginatedList();
        mFlPaginatedList.addDataChangedListener(this);
        mFlPaginatedList.addErrorListener(this);
        mFlPaginatedList.makeRequest();
    }

    public FlListAdapterForGridView getAmListAdapter() {
        return mFlListAdapter;
    }
}
