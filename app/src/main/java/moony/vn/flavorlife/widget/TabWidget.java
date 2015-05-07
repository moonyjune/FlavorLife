package moony.vn.flavorlife.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import moony.vn.flavorlife.R;
import moony.vn.flavorlife.utils.AppPrefers;

/**
 * Created by moony on 3/1/15.
 */
public class TabWidget extends LinearLayout implements View.OnClickListener {

    private View mNewRecipes, mFollows, mHome, mNotification, mCreateRecipe;
    private TextView mNumNotification;

    public TabWidget(Context context) {
        super(context);
        init(context);
    }

    public TabWidget(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public TabWidget(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context);
    }

    public interface OnTabChangeListener {
        public void onNewRecipesTabSelected();

        public void onFollowsTabSelected();

        public void onHomeTabSelected();

        public void onNotificationTabSelected();

        public void onCreateRecipeTabSelected();
    }

    private OnTabChangeListener mTabMainChangeListener;

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
        mNotification = findViewById(R.id.notification);
        mCreateRecipe = findViewById(R.id.create_recipe);
        mNumNotification = (TextView) findViewById(R.id.num_notification);

        mNewRecipes.setOnClickListener(this);
        mFollows.setOnClickListener(this);
        mHome.setOnClickListener(this);
        mNotification.setOnClickListener(this);
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

    public void focusMessagesTab() {
        focusTab(R.id.notification);
    }

    public void focusCreateRecipeTab() {
        focusTab(R.id.create_recipe);
    }

    private void focusTab(int id) {
        switch (id) {
            case R.id.new_recipes:
                mNewRecipes.setSelected(true);
                mFollows.setSelected(false);
                mHome.setSelected(false);
                mNotification.setSelected(false);
                mCreateRecipe.setSelected(false);
                break;
            case R.id.follows:
                mNewRecipes.setSelected(false);
                mFollows.setSelected(true);
                mHome.setSelected(false);
                mNotification.setSelected(false);
                mCreateRecipe.setSelected(false);
                break;
            case R.id.home:
                mNewRecipes.setSelected(false);
                mFollows.setSelected(false);
                mHome.setSelected(true);
                mNotification.setSelected(false);
                mCreateRecipe.setSelected(false);
                break;
            case R.id.notification:
                mNewRecipes.setSelected(false);
                mFollows.setSelected(false);
                mHome.setSelected(false);
                mNotification.setSelected(true);
                mCreateRecipe.setSelected(false);
                AppPrefers.getInstance().saveNumberNotifications(0);
                break;
            case R.id.create_recipe:
                mNewRecipes.setSelected(false);
                mFollows.setSelected(false);
                mHome.setSelected(false);
                mNotification.setSelected(false);
                mCreateRecipe.setSelected(true);
                break;
        }
        setNumNotification(AppPrefers.getInstance().getNumberNotifications());
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.new_recipes:
                if (mTabMainChangeListener != null)
                    mTabMainChangeListener.onNewRecipesTabSelected();
                break;
            case R.id.follows:
                if (mTabMainChangeListener != null)
                    mTabMainChangeListener.onFollowsTabSelected();
                break;
            case R.id.home:
                if (mTabMainChangeListener != null) mTabMainChangeListener.onHomeTabSelected();
                break;
            case R.id.notification:
                if (mTabMainChangeListener != null) mTabMainChangeListener.onNotificationTabSelected();
                break;
            case R.id.create_recipe:
                if (mTabMainChangeListener != null)
                    mTabMainChangeListener.onCreateRecipeTabSelected();
                break;
        }
//        focusTab(v.getId());
    }

    public void unFocusAll() {
        mNewRecipes.setSelected(false);
        mFollows.setSelected(false);
        mCreateRecipe.setSelected(false);
        mNotification.setSelected(false);
        mHome.setSelected(false);
    }

    public void setNumNotification(int num) {
        if (num > 0) {
            if (num > 99) {
                mNumNotification.setText("99");
            } else {
                mNumNotification.setText(String.valueOf(num));
            }
            mNumNotification.setVisibility(VISIBLE);
        } else {
            mNumNotification.setVisibility(GONE);
        }
    }

}
