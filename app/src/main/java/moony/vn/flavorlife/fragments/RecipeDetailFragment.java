package moony.vn.flavorlife.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.ntq.api.model.OnDataChangedListener;
import com.ntq.fragments.NFragmentSwitcher;

import moony.vn.flavorlife.FlavorLifeApplication;
import moony.vn.flavorlife.R;
import moony.vn.flavorlife.api.model.DfeGetRecipeDetail;
import moony.vn.flavorlife.api.model.DfeLikeRecipe;
import moony.vn.flavorlife.api.model.DfeUnLikeRecipe;
import moony.vn.flavorlife.api.model.DfeUnUseRecipe;
import moony.vn.flavorlife.api.model.DfeUseRecipe;
import moony.vn.flavorlife.entities.Recipe;
import moony.vn.flavorlife.layout.DetailRecipeListIngredientsView;
import moony.vn.flavorlife.layout.DetailRecipeListInstructionsView;

/**
 * Created by moony on 3/9/15.
 */
public class RecipeDetailFragment extends NFragmentSwitcher implements View.OnClickListener {
    private static final String RECIPE = "recipe";
    private Recipe mRecipe;
    private DetailRecipeListIngredientsView mDetailRecipeListIngredientsView;
    private DetailRecipeListInstructionsView mDetailRecipeListInstructionsView;
    private DfeGetRecipeDetail mDfeGetRecipeDetail;
    private TextView mKind, mTitle, mLevel, mLikes, mUses, mIntro, mTips, mAuthorComments;
    private ImageView mImage;
    private DfeLikeRecipe mDfeLikeRecipe;
    private DfeUnLikeRecipe mDfeUnLikeRecipe;
    private DfeUseRecipe mDfeUseRecipe;
    private DfeUnUseRecipe mDfeUnUseRecipe;

    private OnDataChangedListener onLikeListener = new OnDataChangedListener() {
        @Override
        public void onDataChanged() {
            hideDialogLoading();
            if (mDfeLikeRecipe != null && mDfeLikeRecipe.isReady()) {
                mLikes.setText(mDfeLikeRecipe.getNumLikes() + "");
                syncLikeIcon(true);
                mRecipe.setLikes(mDfeLikeRecipe.getNumLikes());
            }
        }
    };

    private OnDataChangedListener onUnLikeListener = new OnDataChangedListener() {
        @Override
        public void onDataChanged() {
            hideDialogLoading();
            if (mDfeUnLikeRecipe != null && mDfeUnLikeRecipe.isReady()) {
                mLikes.setText(mDfeUnLikeRecipe.getNumLikes() + "");
                syncLikeIcon(false);
                mRecipe.setLikes(mDfeUnLikeRecipe.getNumLikes());
            }

        }
    };

    private OnDataChangedListener onUseListener = new OnDataChangedListener() {
        @Override
        public void onDataChanged() {
            hideDialogLoading();
            if (mDfeUseRecipe != null && mDfeUseRecipe.isReady()) {
                mUses.setText(mDfeUseRecipe.getNumUses() + "");
                syncUseIcon(true);
                mRecipe.setUsed(mDfeUseRecipe.getNumUses());
            }
        }
    };

    private OnDataChangedListener onUnUseListener = new OnDataChangedListener() {
        @Override
        public void onDataChanged() {
            hideDialogLoading();
            if (mDfeUnUseRecipe != null && mDfeUnUseRecipe.isReady()) {
                mUses.setText(mDfeUnUseRecipe.getNumUses() + "");
                syncUseIcon(false);
                mRecipe.setUsed(mDfeUnUseRecipe.getNumUses());
            }
        }
    };

