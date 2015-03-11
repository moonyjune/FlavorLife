package moony.vn.flavorlife.widget;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;

import moony.vn.flavorlife.R;

/**
 * Created by moony on 3/1/15.
 */
public class TabWidget extends LinearLayout implements View.OnClickListener {

    private View mNewRecipes, mFollows, mHome, mMessage, mCreateRecipe;

    public interface OnTabChangeListener {
        public void onNewRecipesTabSelected();

        public void onFollowsTabSelected();

        public void onHomeTabSelected();

        public void onMessageTabSelected();

        public void onCreateRecipeTabSelected();
    }

    private OnTabChangeListener mTabMainChangeListener;

    public TabWidget(Context context) {
        super(context);
        init(context);
    }

    public TabWidget(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public TabWidget(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public TabWidget(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context);
    }

    public void setTabMainChangeListener(OnTabChangeListener tabMainChangeListener) {
        this.mTabMainChangeListener = tabMainChangeListener;
    }

    private void init(Context context) {
        setOrientation(HORIZONTAL);
        setBackgroundResource(R.color.gray);
        setPadding(0, 5, 0, 0);
        inflate(context, R.layout.tab_widget, this);

        mNewRecipes = findViewById(R.id.new_recipes);
        mFollows = findViewById(R.id.follows);
        mHome = findViewById(R.id.home);
        mMessage = findViewById(R.id.messages);
        mCreateRecipe = findViewById(R.id.create_recipe);

        mNewRecipes.setOnClickListener(this);
        mFollows.setOnClickListener(this);
        mHome.setOnClickListener(this);
        mMessage.setOnClickListener(this);
        mCreateRecipe.setOnClickListener(this);
    }

    public void focusNewRecipesTab() {
        focusTab(R.id.new_recipes);
    }

    public void focusFollowsTab() {
        focusTab(R.id.follows);
    }

    public void focusHomeTab() {
        focusTab(R.id.home);
    }

    public void focusMessages() {
        focusTab(R.id.messages);
    }

    public void focusCreateRecipe() {
        focusTab(R.id.create_recipe);
    }

    private void focusTab(int id) {
        switch (id) {
            case R.id.new_recipes:
                mNewRecipes.setSelected(true);
                mFollows.setSelected(false);
                mHome.setSelected(false);
                mMessage.setSelected(false);
                mCreateRecipe.setSelected(false);
                break;
            case R.id.follows:
                mNewRecipes.setSelected(false);
                mFollows.setSelected(true);
                mHome.setSelected(false);
                mMessage.setSelected(false);
                mCreateRecipe.setSelected(false);
                break;
            case R.id.home:
                mNewRecipes.setSelected(false);
                mFollows.setSelected(false);
                mHome.setSelected(true);
                mMessage.setSelected(false);
                mCreateRecipe.setSelected(false);
                break;
            case R.id.messages:
                mNewRecipes.setSelected(false);
                mFollows.setSelected(false);
                mHome.setSelected(false);
                mMessage.setSelected(true);
                mCreateRecipe.setSelected(false);
                break;
            case R.id.create_recipe:
                mNewRecipes.setSelected(false);
                mFollows.setSelected(false);
                mHome.setSelected(false);
                mMessage.setSelected(false);
                mCreateRecipe.setSelected(true);
                break;
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.new_recipes:
                if (mTabMainChangeListener != null) mTabMainChangeListener.onNewRecipesTabSelected();
                break;
            case R.id.follows:
                if (mTabMainChangeListener != null)
                    mTabMainChangeListener.onFollowsTabSelected();
                break;
            case R.id.home:
                if (mTabMainChangeListener != null) mTabMainChangeListener.onHomeTabSelected();
                break;
            case R.id.messages:
                if (mTabMainChangeListener != null) mTabMainChangeListener.onMessageTabSelected();
                break;
            case R.id.create_recipe:
                if (mTabMainChangeListener != null) mTabMainChangeListener.onCreateRecipeTabSelected();
                break;
        }
        focusTab(v.getId());
    }

}
