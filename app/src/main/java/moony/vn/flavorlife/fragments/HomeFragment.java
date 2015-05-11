package moony.vn.flavorlife.fragments;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.ntq.api.model.OnDataChangedListener;
import com.ntq.fragments.NFragmentSwitcher;

import java.util.ArrayList;
import java.util.List;

import moony.vn.flavorlife.FlavorLifeApplication;
import moony.vn.flavorlife.R;
import moony.vn.flavorlife.adapters.HomePagerAdapter;
import moony.vn.flavorlife.api.model.DfeFollow;
import moony.vn.flavorlife.api.model.DfeGetUserProfile;
import moony.vn.flavorlife.api.model.DfeUnFollow;
import moony.vn.flavorlife.entities.User;
import moony.vn.flavorlife.layout.TabIndicator;
import moony.vn.flavorlife.utils.DialogUtils;

/**
 * Created by moony on 3/1/15.
 */
public class HomeFragment extends NFragmentSwitcher implements View.OnClickListener {
    private static final String KEY_USER = "user";
    private User mUser;
    private Button mEditProfile;
    private DfeGetUserProfile mDfeGetUserProfile;
    private ImageView mUserImage;
    private TextView mNumBook, mNumRecipe, mNumFollower, mUserInspiration, mBookLabel, mRecipeLabel, mFollowerLabel;
    private Button mButtonFollow;
    private DfeFollow mDfeFollow;
    private DfeUnFollow mDfeUnFollow;
    //    private WorkResultFragment workResultFragment;
    private TabIndicator mTabIndicator;
    private ViewPager mHomePager;
    private HomePagerAdapter mHomePagerAdapter;
    private LinearLayout mUserProfileLayout, mUserInforLayout;

    private OnDataChangedListener onDataChangedFollow = new OnDataChangedListener() {
        @Override
        public void onDataChanged() {
            DialogUtils.getInstance().hideDialogLoading();
            if (mDfeFollow != null && mDfeFollow.isReady()) {
                mUser.setFollowed(true);
                mUser.setNumFollowers(mDfeFollow.getNumFollowers());
                setButtonSelected(true);
                mNumFollower.setText(String.valueOf(mDfeFollow.getNumFollowers()));
            }
        }
    };

    private Response.ErrorListener onErrorListenerFollow = new Response.ErrorListener() {
        @Override
        public void onErrorResponse(VolleyError error) {
            DialogUtils.getInstance().hideDialogLoading();
            DialogUtils.getInstance().showDialogMessageError(mContext, error);
        }
    };

    private OnDataChangedListener onDataChangedUnFollow = new OnDataChangedListener() {
        @Override
        public void onDataChanged() {
            DialogUtils.getInstance().hideDialogLoading();
            if (mDfeUnFollow != null && mDfeUnFollow.isReady()) {
                mUser.setFollowed(false);
                mUser.setNumFollowers(mDfeUnFollow.getNumberFollowers());
                setButtonSelected(false);
                mNumFollower.setText(String.valueOf(mDfeUnFollow.getNumberFollowers()));
            }
        }
    };

    private Response.ErrorListener onErrorListenerUnFollow = new Response.ErrorListener() {
        @Override
        public void onErrorResponse(VolleyError error) {
            DialogUtils.getInstance().hideDialogLoading();
            DialogUtils.getInstance().showDialogMessageError(mContext, error);
        }
    };

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

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mTabIndicator = (TabIndicator) view.findViewById(R.id.hf_vp_tab_indicator);
        mHomePager = (ViewPager) view.findViewById(R.id.hf_view_content);
        mUserInspiration = (TextView) view.findViewById(R.id.user_inspiration);
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

    private void inflateFromViewStub(ViewGroup viewGroup) {
        if (viewGroup == null) return;
        ViewStub stub = (ViewStub) viewGroup.findViewById(R.id.view_stub);
        View view = null;
        if (stub != null)
            view = stub.inflate();
        if (view == null) return;
        mUserProfileLayout = (LinearLayout) view.findViewById(R.id.user_profile);
        mUserInforLayout = (LinearLayout) view.findViewById(R.id.user_info);
        mEditProfile = (Button) view.findViewById(R.id.edit_profile);
        mUserImage = (ImageView) view.findViewById(R.id.user_image);
        mNumBook = (TextView) view.findViewById(R.id.user_num_book);
        mNumRecipe = (TextView) view.findViewById(R.id.user_num_recipe);
        mNumFollower = (TextView) view.findViewById(R.id.user_num_follower);
        mBookLabel = (TextView) view.findViewById(R.id.book);
        mRecipeLabel = (TextView) view.findViewById(R.id.recipe);
        mFollowerLabel = (TextView) view.findViewById(R.id.follower);
        mButtonFollow = (Button) view.findViewById(R.id.follow);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mActionbar.syncActionBar(this);
        if (isDataReady()) {
            inflateFromViewStub(mDataView);
            mUserInspiration.setVisibility(View.VISIBLE);
            setDataToView();
        } else {
            mUserInspiration.setVisibility(View.INVISIBLE);
            requestData();
        }
    }

