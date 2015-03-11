package com.ntq.adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.Response.ErrorListener;
import com.android.volley.VolleyError;

import moony.vn.flavorlife.utils.ErrorStrings;

import com.ntq.api.model.PaginatedList;

/**
 * @param <T> Object use in this adapter
 * @author TUNGDX
 */
public abstract class NListAdapter<T> extends PaginatedListAdapter implements
        ErrorListener {
    private static final int FOOTER_ERROR_TYPE = 0;
    private static final int FOOTER_LOADING_TYPE = 1;
    private static final int RESERVED_TYPE_COUNT = 2;

    protected PaginatedList<T> mPaginatedList;

    public NListAdapter(Context context, PaginatedList<T> paginatedList) {
        super(context, paginatedList.inErrorState(), paginatedList
                .isMoreAvailable());
        mPaginatedList = paginatedList;
        mPaginatedList.addDataChangedListener(this);
        mPaginatedList.addErrorListener(this);
    }

    @Override
    protected String getLastRequestError() {
        return ErrorStrings.get(mContext, mPaginatedList.getVolleyError());
    }

    @Override
    protected boolean isMoreDataAvailable() {
        return mPaginatedList.isMoreAvailable();
    }

    /**
     * Must call this method in onDetroy() method of Fragment or Activity to
     * avoid leak memory.
     */
    public void onDestroy() {
        mPaginatedList.removeDataChangedListener(this);
    }

    /**
     * Use this method to supply view by data for adapter.
     *
     * @param position
     * @param convertView
     * @param parent
     * @return
     */
    protected abstract View getViewByData(int position, View convertView,
                                          ViewGroup parent);

    public abstract void onDestroyView();

    /**
     * Retry loading data after error occurred
     */
    @Override
    protected void retryLoadingItems() {
        mPaginatedList.retryLoadItems();
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        triggerFooterErrorMode();
    }

    @Override
    public int getCount() {
        int i = mPaginatedList.getCount();
        if (getFooterMode() != PaginatedListAdapter.FooterMode.NONE)
            i++;
        return i;
    }

    @Override
    public Object getItem(int position) {
        return mPaginatedList.getItem(position);
    }

    /**
     * Do not use this method, use {@link getViewByData} instead.
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        int type = getItemViewType(position);
        if (type == FOOTER_ERROR_TYPE) {
            return getErrorFooterView(convertView, parent);
        } else if (type == FOOTER_LOADING_TYPE) {
            return getLoadingFooterView(convertView, parent);
        } else {
            return getViewByData(position, convertView, parent);
        }
    }

    /**
     * Do not use this method, use {@link getNewItemViewType} instead.
     */
    @Override
    public int getItemViewType(int position) {
        super.getItemViewType(position);
        if (getFooterMode() != FooterMode.NONE && position == getCount() - 1) {
            if (getFooterMode() == FooterMode.LOADING) {
                return FOOTER_LOADING_TYPE;
            } else {
                return FOOTER_ERROR_TYPE;
            }
        } else {
            return getNewItemViewType(position);
        }
    }

    /**
     * Do not use this method, use {@link getNewViewTypeCount} instead.
     */
    @Override
    public int getViewTypeCount() {
        return RESERVED_TYPE_COUNT + getNewViewTypeCount();
    }

    /**
     * Same {@link getViewTypeCount}, but value must be equals or larger than 1
     *
     * @return
     */
    public int getNewViewTypeCount() {
        return 1;
    }

    /**
     * Same {@link getItemViewType}, but value must be equals or larger than 2
     * (Because already have 2 items: loading footer and error footer)
     *
     * @param position
     * @return
     */
    public int getNewItemViewType(int position) {
        return 2;
    }
}
