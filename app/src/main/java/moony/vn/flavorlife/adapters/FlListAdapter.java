package moony.vn.flavorlife.adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import com.ntq.adapters.NListAdapter;
import com.ntq.api.model.PaginatedList;
import com.ntq.imageloader.NImageLoader;

import moony.vn.flavorlife.analytics.AppAnalytics;
import moony.vn.flavorlife.entities.Cookbook;
import moony.vn.flavorlife.entities.Follow;
import moony.vn.flavorlife.entities.Follower;
import moony.vn.flavorlife.entities.Message;
import moony.vn.flavorlife.entities.Recipe;
import moony.vn.flavorlife.navigationmanager.NavigationManager;

/**
 * Created by moony on 3/4/15.
 */
public class FlListAdapter extends NListAdapter {
    private static final int TOTAL_VIEW_COUNT = 5;
    private static final int NEW_RECIPE = 2;
    private static final int COOKBOOK = 3;
    private static final int FOLLOW = 4;
    private static final int FOLLOWER = 5;
    private static final int MESSAGE = 6;

    protected PaginatedList mPaginatedList;
    private NImageLoader mNImageLoader;
    private NavigationManager mNavigationManager;
    private AppAnalytics mAnalytics;

    public FlListAdapter(Context context, PaginatedList paginatedList) {
        super(context, paginatedList);
    }

    public FlListAdapter(Context context, NImageLoader imageLoader, NavigationManager navigationManager, AppAnalytics analytics, PaginatedList paginatedList) {
        super(context, paginatedList);
        mPaginatedList = paginatedList;
        mNImageLoader = imageLoader;
        mNavigationManager = navigationManager;
        mAnalytics = analytics;
    }

    @Override
    protected View getViewByData(int position, View convertView, ViewGroup parent) {
        Object item = mPaginatedList.getItem(position);
        int type = getNewItemViewType(position);
        switch (type) {
            case NEW_RECIPE:
                return AdapterViewUtils.getRecipes(mContext, (Recipe) item, convertView, mNImageLoader, mNavigationManager);
            case COOKBOOK:
                return AdapterViewUtils.getCookbooks(mContext, (Cookbook) item, convertView, mNImageLoader, mNavigationManager);
            case FOLLOW:
                return AdapterViewUtils.getFollows(mContext, (Follow) item, convertView, mNImageLoader, mNavigationManager);
            case FOLLOWER:
                return AdapterViewUtils.getFollowers(mContext, (Follower) item, convertView, mNImageLoader, mNavigationManager);
            case MESSAGE:
                return AdapterViewUtils.getMessages(mContext, (Message) item, convertView, mNImageLoader, mNavigationManager);
        }
        return convertView;
    }

    @Override
    public int getNewItemViewType(int position) {
        Object item = mPaginatedList.getItem(position);
        if (item instanceof Recipe) {
            return NEW_RECIPE;
        } else if (item instanceof Cookbook) {
            return COOKBOOK;
        } else if (item instanceof Follow) {
            return FOLLOW;
        } else if (item instanceof Follower) {
            return FOLLOWER;
        } else if (item instanceof Message) {
            return MESSAGE;
        }
        throw new IllegalArgumentException("Invalid object type");
    }

    @Override
    public int getNewViewTypeCount() {
        return TOTAL_VIEW_COUNT;
    }

    @Override
    public void onDestroyView() {

    }

    @Override
    public long getItemId(int position) {
        return (long) position;
    }

    public void clearAll() {
        mPaginatedList.removeDataChangedListener(this);
        mPaginatedList.removeErrorListener(this);
        mPaginatedList.resetItems();
        notifyDataSetChanged();
    }
}
