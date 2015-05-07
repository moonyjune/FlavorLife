package moony.vn.flavorlife.navigationmanager;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;

import java.util.ArrayList;
import java.util.Stack;

import moony.vn.flavorlife.R;
import moony.vn.flavorlife.activities.BaseActivity;
import moony.vn.flavorlife.activities.CreateNewRecipeActivity;
import moony.vn.flavorlife.activities.FollowsActivity;
import moony.vn.flavorlife.activities.HomeActivity;
import moony.vn.flavorlife.activities.NotificationActivity;
import moony.vn.flavorlife.activities.SplashActivity;
import moony.vn.flavorlife.activities.NewRecipesActivity;
import moony.vn.flavorlife.entities.Recipe;
import moony.vn.flavorlife.fragments.CreateRecipeFragment;
import moony.vn.flavorlife.utils.MainThreadStack;

/**
 * Created by moony on 3/11/15.
 */
public class NavigationManager {
    protected BaseActivity mActivity;

    @SuppressWarnings("rawtypes")
    protected final Stack mBackStack = new MainThreadStack();
    protected FragmentManager mFragmentManager;
    protected int mPlaceholder = R.id.content_layout;

    public NavigationManager(BaseActivity baseActivity) {
        mActivity = baseActivity;
        mFragmentManager = mActivity.getSupportFragmentManager();
    }

    public void addOnBackStackChangedListener(FragmentManager.OnBackStackChangedListener backStackChangedListener) {
        mFragmentManager.addOnBackStackChangedListener(backStackChangedListener);
    }

    public void removeOnBackStackChangedListener(FragmentManager.OnBackStackChangedListener onbackstackchangedlistener) {
        mFragmentManager.removeOnBackStackChangedListener(onbackstackchangedlistener);
    }

    /**
     * Check whether can navigate or not.
     *
     * @return true if can navigate, false otherwise.
     */
    protected boolean canNavigate() {
        return mActivity == null || mActivity.isStateSaved() ? false : true;
    }

    /**
     * Pop fragment in stack if stack isn't empty.
     *
     * @return true if success, false otherwise. (maybe: stack is empty,
     * activity is in onSaveInstance())
     */
    public boolean goBack() {
        if (mActivity == null || mActivity.isStateSaved()
                || mBackStack.isEmpty())
            return false;
        mBackStack.pop();
        mFragmentManager.popBackStack();
        return true;
    }

    public void showPage(Fragment fragment) {
        showPage(fragment, true, true, null);
    }

    public void showPage(Fragment fragment, String name) {
        showPage(fragment, true, true, name);
    }

    public void showPage(Fragment fragment, boolean hasAnimation) {
        showPage(fragment, true, true, null);
    }

    public void showPageWithoutStack(Fragment fragment, String tag) {
        showPage(fragment, true, false, tag);
    }

    public void showPageWithoutStack(Fragment fragment) {
        showPageWithoutStack(fragment, null);
    }

    @SuppressWarnings("unchecked")
    public void showPage(Fragment fragment, boolean hasAnimation, boolean isAddBackStack, String name) {
        if (!canNavigate())
            return;
        FragmentTransaction transaction = mFragmentManager.beginTransaction();
        if (hasAnimation)
            transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);

