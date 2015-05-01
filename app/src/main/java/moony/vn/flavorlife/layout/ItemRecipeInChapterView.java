package moony.vn.flavorlife.layout;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ntq.imageloader.NImageLoader;

import moony.vn.flavorlife.R;
import moony.vn.flavorlife.entities.RecipeChapter;
import moony.vn.flavorlife.fragments.RecipeDetailFragment;
import moony.vn.flavorlife.navigationmanager.NavigationManager;

/**
 * Created by moony on 3/11/15.
 */
public class ItemRecipeInChapterView extends LinearLayout implements View.OnClickListener {
    private NImageLoader mImageLoader;
    private NavigationManager mNavigationManager;
    private ImageView mImage;
    private TextView mName;
    private RecipeChapter mChapterRecipe;

    public ItemRecipeInChapterView(Context context) {
        super(context);
        init(context);
    }

    public ItemRecipeInChapterView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public ItemRecipeInChapterView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    public ItemRecipeInChapterView(Context context, NImageLoader imageLoader, NavigationManager navigationManager) {
        super(context);
        init(context);
        mImageLoader = imageLoader;
        mNavigationManager = navigationManager;
    }

    private void init(Context context) {
        inflate(context, R.layout.item_recipe_in_chapter, this);
        mImage = (ImageView) findViewById(R.id.recipe_image);
        mName = (TextView) findViewById(R.id.recipe_name);
    }

    public void display(RecipeChapter chapterRecipe) {
        if (chapterRecipe == null) return;
        mChapterRecipe = chapterRecipe;
        if (chapterRecipe.getImages() != null && !chapterRecipe.getImages().isEmpty()) {
            mImageLoader.display(chapterRecipe.getImages(), mImage);
        } else {
            mImage.setImageResource(R.drawable.default_recipe_image);
        }
//        mName.setText(cookbook.getName());
        setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        //TODO chuyen sang recipe detail
        mNavigationManager.showPage(RecipeDetailFragment.newInstance(mChapterRecipe));
    }
}
