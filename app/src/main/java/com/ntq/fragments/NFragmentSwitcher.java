package com.ntq.fragments;

/**
 *
 * @author TUNGDX
 *
 */

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.Response.ErrorListener;
import com.android.volley.VolleyError;

import moony.vn.flavorlife.layout.ContentFrame;
import moony.vn.flavorlife.layout.LayoutSwitcher;
import moony.vn.flavorlife.layout.LayoutSwitcher.RetryButtonListener;
import moony.vn.flavorlife.utils.ErrorStrings;
import moony.vn.flavorlife.R;

import com.ntq.api.model.OnDataChangedListener;

/**
 * This is fragment that supplies some functions which usually needed in
 * application.<br>
 * <li>Switch between state of screen: loading, data, error<br> <li>Retry
 * function, help developers avoid to do some jobs that must be repeated in many
 * screens in application.
 */
public abstract class NFragmentSwitcher extends NFragment implements
        RetryButtonListener, ErrorListener, OnDataChangedListener, SwipeRefreshLayout.OnRefreshListener {

    protected ViewGroup mDataView;
    protected boolean mRefreshRequired;
    protected LayoutSwitcher mLayoutSwitcher;
    private SwipeRefreshLayout mSwipeRefreshLayout;

    public NFragmentSwitcher() {
        setArguments(new Bundle());
    }

    @Override
    public View onCreateView(LayoutInflater layoutinflater,
                             ViewGroup viewgroup, Bundle bundle) {
        ContentFrame contentframe = new ContentFrame(getActivity());
        contentframe.setDataLayout(layoutinflater, getLayoutRes(),
                R.id.content_layout);
        mDataView = contentframe.getDataLayout();
        mLayoutSwitcher = new LayoutSwitcher(contentframe,
                R.id.content_layout, R.id.error_layout,
                R.id.loading_layout, this, LayoutSwitcher.DATA_MODE);
        if (mDataView instanceof SwipeRefreshLayout) {
            mSwipeRefreshLayout = (SwipeRefreshLayout) mDataView;
        } else {
            mSwipeRefreshLayout = (SwipeRefreshLayout) mDataView.findViewById(R.id.swipe_refresh_layout);
        }
        if (mSwipeRefreshLayout != null) {
            mSwipeRefreshLayout.setOnRefreshListener(this);
            mSwipeRefreshLayout.setEnabled(false);
            mSwipeRefreshLayout.setColorSchemeResources(R.color.holo_blue_bright, R.color.holo_green_light, R.color.holo_orange_light, R.color.holo_red_light);
        }
        return contentframe;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (isDataReady())
            if (mSwipeRefreshLayout != null) mSwipeRefreshLayout.setEnabled(true);
    }

    public void onResume() {
        super.onResume();
        if (mRefreshRequired)
            refresh();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mDataView = null;
    }

    public void refresh() {
        mRefreshRequired = false;
        requestData();
    }

    public void refreshOnResume() {
        mRefreshRequired = true;
    }

    /**
     * Show screen blank
     */
    protected void switchToBlank() {
        mLayoutSwitcher.switchToBlankMode();
    }

    /**
     * Show screen in display data mode
     */
    protected void switchToData() {
        mLayoutSwitcher.switchToDataMode();
    }

    /**
     * Show screen with error message
     *
     * @param msg
     */
    protected void switchToError(String msg) {
        mLayoutSwitcher.switchToErrorMode(msg);
    }

    /**
     * Show screen with loading progress
     */
    protected void switchToLoading() {
        mLayoutSwitcher.switchToLoadingDelayed(350);
    }

    @Override
    public void onErrorResponse(VolleyError volleyError) {
        enableSwipeRefresh();
        if (canChangeFragmentManagerState())
            switchToError(ErrorStrings.get(mContext, volleyError));
    }

    @Override
    public void onDataChanged() {
        enableSwipeRefresh();
        if (!isAdded()) {
            mRefreshRequired = true;
        } else {
            mRefreshRequired = false;
            switchToData();
        }
    }

    @Override
    public void onRetry() {
        refresh();
    }

    /**
     * Supply layout resource for fragment
     *
     * @return layout resource
     */
    protected abstract int getLayoutRes();

    /**
     * Should start make request in here
     */
    protected abstract void requestData();

    protected abstract boolean isDataReady();

    protected void disableSwipeRefresh() {
        if (mSwipeRefreshLayout != null) {
            mSwipeRefreshLayout.setRefreshing(false);
            mSwipeRefreshLayout.setEnabled(false);
        }
    }

    protected void enableSwipeRefresh() {
        if (mSwipeRefreshLayout != null) {
            mSwipeRefreshLayout.setRefreshing(false);
            mSwipeRefreshLayout.setEnabled(true);
        }
    }

    @Override
    public void onRefresh() {
        requestData();
    }

}
