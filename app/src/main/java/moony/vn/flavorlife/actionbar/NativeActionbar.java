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
import moony.vn.flavorlife.fragments.ChapterDetailFragment;
import moony.vn.flavorlife.fragments.ComingSoonFragment;
import moony.vn.flavorlife.fragments.CookBookDetailFragment;
import moony.vn.flavorlife.fragments.CreateRecipeFragment;
import moony.vn.flavorlife.fragments.FollowsFragment;
import moony.vn.flavorlife.fragments.HomeFragment;
import moony.vn.flavorlife.fragments.LoginFragment;
import moony.vn.flavorlife.fragments.MessagesFragment;
import moony.vn.flavorlife.fragments.NewRecipesFragment;
import moony.vn.flavorlife.fragments.RecipeDetailFragment;
import moony.vn.flavorlife.navigationmanager.NavigationManager;

public class NativeActionbar implements CustomActionbar {
    protected NavigationManager mNavigationManager;
    private BaseActivity mBaseActivity;
    private ActionBar mActionBar;
    private int mCurrentResId = R.layout.actionbar_title;
    private TextView mTitle;
    private ImageView mSearch;
    private ImageView mMessage;
    private ImageView mFinish;
    private ImageView mBack;

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
        if (activePage instanceof NewRecipesFragment) {
            return R.layout.actionbar;
        } else {
            return R.layout.actionbar_2;
        }

    }

    protected void findChildViews() {
        View actionbarView = mActionBar.getCustomView();

        mTitle = (TextView) actionbarView.findViewById(R.id.title);
        mSearch = (ImageView) actionbarView.findViewById(R.id.search);
        mMessage = (ImageView) actionbarView.findViewById(R.id.message);
        mFinish = (ImageView) actionbarView.findViewById(R.id.finish);
        mBack = (ImageView) actionbarView.findViewById(R.id.back);
    }

    protected void removeAllChildViews() {
        View actionbarView = mActionBar.getCustomView();
        if (actionbarView instanceof ViewGroup) {
            ((ViewGroup) actionbarView).removeAllViews();
        }
    }

    protected void setupChildViews() {
        setupTitle();
        setupBtnLeft();
        setupBtnRight();
    }

    private void setupBtnRight() {
        setupBtnMessage();
        setupBtnFinish();
    }

    private void setupBtnMessage() {
        if (mMessage == null) return;
        mMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO move to screen messages
                mNavigationManager.showPage(ComingSoonFragment.newInstance());
            }
        });
    }

    private void setupBtnFinish() {
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
        setUpSearch();
        setUpBack();
    }

    private void setUpSearch() {
        if (mSearch == null) return;
        mSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mNavigationManager.getActivePage() instanceof OnBackButtonListener) {
                    boolean handled = ((OnBackButtonListener) mNavigationManager.getActivePage()).onBackButtonClicked();
                    //TODO ab button left override from fragment
                } else {
                    //TODO ab button left
                }
            }
        });
    }

    private void setUpBack() {
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
        syncBtnLeft(activePage);
        syncBtnRight(activePage);
    }

    private void syncBtnRight(Fragment activePage) {
        syncBtnMessage(activePage);
        syncBtnFinish(activePage);
        syncBtnBack(activePage);
    }

    private void syncBtnBack(Fragment activePage) {
        if (mBack == null) return;
        if (activePage instanceof RecipeDetailFragment || activePage instanceof CookBookDetailFragment || activePage instanceof ChapterDetailFragment) {
            mBack.setVisibility(View.VISIBLE);
        } else if (activePage instanceof HomeFragment) {
            if ((((HomeFragment) activePage).isOwner())) {
                mBack.setVisibility(View.GONE);
            } else {
                mBack.setVisibility(View.VISIBLE);
            }
        } else {
            mBack.setVisibility(View.GONE);
        }
    }

    private void syncBtnMessage(Fragment activePage) {
        if (mMessage == null) return;
        if (activePage instanceof LoginFragment || activePage instanceof ComingSoonFragment) {
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
        syncBtnBack(activePage);
        syncBtnSearch(activePage);
    }

    private void syncBtnSearch(Fragment activePage) {
        if (mSearch == null) return;
        if (activePage instanceof LoginFragment || activePage instanceof RecipeDetailFragment ||
                activePage instanceof CookBookDetailFragment ||
                activePage instanceof ChapterDetailFragment ||
                activePage instanceof HomeFragment || activePage instanceof ComingSoonFragment) {
            mSearch.setVisibility(View.GONE);
        } else {
            mSearch.setVisibility(View.VISIBLE);
        }
    }

    private void syncTitle(Fragment activePage) {
        if (mTitle == null) return;
        mTitle.setVisibility(View.VISIBLE);
        String text = mBaseActivity.getString(R.string.app_name);
        if (activePage instanceof NewRecipesFragment) {
            text = mBaseActivity.getString(R.string.action_bar_title_recipes);
        } else if (activePage instanceof FollowsFragment) {
            text = mBaseActivity.getString(R.string.action_bar_title_follows);
        } else if (activePage instanceof HomeFragment) {
            text = mBaseActivity.getString(R.string.acion_bar_title_home);
        } else if (activePage instanceof MessagesFragment) {
            text = mBaseActivity.getString(R.string.action_bar_title_messages);
        } else if (activePage instanceof CreateRecipeFragment) {
            text = mBaseActivity.getString(R.string.action_bar_title_create_recipe);
        } else if (activePage instanceof RecipeDetailFragment) {
            mBaseActivity.getString(R.string.action_bar_title_recipe_detail);
        }

        mTitle.setText(text);
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

