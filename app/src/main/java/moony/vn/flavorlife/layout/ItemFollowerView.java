package moony.vn.flavorlife.layout;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.ntq.api.model.OnDataChangedListener;
import com.ntq.imageloader.NImageLoader;

import moony.vn.flavorlife.FlavorLifeApplication;
import moony.vn.flavorlife.R;
import moony.vn.flavorlife.api.model.DfeFollow;
import moony.vn.flavorlife.api.model.DfeUnFollow;
import moony.vn.flavorlife.entities.Follow;
import moony.vn.flavorlife.entities.Follower;
import moony.vn.flavorlife.fragments.HomeFragment;
import moony.vn.flavorlife.navigationmanager.NavigationManager;
import moony.vn.flavorlife.utils.DialogUtils;

/**
 * Created by moony on 3/11/15.
 */
public class ItemFollowerView extends LinearLayout implements View.OnClickListener {
    private NImageLoader mImageLoader;
    private NavigationManager mNavigationManager;
    private TextView mName, mNumberBook, mNumberRecipe, mNumberFollower;
    private ImageView mUserImage;
    private Button mButtonFollow;
    private DfeFollow mDfeFollow;
    private DfeUnFollow mDfeUnFollow;
    private Follower mFollower;
    private Context mContext;

    private OnDataChangedListener onDataChangedFollow = new OnDataChangedListener() {
        @Override
        public void onDataChanged() {
            DialogUtils.getInstance().hideDialogLoading();
            if (mDfeFollow != null && mDfeFollow.isReady()) {
                mFollower.setFollowed(true);
                mFollower.setNumFollowers(mDfeFollow.getNumFollowers());
                setButtonSelected(true);
                mNumberFollower.setText(String.valueOf(mDfeFollow.getNumFollowers()));
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
                mFollower.setFollowed(false);
                mFollower.setNumFollowers(mDfeUnFollow.getNumberFollowers());
                setButtonSelected(false);
                mNumberFollower.setText(String.valueOf(mDfeUnFollow.getNumberFollowers()));
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

    public ItemFollowerView(Context context) {
        super(context);
        init(context);
    }

    public ItemFollowerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public ItemFollowerView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    public ItemFollowerView(Context context, NImageLoader imageLoader, NavigationManager navigationManager) {
        super(context);
        init(context);
        mImageLoader = imageLoader;
        mNavigationManager = navigationManager;
    }

    private void init(Context context) {
        inflate(context, R.layout.item_follow, this);
        mName = (TextView) findViewById(R.id.user_name);
        mNumberBook = (TextView) findViewById(R.id.user_num_book);
        mNumberRecipe = (TextView) findViewById(R.id.user_num_recipe);
        mNumberFollower = (TextView) findViewById(R.id.user_num_follower);
        mUserImage = (ImageView) findViewById(R.id.user_image);
        mButtonFollow = (Button) findViewById(R.id.follow);
        mButtonFollow.setOnClickListener(this);
    }

    public void display(Follower follower) {
        if (follower == null) return;
        mFollower = follower;
        mName.setText(follower.getUserName());
        mNumberBook.setText(String.valueOf(follower.getNumBooks()));
        mNumberFollower.setText(String.valueOf(follower.getNumFollowers()));
        if (follower.getImageDisplay() != null && !follower.getImageDisplay().isEmpty()) {
            mImageLoader.display(follower.getImageDisplay(), mUserImage);
        } else {
//            if (follower.getSocialNetworkImage() != null && !follower.getSocialNetworkImage().isEmpty()) {
//                mImageLoader.display(follower.getSocialNetworkImage(), mUserImage);
//            } else {
                mUserImage.setImageResource(R.drawable.default_monkey_image);
//            }
        }
        mNumberRecipe.setText(String.valueOf(follower.getNumRecipes()));
        if (follower.isFollowed()) {
            setButtonSelected(true);
        } else {
            setButtonSelected(false);
        }

        setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                mNavigationManager.showPage(HomeFragment.newInstance(mFollower));
            }
        });
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
        }
    }

    private void requestUnFollow() {
        DialogUtils.getInstance().showDialogLoading(getContext());
        if (mDfeUnFollow == null) {
            mDfeUnFollow = new DfeUnFollow(FlavorLifeApplication.get().getDfeApi());
            mDfeUnFollow.addDataChangedListener(onDataChangedUnFollow);
            mDfeUnFollow.addErrorListener(onErrorListenerUnFollow);
        }
        mDfeUnFollow.makeRequest(mFollower.getId());
    }

    private void requestFollow() {
        DialogUtils.getInstance().showDialogLoading(getContext());
        if (mDfeFollow == null) {
            mDfeFollow = new DfeFollow(FlavorLifeApplication.get().getDfeApi());
            mDfeFollow.addDataChangedListener(onDataChangedFollow);
            mDfeFollow.addErrorListener(onErrorListenerFollow);
        }
        mDfeFollow.makeRequest(mFollower.getId());
    }

    private void setButtonSelected(boolean isSelected) {
        if (isSelected) {
            mButtonFollow.setSelected(true);
            mButtonFollow.setTextColor(Color.WHITE);
            mButtonFollow.setText("Following");
            mButtonFollow.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_followed, 0, 0, 0);
        } else {
            mButtonFollow.setSelected(false);
            mButtonFollow.setTextColor(getResources().getColor(R.color.fl_color));
            mButtonFollow.setText("+ Follow");
            mButtonFollow.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
        }
    }
}