    @Override
    public void onDataChanged() {
        super.onDataChanged();
        inflateFromViewStub(mDataView);
        mUserInspiration.setVisibility(View.VISIBLE);
        switchToData();
        if (isDataReady()) {
            mUser.updateUser(mDfeGetUserProfile.getUser());
            setDataToView();
        }
    }

    private void setDataToView() {
        switchToData();
        mButtonFollow.setOnClickListener(this);
        mEditProfile.setOnClickListener(this);
        mActionbar.syncActionBar(this);
        if (mUser.isFollowed()) {
            setButtonSelected(true);
        } else {
            setButtonSelected(false);
        }

        if (isDataReady()) {
            User user = mDfeGetUserProfile.getUser();
            if (user.getImageDisplay() != null && !user.getImageDisplay().isEmpty()) {
                mImageLoader.display(user.getImageDisplay(), mUserImage);
            } else {
//                if (user.getSocialNetworkImage() != null && !user.getSocialNetworkImage().isEmpty()) {
//                    mImageLoader.display(user.getSocialNetworkImage(), mUserImage);
//                } else {
                //TODO can anh default khac LOL
                mUserImage.setImageResource(R.drawable.default_monkey_image);
//                }
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
            if (user.getInspiration() != null && !user.getInspiration().isEmpty()) {
                mUserInspiration.setVisibility(View.VISIBLE);
                mUserInspiration.setText(user.getInspiration());
            } else {
                mUserInspiration.setVisibility(View.GONE);
            }
            ViewGroup.LayoutParams layoutParams = mUserProfileLayout.getLayoutParams();
            layoutParams.height = mUserInforLayout.getMeasuredHeight() + mUserInspiration.getMeasuredHeight();
            mUserProfileLayout.setLayoutParams(layoutParams);
//            if (getChildFragmentManager().findFragmentById(R.id.workresult) == null) {
//                workResultFragment = WorkResultFragment.newInstance(mUser);
//                getChildFragmentManager().beginTransaction().replace(R.id.workresult, workResultFragment).commit();
//            }
            List<Fragment> listPager = new ArrayList<Fragment>();
            listPager.add(UserRecipesFragment.newInstance(mUser.getId()));
            listPager.add(UserCookbooksFragment.newInstance(mUser.getId()));
            if (mHomePagerAdapter == null)
                mHomePagerAdapter = new HomePagerAdapter(getChildFragmentManager(), listPager);
            mHomePager.setAdapter(mHomePagerAdapter);
            mTabIndicator.setViewPager(mHomePager, getTabNames());
        }
        syncButtonFollowEditProfile();
    }

    private void syncButtonFollowEditProfile() {
        if (isOwner()) {
            mEditProfile.setVisibility(View.VISIBLE);
            mButtonFollow.setVisibility(View.GONE);
        } else {
            mEditProfile.setVisibility(View.GONE);
            mButtonFollow.setVisibility(View.VISIBLE);
        }
    }

    public boolean isOwner() {
        if (mUser.getId() == FlavorLifeApplication.get().getUser().getId()) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    protected boolean isDataReady() {
        if (mDfeGetUserProfile != null && mDfeGetUserProfile.isReady())
            return true;
        return false;
    }

    private void requestUnFollow() {
        DialogUtils.getInstance().showDialogLoading(getActivity());
        if (mDfeUnFollow == null) {
            mDfeUnFollow = new DfeUnFollow(FlavorLifeApplication.get().getDfeApi());
            mDfeUnFollow.addDataChangedListener(onDataChangedUnFollow);
            mDfeUnFollow.addErrorListener(onErrorListenerUnFollow);
        }
        mDfeUnFollow.makeRequest(mUser.getId());
    }

    private void requestFollow() {
        DialogUtils.getInstance().showDialogLoading(getActivity());
        if (mDfeFollow == null) {
            mDfeFollow = new DfeFollow(FlavorLifeApplication.get().getDfeApi());
            mDfeFollow.addDataChangedListener(onDataChangedFollow);
            mDfeFollow.addErrorListener(onErrorListenerFollow);
        }
        mDfeFollow.makeRequest(mUser.getId());
    }

    private void setButtonSelected(boolean isSelected) {
        if (isSelected) {
            mButtonFollow.setSelected(true);
            mButtonFollow.setTextColor(Color.WHITE);
            mButtonFollow.setText("Following");
//            mButtonFollow.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_followed, 0, 0, 0);
        } else {
            mButtonFollow.setSelected(false);
            mButtonFollow.setTextColor(getResources().getColor(R.color.fl_color));
            mButtonFollow.setText("+ Follow");
//            mButtonFollow.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.follow:
                if (!mButtonFollow.isSelected()) {
                    requestFollow();
                } else {
                    requestUnFollow();
                }
                break;
            case R.id.edit_profile:
                mNavigationManager.showPage(EditProfileFragment.newInstance(mUser));
                break;
        }
    }

    public String getTitleActionBar() {
        if (mUser != null)
            return mUser.getUserName();
        return null;
    }

    public List<String> getTabNames() {
        List<String> tabNames = new ArrayList<String>();
        tabNames.add("Recipes");
        tabNames.add("Cookbooks");
        return tabNames;
    }

}
