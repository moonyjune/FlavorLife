package moony.vn.flavorlife.layout;

import android.content.Context;
import android.media.Image;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.ntq.imageloader.NImageLoader;

import moony.vn.flavorlife.R;
import moony.vn.flavorlife.entities.Recipe;
import moony.vn.flavorlife.fragments.RecipeDetailFragment;
import moony.vn.flavorlife.navigationmanager.NavigationManager;
import moony.vn.flavorlife.widget.SquareImageView;

/**
 * Created by moony on 4/20/15.
 */
public class UserNewsRecipesView extends LinearLayout implements View.OnClickListener {
    private NImageLoader mImageLoader;
    private NavigationManager mNavigationManager;
    private SquareImageView mRecipeImage;
    private Recipe mRecipe;

    public UserNewsRecipesView(Context context) {
        super(context);
        init();
    }

    public UserNewsRecipesView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public UserNewsRecipesView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    public UserNewsRecipesView(Context context, NImageLoader imageLoader, NavigationManager navigationManager) {
        super(context);
        init();
        mImageLoader = imageLoader;
        mNavigationManager = navigationManager;
    }

    private void init() {
        inflate(getContext(), R.layout.item_recipe_for_grid_view, this);
        mRecipeImage = (SquareImageView) findViewById(R.id.recipe_image);
    }

    public void display(Recipe recipe) {
        if (recipe == null)
            return;
        mRecipe = recipe;
        if (recipe.getImages() != null && !recipe.getImages().isEmpty()) {
            mImageLoader.display(recipe.getImages(), mRecipeImage);
        } else {
            mRecipeImage.setImageResource(R.drawable.default_recipe_image);
        }
        setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        mNavigationManager.showPage(RecipeDetailFragment.newInstance(mRecipe));
    }
}
