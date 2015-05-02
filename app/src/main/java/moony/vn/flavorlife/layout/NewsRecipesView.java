package moony.vn.flavorlife.layout;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ntq.imageloader.NImageLoader;

import moony.vn.flavorlife.R;
import moony.vn.flavorlife.entities.Recipe;
import moony.vn.flavorlife.fragments.RecipeDetailFragment;
import moony.vn.flavorlife.navigationmanager.NavigationManager;

/**
 * Created by moony on 3/1/15.
 */
public class NewsRecipesView extends LinearLayout implements View.OnClickListener {
    private NavigationManager mNavigationManager;
    private Recipe mRecipe;
    private TextView mRecipeName, mLevel, mLikes, mUses, mTime, mAuthorName;
    private ImageView mRecipeImage, mType, mUserImage;
    private NImageLoader mImageLoader;

    public NewsRecipesView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public NewsRecipesView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    public NewsRecipesView(Context context) {
        super(context);
        init(context);
    }

    public NewsRecipesView(Context context, NImageLoader imageLoader, NavigationManager navigationManager) {
        super(context);
        init(context);
        mNavigationManager = navigationManager;
        mImageLoader = imageLoader;
    }

    private void init(Context context) {
        inflate(context, R.layout.item_recipe, this);
        mUserImage = (ImageView) findViewById(R.id.user_image);
        mRecipeName = (TextView) findViewById(R.id.recipe_name);
        mRecipeImage = (ImageView) findViewById(R.id.recipe_image);
        mAuthorName = (TextView) findViewById(R.id.recipe_author);
        mType = (ImageView) findViewById(R.id.recipe_type);
        mLevel = (TextView) findViewById(R.id.recipe_level);
        mUses = (TextView) findViewById(R.id.recipe_used);
        mLikes = (TextView) findViewById(R.id.recipe_likes);
        mTime = (TextView) findViewById(R.id.recipe_cooking_time);
        setOnClickListener(this);
    }

    public void display(Recipe recipe) {
        if (recipe == null) return;
        mRecipe = recipe;
        mRecipeName.setText(recipe.getName());
        if (recipe.getImages() != null && !recipe.getImages().isEmpty()) {
            mImageLoader.display(recipe.getImages(), mRecipeImage);
        } else {
            mRecipeImage.setImageResource(R.drawable.default_recipe_image);
        }
        mTime.setText(String.valueOf(recipe.getCookingTime()));
        mLevel.setText(String.valueOf(recipe.getLevel()));
        mLikes.setText(String.valueOf(recipe.getLikes()));
        mUses.setText(String.valueOf(recipe.getUsed()));
        mAuthorName.setText(recipe.getAuthorName());
        //TODO xu ly type
    }

    @Override
    public void onClick(View view) {
        mNavigationManager.showPage(RecipeDetailFragment.newInstance(mRecipe));
    }
}