package moony.vn.flavorlife.actionbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager.OnBackStackChangedListener;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import moony.vn.flavorlife.R;
import moony.vn.flavorlife.activities.BaseActivity;
import moony.vn.flavorlife.entities.SearchRecipe;
import moony.vn.flavorlife.fragments.ChapterDetailFragment;
import moony.vn.flavorlife.fragments.ComingSoonFragment;
import moony.vn.flavorlife.fragments.CookBookDetailFragment;
import moony.vn.flavorlife.fragments.CreateEditBook;
import moony.vn.flavorlife.fragments.CreateRecipeFragment;
import moony.vn.flavorlife.fragments.EditProfileFragment;
import moony.vn.flavorlife.fragments.PeopleFragment;
import moony.vn.flavorlife.fragments.HomeFragment;
import moony.vn.flavorlife.fragments.LoginFragment;
import moony.vn.flavorlife.fragments.MessagesFragment;
import moony.vn.flavorlife.fragments.NewRecipesFragment;
import moony.vn.flavorlife.fragments.RecipeDetailFragment;
import moony.vn.flavorlife.fragments.SearchFragment;
import moony.vn.flavorlife.fragments.SearchPeopleFragment;
import moony.vn.flavorlife.fragments.SearchRecipeFragment;
import moony.vn.flavorlife.fragments.UserCookbooksFragment;
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
    private EditText mDataSearch;
    private ImageView mAdvance;

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
        if (activePage instanceof NewRecipesFragment || activePage instanceof ComingSoonFragment) {
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
        mDataSearch = (EditText) actionbarView.findViewById(R.id.data_search);
        mAdvance = (ImageView) actionbarView.findViewById(R.id.advance);
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
        setupBtnAdvance();
    }

    private void setupBtnAdvance() {
        if (mAdvance == null) return;
        mAdvance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mNavigationManager.getActivePage() instanceof CookBookDetailFragment)
                    ((CookBookDetailFragment) mNavigationManager.getActivePage()).editCookbook();
                else if (mNavigationManager.getActivePage() instanceof ChapterDetailFragment)
                    ((ChapterDetailFragment) mNavigationManager.getActivePage()).editChapter();
            }
        });
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
                } else if (fragment instanceof CreateEditBook) {
                    ((CreateEditBook) fragment).finish();
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
                mNavigationManager.showPage(new SearchFragment());
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
//                    if (!handled) mNavigationManager.goBack();
                    mBaseActivity.onBackPressed();
                } else {
//                    mNavigationManager.goBack();
                    mBaseActivity.onBackPressed();
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
//        if (mDataSearch == null) return;
//        mDataSearch.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
//                requestSearch();
//            }
//
//            @Override
//            public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
//
//            }
//
//            @Override
//            public void afterTextChanged(Editable editable) {
//                requestSearch();
//            }
//        });
    }

    private void requestSearch() {
        if (mDataSearch.getText() != null && !mDataSearch.getText().toString().isEmpty())
            if (mNavigationManager.getActivePage() instanceof SearchPeopleFragment)
                ((SearchPeopleFragment) mNavigationManager.getActivePage()).requestSearch(mDataSearch.getText().toString());
            else if (mNavigationManager.getActivePage() instanceof SearchRecipeFragment)
                ((SearchRecipeFragment) mNavigationManager.getActivePage()).requestSearch(mDataSearch.getText().toString());
    }

    protected void syncChildViews(Fragment activePage) {
        syncTitle(activePage);
        syncBtnLeft(activePage);
        syncBtnRight(activePage);
    }

    private void syncBtnRight(Fragment activePage) {
        syncBtnMessage(activePage);
        syncBtnFinish(activePage);
        syncBtnAdvance(activePage);
    }

    private void syncBtnAdvance(Fragment activePage) {
        if (mAdvance == null) return;
        if (activePage instanceof CookBookDetailFragment || activePage instanceof ChapterDetailFragment) {
            mAdvance.setVisibility(View.VISIBLE);
        } else {
            mAdvance.setVisibility(View.GONE);
        }
    }

    private void syncBtnBack(Fragment activePage) {
        if (mBack == null) return;
        if (activePage instanceof RecipeDetailFragment || activePage instanceof CookBookDetailFragment || activePage instanceof ChapterDetailFragment ||
                activePage instanceof EditProfileFragment || activePage instanceof SearchFragment || activePage instanceof SearchPeopleFragment ||
                activePage instanceof SearchRecipeFragment || activePage instanceof CreateEditBook) {
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
        if (activePage instanceof LoginFragment || activePage instanceof ComingSoonFragment || activePage instanceof CreateRecipeFragment ||
                activePage instanceof EditProfileFragment || activePage instanceof CreateEditBook || activePage instanceof CookBookDetailFragment ||
                activePage instanceof ChapterDetailFragment) {
            mMessage.setVisibility(View.GONE);
        } else {
            mMessage.setVisibility(View.VISIBLE);
        }
    }

    private void syncBtnFinish(Fragment activePage) {
        if (mFinish == null) return;
        if (activePage instanceof CreateRecipeFragment || activePage instanceof CreateEditBook) {
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
                activePage instanceof CookBookDetailFragment || activePage instanceof CreateEditBook ||
                activePage instanceof ChapterDetailFragment || activePage instanceof SearchFragment ||
                activePage instanceof HomeFragment || activePage instanceof ComingSoonFragment) {
            mSearch.setVisibility(View.GONE);
        } else {
            mSearch.setVisibility(View.VISIBLE);
        }
    }

    private void syncTitle(Fragment activePage) {
        if (mTitle == null) return;
        mTitle.setVisibility(View.VISIBLE);
        int stringId = R.string.app_name;
        String title = "FlavorLife";
        if (activePage instanceof NewRecipesFragment) {
            stringId = R.string.action_bar_title_recipes;
        } else if (activePage instanceof PeopleFragment) {
            stringId = R.string.action_bar_title_follows;
        } else if (activePage instanceof HomeFragment) {
            title = ((HomeFragment) activePage).getTitleActionBar();
            if (title != null && !title.isEmpty()) {
                stringId = 0;
            } else {
                stringId = R.string.acion_bar_title_home;
            }
        } else if (activePage instanceof MessagesFragment) {
            stringId = R.string.action_bar_title_messages;
        } else if (activePage instanceof CreateRecipeFragment) {
            stringId = R.string.action_bar_title_create_recipe;
        } else if (activePage instanceof RecipeDetailFragment) {
            stringId = R.string.action_bar_title_recipe_detail;
        } else if (activePage instanceof CookBookDetailFragment) {
//            stringId = 0;
//            title = ((CookBookDetailFragment) activePage).getTitle();
            stringId = R.string.action_bar_title_cookbook_detail;
        } else if (activePage instanceof ChapterDetailFragment) {
//            stringId = 0;
//            title = ((ChapterDetailFragment) activePage).getTitle();
            stringId = R.string.action_bar_title_chapter_detail;
        } else if (activePage instanceof EditProfileFragment) {
            stringId = R.string.action_bar_title_edit_profile;
        } else if (activePage instanceof SearchFragment) {
            stringId = R.string.action_bar_title_search;
        } else if (activePage instanceof CreateEditBook) {
            stringId = ((CreateEditBook) activePage).getTitle();
        }

        if (stringId != 0) {
            mTitle.setText(stringId);
        } else {
            mTitle.setText(title);
        }
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

