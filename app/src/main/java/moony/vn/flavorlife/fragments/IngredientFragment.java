package moony.vn.flavorlife.fragments;

import android.os.Bundle;

import com.ntq.fragments.NFragmentSwitcher;

import moony.vn.flavorlife.R;

/**
 * Created by moony on 3/4/15.
 */
public class IngredientFragment extends NFragmentSwitcher {

    public static IngredientFragment newInstance(){
        IngredientFragment ingredientFragment = new IngredientFragment();
        Bundle bundle = new Bundle();
        ingredientFragment.setArguments(bundle);
        return ingredientFragment;
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_ingredient;
    }

    @Override
    protected void requestData() {

    }
}
