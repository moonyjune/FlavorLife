package moony.vn.flavorlife.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.ntq.fragments.NFragmentSwitcher;

import java.util.ArrayList;
import java.util.List;

import moony.vn.flavorlife.R;
import moony.vn.flavorlife.entities.Ingredient;
import moony.vn.flavorlife.entities.Recipe;
import moony.vn.flavorlife.entities.SectionIngredient;
import moony.vn.flavorlife.entities.SectionInstruction;
import moony.vn.flavorlife.entities.Step;
import moony.vn.flavorlife.layout.DetailRecipeListIngredientsView;
import moony.vn.flavorlife.layout.DetailRecipeListInstructionsView;

/**
 * Created by moony on 3/9/15.
 */
public class RecipeDetailFragment extends NFragmentSwitcher {
    private static final String RECIPE = "recipe";
    private Recipe mRecipe;
    private DetailRecipeListIngredientsView mDetailRecipeListIngredientsView;
    private DetailRecipeListInstructionsView mDetailRecipeListInstructionsView;

    public static RecipeDetailFragment newInstance(Recipe recipe) {
        RecipeDetailFragment recipeDetailFragment = new RecipeDetailFragment();
        Bundle argument = new Bundle();
        recipeDetailFragment.setArguments(argument);
        return recipeDetailFragment;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mDetailRecipeListIngredientsView = (DetailRecipeListIngredientsView) view.findViewById(R.id.list_ingredients);
        mDetailRecipeListInstructionsView = (DetailRecipeListInstructionsView) view.findViewById(R.id.list_instruction);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        List<SectionIngredient> sectionIngredientList = new ArrayList<SectionIngredient>();
        SectionIngredient sectionIngredient = new SectionIngredient();
        List<Ingredient> ingredients = new ArrayList<Ingredient>();
        Ingredient ingredient = new Ingredient();
        ingredients.add(ingredient);
        ingredients.add(ingredient);
        ingredients.add(ingredient);
        sectionIngredient.setListIngredients(ingredients);
        sectionIngredientList.add(sectionIngredient);
        sectionIngredientList.add(sectionIngredient);
        sectionIngredientList.add(sectionIngredient);
        mDetailRecipeListIngredientsView.setListSectionIngredient(sectionIngredientList);

        List<SectionInstruction> sectionInstructionList = new ArrayList<SectionInstruction>();
        SectionInstruction sectionInstruction = new SectionInstruction();
        List<Step> steps = new ArrayList<Step>();
        Step step = new Step();
        steps.add(step);
        steps.add(step);
        steps.add(step);
        sectionInstruction.setListSteps(steps);
        sectionInstructionList.add(sectionInstruction);
        sectionInstructionList.add(sectionInstruction);
        sectionInstructionList.add(sectionInstruction);
        mDetailRecipeListInstructionsView.setListSectionIngredient(sectionInstructionList);
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_recipe_detail;
    }

    @Override
    protected void requestData() {

    }
}
