package moony.vn.flavorlife.fragments;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ntq.fragments.NFragment;

import moony.vn.flavorlife.R;

/**
 * Created by moony on 4/29/15.
 */
public class ComingSoonFragment extends NFragment {
    private TextView mComingSoon;
    private int countTime;
    private Handler handler;

    public static ComingSoonFragment newInstance() {
        ComingSoonFragment comingSoonFragment = new ComingSoonFragment();
        Bundle bundle = new Bundle();
        comingSoonFragment.setArguments(bundle);
        return comingSoonFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_coming_soon, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mComingSoon = (TextView) view.findViewById(R.id.coming_soon);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mActionbar.syncActionBar(this);
        handler = new Handler();
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                countTime++;
                if (countTime % 3 == 0) {
                    mComingSoon.setText("Coming soon...");
                } else if (countTime % 3 == 1) {
                    mComingSoon.setText("Coming soon.");
                } else {
                    mComingSoon.setText("Coming soon..");
                }
                handler.postDelayed(this, 500);
            }
        };
        handler.postDelayed(runnable, 0);
    }

}
