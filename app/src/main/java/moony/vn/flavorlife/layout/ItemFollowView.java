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
import moony.vn.flavorlife.navigationmanager.NavigationManager;

/**
 * Created by moony on 3/11/15.
 */
public class ItemFollowView extends LinearLayout implements View.OnClickListener {
    private NImageLoader mImageLoader;
    private NavigationManager mNavigationManager;
    private TextView mName, mNumberBook, mNumberRecipe, mNumberFollower;
    private ImageView mUserImage;
    private Button mButtonFollow;
    private DfeFollow mDfeFollow;
    private DfeUnFollow mDfeUnFollow;
    private Follow mFollow;

    private OnDataChangedListener onDataChangedFollow = new OnDataChangedListener() {
        @Override
        public void onDataChanged() {
            if (mDfeFollow != null && mDfeFollow.isReady()) {
                mNumberFollower.setText(String.valueOf(mDfeFollow.getNumFollowers()));
                setButtonSelected(true);
            }
        }
    };

    private Response.ErrorListener onErrorListenerFollow = new Response.ErrorListener() {
        @Override
        public void onErrorResponse(VolleyError error) {
            //TODO show dialog
        }
    };

    private OnDataChangedListener onDataChangedUnFollow = new OnDataChangedListener() {
        @Override
        public void onDataChanged() {
            if (mDfeUnFollow != null && mDfeUnFollow.isReady()) {
                setButtonSelected(false);
                mNumberFollower.setText(String.valueOf(mDfeUnFollow.getNumberFollowers()));
            }
        }
    };

    private Response.ErrorListener onErrorListenerUnFollow = new Response.ErrorListener() {
        @Override
        public void onErrorResponse(VolleyError error) {
            //TODO show dialog
        }
    };

    public ItemFollowView(Context context) {
        super(context);
        init(context);
    }

    public ItemFollowView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public ItemFollowView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    public ItemFollowView(Context context, NImageLoader imageLoader, NavigationManager navigationManager) {
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

    public void display(Follow follow) {
        if (follow == null) return;
        mFollow = follow;
        mName.setText(follow.getEmail());
        mNumberBook.setText(String.valueOf(follow.getNumBooks()));
        mNumberFollower.setText(String.valueOf(follow.getNumFollowers()));
        if (follow.getImage() != null && !follow.getImage().isEmpty()) {
            mImageLoader.display(follow.getImage(), mUserImage);
        } else {
            mUserImage.setImageResource(R.drawable.default_monkey_image);
        }
        mNumberRecipe.setText(String.valueOf(follow.getNumRecipes()));
        if (follow.isFollowed()) {
            setButtonSelected(true);
        } else {
            setButtonSelected(false);
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
        }
    }

    private void requestUnFollow() {
        if (mDfeUnFollow == null) {
            mDfeUnFollow = new DfeUnFollow(FlavorLifeApplication.get().getDfeApi());
            mDfeUnFollow.addDataChangedListener(onDataChangedUnFollow);
            mDfeUnFollow.addErrorListener(onErrorListenerUnFollow);
        }
        mDfeUnFollow.makeRequest(mFollow.getId());
    }

    private void requestFollow() {
        if (mDfeFollow == null) {
            mDfeFollow = new DfeFollow(FlavorLifeApplication.get().getDfeApi());
            mDfeFollow.addDataChangedListener(onDataChangedFollow);
            mDfeFollow.addErrorListener(onErrorListenerFollow);
        }
        mDfeFollow.makeRequest(mFollow.getId());
    }

    private void setButtonSelected(boolean isSelected) {
        if (isSelected) {
            mButtonFollow.setSelected(true);
            mButtonFollow.setTextColor(Color.WHITE);
            mButtonFollow.setText("Following");
        } else {
            mButtonFollow.setSelected(false);
            mButtonFollow.setTextColor(getResources().getColor(R.color.fl_color));
            mButtonFollow.setText("+ Follow");
        }
    }

}
