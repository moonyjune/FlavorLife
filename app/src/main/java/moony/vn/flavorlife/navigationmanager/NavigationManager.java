package moony.vn.flavorlife.navigationmanager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Stack;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentManager.OnBackStackChangedListener;
import android.support.v4.app.FragmentTransaction;

import moony.vn.flavorlife.activities.MainActivity;
import moony.vn.flavorlife.utils.MainThreadStack;
import moony.vn.flavorlife.R;

public class NavigationManager {
    protected MainActivity mActivity;
    @SuppressWarnings("rawtypes")
    protected final Stack mBackStack = new MainThreadStack();
    protected FragmentManager mFragmentManager;
    protected int mPlaceholder = R.id.content_layout;

    protected MainActivity mMainActivity;
    protected final Map<Integer, Stack> mBackStackList = new HashMap<Integer, Stack>();
    protected List<Integer> mPlaceHolderList = new ArrayList<Integer>();
    protected final Map<Integer, FragmentManager> mFragmentManagerList = new HashMap<Integer, FragmentManager>();
    protected final Map<Integer, Fragment> mTabFragmentList = new HashMap<Integer, Fragment>();
    private final List<OnNavigationTabChangeListener> mTabChangeListener = new ArrayList<NavigationManager.OnNavigationTabChangeListener>();
    private final List<OnBackStackChangedListener> mOnBackStackChangedListeners = new ArrayList<OnBackStackChangedListener>();

    protected int mCurrentTabSelected = Tabs.MAIN;
    protected int mCurrentTabSelected_InTabIndicator = Tabs.HOME;

    public NavigationManager(MainActivity mainactivity) {
        mMainActivity = mainactivity;

        FragmentManager fragmentManager = mMainActivity.getSupportFragmentManager();
        fragmentManager.addOnBackStackChangedListener(simpleStackChangedListener);
        mFragmentManagerList.put(Tabs.MAIN, fragmentManager);

        mBackStackList.put(Tabs.MAIN, new MainThreadStack());
        mPlaceHolderList.add(Tabs.MAIN);
    }

    public void addOnBackStackChangedListener(OnBackStackChangedListener backStackChangedListener) {
        synchronized (mOnBackStackChangedListeners) {
            mOnBackStackChangedListeners.add(backStackChangedListener);
        }
    }

    public void removeOnBackStackChangedListener(
            OnBackStackChangedListener onbackstackchangedlistener) {
        mFragmentManager
                .removeOnBackStackChangedListener(onbackstackchangedlistener);
    }

    public OnBackStackChangedListener simpleStackChangedListener = new OnBackStackChangedListener() {
        @Override
        public void onBackStackChanged() {
            for (OnBackStackChangedListener listener : mOnBackStackChangedListeners)
                listener.onBackStackChanged();
        }
    };

    public interface OnNavigationTabChangeListener {
        public void onNavigationTabChange(int tabId);
    }

    public void addTabChangeListener(
            OnNavigationTabChangeListener tabChangeListener) {
        synchronized (mTabChangeListener) {
            mTabChangeListener.add(tabChangeListener);
        }
    }

    public interface OnMainTabFocused {
        public void onMainTabFocused();
    }

    private OnMainTabFocused mMainTabFocusedListener;

    public void setRootSelectedListener(OnMainTabFocused mainTabFocused) {
        this.mMainTabFocusedListener = mainTabFocused;
    }

    public Fragment getTab(int tabId) {
        return mTabFragmentList.get(tabId);
    }

    public void setCurrentTabSelected(int placeHolder) {
        mCurrentTabSelected = placeHolder;
    }

    public void setCurrentTabSelected_InTabIndicator(int tabId) {
        mCurrentTabSelected_InTabIndicator = tabId;
    }

    /**
     * Check whether can navigate or not.
     *
     * @return true if can navigate, false otherwise.
     */
    protected boolean canNavigate() {
        return mMainActivity != null && !mMainActivity.isStateSaved();
    }

    /**
     * Pop fragment in stack if stack isn't empty.
     *
     * @return true if success, false otherwise. (maybe: stack is empty,
     * activity is in onSaveInstance())
     */
    public boolean goBack() {
        if (mMainActivity == null || mMainActivity.isStateSaved()
                || mBackStackList.size() == 0)
            return false;
        Stack backStack = mBackStackList.get(mCurrentTabSelected);
        //Neu' dang o Main va khong co fragment nao o tab nay & co tab khac' => remove fragment o Main va show tab khac'
        if (mCurrentTabSelected == Tabs.MAIN && backStack != null && backStack.size() == 0 && mTabFragmentList.size() > 0) {
            FragmentManager fm = getFragmentManagerByTab(mCurrentTabSelected);
            if (fm != null) {
                Fragment fragment = fm.findFragmentById(mCurrentTabSelected);
                if (fragment != null)
                    fm.beginTransaction().remove(fragment).commit();
            }
            mMainActivity.showTab(mCurrentTabSelected_InTabIndicator);
            mCurrentTabSelected = mCurrentTabSelected_InTabIndicator;
            return true;
        }
        if (backStack == null || backStack.size() <= 0) {
            //get place holder that has stack of fragment. (order: LIFO)
            for (int i = mPlaceHolderList.size() - 1; i >= 0; i--) {
                int place = mPlaceHolderList.get(i);
                backStack = mBackStackList.get(place);
                if (backStack != null && backStack.size() > 0) {
                    //check switch tab
                    NavigationState state = (NavigationState) backStack.peek();
                    if (state.placeholder != mCurrentTabSelected) {
                        mCurrentTabSelected = place;
                        notifyTabChange(state.placeholder);
                        return true;
                    }
                    break;
                }
            }
        }
        //pop fragment from stack if true.
        if (backStack != null && backStack.size() > 0) {
            backStack.pop();
            getFragmentManagerByTab(mCurrentTabSelected).popBackStack();
            return true;
        }
        return false;
    }