    public static RecipeDetailFragment newInstance(Recipe recipe) {
        RecipeDetailFragment recipeDetailFragment = new RecipeDetailFragment();
        Bundle argument = new Bundle();
        argument.putSerializable(RECIPE, recipe);
        recipeDetailFragment.setArguments(argument);
        return recipeDetailFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState == null) {
            mRecipe = (Recipe) getArguments().getSerializable(RECIPE);
        } else {
            mRecipe = (Recipe) savedInstanceState.getSerializable(RECIPE);
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (mRecipe != null)
            outState.putSerializable(RECIPE, mRecipe);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mDetailRecipeListIngredientsView = (DetailRecipeListIngredientsView) view.findViewById(R.id.list_ingredients);
        mDetailRecipeListInstructionsView = (DetailRecipeListInstructionsView) view.findViewById(R.id.list_instruction);
        mKind = (TextView) view.findViewById(R.id.recipe_kind);
        mTitle = (TextView) view.findViewById(R.id.recipe_name);
        mLikes = (TextView) view.findViewById(R.id.recipe_likes);
        mUses = (TextView) view.findViewById(R.id.recipe_used);
        mImage = (ImageView) view.findViewById(R.id.recipe_image);
        mIntro = (TextView) view.findViewById(R.id.recipe_info);
        mTips = (TextView) view.findViewById(R.id.recipe_tip_note);
        mLevel = (TextView) view.findViewById(R.id.recipe_level);
        mAuthorComments = (TextView) view.findViewById(R.id.recipe_author_comment);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
//        List<SectionIngredient> sectionIngredientList = new ArrayList<SectionIngredient>();
//        SectionIngredient sectionIngredient = new SectionIngredient();
//        List<Ingredient> ingredients = new ArrayList<Ingredient>();
//        Ingredient ingredient = new Ingredient();
//        ingredients.add(ingredient);
//        ingredients.add(ingredient);
//        ingredients.add(ingredient);
//        sectionIngredient.setListIngredients(ingredients);
//        sectionIngredientList.add(sectionIngredient);
//        sectionIngredientList.add(sectionIngredient);
//        sectionIngredientList.add(sectionIngredient);
//        mDetailRecipeListIngredientsView.setListSectionIngredient(sectionIngredientList);
//
//        List<SectionInstruction> sectionInstructionList = new ArrayList<SectionInstruction>();
//        SectionInstruction sectionInstruction = new SectionInstruction();
//        List<Step> steps = new ArrayList<Step>();
//        Step step = new Step();
//        steps.add(step);
//        steps.add(step);
//        steps.add(step);
//        sectionInstruction.setListSteps(steps);
//        sectionInstructionList.add(sectionInstruction);
//        sectionInstructionList.add(sectionInstruction);
//        sectionInstructionList.add(sectionInstruction);
//        mDetailRecipeListInstructionsView.setListSectionIngredient(sectionInstructionList);
        if (isDataReady()) {
            switchToData();
            mDetailRecipeListIngredientsView.setListSectionIngredient(mDfeGetRecipeDetail.getRecipe().getListSectionIngredients());
            mDetailRecipeListInstructionsView.setListSectionIngredient(mDfeGetRecipeDetail.getRecipe().getListSectionInstructions());
            setDataToViews(mDfeGetRecipeDetail.getRecipe());
        } else {
            requestData();
        }
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_recipe_detail;
    }

    @Override
    protected void requestData() {
        switchToLoading();
        if (mDfeGetRecipeDetail == null) {
            mDfeGetRecipeDetail = new DfeGetRecipeDetail(mApi);
            mDfeGetRecipeDetail.addDataChangedListener(this);
            mDfeGetRecipeDetail.addErrorListener(this);
        }
        mDfeGetRecipeDetail.makeRequest(mRecipe.getId());
    }

    @Override
    public void onDataChanged() {
        super.onDataChanged();
        switchToData();
        if (isDataReady()) {
            mRecipe.updateRecipe(mDfeGetRecipeDetail.getRecipe());
            mDetailRecipeListIngredientsView.setListSectionIngredient(mDfeGetRecipeDetail.getRecipe().getListSectionIngredients());
            mDetailRecipeListInstructionsView.setListSectionIngredient(mDfeGetRecipeDetail.getRecipe().getListSectionInstructions());
            setDataToViews(mDfeGetRecipeDetail.getRecipe());
        }
    }

