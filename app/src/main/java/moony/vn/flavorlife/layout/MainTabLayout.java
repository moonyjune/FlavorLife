package moony.vn.flavorlife.layout;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;

import moony.vn.flavorlife.R;
import moony.vn.flavorlife.navigationmanager.Tabs;
import moony.vn.flavorlife.widget.TabWidget;

/**
 * Created by moony on 3/1/15.
 */
public class MainTabLayout extends FrameLayout {
    private FrameLayout mTabNewRecipes, mTabFollows, mTabHome, mTabMessages, mTabCreateRecipe, mTabMain;
    private TabWidget mTabWidget;
    private TabWidget.OnTabChangeListener mOnTabMainChangeListener;

    public MainTabLayout(Context context) {
        super(context);
    }

    public MainTabLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public MainTabLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public void setTabMainChangeListener(TabWidget.OnTabChangeListener tabMainChangeListener) {
        mOnTabMainChangeListener = tabMainChangeListener;
        mTabWidget.setTabMainChangeListener(new TabWidget.OnTabChangeListener() {
            @Override
            public void onNewRecipesTabSelected() {
                setCurrentTab(Tabs.NEW_RECIPES);
                if (mOnTabMainChangeListener != null)
                    mOnTabMainChangeListener.onNewRecipesTabSelected();
            }

            @Override
            public void onFollowsTabSelected() {
                setCurrentTab(Tabs.FOLLOWS);
                if (mOnTabMainChangeListener != null)
                    mOnTabMainChangeListener.onFollowsTabSelected();
            }

            @Override
            public void onHomeTabSelected() {
                setCurrentTab(Tabs.HOME);
                if (mOnTabMainChangeListener != null)
                    mOnTabMainChangeListener.onHomeTabSelected();
            }

            @Override
            public void onMessageTabSelected() {
                setCurrentTab(Tabs.MESSAGES);
                if (mOnTabMainChangeListener != null)
                    mOnTabMainChangeListener.onMessageTabSelected();
            }

            @Override
            public void onCreateRecipeTabSelected() {
                setCurrentTab(Tabs.CREATE_RECIPE);
                if (mOnTabMainChangeListener != null)
                    mOnTabMainChangeListener.onCreateRecipeTabSelected();
            }
        });
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        mTabNewRecipes = (FrameLayout) findViewById(Tabs.NEW_RECIPES);
        mTabFollows = (FrameLayout) findViewById(Tabs.FOLLOWS);
        mTabHome = (FrameLayout) findViewById(Tabs.HOME);
        mTabMessages = (FrameLayout) findViewById(Tabs.MESSAGES);
        mTabCreateRecipe = (FrameLayout) findViewById(Tabs.CREATE_RECIPE);
        mTabMain = (FrameLayout) findViewById(Tabs.MAIN);
        mTabWidget = (TabWidget) findViewById(R.id.tab_indicator);
    }


    /**
     * @param tabId
     */
    public void setCurrentTab(int tabId) {
        switch (tabId) {
            case Tabs.NEW_RECIPES:
                mTabNewRecipes.setVisibility(View.VISIBLE);
                mTabWidget.setVisibility(View.VISIBLE);
                mTabWidget.focusNewRecipesTab();

                mTabMain.setVisibility(View.INVISIBLE);
                mTabFollows.setVisibility(View.INVISIBLE);
                mTabHome.setVisibility(View.INVISIBLE);
                mTabMessages.setVisibility(View.INVISIBLE);
                mTabCreateRecipe.setVisibility(View.INVISIBLE);
                break;
            case Tabs.FOLLOWS:
                mTabFollows.setVisibility(View.VISIBLE);
                mTabWidget.setVisibility(View.VISIBLE);
                mTabWidget.focusFollowsTab();

                mTabMain.setVisibility(View.INVISIBLE);
                mTabNewRecipes.setVisibility(View.INVISIBLE);
                mTabHome.setVisibility(View.INVISIBLE);
                mTabMessages.setVisibility(View.INVISIBLE);
                mTabCreateRecipe.setVisibility(View.INVISIBLE);
                break;
            case Tabs.HOME:
                mTabHome.setVisibility(View.VISIBLE);
                mTabWidget.setVisibility(View.VISIBLE);
                mTabWidget.focusHomeTab();

                mTabMain.setVisibility(View.INVISIBLE);
                mTabFollows.setVisibility(View.INVISIBLE);
                mTabNewRecipes.setVisibility(View.INVISIBLE);
                mTabMessages.setVisibility(View.INVISIBLE);
                mTabCreateRecipe.setVisibility(View.INVISIBLE);
                break;
            case Tabs.MESSAGES:
                mTabMessages.setVisibility(View.VISIBLE);
                mTabWidget.setVisibility(View.VISIBLE);
                mTabWidget.focusMessages();

                mTabMain.setVisibility(View.INVISIBLE);
                mTabHome.setVisibility(View.INVISIBLE);
                mTabFollows.setVisibility(View.INVISIBLE);
                mTabNewRecipes.setVisibility(View.INVISIBLE);
                mTabHome.setVisibility(View.INVISIBLE);
                mTabCreateRecipe.setVisibility(View.INVISIBLE);
                break;
            case Tabs.CREATE_RECIPE:
                mTabWidget.setVisibility(View.VISIBLE);
                mTabCreateRecipe.setVisibility(View.VISIBLE);
                mTabWidget.focusCreateRecipe();

                mTabMain.setVisibility(View.INVISIBLE);
                mTabMessages.setVisibility(View.INVISIBLE);
                mTabHome.setVisibility(View.INVISIBLE);
                mTabFollows.setVisibility(View.INVISIBLE);
                mTabNewRecipes.setVisibility(View.INVISIBLE);
                mTabHome.setVisibility(View.INVISIBLE);
                break;
            case Tabs.MAIN:
                mTabMain.setVisibility(View.VISIBLE);
                mTabWidget.setVisibility(View.GONE);

                mTabCreateRecipe.setVisibility(View.INVISIBLE);
                mTabMessages.setVisibility(View.INVISIBLE);
                mTabHome.setVisibility(View.INVISIBLE);
                mTabFollows.setVisibility(View.INVISIBLE);
                mTabNewRecipes.setVisibility(View.INVISIBLE);
                mTabHome.setVisibility(View.INVISIBLE);
                break;
        }
    }
}
