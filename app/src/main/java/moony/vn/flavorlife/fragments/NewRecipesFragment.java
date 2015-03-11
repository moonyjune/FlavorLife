package moony.vn.flavorlife.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ntq.fragments.NFragment;
import com.ntq.fragments.NFragmentSwitcher;

import moony.vn.flavorlife.R;
import moony.vn.flavorlife.api.model.DfeGetNewRecipes;
import moony.vn.flavorlife.api.model.FlPaginatedList;

/**
 * Created by moony on 3/1/15.
 */
public class NewRecipesFragment extends TabRootFragment {

    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_new_recipes;
    }

    @Override
    public int getTabRootId() {
        return R.id.tab_new_recipes;
    }

    @Override
    protected FlPaginatedList getFlPaginatedList() {
        return new DfeGetNewRecipes(mApi);
    }
}
