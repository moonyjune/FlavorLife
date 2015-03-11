package com.ntq.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.ntq.api.model.OnDataChangedListener;

import moony.vn.flavorlife.R;

/**
 * @author TUNGDX
 */
public abstract class PaginatedListAdapter extends BaseAdapter implements
        OnDataChangedListener {
    protected enum FooterMode {
        ERROR, LOADING, NONE;
    }

    protected final Context mContext;
    private FooterMode mFooterMode;
    protected final LayoutInflater mLayoutInflater;
    protected OnClickListener mRetryClickListener;

    public PaginatedListAdapter(Context context, boolean isError,
                                boolean isLoading) {
        mRetryClickListener = new OnClickListener() {

            public void onClick(View view) {
                if (mFooterMode == FooterMode.ERROR)
                    retryLoadingItems();
                setFooterMode(FooterMode.LOADING);
            }

        };
        mContext = context;
        mLayoutInflater = LayoutInflater.from(context);
        if (!isError) {
            if (!isLoading)
                mFooterMode = FooterMode.NONE;
            else
                mFooterMode = FooterMode.LOADING;
        } else {
            mFooterMode = FooterMode.ERROR;
        }
    }

    private void setFooterMode(FooterMode footermode) {
        mFooterMode = footermode;
        notifyDataSetChanged();
    }

    protected View getErrorFooterView(View view, ViewGroup viewgroup) {
        if (view == null) {
            view = inflate(R.layout.error_footer_layout, viewgroup, false);
            ((Button) view.findViewById(R.id.retry_button))
                    .setOnClickListener(mRetryClickListener);
        }
        ((TextView) view.findViewById(R.id.state_text))
                .setText(getLastRequestError());
        return view;
    }

    protected FooterMode getFooterMode() {
        return mFooterMode;
    }

    protected View getLoadingFooterView(View view, ViewGroup viewgroup) {
        if (view == null)
            view = inflate(R.layout.loading_footer_layout, viewgroup, false);
        return view;
    }

    protected View inflate(int resLayout, ViewGroup viewgroup, boolean attach) {
        return mLayoutInflater.inflate(resLayout, viewgroup, attach);
    }

    @Override
    public void onDataChanged() {
        if (!isMoreDataAvailable())
            setFooterMode(FooterMode.NONE);
        else
            setFooterMode(FooterMode.LOADING);
    }

    public void triggerFooterErrorMode() {
        setFooterMode(FooterMode.ERROR);
    }

    public void triggerNoneMode() {
        setFooterMode(FooterMode.NONE);
    }

    protected abstract boolean isMoreDataAvailable();

    protected abstract String getLastRequestError();

    protected abstract void retryLoadingItems();
}
