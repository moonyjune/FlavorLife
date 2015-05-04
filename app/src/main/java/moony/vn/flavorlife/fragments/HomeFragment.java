package moony.vn.flavorlife.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.ntq.fragments.NFragment;
import com.ntq.fragments.NFragmentSwitcher;

import java.util.ArrayList;
import java.util.List;

import moony.vn.flavorlife.FlavorLifeApplication;
import moony.vn.flavorlife.R;
import moony.vn.flavorlife.adapters.HomePagerAdapter;
import moony.vn.flavorlife.api.model.DfeGetUserProfile;
import moony.vn.flavorlife.entities.User;
import moony.vn.flavorlife.layout.TabIndicator;

/**
 * Created by moony on 3/1/15.
 */
public class HomeFragment extends NFragmentSwitcher {
    private static final String KEY_USER = "user";
    private TabIndicator mTabIndicator;
    private ViewPager mHomePager;
    private HomePagerAdapter mHomePagerAdapter;
    private User mUser;
    private Button mEditProfile;
    private DfeGetUserProfile mDfeGetUserProfile;
    private ImageView mUserImage;
    private TextView mNumBook, mNumRecipe, mNumFollower, mUserInspiration, mBookLabel, mRecipeLabel, mFollowerLabel;

    public static HomeFragment newInstance(User user) {
        HomeFragment homeFragment = new HomeFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable(KEY_USER, user);
        homeFragment.setArguments(bundle);
        return homeFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState == null) {
            mUser = (User) getArguments().getSerializable(KEY_USER);
        } else {
            mUser = (User) savedInstanceState.getSerializable(KEY_USER);
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (mUser != null)
            outState.putSerializable(KEY_USER, mUser);
    }

//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//        return inflater.inflate(R.layout.fragment_home, container, false);
//    }

    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_home;
    }

    @Override
    protected void requestData() {
        switchToLoading();
        if (mDfeGetUserProfile == null) {
            mDfeGetUserProfile = new DfeGetUserProfile(mApi);
            mDfeGetUserProfile.addDataChangedListener(this);
            mDfeGetUserProfile.addErrorListener(this);
        }
        mDfeGetUserProfile.makeRequest(mUser.getId());
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mEditProfile = (Button) view.findViewById(R.id.edit_profile);
        mTabIndicator = (TabIndicator) view.findViewById(R.id.hf_vp_tab_indicator);
        mHomePager = (ViewPager) view.findViewById(R.id.hf_view_content);
        mUserImage = (ImageView) view.findViewById(R.id.user_image);
        mNumBook = (TextView) view.findViewById(R.id.user_num_book);
        mNumRecipe = (TextView) view.findViewById(R.id.user_num_recipe);
        mNumFollower = (TextView) view.findViewById(R.id.user_num_follower);
        mUserInspiration = (TextView) view.findViewById(R.id.user_inspiration);
        mBookLabel = (TextView) view.findViewById(R.id.book);
        mRecipeLabel = (TextView) view.findViewById(R.id.recipe);
        mFollowerLabel = (TextView) view.findViewById(R.id.follower);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mActionbar.syncActionBar(this);
        if (isDataReady()) {
            setDataToView();
        } else {
            requestData();
        }
    }

    @Override
    public void onDataChanged() {
        super.onDataChanged();
        switchToData();
        if (isDataReady()) {
            mUser.updateUser(mDfeGetUserProfile.getUser());
            setDataToView();
        }

    }

    private void setDataToView() {
        switchToData();
        List<Fragment> listPager = new ArrayList<Fragment>();
        listPager.add(UserRecipesFragment.newInstance(mUser.getId()));
        listPager.add(UserCookbooksFragment.newInstance(mUser.getId()));
        mHomePagerAdapter = new HomePagerAdapter(getChildFragmentManager(), listPager);
        mHomePager.setAdapter(mHomePagerAdapter);
        mTabIndicator.setViewPager(mHomePager, getTabNames());

        if (isDataReady()) {
            User user = mDfeGetUserProfile.getUser();
            if (user.getImage() != null && !user.getImage().isEmpty()) {
                mImageLoader.display(user.getImage(), mUserImage);
            } else {
                //TODO can anh default khac LOL
                mUserImage.setImageResource(R.drawable.default_monkey_image);
            }

            if (user.getNumBooks() > 1) {
                mBookLabel.setText("books");
            } else {
                mBookLabel.setText("book");
            }

            if (user.getNumRecipes() > 1) {
                mRecipeLabel.setText("recipes");
            } else {
                mRecipeLabel.setText("recipe");
            }

            if (user.getNumFollowers() > 1) {
                mFollowerLabel.setText("followers");
            } else {
                mFollowerLabel.setText("follower");
            }

            mNumBook.setText(user.getNumBooks() + "");
            mNumRecipe.setText(user.getNumRecipes() + "");
            mNumFollower.setText(user.getNumFollowers() + "");
//            mUserInspiration.setText(user.getInspiration());
        }
        if (isOwner()) {
            mEditProfile.setVisibility(View.VISIBLE);
        } else {
            mEditProfile.setVisibility(View.GONE);
        }
    }

    public List<String> getTabNames() {
        List<String> tabNames = new ArrayList<String>();
        tabNames.add("My Recipes");
        tabNames.add("My Cookbooks");
        return tabNames;
    }

    public boolean isOwner() {
        if (mUser.getId() == FlavorLifeApplication.get().getUser().getId()) {
            return true;
        } else {
            return false;
        }
    }

    private boolean isDataReady() {
        if (mDfeGetUserProfile != null && mDfeGetUserProfile.isReady())
            return true;
        return false;
    }
}