    private boolean isDataReady() {
        if (mDfeGetRecipeDetail != null && mDfeGetRecipeDetail.isReady()) {
            return true;
        } else {
            return false;
        }
    }

    private void setDataToViews(Recipe recipe) {
        mKind.setText(recipe.getKindName());
        mTitle.setText(recipe.getName());
        mLikes.setText(recipe.getLikes() + "");
        mUses.setText(recipe.getUsed() + "");
        mLevel.setText(recipe.getLevel() + "");
        mIntro.setText(recipe.getIntroduction());
        mTips.setText(recipe.getTipNote());
        mAuthorComments.setText(recipe.getAuthorComments());
        if (recipe.getImages() != null && !recipe.getImages().isEmpty()) {
            mImageLoader.display(recipe.getImages(), mImage);
        } else {
            mImage.setImageResource(R.drawable.default_recipe_image);
        }

        syncLikeIcon(recipe.isLiked());
        syncUseIcon(recipe.isUsed());

        mLikes.setOnClickListener(this);
        mUses.setOnClickListener(this);
    }

    private void syncLikeIcon(boolean isLiked) {
        if (mRecipe != null) {
            mRecipe.setIsLiked(isLiked);
        }
        if (isLiked) {
            mLikes.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_heart_red, 0, 0, 0);
        } else {
            mLikes.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_heart_border_red, 0, 0, 0);
        }
    }

    private void syncUseIcon(boolean isUsed) {
        if (mRecipe != null)
            mRecipe.setIsUsed(isUsed);
        if (isUsed) {
            mUses.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_use_red, 0, 0, 0);
        } else {
            mUses.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_use_black, 0, 0, 0);
        }
    }

    private boolean isOwner() {
        if (mRecipe.getId() == FlavorLifeApplication.get().getUser().getId()) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.recipe_likes:
                if (mRecipe.isLiked()) {
                    requestUnlike();
                } else {
                    requestLike();
                }
                break;
            case R.id.recipe_used:
                if (mRecipe.isUsed()) {
                    requestUnUse();
                } else {
                    requestUse();
                }
                break;
        }
    }

    private void requestLike() {
        showDialogLoading();
        if (mDfeLikeRecipe == null) {
            mDfeLikeRecipe = new DfeLikeRecipe(mApi);
            mDfeLikeRecipe.addDataChangedListener(onLikeListener);
            mDfeLikeRecipe.addErrorListener(this);
        }
        mDfeLikeRecipe.makeRequest(mRecipe.getId());
    }

    private void requestUnlike() {
        showDialogLoading();
        if (mDfeUnLikeRecipe == null) {
            mDfeUnLikeRecipe = new DfeUnLikeRecipe(mApi);
            mDfeUnLikeRecipe.addDataChangedListener(onUnLikeListener);
            mDfeUnLikeRecipe.addErrorListener(this);
        }
        mDfeUnLikeRecipe.makeRequest(mRecipe.getId());
    }

    private void requestUse() {
        showDialogLoading();
        if (mDfeUseRecipe == null) {
            mDfeUseRecipe = new DfeUseRecipe(mApi);
            mDfeUseRecipe.addDataChangedListener(onUseListener);
            mDfeUseRecipe.addErrorListener(this);
        }
        mDfeUseRecipe.makeRequest(mRecipe.getId());
    }

    private void requestUnUse() {
        showDialogLoading();
        if (mDfeUnUseRecipe == null) {
            mDfeUnUseRecipe = new DfeUnUseRecipe(mApi);
            mDfeUnUseRecipe.addDataChangedListener(onUnUseListener);
            mDfeUnUseRecipe.addErrorListener(this);
        }
        mDfeUnUseRecipe.makeRequest(mRecipe.getId());
    }


    @Override
    public void onErrorResponse(VolleyError volleyError) {
        super.onErrorResponse(volleyError);
        hideDialogLoading();
        showDialogMessageError(volleyError);
    }

}