        transaction.replace(mPlaceholder, fragment);
        NavigationState navigationState = new NavigationState(mPlaceholder);
        if (isAddBackStack) {
            if (TextUtils.isEmpty(name)) {
                name = navigationState.backStackName;
            }
            transaction.addToBackStack(name);
            mBackStack.push(navigationState);
        }
        transaction.commit();
    }


    /**
     * Terminate resource that keep by this class. Must call in
     * {@link android.app.Activity#onDestroy()}
     */
    public void terminate() {
        mActivity = null;
    }

    public int getCurrentPlaceholder() {
        return mPlaceholder;
    }

    public boolean isBackStackEmpty() {
        return mFragmentManager.getBackStackEntryCount() <= 0 ? true : false;
    }

    public Fragment getActivePage() {
        return mFragmentManager.findFragmentById(mPlaceholder);
    }

    /**
     * Use for save state of Navigation Manager, called in
     * {@link android.app.Activity#onSaveInstanceState(android.os.Bundle)}
     *
     * @param bundle
     */
    @SuppressWarnings({"rawtypes", "unchecked"})
    public void serialize(Bundle bundle) {
        if (mBackStack != null && !mBackStack.isEmpty())
            bundle.putParcelableArrayList("nm_state", new ArrayList(mBackStack));
    }

    /**
     * Use for restore state that saved in {@link #serialize(Bundle)}, called in
     * {@link android.app.Activity#onCreate(Bundle)}
     *
     * @param bundle
     */
    @SuppressWarnings("unchecked")
    public void deserialize(Bundle bundle) {
        @SuppressWarnings("rawtypes")
        ArrayList arraylist = bundle.getParcelableArrayList("nm_state");
        if (arraylist != null && arraylist.size() != 0) {
            for (Object navigationState : arraylist) {
                mBackStack.push(navigationState);
            }

        }
    }

    public void clearStack(int placeHolder) {
        //do nothing
    }

    /**
     * Popback all fragment in FragmentManager until {@code tag}
     *
     * @param tag Use when show page. If null -> popback all fragment without root in activity.
     */
    public void clearStack(String tag) {
        if (tag != null)
            mFragmentManager.popBackStack(tag, 0);
        else
            mFragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
    }

    public int getBackStackSize() {
        return mBackStack.size();
    }

    public int getBackStackEntryCount(int placeHolder) {
        return 0;
    }

    public void goBackToScreenWithId(int sizeBackStack) {
        final Stack backStack = mBackStack;
        if (sizeBackStack > 0) {
            while (backStack.size() > sizeBackStack) {
                backStack.pop();
            }
            mFragmentManager.popBackStack(sizeBackStack - 1, 0);
        } else if (sizeBackStack == 0) {
            while (backStack.size() > 0) {
                backStack.pop();
            }
            mFragmentManager.popBackStack(0, FragmentManager.POP_BACK_STACK_INCLUSIVE);
        }
    }

    public int getBackStackEntryCountCurrentPlace() {
        return 0;
    }


    public Fragment getTab(int id) {
        return null;
    }

    /**
     * Show tab without flags.
     *
     * @param cls
     */
    private void showTab(Class cls) {
        showTab(cls, 0);
    }

    private void showTabCreateRecipe(Class cls, Recipe recipe, int flagCreateRecipe) {
        showTabCreateRecipe(cls, 0, recipe, flagCreateRecipe);
    }

    /**
     * Show Tab with flags
     *
     * @param cls   Activity to show
     * @param flags flags are flag value in {@link android.content.Intent} class. If flags==0 -> show without flags, prefers use {@link #showTab(Class)}
     */
    private void showTab(Class cls, int flags) {
        Intent intent = new Intent(mActivity, cls);
        if (flags != 0) {
            intent.addFlags(flags);
        }
        mActivity.startActivity(intent);
    }

    private void showTabCreateRecipe(Class cls, int flags, Recipe recipe, int flagCreateRecipe) {
        Intent intent = new Intent(mActivity, cls);
        Bundle bundle = new Bundle();
        if (recipe != null)
            bundle.putSerializable(CreateNewRecipeActivity.RECIPE, recipe);
        bundle.putInt(CreateNewRecipeActivity.FLAG, flagCreateRecipe);
        intent.putExtra(CreateNewRecipeActivity.DATA, bundle);
        if (flags != 0) {
            intent.addFlags(flags);
        }
        mActivity.startActivity(intent);
    }

    /**
     * Show Tab with flags
     *
     * @param cls   Activity to show
     * @param flags If flags==0 -> show without flags,prefers use {@link #showTab(Class)}
     */
    private void showTab(Class cls, int flags, Bundle bundle) {
        Intent intent = new Intent(mActivity, cls);
        if (flags != 0) {
            intent.addFlags(flags);
        }
        intent.putExtras(bundle);
        mActivity.startActivity(intent);
    }

    public void showNewRecipes() {
        showNewRecipes(false);
    }

    public void showNewRecipes(boolean isClearAllActivity) {
        if (isClearAllActivity)
            showTab(NewRecipesActivity.class, Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        else showTab(NewRecipesActivity.class);
    }

    /**
     * Use {@link #showFollows(boolean)} with params=false.
     */
    public void showFollows() {
        showFollows(false);
    }

    /**
     * @param isClearAllStack If true then clear all fragment without root else just show
     */
    public void showFollows(boolean isClearAllStack) {
        if (isClearAllStack) {
            Bundle bundle = new Bundle();
            bundle.putBoolean(FollowsActivity.KEY_CLEAR_ALL_STACK, true);
            showTab(FollowsActivity.class, 0, bundle);
        } else {
            showTab(FollowsActivity.class);
        }
    }

    public void showHome() {
        showTab(HomeActivity.class);
    }

    public void showNotification() {
        showTab(NotificationActivity.class);
    }

    public void showCreateNewRecipe() {
        showTab(CreateNewRecipeActivity.class);
    }

    public void showUpgradeRecipe(Recipe recipe) {
        showTabCreateRecipe(CreateNewRecipeActivity.class, recipe, CreateRecipeFragment.FLAG_UPGRADE);
    }

    public void showEditRecipe(Recipe recipe) {
        showTabCreateRecipe(CreateNewRecipeActivity.class, recipe, CreateRecipeFragment.FLAG_EDIT);
    }

    public void showMain(boolean isClearAllActivity) {
        if (isClearAllActivity) {
            showTab(SplashActivity.class, Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        } else {
            showTab(SplashActivity.class);
        }
    }

}
