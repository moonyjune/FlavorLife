package com.ntq.fragments;

/**
 * @author TUNGDX
 */

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;

import moony.vn.flavorlife.actionbar.CustomActionbar;
import moony.vn.flavorlife.analytics.AppAnalytics;
import moony.vn.flavorlife.api.Api;
import moony.vn.flavorlife.navigationmanager.NavigationManager;

import com.ntq.imageloader.NImageLoader;

/**
 * This is base fragment. <br>
 * It contains some default attributes: Context, Api, ImageLoader,
 * NavigationManager, Actionbar <br>
 */
public class NFragment extends Fragment {

    protected Context mContext;
    protected Api mApi;
    protected NFragmentHost mPageFragmentHost;
    protected boolean mSaveInstanceStateCalled;
    protected NImageLoader mImageLoader;
    protected NavigationManager mNavigationManager;
    protected CustomActionbar mActionbar;
    protected AppAnalytics mAppAnalytics;

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
}
