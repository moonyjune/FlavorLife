package moony.vn.flavorlife.fragments;

import android.os.Bundle;

import com.ntq.fragments.NFragmentSwitcher;

import moony.vn.flavorlife.R;
import moony.vn.flavorlife.entities.Recipe;

/**
 * Created by moony on 3/9/15.
 */
public class RecipeDetailFragment extends NFragmentSwitcher {
    private static final String RECIPE = "recipe";
    private Recipe mRecipe;

    public static RecipeDetailFragment newInstance(Recipe recipe) {
        RecipeDetailFragment recipeDetailFragment = new RecipeDetailFragment();
        Bundle argument = new Bundle();
        recipeDetailFragment.setArguments(argument);
        return recipeDetailFragment;
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_recipe_detail;
    }

    @Override
    protected void requestData() {

    }
}
