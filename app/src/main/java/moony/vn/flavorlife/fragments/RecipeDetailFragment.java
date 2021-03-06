package moony.vn.flavorlife.fragments;

import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.ntq.api.model.OnDataChangedListener;
import com.ntq.fragments.NFragmentSwitcher;
import com.sromku.simple.fb.SimpleFacebook;
import com.sromku.simple.fb.entities.Photo;
import com.sromku.simple.fb.listeners.OnPublishListener;

import moony.vn.flavorlife.FlavorLifeApplication;
import moony.vn.flavorlife.R;
import moony.vn.flavorlife.activities.CreateNewRecipeActivity;
import moony.vn.flavorlife.api.model.DfeDeleteRecipe;
import moony.vn.flavorlife.api.model.DfeGetRecipeDetail;
import moony.vn.flavorlife.api.model.DfeLikeRecipe;
import moony.vn.flavorlife.api.model.DfeUnLikeRecipe;
import moony.vn.flavorlife.api.model.DfeUnUseRecipe;
import moony.vn.flavorlife.api.model.DfeUseRecipe;
import moony.vn.flavorlife.entities.Recipe;
import moony.vn.flavorlife.layout.DetailRecipeListIngredientsView;
import moony.vn.flavorlife.layout.DetailRecipeListInstructionsView;
import moony.vn.flavorlife.utils.DialogUtils;
import moony.vn.flavorlife.utils.ToastUtils;

/**
 * Created by moony on 3/9/15.
 */
public class RecipeDetailFragment extends NFragmentSwitcher implements View.OnClickListener {
    private static final String RECIPE = "recipe";
    private Recipe mRecipe;
    private DetailRecipeListIngredientsView mDetailRecipeListIngredientsView;
    private DetailRecipeListInstructionsView mDetailRecipeListInstructionsView;
    private DfeGetRecipeDetail mDfeGetRecipeDetail;
    private TextView mKind, mTitle, mLevel, mLikes, mUses, mIntro, mTips, mAuthorComments, mCookingTime, mType, mSubType;
    private ImageView mImage, mFlav, mEdit, mDelete;
    private DfeLikeRecipe mDfeLikeRecipe;
    private DfeUnLikeRecipe mDfeUnLikeRecipe;
    private DfeUseRecipe mDfeUseRecipe;
    private DfeUnUseRecipe mDfeUnUseRecipe;
    private DfeDeleteRecipe mDfeDeleteRecipe;
    private LinearLayout mLayoutTips, mLayoutComments;
    private View view = null;
    private ScrollView mScrollView;
    //    private Button mUpgrade, mEdit, mDelete;

    private OnDataChangedListener onDeleteRecipeListener = new OnDataChangedListener() {
        @Override
        public void onDataChanged() {
            hideDialogLoading();
            if (mDfeDeleteRecipe != null && mDfeDeleteRecipe.isReady()) {
                Toast.makeText(getActivity(), "You've deleted a recipe...", Toast.LENGTH_SHORT).show();
                getActivity().onBackPressed();
            }
        }
    };

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
    private OnPublishListener onPublishListener = new OnPublishListener() {
        @Override
        public void onComplete(String response) {
            super.onComplete(response);
            hideDialogLoading();
            ToastUtils.showToastLong(getActivity(), "You've share a recipe...");
        }

        @Override
        public void onException(Throwable throwable) {
            super.onException(throwable);
            hideDialogLoading();
            ToastUtils.showToastShort(getActivity(), throwable.toString());
        }

        @Override
        public void onFail(String reason) {
            super.onFail(reason);
            hideDialogLoading();
            showDialogMessageError(reason);
        }
    };

    public static RecipeDetailFragment newInstance(Recipe recipe) {
        RecipeDetailFragment recipeDetailFragment = new RecipeDetailFragment();
        Bundle argument = new Bundle();
        argument.putSerializable(RECIPE, recipe);
        recipeDetailFragment.setArguments(argument);
        return recipeDetailFragment;
    }

