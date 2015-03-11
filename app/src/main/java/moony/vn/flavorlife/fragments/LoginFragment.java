package moony.vn.flavorlife.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ntq.fragments.NFragment;

import moony.vn.flavorlife.R;
import moony.vn.flavorlife.activities.MainActivity;

public class LoginFragment extends NFragment implements View.OnClickListener {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_login, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        view.findViewById(R.id.login_fb).setOnClickListener(this);
        view.findViewById(R.id.login_gplus).setOnClickListener(this);
        view.findViewById(R.id.login_twitter).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.login_fb:
                ((MainActivity) getActivity()).showMain();
                break;
            case R.id.login_gplus:
                ((MainActivity) getActivity()).showMain();
                break;
            case R.id.login_twitter:
                ((MainActivity) getActivity()).showMain();
                break;
        }
    }
}
