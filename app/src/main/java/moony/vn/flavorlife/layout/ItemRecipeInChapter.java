package moony.vn.flavorlife.layout;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ntq.imageloader.NImageLoader;

import moony.vn.flavorlife.R;
import moony.vn.flavorlife.entities.Cookbook;
import moony.vn.flavorlife.fragments.CookBookDetailFragment;
import moony.vn.flavorlife.navigationmanager.NavigationManager;

/**
 * Created by moony on 3/11/15.
 */
public class ItemRecipeInChapter extends LinearLayout implements View.OnClickListener {
    private NImageLoader mImageLoader;
    private NavigationManager mNavigationManager;
    private ImageView mImage;
    private TextView mName, mIntro;
    private Cookbook mCookbook;

    public ItemRecipeInChapter(Context context) {
        super(context);
        init(context);
    }

    public ItemRecipeInChapter(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public ItemRecipeInChapter(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    public ItemRecipeInChapter(Context context, NImageLoader imageLoader, NavigationManager navigationManager) {
        super(context);
        init(context);
        mImageLoader = imageLoader;
        mNavigationManager = navigationManager;
    }

    private void init(Context context) {
        inflate(context, R.layout.item_cookbook, this);
        mImage = (ImageView) findViewById(R.id.book_image);
        mName = (TextView) findViewById(R.id.book_name);
        mIntro = (TextView) findViewById(R.id.book_intro);
    }

    public void display(Cookbook cookbook) {
        if (cookbook == null) return;
        mCookbook = cookbook;
        if (cookbook.getImage() != null && !cookbook.getImage().isEmpty()) {
            mImageLoader.display(cookbook.getImage(), mImage);
        } else {
            mImage.setImageResource(R.drawable.default_cookbook_image);
        }
//        mName.setText(cookbook.getName());
//        mIntro.setText(cookbook.getIntro());
        setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        mNavigationManager.showPage(CookBookDetailFragment.newInstance(mCookbook.getUserId(), mCookbook.getId()));
    }
}
