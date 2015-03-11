package com.ntq.activities;

import android.os.Bundle;
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
 *
 * @author TUNGDX
 *
 */

/**
 * This is activity that supplies some functions which usually needed in
 * application.<br>
 * <li>Switch between state of screen: loading, data, error<br> <li>Retry
 * function, help developers avoid to do some jobs that must be repeated in many
 * screens in application.
 */
public abstract class NActivitySwitcher extends NActivity implements
        RetryButtonListener, OnDataChangedListener, ErrorListener {
    protected static final String TAG = "NActivitySwitcher";
    protected ViewGroup mDataView;
    private LayoutSwitcher mLayoutSwitcher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView();
    }

    protected void setContentView() {
        ContentFrame contentFrame = new ContentFrame(getApplicationContext());
        contentFrame.setDataLayout(getLayoutInflater(), getLayoutRes(),
                R.id.content_layout);
        mDataView = contentFrame.getDataLayout();
        mLayoutSwitcher = new LayoutSwitcher(contentFrame,
                R.id.content_layout, R.id.error_layout,
                R.id.loading_layout, this, LayoutSwitcher.DATA_MODE);
        setContentView(contentFrame);
    }

    protected abstract int getLayoutRes();

    protected abstract void requestData();

    protected void switchToBlank() {
        if (mLayoutSwitcher != null)
            mLayoutSwitcher.switchToBlankMode();
    }

    protected void switchToData() {
        if (mLayoutSwitcher != null)
            mLayoutSwitcher.switchToDataMode();
    }

    protected void switchToError(String msg) {
        if (mLayoutSwitcher != null) {
            mLayoutSwitcher.switchToErrorMode(msg);
        }
    }

    protected void switchToLoading() {
        if (mLayoutSwitcher != null)
            mLayoutSwitcher.switchToLoadingDelayed(350);
    }

    @Override
    public void onErrorResponse(VolleyError volleyerror) {
        switchToError(ErrorStrings.get(this, volleyerror));
    }

    @Override
    public void onRetry() {
        refresh();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mDataView = null;
        mLayoutSwitcher = null;
    }

    @Override
    public void onDataChanged() {
        switchToData();
    }

    public void refresh() {
        requestData();
    }
}
