package moony.vn.flavorlife.layout;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ntq.imageloader.NImageLoader;

import moony.vn.flavorlife.R;
import moony.vn.flavorlife.entities.Recipe;
import moony.vn.flavorlife.entities.SearchRecipe;
import moony.vn.flavorlife.fragments.RecipeDetailFragment;
import moony.vn.flavorlife.navigationmanager.NavigationManager;

/**
 * Created by moony on 3/1/15.
 */
public class ItemSearchRecipesView extends LinearLayout implements View.OnClickListener {
    private NavigationManager mNavigationManager;
    private Recipe mRecipe;
    private TextView mRecipeName, mAuthorName;
    private ImageView mRecipeImage;
    private NImageLoader mImageLoader;

    public ItemSearchRecipesView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public ItemSearchRecipesView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    public ItemSearchRecipesView(Context context) {
        super(context);
        init(context);
    }

    public ItemSearchRecipesView(Context context, NImageLoader imageLoader, NavigationManager navigationManager) {
        super(context);
        init(context);
        mNavigationManager = navigationManager;
        mImageLoader = imageLoader;
    }

    private void init(Context context) {
        inflate(context, R.layout.item_search_recipe, this);
        mRecipeName = (TextView) findViewById(R.id.recipe_name);
        mRecipeImage = (ImageView) findViewById(R.id.recipe_image);
        mAuthorName = (TextView) findViewById(R.id.recipe_author);
        setOnClickListener(this);
    }

    public void display(SearchRecipe recipe) {
        if (recipe == null) return;
        mRecipe = recipe;
        mRecipeName.setText(recipe.getName());
        if (recipe.getImages() != null && !recipe.getImages().isEmpty()) {
            mImageLoader.display(recipe.getImages(), mRecipeImage);
        } else {
            mRecipeImage.setImageResource(R.drawable.default_recipe_image);
        }

        mAuthorName.setText("by " + recipe.getAuthorName());
    }

    @Override
    public void onClick(View view) {
        mNavigationManager.showPage(RecipeDetailFragment.newInstance(mRecipe));
    }
}
