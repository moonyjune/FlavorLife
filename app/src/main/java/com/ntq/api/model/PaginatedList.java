package com.ntq.api.model;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;

import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.android.volley.Response.Listener;

/**
 * 
 * @author TUNGDX
 * 
 * @param <T>
 */
public abstract class PaginatedList<T> extends DfeModel implements
		Listener<JSONObject> {

    protected boolean mAutoLoadNextPage;
    protected List<T> mItems;
    protected JSONObject mLastResponse;
    private int mItemsUntilEndCount;
    private boolean mMoreAvailable;
    private boolean mIsLoading = false;

    protected PaginatedList(boolean autoLoadNextPage) {
        mItems = new ArrayList<T>();
        mItemsUntilEndCount = 4;
        mMoreAvailable = true;
        mAutoLoadNextPage = autoLoadNextPage;
    }

    protected PaginatedList() {
        this(true);
    }

    public PaginatedList(List<T> list) {
        this(false);
        mItems = list;
    }

    private void updateItemsUntilEndCount(int listSize) {
        if (mItemsUntilEndCount > 0)
            mItemsUntilEndCount = Math.max(1, listSize / 4);
        else
            mItemsUntilEndCount = 4;
    }

    public int getCount() {
        return mItems.size();
    }

    /**
     * Parse items from response from Server
     *
     * @param response response from Server, whether: json, xml, string...
     * @return items list data
     */
    protected abstract List<T> getItemsFromResponse(JSONObject response);

    protected abstract boolean checkMoreAvaiable(List<T> response);

    public boolean isMoreAvailable() {
        return mMoreAvailable;
    }

    public boolean isReady() {
        return mLastResponse == null && mItems.size() <= 0 ? false : true;
    }

    protected abstract Request<JSONObject> makeRequest();

    protected abstract Request<JSONObject> makeMoreRequest();

    protected abstract Request<JSONObject> makeRetryRequest();

    public void resetItems() {
        mMoreAvailable = true;
        mItems.clear();
        notifyDataSetChanged();
    }

    public void retryLoadItems() {
        if (!inErrorState() || mIsLoading) {
            return;
        }
        clearErrors();
        makeRetryRequest();
        mIsLoading = true;
    }

    public void startLoadItems() {
        if (mMoreAvailable && getCount() == 0) {
            clearErrors();
            makeRequest();
            mIsLoading = true;
        }
    }

    private void requestMoreItemsIfNoRequestExists() {
        if (inErrorState()) {
            return;
        }
        if (!mIsLoading) {
            makeMoreRequest();
            mIsLoading = true;
        }
    }

    public T getItem(int position) {
        return getItem(position, true);
    }

    public final T getItem(int position, boolean isLoadMore) {
        if (position < 0)
            throw new IllegalArgumentException((new StringBuilder())
                    .append("Can't return an item with a negative index: ")
                    .append(position).toString());

        if (position >= getCount())
            return null;
        T obj = mItems.get(position);
        if (mAutoLoadNextPage && mMoreAvailable
                && position >= getCount() - mItemsUntilEndCount) {
            if (isLoadMore)
                requestMoreItemsIfNoRequestExists();
        }
        return obj;
    }

    @Override
    public void onResponse(JSONObject response) {
        mIsLoading = false;
        clearErrors();
        mLastResponse = response;
        List<T> list = getItemsFromResponse(response);
        if (list != null) {
            updateItemsUntilEndCount(list.size());
            mItems.addAll(list);
        }
        mMoreAvailable = checkMoreAvaiable(list);
        notifyDataSetChanged();
    }

    @Override
    public void onErrorResponse(VolleyError volleyerror) {
        super.onErrorResponse(volleyerror);
        mIsLoading = false;
    }

    public List<T> getAllItems() {
        return mItems;
    }

    public void appendItems(int position, List<T> items) {
        mItems.addAll(position, items);
    }
}
