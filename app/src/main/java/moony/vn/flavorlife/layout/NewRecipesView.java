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
public class NewRecipesView extends LinearLayout implements View.OnClickListener {
    private NavigationManager mNavigationManager;
    private Recipe mRecipe;
    private TextView mRecipeName;
    private ImageView mRecipeImage;
    private NImageLoader mImageLoader;

    public NewRecipesView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public NewRecipesView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    public NewRecipesView(Context context) {
        super(context);
        init(context);
    }

    public NewRecipesView(Context context, NImageLoader imageLoader, NavigationManager navigationManager) {
        super(context);
        init(context);
        mNavigationManager = navigationManager;
        mImageLoader = imageLoader;
    }

    private void init(Context context) {
        inflate(context, R.layout.item_recipe, this);
        mRecipeName = (TextView) findViewById(R.id.recipe_name);
        mRecipeImage = (ImageView) findViewById(R.id.recipe_image);
        setOnClickListener(this);
    }

    public void display(Recipe recipe) {
        if (recipe == null) return;
        mRecipe = recipe;
        mRecipeName.setText(recipe.getName());
//        mImageLoader.display(recipe.getImages(), mRecipeImage);
    }

    @Override
    public void onClick(View view) {
        mNavigationManager.showPage(RecipeDetailFragment.newInstance(mRecipe));
    }
}
