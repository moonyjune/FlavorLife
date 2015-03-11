package moony.vn.flavorlife.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ntq.fragments.NFragmentSwitcher;

import moony.vn.flavorlife.R;

/**
 * Created by moony on 3/1/15.
 */
public class MessagesFragment extends TabRootDefaultFragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_messages, container, false);
    }

    @Override
    public int getTabRootId() {
        return R.layout.tab_messages;
    }
}
