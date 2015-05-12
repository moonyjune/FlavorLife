package moony.vn.flavorlife.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import moony.vn.flavorlife.FlavorLifeApplication;
import moony.vn.flavorlife.R;
import moony.vn.flavorlife.api.model.DfeGetBookDetail;
import moony.vn.flavorlife.api.model.DfeGetCookbooks;
import moony.vn.flavorlife.api.model.FlPaginatedList;

/**
 * Created by moony on 3/11/15.
 */
public class UserCookbooksFragment extends FlListFragment {
    private static final String USER_ID = "user_id";
    private int mUserId;
    private View mFooter;
    private TextView mNoData;
    private DfeGetCookbooks mDfeGetCookbooks;

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
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mNoData = (TextView) view.findViewById(R.id.footer_no_data);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (isOwner()) {
            mNoData.setVisibility(View.GONE);
            ListView listView = getListView();
            ListAdapter listAdapter = listView.getAdapter();
            if (mFooter == null) {
                mFooter = getActivity().getLayoutInflater().inflate(R.layout.footer_add_book, listView, false);
            }
            mFooter.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mNavigationManager.showPage(CreateEditBook.newInstance(CreateEditBook.FLAG_CREATE));
                }
            });

            if (listAdapter != null) {
                //must remove Adapter before addHeaderView
                listView.setAdapter(null);
                listView.addFooterView(mFooter);
                listView.setAdapter(listAdapter);
            } else {
                listView.addFooterView(mFooter);
            }
        } else {
            syncNoDataView();
        }

    }

    private void syncNoDataView() {
        if (mDfeGetCookbooks != null && mDfeGetCookbooks.isReady() && mDfeGetCookbooks.getCount() == 0) {
            mNoData.setVisibility(View.VISIBLE);
        } else {
            mNoData.setVisibility(View.GONE);
        }
    }

    @Override
    protected FlPaginatedList getFlPaginatedList() {
        return mDfeGetCookbooks = new DfeGetCookbooks(mApi, mUserId);
    }

    @Override
    public void onDataChanged() {
        super.onDataChanged();
        if (!isOwner())
            syncNoDataView();
        else
            mNoData.setVisibility(View.GONE);
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_user_cookbooks;
    }

    private boolean isOwner() {
        if (mUserId == FlavorLifeApplication.get().getUser().getId()) {
            return true;
        } else {
            return false;
        }
    }
}
