package moony.vn.flavorlife.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import moony.vn.flavorlife.adapters.FlListAdapter;

import com.ntq.fragments.NFragmentSwitcher;

import moony.vn.flavorlife.R;
import moony.vn.flavorlife.api.model.FlPaginatedList;
import moony.vn.flavorlife.utils.ListViewUtils;

/**
 * Created by moony on 3/4/15.
 */
public abstract class FlListFragment extends NFragmentSwitcher implements SwipeRefreshLayout.OnRefreshListener {
    private ListView mListView;
    protected FlListAdapter mFlListAdapter;
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
            mListView = (ListView) mDataView;
        } else {
            mListView = (ListView) mDataView.findViewById(R.id.list);
        }
        mListView.setOnItemClickListener(mItemClickListener);
    }

    public void rebindAdapter() {
        if (mDataView == null || !isDataReady()) return;
        if (mFlListAdapter == null) {
            mFlListAdapter = new FlListAdapter(mContext, mImageLoader, mNavigationManager, mAppAnalytics, mFlPaginatedList);
        }
        mListView.setAdapter(mFlListAdapter);
    }

    public boolean isDataReady() {
        return mFlPaginatedList != null && mFlPaginatedList.isReady();
    }

    public ListView getListView() {
        return mListView;
    }

    public void setItemClickListener(AdapterView.OnItemClickListener itemClickListener) {
        this.mItemClickListener = itemClickListener;
    }

    @Override
    public void onRefresh() {
        if (mFlListAdapter != null) {
            mFlListAdapter.clearAll();
            mFlListAdapter.triggerNoneMode();
        }
        mFlListAdapter = null;
        if (mFlPaginatedList != null)
            mFlPaginatedList.unregisterAll();
        mFlPaginatedList = null;

        //request new data
        mFlPaginatedList = getFlPaginatedList();
        mFlPaginatedList.addDataChangedListener(this);
        mFlPaginatedList.addErrorListener(this);
        mFlPaginatedList.makeRequest();
    }

    public FlListAdapter getAmListAdapter() {
        return mFlListAdapter;
    }

}
