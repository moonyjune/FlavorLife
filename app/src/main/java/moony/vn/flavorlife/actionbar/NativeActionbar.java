package moony.vn.flavorlife.actionbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager.OnBackStackChangedListener;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import moony.vn.flavorlife.R;
import moony.vn.flavorlife.activities.BaseActivity;
import moony.vn.flavorlife.fragments.CreateRecipeFragment;
import moony.vn.flavorlife.fragments.FollowsFragment;
import moony.vn.flavorlife.fragments.HomeFragment;
import moony.vn.flavorlife.fragments.LoginFragment;
import moony.vn.flavorlife.fragments.MessagesFragment;
import moony.vn.flavorlife.fragments.NewRecipesFragment;
import moony.vn.flavorlife.navigationmanager.NavigationManager;

public class NativeActionbar implements CustomActionbar {
    protected NavigationManager mNavigationManager;
    private BaseActivity mBaseActivity;
    private ActionBar mActionBar;
    private int mCurrentResId = R.layout.actionbar_title;
    private TextView mTitle;
    private TextView mBack;
    private TextView mMenu;

    @Override
    public void initialize(NavigationManager navigationManager,
                           ActionBarActivity activity) {
        mNavigationManager = navigationManager;
        mBaseActivity = (BaseActivity) activity;
        mActionBar = activity.getSupportActionBar();
        mNavigationManager.addOnBackStackChangedListener(backStackChangedListener);
        mActionBar.setBackgroundDrawable(null);
        mActionBar.setDisplayShowCustomEnabled(true);
        mActionBar.setDisplayHomeAsUpEnabled(false);
        mActionBar.setDisplayShowHomeEnabled(false);
        mActionBar.setDisplayShowTitleEnabled(false);
        mActionBar.setCustomView(mCurrentResId);
        findChildViews();
        setupChildViews();
    }

    private OnBackStackChangedListener backStackChangedListener = new OnBackStackChangedListener() {
        @Override
        public void onBackStackChanged() {
            int count = mNavigationManager.getBackStackEntryCountCurrentPlace();
            if (count <= 0) {
                int placeHolder = mNavigationManager.getCurrentPlaceholder();
                switch (placeHolder) {
                    case R.id.tab_new_recipes:
                    case R.id.tab_follows:
                    case R.id.tab_home:
                    case R.id.tab_messages:
                    case R.id.tab_create_recipe:
                        syncActionBar(mNavigationManager.getTab(placeHolder));
                        break;
                    default:
                        syncActionBar(mNavigationManager.getActivePage());
                        break;
                }
            } else {
                syncActionBar(mNavigationManager.getActivePage());
            }
        }
    };

    @Override
    public void syncActionBar(Fragment activePage) {
        if (activePage == null)
            return;
        int newResId = findResourceIdForActionbar(activePage);
        if (newResId != mCurrentResId) {
            mCurrentResId = newResId;
            removeAllChildViews();
            mActionBar.setCustomView(mCurrentResId);
            findChildViews();
            setupChildViews();
        }
        syncChildViews(activePage);
        syncActionBarVisibility(activePage);
    }

    @Override
    public void dispose() {
        mNavigationManager.removeOnBackStackChangedListener(backStackChangedListener);
        mBaseActivity = null;
        mNavigationManager = null;
        mActionBar = null;
    }

    protected int findResourceIdForActionbar(Fragment activePage) {
        // TODO check fragment and return layout actionbar for that fragment. (1 layout for group fragment)
        return R.layout.actionbar;

    }

    protected void findChildViews() {
        // TODO find child view in Actionbar.
        View actionbarView = mActionBar.getCustomView();

        mTitle = (TextView) actionbarView.findViewById(R.id.title);
        mBack = (TextView) actionbarView.findViewById(R.id.back);
        mMenu = (TextView) actionbarView.findViewById(R.id.menu);
    }

    protected void removeAllChildViews() {
        View actionbarView = mActionBar.getCustomView();
        if (actionbarView instanceof ViewGroup) {
            ((ViewGroup) actionbarView).removeAllViews();
        }
    }

    protected void setupChildViews() {
        // TODO setup for child view (ex:click listener)
        setupTitle();
        setupBack();
        setupMenu();
    }

    private void setupMenu() {
        if (mMenu == null) return;
        mMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mBaseActivity.getMenu().showMenu(true);
                // unlock slide menu
                if (!mBaseActivity.getMenu().isSlidingEnabled())
                    mBaseActivity.getMenu().setSlidingEnabled(true);
            }
        });
    }

    private void setupBack() {
        if (mBack == null) return;
        mBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mNavigationManager.getActivePage() instanceof OnBackButtonListener) {
                    boolean handled = ((OnBackButtonListener) mNavigationManager.getActivePage()).onBackButtonClicked();
                    if (!handled) mNavigationManager.goBack();
                } else {
                    mNavigationManager.goBack();
                }
            }
        });
    }

    private void setupTitle() {
        if (mTitle == null) return;
        mTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // do nothing
            }
        });
    }

    protected void syncChildViews(Fragment activePage) {
        syncTitle(activePage);
        syncBack(activePage);
        syncMenu(activePage);
    }

    private void syncMenu(Fragment activePage) {
        if (mMenu == null) return;
        if (activePage instanceof LoginFragment) {
            mMenu.setVisibility(View.GONE);
        } else {
            mMenu.setVisibility(View.VISIBLE);
        }
    }

    private void syncBack(Fragment activePage) {
        if (mBack == null) return;
        if (activePage instanceof LoginFragment || activePage instanceof NewRecipesFragment) {
            mBack.setVisibility(View.GONE);
        } else {
            mBack.setVisibility(View.VISIBLE);
        }
    }

    private void syncTitle(Fragment activePage) {
        if (mTitle == null) return;
        mTitle.setVisibility(View.VISIBLE);
        int resId = R.string.app_name;
        if (activePage instanceof NewRecipesFragment) {
            resId = R.string.action_bar_title_recipes;
        } else if (activePage instanceof FollowsFragment) {
            resId = R.string.action_bar_title_follows;
        } else if (activePage instanceof HomeFragment) {
            resId = R.string.acion_bar_title_home;
        } else if (activePage instanceof MessagesFragment) {
            resId = R.string.action_bar_title_messages;
        } else if (activePage instanceof CreateRecipeFragment) {
            resId = R.string.action_bar_title_create_recipe;
        }

        mTitle.setText(resId);
    }

    @Override
    public void hide() {
        mActionBar.hide();
    }

    @Override
    public void show() {
        mActionBar.show();
    }

    private void syncActionBarVisibility(Fragment activePage) {
        mActionBar.show();
    }

}

