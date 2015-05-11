package moony.vn.flavorlife.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import moony.vn.flavorlife.R;
import moony.vn.flavorlife.api.model.DfeGetPeople;
import moony.vn.flavorlife.api.model.DfeSearchPeople;
import moony.vn.flavorlife.api.model.FlPaginatedList;

/**
 * Created by moony on 3/1/15.
 */
public class SearchPeopleFragment extends FlListFragment implements TextWatcher {
    private static final String DATA_SEARCH = "data_search";
    private DfeGetPeople mDfeGetPeople;
    private DfeSearchPeople mDfeSearchPeople;
    private TextView mNoData;
    private boolean isSearch = false;
    private String mData;
    private EditText mDataSearch;

    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_search_people;
    }

    @Override
    protected FlPaginatedList getFlPaginatedList() {
        if (isSearch) {
            return mDfeSearchPeople = new DfeSearchPeople(mApi, mData);
        } else {
            return mDfeGetPeople = new DfeGetPeople(mApi);
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null)
            mData = savedInstanceState.getString(DATA_SEARCH);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (mData != null && !mData.isEmpty())
            outState.putSerializable(DATA_SEARCH, mData);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mNoData = (TextView) view.findViewById(R.id.footer_no_data);
        mDataSearch = (EditText) view.findViewById(R.id.data_search);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mDataSearch.addTextChangedListener(this);
        syncNoDataView();
    }

    @Override
    public void onDataChanged() {
        super.onDataChanged();
        syncNoDataView();
    }

    private void syncNoDataView() {
        if (!isSearch) {
            if (mDfeGetPeople != null && mDfeGetPeople.isReady() && mDfeGetPeople.getCount() == 0) {
                mNoData.setVisibility(View.VISIBLE);
            } else {
                mNoData.setVisibility(View.GONE);
            }
        } else {
            if (mDfeSearchPeople != null && mDfeSearchPeople.isReady() && mDfeSearchPeople.getCount() == 0) {
                mNoData.setVisibility(View.VISIBLE);
            } else {
                mNoData.setVisibility(View.GONE);
            }
        }
    }

    private void search() {
        if (mDataSearch.getText() != null && !mDataSearch.getText().toString().isEmpty()) {
            requestSearch(mDataSearch.getText().toString());
        }
    }

    public void requestSearch(String s) {
        mData = s;
        isSearch = true;
        mFlListAdapter = null;
        mFlPaginatedList = null;
        onRefresh();
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
        search();
    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {

    }

    @Override
    public void afterTextChanged(Editable editable) {
        search();
    }
}