    public static RecipeDetailFragment newInstance(int recipeId) {
        RecipeDetailFragment recipeDetailFragment = new RecipeDetailFragment();
        Bundle argument = new Bundle();
        Recipe recipe = new Recipe();
        recipe.setId(recipeId);
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
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mActionbar.syncActionBar(this);
        if (isDataReady()) {
            inflateFromViewStub(mDataView);
            switchToData();
            mDetailRecipeListIngredientsView.setListSectionIngredient(mDfeGetRecipeDetail.getRecipe().getListSectionIngredients());
            mDetailRecipeListInstructionsView.setListSectionInstruction(mDfeGetRecipeDetail.getRecipe().getListSectionInstructions());
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
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.view = view;
        mScrollView = (ScrollView) view.findViewById(R.id.scroll_view);
    }

    private void inflateFromViewStub(ViewGroup viewGroup) {
        if (viewGroup == null) return;
        ViewStub stub = (ViewStub) viewGroup.findViewById(R.id.view_stub);
        View view = null;
        if (stub != null)
            view = stub.inflate();

        if (view == null) return;
        mDetailRecipeListIngredientsView = (DetailRecipeListIngredientsView) view.findViewById(R.id.list_ingredients);
        mDetailRecipeListInstructionsView = (DetailRecipeListInstructionsView) view.findViewById(R.id.list_instruction);
        mLayoutTips = (LinearLayout) view.findViewById(R.id.layout_tips);
        mLayoutComments = (LinearLayout) view.findViewById(R.id.layout_comments);
        mKind = (TextView) view.findViewById(R.id.recipe_kind);
        mTitle = (TextView) view.findViewById(R.id.recipe_name);
        mLikes = (TextView) view.findViewById(R.id.recipe_likes);
        mUses = (TextView) view.findViewById(R.id.recipe_used);
        mImage = (ImageView) view.findViewById(R.id.recipe_image);
        mIntro = (TextView) view.findViewById(R.id.recipe_info);
        mTips = (TextView) view.findViewById(R.id.recipe_tip_note);
        mLevel = (TextView) view.findViewById(R.id.recipe_level);
        mAuthorComments = (TextView) view.findViewById(R.id.recipe_author_comment);
        view.findViewById(R.id.upgrade).setOnClickListener(this);
        mEdit = (ImageView) view.findViewById(R.id.edit_recipe);
        mEdit.setOnClickListener(this);
        mDelete = (ImageView) view.findViewById(R.id.delete_recipe);
        mDelete.setOnClickListener(this);

        mFlav = (ImageView) view.findViewById(R.id.flav_recipe);
        mCookingTime = (TextView) view.findViewById(R.id.recipe_cooking_time);
        mType = (TextView) view.findViewById(R.id.recipe_type);
        mSubType = (TextView) view.findViewById(R.id.type_subcontent);

        view.findViewById(R.id.fb_share).setOnClickListener(this);
    }

    @Override
    protected void requestData() {
        disableSwipeRefresh();
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
        inflateFromViewStub(mDataView);
        if (isDataReady()) {
            mRecipe.updateRecipe(mDfeGetRecipeDetail.getRecipe(), true);
            mDetailRecipeListIngredientsView.setListSectionIngredient(mDfeGetRecipeDetail.getRecipe().getListSectionIngredients());
            mDetailRecipeListInstructionsView.setListSectionInstruction(mDfeGetRecipeDetail.getRecipe().getListSectionInstructions());
            setDataToViews(mDfeGetRecipeDetail.getRecipe());
        }
    }

    @Override
    protected boolean isDataReady() {
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
        String mins = "";
        if (recipe.getCookingTime() <= 1) {
            mins = "min";
        } else {
            mins = "mins";
        }
        mCookingTime.setText(recipe.getCookingTime() + " " + mins);
        if (recipe.getTipNote() != null && !recipe.getTipNote().isEmpty()) {
            mLayoutTips.setVisibility(View.VISIBLE);
            mTips.setText(recipe.getTipNote());
        } else {
            mLayoutTips.setVisibility(View.GONE);
        }
//        if (recipe.getAuthorComments() != null && !recipe.getAuthorComments().isEmpty()) {
//            mLayoutComments.setVisibility(View.VISIBLE);
//            mAuthorComments.setText(recipe.getAuthorComments());
//        } else {
//            mLayoutComments.setVisibility(View.GONE);
//        }
        if (recipe.getImages() != null && !recipe.getImages().isEmpty()) {
            mImageLoader.display(recipe.getImages(), mImage);
        } else {
            mImage.setImageResource(R.drawable.default_recipe_image);
        }

        mType.setText(recipe.getTypeName());

        if (recipe.isCreateNew()) {
            mSubType.setVisibility(View.GONE);
        } else if (recipe.isEdit()) {
            mSubType.setVisibility(View.GONE);
        } else if (recipe.isUpgrade()) {
            mSubType.setVisibility(View.VISIBLE);
            mSubType.setText(recipe.getSubTypeContent());
        }

        syncLikeIcon(recipe.isLiked());
        syncUseIcon(recipe.isUsed());
        syncOwner();
        mUses.setOnClickListener(this);
        mFlav.setOnClickListener(this);
    }

    private void syncOwner() {
        if (isOwner()) {
            mDelete.setVisibility(View.VISIBLE);
            mEdit.setVisibility(View.VISIBLE);
        } else {
            mDelete.setVisibility(View.GONE);
            mEdit.setVisibility(View.GONE);
        }
    }

    private void syncLikeIcon(boolean isLiked) {
        if (mRecipe != null) {
            mRecipe.setIsLiked(isLiked);
        }
        if (isLiked) {
            mFlav.setImageResource(R.drawable.ic_flaved);
        } else {
            mFlav.setImageResource(R.drawable.ic_flav);
        }
    }

    private void syncUseIcon(boolean isUsed) {
        if (mRecipe != null)
            mRecipe.setIsUsed(isUsed);
//        if (isUsed) {
//            mUses.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_use_red, 0, 0, 0);
//        } else {
//            mUses.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_use_black, 0, 0, 0);
//        }
    }

    private boolean isOwner() {
        if (mRecipe.getIdUser() == FlavorLifeApplication.get().getUser().getId()) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.flav_recipe:
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
            case R.id.upgrade:
//                mNavigationManager.showUpgradeRecipe(mRecipe, CreateRecipeFragment.FLAG_UPGRADE);
                if (getActivity() instanceof CreateNewRecipeActivity) {
                    mNavigationManager.showPage(CreateRecipeFragment.newInstance(mRecipe, CreateRecipeFragment.FLAG_UPGRADE));
                } else {
                    mNavigationManager.showUpgradeRecipe(mRecipe);
                }
                break;
            case R.id.edit_recipe:
                if (getActivity() instanceof CreateNewRecipeActivity) {
                    mNavigationManager.showPage(CreateRecipeFragment.newInstance(mRecipe, CreateRecipeFragment.FLAG_EDIT));
                } else {
                    mNavigationManager.showEditRecipe(mRecipe);
                }
                break;
            case R.id.delete_recipe:
                DialogUtils.getInstance().showDialog(getActivity(), "Are you sure that you want to delete this recipe ?", true, true, "Yes", "No", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        requestDeleteRecipe();
                        DialogUtils.getInstance().dismissDialog();
                    }
                }, new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        DialogUtils.getInstance().dismissDialog();
                    }
                });
                break;
            case R.id.fb_share:
                shareRecipe();
                break;
        }
    }

    private void shareRecipe() {
        Bitmap bmap = null;
//        mScrollView.setDrawingCacheEnabled(true);

        bmap = screenShot(view);

        int contentViewTop = getActivity().getWindow().findViewById(Window.ID_ANDROID_CONTENT).getTop(); /* skip status bar in screenshot */
        Bitmap.createBitmap(bmap, 0, contentViewTop, bmap.getWidth(), bmap.getHeight() - contentViewTop, null, true);

//        view.setDrawingCacheEnabled(false);

        if (bmap != null) {
            Photo photo = new Photo.Builder().setImage(bmap).setName(mRecipe.getName()).build();
            if (checkFbApp()) {
                mSimpleFacebook.publish(photo, true, onPublishListener);
            } else {
                showDialogLoading();
                mSimpleFacebook.publish(photo, false, onPublishListener);
            }
        }
    }

    public Bitmap screenShot(View view) {
        Bitmap bitmap = Bitmap.createBitmap(
                mScrollView.getChildAt(0).getWidth(),
                mScrollView.getChildAt(0).getHeight(),
                Bitmap.Config.ARGB_8888);
        Canvas c = new Canvas(bitmap);
        mScrollView.getChildAt(0).draw(c);

        return bitmap;
    }

    private boolean checkFbApp() {
        try {
            ApplicationInfo info = getActivity().getPackageManager().
                    getApplicationInfo("com.facebook.katana", 0);
            return true;
        } catch (PackageManager.NameNotFoundException e) {
            return false;
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

    private void requestDeleteRecipe() {
        showDialogLoading();
        if (mDfeDeleteRecipe == null) {
            mDfeDeleteRecipe = new DfeDeleteRecipe(mApi);
            mDfeDeleteRecipe.addErrorListener(this);
            mDfeDeleteRecipe.addDataChangedListener(onDeleteRecipeListener);
        }
        mDfeDeleteRecipe.makeRequest(mRecipe.getId());
    }

    @Override
    public void onErrorResponse(VolleyError volleyError) {
        super.onErrorResponse(volleyError);
        hideDialogLoading();
        showDialogMessageError(volleyError);
    }

}
