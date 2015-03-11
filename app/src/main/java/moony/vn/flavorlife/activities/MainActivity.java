package moony.vn.flavorlife.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;

import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.ntq.activities.NActivity;

import moony.vn.flavorlife.FlavorLifeApplacation;
import moony.vn.flavorlife.R;
import moony.vn.flavorlife.actionbar.CustomActionBarFactory;
import moony.vn.flavorlife.actionbar.CustomActionbar;
import moony.vn.flavorlife.api.Api;
import moony.vn.flavorlife.api.UploadImage;
import moony.vn.flavorlife.fragments.CreateRecipeFragment;
import moony.vn.flavorlife.fragments.FollowsFragment;
import moony.vn.flavorlife.fragments.HomeFragment;
import moony.vn.flavorlife.fragments.MessagesFragment;
import moony.vn.flavorlife.fragments.NewRecipesFragment;
import moony.vn.flavorlife.fragments.SplashFragment;
import moony.vn.flavorlife.layout.MainTabLayout;
import moony.vn.flavorlife.navigationmanager.NavigationManager;
import moony.vn.flavorlife.analytics.AppAnalytics;
import moony.vn.flavorlife.navigationmanager.Tabs;
import moony.vn.flavorlife.widget.TabWidget;

import com.ntq.imageloader.NImageLoader;

public class MainActivity extends NActivity implements TabWidget.OnTabChangeListener, NavigationManager.OnMainTabFocused, NavigationManager.OnNavigationTabChangeListener {
    protected NavigationManager mNavigationManager;
    protected CustomActionbar mActionbar;

    private SlidingMenu menu;
    private MainTabLayout mMainTabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mNavigationManager = new NavigationManager(this);
        mActionbar = CustomActionBarFactory.getInstance(this);
        mActionbar.initialize(mNavigationManager, this);

        if (savedInstanceState != null) {
            mNavigationManager.deserialize(savedInstanceState);
            mNavigationManager.setUpTabs(Tabs.NEW_RECIPES, getSupportFragmentManager().findFragmentById(Tabs.NEW_RECIPES));
            mNavigationManager.setUpTabs(Tabs.FOLLOWS, getSupportFragmentManager().findFragmentById(Tabs.FOLLOWS));
            mNavigationManager.setUpTabs(Tabs.HOME, getSupportFragmentManager().findFragmentById(Tabs.HOME));
            mNavigationManager.setUpTabs(Tabs.MESSAGES, getSupportFragmentManager().findFragmentById(Tabs.MESSAGES));
            mNavigationManager.setUpTabs(Tabs.CREATE_RECIPE, getSupportFragmentManager().findFragmentById(Tabs.CREATE_RECIPE));
        }

        mMainTabLayout = (MainTabLayout) findViewById(R.id.content_layout);
        setUpSlidingMenu();

