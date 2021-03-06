package com.ntq.fragments;

/**
 * @author TUNGDX
 */

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;

import com.android.volley.VolleyError;
import com.ntq.imageloader.NImageLoader;
import com.sromku.simple.fb.SimpleFacebook;

import moony.vn.flavorlife.R;
import moony.vn.flavorlife.actionbar.CustomActionbar;
import moony.vn.flavorlife.analytics.AppAnalytics;
import moony.vn.flavorlife.api.Api;
import moony.vn.flavorlife.navigationmanager.NavigationManager;
import moony.vn.flavorlife.utils.DialogUtils;
import moony.vn.flavorlife.utils.ErrorStrings;

/**
 * This is base fragment. <br>
 * It contains some default attributes: Context, Api, ImageLoader,
 * NavigationManager, Actionbar <br>
 */
public class NFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {

    protected Context mContext;
    protected Api mApi;
    protected NFragmentHost mPageFragmentHost;
    protected boolean mSaveInstanceStateCalled;
    protected NImageLoader mImageLoader;
    protected NavigationManager mNavigationManager;
    protected CustomActionbar mActionbar;
    protected AppAnalytics mAppAnalytics;
    private ProgressDialog mProgressDialog;
    protected SwipeRefreshLayout mSwipeRefreshLayout;
    protected SimpleFacebook mSimpleFacebook;

    public NFragment() {
        setArguments(new Bundle());
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if ((NFragmentHost) getActivity() != mPageFragmentHost) {
            mPageFragmentHost = (NFragmentHost) getActivity();
            mContext = getActivity();
            mApi = mPageFragmentHost.getDfeApi();
            mImageLoader = mPageFragmentHost.getImageLoader();
            mNavigationManager = mPageFragmentHost.getNavigationManager();
            mActionbar = mPageFragmentHost.getActionbar();
            mAppAnalytics = mPageFragmentHost.getAnalytics();
        }
        mSaveInstanceStateCalled = false;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        hideKeyBoard();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mSimpleFacebook = SimpleFacebook.getInstance(getActivity());
        mSaveInstanceStateCalled = false;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mSaveInstanceStateCalled = false;
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();
        mSimpleFacebook = SimpleFacebook.getInstance(getActivity());
        mSaveInstanceStateCalled = false;
    }

    @Override
    public void onStart() {
        super.onStart();
        mSaveInstanceStateCalled = false;
    }

    private void hideKeyBoard() {
        InputMethodManager inputMethodManager = (InputMethodManager) getActivity()
                .getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(getView().getWindowToken(), 0);
    }

    /**
     * Method check state of fragment. Can not change state of fragment (like:
     * navigate in fragment, change layout...)
     *
     * @return true Valid for change state, otherwise not valid
     */
    public boolean canChangeFragmentManagerState() {
        FragmentActivity fragmentactivity = getActivity();
        return mSaveInstanceStateCalled || fragmentactivity == null ? false
                : true;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mSaveInstanceStateCalled = true;
    }

    public void showDialogLoading() {
        if (mProgressDialog == null)
            mProgressDialog = new ProgressDialog(getActivity());
        mProgressDialog.setMessage(getString(R.string.waiting));
        mProgressDialog.setCanceledOnTouchOutside(true);
        mProgressDialog.show();
    }

    public void hideDialogLoading() {
        if (mProgressDialog != null)
            mProgressDialog.dismiss();
    }

    public void showDialogMessageError(String mess) {
        DialogUtils.getInstance().showDialog(getActivity(), mess, true, false, "OK", null, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogUtils.getInstance().dismissDialog();
            }
        }, null);
    }

    public void showDialogMessageError(VolleyError error) {
        DialogUtils.getInstance().showDialog(getActivity(), ErrorStrings.get(getActivity(), error), true, false, "OK", null, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogUtils.getInstance().dismissDialog();
            }
        }, null);
    }

    @Override
    public void onRefresh() {

    }
}
