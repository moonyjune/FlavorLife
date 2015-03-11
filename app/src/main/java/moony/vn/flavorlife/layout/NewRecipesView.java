package moony.vn.flavorlife.layout;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;

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

    public NewRecipesView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public NewRecipesView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public NewRecipesView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
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
    }

    private void init(Context context) {
        inflate(context, R.layout.item_recipe, this);
        setOnClickListener(this);
    }

    public void display(Recipe recipe) {
        mRecipe = recipe;
    }

    @Override
    public void onClick(View view) {
        mNavigationManager.showPage(RecipeDetailFragment.newInstance(mRecipe));
    }
}
