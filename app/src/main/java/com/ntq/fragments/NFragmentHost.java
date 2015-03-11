package com.ntq.fragments;

import com.ntq.imageloader.NImageLoader;

import moony.vn.flavorlife.actionbar.CustomActionbar;
import moony.vn.flavorlife.analytics.AppAnalytics;
import moony.vn.flavorlife.api.Api;
import moony.vn.flavorlife.navigationmanager.NavigationManager;

/**
 * @author TUNGDX
 */
public interface NFragmentHost {
    public NImageLoader getImageLoader();

    public Api getDfeApi();

    public NavigationManager getNavigationManager();

    public CustomActionbar getActionbar();

    public AppAnalytics getAnalytics();
}
