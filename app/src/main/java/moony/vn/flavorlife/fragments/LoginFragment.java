package moony.vn.flavorlife.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.ntq.api.model.OnDataChangedListener;
import com.ntq.fragments.NFragment;
import com.sromku.simple.fb.Permission;
import com.sromku.simple.fb.SimpleFacebook;
import com.sromku.simple.fb.entities.Profile;
import com.sromku.simple.fb.listeners.OnLoginListener;
import com.sromku.simple.fb.listeners.OnProfileListener;
import com.sromku.simple.fb.utils.Attributes;
import com.sromku.simple.fb.utils.PictureAttributes;

import moony.vn.flavorlife.FlavorLifeApplication;
import moony.vn.flavorlife.R;
import moony.vn.flavorlife.api.model.DfeLogin;
import moony.vn.flavorlife.utils.ToastUtils;

public class LoginFragment extends NFragment implements View.OnClickListener, OnDataChangedListener, Response.ErrorListener {
    private DfeLogin mDfeLogin;
    private EditText mEmail;
    private SimpleFacebook mSimpleFacebook;

    private OnProfileListener onProfileListener = new OnProfileListener() {
        @Override
        public void onComplete(Profile profile) {
            super.onComplete(profile);
            FlavorLifeApplication.get().updateEmail(profile.getEmail());
            FlavorLifeApplication.get().updateUserName(profile.getName());
            FlavorLifeApplication.get().updateSocialNetworkImage(profile.getPicture());
            requestLogin();
        }

        @Override
        public void onException(Throwable throwable) {
            super.onException(throwable);
            System.out.println("MJ : " + throwable.toString());
            ToastUtils.showToastShort(getActivity(), "Exception: " + throwable.toString());
        }

        @Override
        public void onFail(String reason) {
            super.onFail(reason);
            ToastUtils.showToastShort(getActivity(), "Fail: " + reason);
        }
    };

    private OnLoginListener onLoginListener = new OnLoginListener() {
        @Override
        public void onLogin() {
            System.out.println("MJ : login fb");
            ToastUtils.showToastShort(getActivity(), "Logged in");
            getFbProfile();
        }

        @Override
        public void onNotAcceptingPermissions(Permission.Type type) {
            ToastUtils.showToastShort(getActivity(), "Not accepting permission " + type.name());
        }

        @Override
        public void onThinking() {

        }

        @Override
        public void onException(Throwable throwable) {
            System.out.println("MJ : " + throwable.toString());
            ToastUtils.showToastShort(getActivity(), "Exception: " + throwable.toString());
        }

        @Override
        public void onFail(String s) {
            ToastUtils.showToastShort(getActivity(), "Fail: " + s);
        }
    };

    private void getFbProfile() {
        PictureAttributes pictureAttributes = Attributes.createPictureAttributes();
        pictureAttributes.setHeight(720);
        pictureAttributes.setWidth(720);
        pictureAttributes.setType(PictureAttributes.PictureType.SQUARE);

        Profile.Properties properties = new Profile.Properties.Builder()
                .add(Profile.Properties.ID)
                .add(Profile.Properties.EMAIL)
                .add(Profile.Properties.NAME)
                .add(Profile.Properties.PICTURE, pictureAttributes)
                .build();
        mSimpleFacebook.getProfile(properties, onProfileListener);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mSimpleFacebook = SimpleFacebook.getInstance(getActivity());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_login, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mActionbar.syncActionBar(this);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mEmail = (EditText) view.findViewById(R.id.email);
        view.findViewById(R.id.login_fb).setOnClickListener(this);
        view.findViewById(R.id.login_gplus).setOnClickListener(this);
        view.findViewById(R.id.login_twitter).setOnClickListener(this);
        view.findViewById(R.id.login).setOnClickListener(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        mSimpleFacebook = SimpleFacebook.getInstance(getActivity());
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.login:
//                requestLogin();
                loginFb();
                break;
            case R.id.login_fb:
                loginFb();
                break;
            case R.id.login_gplus:
                mNavigationManager.showNewRecipes();
                getActivity().finish();
                break;
            case R.id.login_twitter:
                mNavigationManager.showNewRecipes();
                getActivity().finish();
                break;
        }
    }

    private void loginFb() {
        if (mSimpleFacebook.isLogin()) {
            getFbProfile();
        } else {
            mSimpleFacebook.login(onLoginListener);
        }
    }

    private void requestLogin() {
        showDialogLoading();
        if (mDfeLogin == null) {
            mDfeLogin = new DfeLogin(mApi);
            mDfeLogin.addErrorListener(this);
            mDfeLogin.addDataChangedListener(this);
        }
        mDfeLogin.makeRequest(FlavorLifeApplication.get().getUser().getEmail());
    }

    @Override
    public void onDataChanged() {
        hideDialogLoading();
        if (mDfeLogin != null && mDfeLogin.isReady()) {
            FlavorLifeApplication.get().updateIdUser(mDfeLogin.getUserId());
            mNavigationManager.showNewRecipes();
            getActivity().finish();
        }
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        hideDialogLoading();
        showDialogMessageError(error);
    }

//    @Override
//    public void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        mSimpleFacebook.onActivityResult(getActivity(), requestCode, resultCode, data);
//    }
}
