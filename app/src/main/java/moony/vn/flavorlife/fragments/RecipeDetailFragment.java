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
import moony.vn.flavorlife.layout.ListIngredientsView;
import moony.vn.flavorlife.layout.ListInstructionsView;

/**
 * Created by moony on 3/9/15.
 */
public class RecipeDetailFragment extends NFragmentSwitcher {
    private static final String RECIPE = "recipe";
    private Recipe mRecipe;
    private ListIngredientsView mListIngredientsView;
    private ListInstructionsView mListInstructionsView;

    public static RecipeDetailFragment newInstance(Recipe recipe) {
        RecipeDetailFragment recipeDetailFragment = new RecipeDetailFragment();
        Bundle argument = new Bundle();
        recipeDetailFragment.setArguments(argument);
        return recipeDetailFragment;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mListIngredientsView = (ListIngredientsView) view.findViewById(R.id.list_ingredients);
        mListInstructionsView = (ListInstructionsView) view.findViewById(R.id.list_instruction);
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
        mListIngredientsView.setListSectionIngredient(sectionIngredientList);

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
        mListInstructionsView.setListSectionIngredient(sectionInstructionList);
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_recipe_detail;
    }

    @Override
    protected void requestData() {

    }
}
