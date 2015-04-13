package moony.vn.flavorlife.fragments;

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

import moony.vn.flavorlife.FlavorLifeApplication;
import moony.vn.flavorlife.R;
import moony.vn.flavorlife.activities.MainActivity;
import moony.vn.flavorlife.api.model.DfeLogin;

public class LoginFragment extends NFragment implements View.OnClickListener, OnDataChangedListener, Response.ErrorListener {
    private DfeLogin mDfeLogin;
    private EditText mEmail;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_login, container, false);
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
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.login:
                requestLogin();
                break;
            case R.id.login_fb:
                mNavigationManager.showNewRecipes();
                getActivity().finish();
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

    private void requestLogin() {
        if (mDfeLogin == null) {
            mDfeLogin = new DfeLogin(mApi);
            mDfeLogin.addErrorListener(this);
            mDfeLogin.addDataChangedListener(this);
        }
        mDfeLogin.makeRequest(mEmail.getText().toString().trim());
    }

    @Override
    public void onDataChanged() {
        if (mDfeLogin != null && mDfeLogin.isReady()) {
            FlavorLifeApplication.get().updateIdUser(mDfeLogin.getUserId());
            mNavigationManager.showNewRecipes();
            getActivity().finish();
        }
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        showDialogMessageError(error);
    }
}
