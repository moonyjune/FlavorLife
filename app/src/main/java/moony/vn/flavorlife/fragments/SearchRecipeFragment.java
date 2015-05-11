package moony.vn.flavorlife.fragments;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.VolleyError;

import moony.vn.flavorlife.R;
import moony.vn.flavorlife.api.model.DfeGetRecipes;
import moony.vn.flavorlife.api.model.DfeSearchRecipe;
import moony.vn.flavorlife.api.model.FlPaginatedList;
import moony.vn.flavorlife.entities.SearchRecipeCondition;

/**
 * Created by moony on 3/1/15.
 */
public class SearchRecipeFragment extends FlListFragment implements TextWatcher, View.OnClickListener {
    private static final String SEARCH_CONDITION = "search_condition";
    private static final int REQUEST_ADVANCE_SEARCH = 1010;
    private DfeGetRecipes mDfeGetRecipes;
    private DfeSearchRecipe mDfeSearchRecipe;
    private TextView mNoData;
    private boolean isSearch = false;
    private boolean isAdvancedSearch = false;
    private SearchRecipeCondition mSearchRecipeCondition;
    private EditText mDataSearch;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null)
            mSearchRecipeCondition = (SearchRecipeCondition) savedInstanceState.getSerializable(SEARCH_CONDITION);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (mSearchRecipeCondition != null)
            outState.putSerializable(SEARCH_CONDITION, mSearchRecipeCondition);
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_search_recipe;
    }

    @Override
    protected FlPaginatedList getFlPaginatedList() {
        if (isSearch) {
            return mDfeSearchRecipe = new DfeSearchRecipe(mApi, mSearchRecipeCondition);
        } else {
            return mDfeGetRecipes = new DfeGetRecipes(mApi);
        }
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mNoData = (TextView) view.findViewById(R.id.footer_no_data);
        mDataSearch = (EditText) view.findViewById(R.id.data_search);
        view.findViewById(R.id.advance).setOnClickListener(this);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mDataSearch.addTextChangedListener(this);
        syncNoDataView();
    }

    private void search() {
        if (mDataSearch.getText() != null && !mDataSearch.getText().toString().isEmpty()) {
            requestSearch(mDataSearch.getText().toString());
        }
    }

    @Override
    public void onDataChanged() {
        super.onDataChanged();
        hideDialogLoading();
        syncNoDataView();
    }

    @Override
    public void onErrorResponse(VolleyError volleyError) {
        super.onErrorResponse(volleyError);
        hideDialogLoading();
    }

    private void syncNoDataView() {
        if (!isSearch) {
            if (mDfeGetRecipes != null && mDfeGetRecipes.isReady() && mDfeGetRecipes.getCount() == 0) {
                mNoData.setVisibility(View.VISIBLE);
            } else {
                mNoData.setVisibility(View.GONE);
            }
        } else {
            if (mDfeSearchRecipe != null && mDfeSearchRecipe.isReady() && mDfeSearchRecipe.getCount() == 0) {
                mNoData.setVisibility(View.VISIBLE);
            } else {
                mNoData.setVisibility(View.GONE);
            }
        }
    }

    public void requestSearch(String s) {
        mSearchRecipeCondition = new SearchRecipeCondition();
        mSearchRecipeCondition.setName(s);
        isAdvancedSearch = false;
        isSearch = true;
        mFlListAdapter = null;
        mFlPaginatedList = null;
        onRefresh();
    }

//    public void searchAdvance() {
//        System.out.println("Mj : search advance");
//    }

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

    @Override
    public void onClick(View view) {
        if (!isAdvancedSearch)
            mSearchRecipeCondition = null;
        AdvanceSearchDialogFragment advanceSearchDialogFragment = AdvanceSearchDialogFragment.newInstance(mSearchRecipeCondition);
        advanceSearchDialogFragment.setTargetFragment(this, REQUEST_ADVANCE_SEARCH);
        advanceSearchDialogFragment.show(getFragmentManager(), null);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        showDialogLoading();
        if (requestCode == REQUEST_ADVANCE_SEARCH) {
            if (resultCode == Activity.RESULT_OK && data != null) {
                Bundle bundle = data.getBundleExtra(AdvanceSearchDialogFragment.DATA);
                if (bundle == null) {
                    hideDialogLoading();
                    return;
                }
                mSearchRecipeCondition = (SearchRecipeCondition) bundle.getSerializable(SEARCH_CONDITION);
                isAdvancedSearch = true;
                isSearch = true;
                onRefresh();
            } else {
                hideDialogLoading();
            }
        } else {
            hideDialogLoading();
        }
    }
}
