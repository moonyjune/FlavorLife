package moony.vn.flavorlife.actionbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager.OnBackStackChangedListener;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
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
    private ImageView mBtnLeft;
    private ImageView mMessage;
    private ImageView mFinish;

    @Override
    public void initialize(NavigationManager navigationManager,
                           ActionBarActivity activity) {
        mNavigationManager = navigationManager;
        mBaseActivity = (BaseActivity) activity;
        mActionBar = activity.getSupportActionBar();
        mNavigationManager.addOnBackStackChangedListener(backStackChangedListener);
        mActionBar.setDisplayShowCustomEnabled(true);
        mActionBar.setDisplayHomeAsUpEnabled(false);
        mActionBar.setDisplayShowHomeEnabled(false);
        mActionBar.setDisplayShowTitleEnabled(false);
        mActionBar.setDisplayUseLogoEnabled(false);
        mActionBar.setCustomView(mCurrentResId);
        findChildViews();
        setupChildViews();
    }

    private OnBackStackChangedListener backStackChangedListener = new OnBackStackChangedListener() {
        @Override
        public void onBackStackChanged() {
            syncActionBar(mNavigationManager.getActivePage());
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
        if (activePage instanceof NewRecipesFragment) {
            return R.layout.actionbar;
        } else if (activePage instanceof CreateRecipeFragment) {
            return R.layout.actionbar_2;
        } else {
            return R.layout.actionbar_3;
        }

    }

    protected void findChildViews() {
        View actionbarView = mActionBar.getCustomView();

        mTitle = (TextView) actionbarView.findViewById(R.id.title);
        mBtnLeft = (ImageView) actionbarView.findViewById(R.id.search);
        mMessage = (ImageView) actionbarView.findViewById(R.id.message);
        mFinish = (ImageView) actionbarView.findViewById(R.id.finish);
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
        setupBtnLeft();
        setupBtnRight();
    }

    private void setupBtnRight() {
        if (mMessage == null) return;
        mMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO move to screen messages
//                mBaseActivity.getMenu().showMenu(true);
//                // unlock slide menu
//                if (!mBaseActivity.getMenu().isSlidingEnabled())
//                    mBaseActivity.getMenu().setSlidingEnabled(true);
            }
        });
        if (mFinish == null) return;
        mFinish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = mNavigationManager.getActivePage();
                if (fragment instanceof CreateRecipeFragment) {
                    ((CreateRecipeFragment) fragment).request();
                }
            }
        });

    }

    private void setupBtnLeft() {
        if (mBtnLeft == null) return;
        mBtnLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mNavigationManager.getActivePage() instanceof OnBackButtonListener) {
                    boolean handled = ((OnBackButtonListener) mNavigationManager.getActivePage()).onBackButtonClicked();
                    //TODO ab button left override from fragment
//                    if (!handled) mNavigationManager.goBack();
                } else {
                    //TODO ab button left
//                    mNavigationManager.goBack();
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
        syncBtnLeft(activePage);
        syncBtnRight(activePage);
    }

    private void syncBtnRight(Fragment activePage) {
        syncBtnMessage(activePage);
        syncBtnFinish(activePage);
    }

    private void syncBtnMessage(Fragment activePage) {
        if (mMessage == null) return;
        if (activePage instanceof LoginFragment) {
            mMessage.setVisibility(View.GONE);
        } else {
            mMessage.setVisibility(View.VISIBLE);
        }
    }

    private void syncBtnFinish(Fragment activePage) {
        if (mFinish == null) return;
        if (activePage instanceof CreateRecipeFragment) {
            mFinish.setVisibility(View.VISIBLE);
        } else {
            mFinish.setVisibility(View.GONE);
        }
    }


    private void syncBtnLeft(Fragment activePage) {
        if (mBtnLeft == null) return;
        if (activePage instanceof LoginFragment) {
            mBtnLeft.setVisibility(View.GONE);
        } else {
            mBtnLeft.setVisibility(View.VISIBLE);
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
        if (activePage instanceof LoginFragment) {
            hide();
        } else {
            show();
        }
    }

}