        mMainTabLayout.setTabMainChangeListener(this);
        mNavigationManager.addTabChangeListener(this);
        mNavigationManager.setRootSelectedListener(this);
        Fragment fragment = getSupportFragmentManager().findFragmentById(mNavigationManager.getCurrentPlaceholder());
        if (fragment == null) {
            mMainTabLayout.setCurrentTab(Tabs.MAIN);
            mNavigationManager.showPageWithoutStack(new SplashFragment());
        }
        mMainTabLayout.setCurrentTab(mNavigationManager.getCurrentPlaceholder());
        initView();

    }

    private void initView() {

    }

    private void setUpSlidingMenu() {
        menu = new SlidingMenu(this);
        menu.setMode(SlidingMenu.RIGHT);
        menu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
        menu.setShadowWidthRes(R.dimen.shadow_width);
        menu.setShadowDrawable(R.drawable.shadow);
        menu.setBehindOffsetRes(R.dimen.slidingmenu_offset);
        menu.setFadeDegree(0.35f);
        menu.attachToActivity(this, SlidingMenu.SLIDING_WINDOW);
//        menu.setMenu(R.layout.menu_debug);
        menu.setSecondaryMenu(R.layout.fragment_app_menu);
        menu.setSlidingEnabled(false);
    }

    private void enableSlideMenu() {
        if (!menu.isSlidingEnabled()) {
            menu.setSlidingEnabled(true);
        }
    }

    public void showMain() {
        showTab(Tabs.NEW_RECIPES);
        mNavigationManager.setCurrentTabSelected_InTabIndicator(Tabs.NEW_RECIPES);
        Fragment fragment = getSupportFragmentManager().findFragmentById(Tabs.MAIN);
        if (fragment != null) {
            getSupportFragmentManager().beginTransaction().remove(fragment).commit();
        }
        menu.setSlidingEnabled(true);
    }

    @Override
    public NavigationManager getNavigationManager() {
        return mNavigationManager;
    }

    @Override
    public CustomActionbar getActionbar() {
        return mActionbar;
    }

    @Override
    public NImageLoader getImageLoader() {
        return FlavorLifeApplacation.get().getImageLoader();
    }

    @Override
    public Api getDfeApi() {
        return FlavorLifeApplacation.get().getDfeApi();
    }

    @Override
    public AppAnalytics getAnalytics() {
        return FlavorLifeApplacation.get().getAnalytic();
    }

    @Override
    public void onNewRecipesTabSelected() {
        enableSlideMenu();
        showTab(Tabs.NEW_RECIPES);
    }

    @Override
    public void onFollowsTabSelected() {
        enableSlideMenu();
        showTab(Tabs.FOLLOWS);
    }

    @Override
    public void onHomeTabSelected() {
        enableSlideMenu();
        showTab(Tabs.HOME);
    }

    @Override
    public void onMessageTabSelected() {
        enableSlideMenu();
        showTab(Tabs.MESSAGES);
    }

    @Override
    public void onCreateRecipeTabSelected() {
        enableSlideMenu();
        showTab(Tabs.CREATE_RECIPE);
    }

    @Override
    public void onMainTabFocused() {
        mMainTabLayout.setCurrentTab(Tabs.MAIN);
    }

    @Override
    public void onNavigationTabChange(int tabId) {
        showTab(tabId);
    }

    public void showTab(int tabId) {
        mMainTabLayout.setCurrentTab(tabId);
        mNavigationManager.setCurrentTabSelected(tabId);
        mNavigationManager.setCurrentTabSelected_InTabIndicator(tabId);

        Fragment fragment = getSupportFragmentManager().findFragmentById(tabId);
        if (fragment == null) {
            switch (tabId) {
                case Tabs.NEW_RECIPES:
                    fragment = new NewRecipesFragment();
                    break;
                case Tabs.FOLLOWS:
                    fragment = new FollowsFragment();
                    break;
                case Tabs.HOME:
                    fragment = new HomeFragment();
                    break;
                case Tabs.MESSAGES:
                    fragment = new MessagesFragment();
                    break;
                case Tabs.CREATE_RECIPE:
                    fragment = new CreateRecipeFragment();
                    break;
            }
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(tabId, fragment).commit();
        } else {
            if (mNavigationManager.getActivePage() == null) {
                mActionbar.syncActionBar(fragment);
            } else {
                mActionbar.syncActionBar(mNavigationManager.getActivePage());
            }
        }
        mNavigationManager.setUpTabs(tabId, fragment);
    }

    public void clearAllTab() {
        Fragment fragment = getSupportFragmentManager().findFragmentById(Tabs.NEW_RECIPES);
        if (fragment != null) {
            getSupportFragmentManager().beginTransaction().remove(fragment).commit();
        }
        fragment = getSupportFragmentManager().findFragmentById(Tabs.FOLLOWS);
        if (fragment != null) {
            getSupportFragmentManager().beginTransaction().remove(fragment).commit();
        }
        fragment = getSupportFragmentManager().findFragmentById(Tabs.HOME);
        if (fragment != null) {
            getSupportFragmentManager().beginTransaction().remove(fragment).commit();
        }
        fragment = getSupportFragmentManager().findFragmentById(Tabs.MESSAGES);
        if (fragment != null) {
            getSupportFragmentManager().beginTransaction().remove(fragment).commit();
        }
        fragment = getSupportFragmentManager().findFragmentById(Tabs.CREATE_RECIPE);
        if (fragment != null) {
            getSupportFragmentManager().beginTransaction().remove(fragment).commit();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        System.out.println("Mj - test");
        Uri uri = data.getData();
        String realPath = getRealPathFromURI(uri);
        String[] params = new String[2];
        params[0] = "uploadImage";
        params[1] = realPath;
        new UploadImage().execute(params);
    }

    private String getRealPathFromURI(Uri contentURI) {
        String[] filePathColumn = {MediaStore.Images.Media.DATA};

        Cursor cursor = getContentResolver().query(contentURI, filePathColumn, null, null, null);
        cursor.moveToFirst();

        int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
        String picturePath = cursor.getString(columnIndex);
        cursor.close();
        return picturePath;
    }
}
