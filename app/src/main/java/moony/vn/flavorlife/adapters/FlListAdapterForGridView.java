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
public class FlListAdapterForGridView extends NListAdapter {
    private static final int TOTAL_VIEW_COUNT = 1;
    private static final int NEW_RECIPE = 2;

    protected PaginatedList mPaginatedList;
    private NImageLoader mNImageLoader;
    private NavigationManager mNavigationManager;
    private AppAnalytics mAnalytics;

    public FlListAdapterForGridView(Context context, PaginatedList paginatedList) {
        super(context, paginatedList);
    }

    public FlListAdapterForGridView(Context context, NImageLoader imageLoader, NavigationManager navigationManager, AppAnalytics analytics, PaginatedList paginatedList) {
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
                return AdapterViewUtils.getRecipesForGridView(mContext, (Recipe) item, convertView, mNImageLoader, mNavigationManager);
        }
        return convertView;
    }

    @Override
    public int getNewItemViewType(int position) {
        Object item = mPaginatedList.getItem(position);
        if (item instanceof Recipe) {
            return NEW_RECIPE;
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
