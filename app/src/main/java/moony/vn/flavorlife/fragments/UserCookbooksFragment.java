package moony.vn.flavorlife.fragments;

import android.os.Bundle;
import android.view.View;
import android.widget.ListAdapter;
import android.widget.ListView;

import moony.vn.flavorlife.R;
import moony.vn.flavorlife.api.model.DfeGetCookbooks;
import moony.vn.flavorlife.api.model.FlPaginatedList;

/**
 * Created by moony on 3/11/15.
 */
public class UserCookbooksFragment extends FlListFragment {
    private static final String USER_ID = "user_id";
    private int mUserId;
    private View mFooter;

    public static UserCookbooksFragment newInstance(int user_id) {
        UserCookbooksFragment userCookbooksFragment = new UserCookbooksFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(USER_ID, user_id);
        userCookbooksFragment.setArguments(bundle);
        return userCookbooksFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null) {
            mUserId = savedInstanceState.getInt(USER_ID);
        } else {
            mUserId = getArguments().getInt(USER_ID);
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(USER_ID, mUserId);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ListView listView = getListView();
        ListAdapter listAdapter = listView.getAdapter();
        if (mFooter == null) {
            mFooter = getActivity().getLayoutInflater().inflate(R.layout.footer_add_book, listView, false);
        }

        if (listAdapter != null) {
            //must remove Adapter before addHeaderView
            listView.setAdapter(null);
            listView.addFooterView(mFooter);
            listView.setAdapter(listAdapter);
        } else {
            listView.addFooterView(mFooter);
        }
    }

    @Override
    protected FlPaginatedList getFlPaginatedList() {
        return new DfeGetCookbooks(mApi);
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_user_cookbooks;
    }
}
