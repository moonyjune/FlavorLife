package moony.vn.flavorlife.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.GridView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import moony.vn.flavorlife.R;
import moony.vn.flavorlife.api.model.DfeGetUserRecipes;
import moony.vn.flavorlife.api.model.FlPaginatedList;

/**
 * Created by moony on 3/11/15.
 */
public class UserRecipesFragment extends FlListFragmentForGridView {
    private static final String USER_ID = "user_id";
    private int mUserId;
    private TextView mNoData;
    private DfeGetUserRecipes mDfeGetUserRecipes;

    public static UserRecipesFragment newInstance(int userId) {
        UserRecipesFragment userRecipesFragment = new UserRecipesFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(USER_ID, userId);
        userRecipesFragment.setArguments(bundle);
        return userRecipesFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null) {
            mUserId = savedInstanceState.getInt(USER_ID);
        } else {
            getArguments().getInt(USER_ID);
        }
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mNoData = (TextView) view.findViewById(R.id.footer_no_data);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        syncNoDataView();
    }

    @Override
    public void onDataChanged() {
        super.onDataChanged();
        syncNoDataView();
    }

    private void syncNoDataView() {
        if (mDfeGetUserRecipes != null && mDfeGetUserRecipes.isReady()) {
            if (mDfeGetUserRecipes.getCount() == 0) {
                mNoData.setVisibility(View.VISIBLE);
            } else {
                mNoData.setVisibility(View.GONE);
            }
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(USER_ID, mUserId);
    }

    @Override
    protected FlPaginatedList getFlPaginatedList() {
        return mDfeGetUserRecipes = new DfeGetUserRecipes(mApi, mUserId);
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_user_recipes;
    }
}