    public void notifyTabChange(int placeholder) {
        synchronized (mTabChangeListener) {
            for (OnNavigationTabChangeListener tabChangeListener : mTabChangeListener) {
                tabChangeListener.onNavigationTabChange(placeholder);
            }
        }
    }

    public void setPlaceHolder(int placeHolder) {
        mPlaceholder = placeHolder;
    }

    public void showPage(Fragment fragment) {
        showPage(fragment, true, true, mCurrentTabSelected);
    }

    public void showPage(Fragment fragment, boolean hasAnimation, boolean isAddBackStack, int placeHolder) {
        if (!canNavigate())
            return;
        FragmentTransaction transaction = getFragmentManagerByTab(placeHolder).beginTransaction();
        if (hasAnimation)
            transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
//            transaction.setCustomAnimations(R.anim.fragment_enter,
//                    R.anim.fragment_exit, R.anim.fragment_pop_enter,
//                    R.anim.fragment_pop_exit);
        transaction.replace(placeHolder, fragment);
        if (isAddBackStack) {
            if (!(placeHolder == Tabs.MAIN && (getFragmentManagerByTab(placeHolder).findFragmentById(placeHolder) == null))) {
                NavigationState navigationState = new NavigationState(placeHolder);
                transaction.addToBackStack(navigationState.backStackName);
                mBackStackList.get(placeHolder).push(navigationState);
            }
        }
        transaction.commit();
        if (placeHolder == Tabs.MAIN && mMainTabFocusedListener != null)
            mMainTabFocusedListener.onMainTabFocused();
    }

    public void showPageWithoutStack(Fragment fragment) {
        showPage(fragment, true, false, mCurrentTabSelected);
    }

    public Fragment getActivePage() {
        return getFragmentManagerByTab(mCurrentTabSelected).findFragmentById(mCurrentTabSelected);
    }

    public void setUpTabs(int tabId, Fragment fragment) {
        synchronized (mTabFragmentList) {
            if (mTabFragmentList.get(tabId) == null)
                mTabFragmentList.put(tabId, fragment);
        }
        synchronized (mBackStackList) {
            if (mBackStackList.get(tabId) == null)
                mBackStackList.put(tabId, new MainThreadStack());
        }
        synchronized (mPlaceHolderList) {
            if (!mPlaceHolderList.contains(tabId))
                mPlaceHolderList.add(tabId);
        }
    }

    private FragmentManager getFragmentManagerByTab(final int tabId) {
        FragmentManager fm = mFragmentManagerList.get(tabId);
        if (fm == null) {
            Fragment fragment = mTabFragmentList.get(tabId);
            if (fragment == null) {
                fragment = mMainActivity.getSupportFragmentManager().findFragmentById(tabId);
            }
            fm = fragment.getChildFragmentManager();
            mFragmentManagerList.put(tabId, fm);
            fm.addOnBackStackChangedListener(simpleStackChangedListener);
        }
        return fm;
    }

    public int getBackStackEntryCount(int placeHolder) {
        if (mFragmentManagerList.get(placeHolder) == null) return 0;
        return mFragmentManagerList.get(placeHolder).getBackStackEntryCount();
    }

    public int getBackStackEntryCountCurrentPlace() {
        return getBackStackEntryCount(mCurrentTabSelected);
    }

    /**
     * Terminate resource that keep by this class. Must call in
     * {@link Activity#onDestroy()}
     */
    public void terminate() {
        mActivity = null;
    }

    public int getCurrentPlaceholder() {
        return mCurrentTabSelected;
    }

    public int getCurrentPlaceholderInBackstack() {
        return !mBackStack.isEmpty() ? ((NavigationState) mBackStack.peek()).placeholder
                : 0;
    }

    public boolean isBackStackEmpty() {
        return mFragmentManager.getBackStackEntryCount() <= 0 ? true : false;
    }

    public boolean isPlaceHolderEmpty(int placeHolder) {
        Stack stack = mBackStackList.get(placeHolder);
        if (stack != null) {
            for (Object object : stack) {
                if (object instanceof NavigationState) {
                    if (((NavigationState) object).placeholder == placeHolder) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public boolean isCurrentPlaceHolderEmpty() {
        return isPlaceHolderEmpty(mCurrentTabSelected);
    }

    public void clearAllBackStack() {
        Iterator<Integer> iterator = mPlaceHolderList.iterator();
        while (iterator.hasNext()) {
            int placeHolder = iterator.next();
            Stack stack = mBackStackList.get(placeHolder);
            if (stack != null)
                stack.clear();
            FragmentManager fm = mFragmentManagerList.get(placeHolder);
            if (fm != null)
                fm.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
            if (placeHolder != Tabs.MAIN) {
                mBackStackList.remove(placeHolder);
                mTabFragmentList.remove(placeHolder);
                mFragmentManagerList.remove(placeHolder);
                iterator.remove();
            }
        }
        mMainActivity.clearAllTab();
        mCurrentTabSelected = Tabs.MAIN;
        mCurrentTabSelected_InTabIndicator = Tabs.HOME;
    }


    /**
     * Use for save state of Navigation Manager, called in
     * {@link Activity#onSaveInstanceState(Bundle)}
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
     * {@link Activity#onCreate(Bundle)}
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

}
