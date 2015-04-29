package moony.vn.flavorlife.fragments;

import android.os.Bundle;

import moony.vn.flavorlife.R;
import moony.vn.flavorlife.api.model.DfeGetUserRecipes;
import moony.vn.flavorlife.api.model.FlPaginatedList;

/**
 * Created by moony on 3/11/15.
 */
public class UserRecipesFragment extends FlListFragmentForGridView {
    private static final String USER_ID = "user_id";
    private int mUserId;

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
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(USER_ID, mUserId);
    }

    @Override
    protected FlPaginatedList getFlPaginatedList() {
        return new DfeGetUserRecipes(mApi);
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_user_recipes;
    }
}
