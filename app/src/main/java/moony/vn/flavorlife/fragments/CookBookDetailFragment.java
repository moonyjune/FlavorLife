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
import moony.vn.flavorlife.api.model.FlPaginatedList;

/**
 * Created by moony on 4/30/15.
 */
public class CookBookDetailFragment extends FlListFragment {
    private static final String KEY_BOOK_ID = "book_id";
    private static final String KEY_USER_ID = "user_id";
    private int mBookId;
    private int mUserId;
    private View mFooter;
    private TextView mNoData;
    private DfeGetBookDetail mDfeGetBookDetail;

    public static CookBookDetailFragment newInstance(int userId, int bookId) {
        CookBookDetailFragment cookBookDetailFragment = new CookBookDetailFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(KEY_BOOK_ID, bookId);
        bundle.putInt(KEY_USER_ID, userId);
        cookBookDetailFragment.setArguments(bundle);
        return cookBookDetailFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState == null) {
            mBookId = getArguments().getInt(KEY_BOOK_ID);
            mUserId = getArguments().getInt(KEY_USER_ID);
        } else {
            mBookId = savedInstanceState.getInt(KEY_BOOK_ID);
            mUserId = savedInstanceState.getInt(KEY_USER_ID);
        }
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mNoData = (TextView) view.findViewById(R.id.footer_no_data);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(KEY_BOOK_ID, mBookId);
        outState.putInt(KEY_USER_ID, mUserId);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (isOwner()) {
            ListView listView = getListView();
            ListAdapter listAdapter = listView.getAdapter();
            if (mFooter == null) {
                mFooter = getActivity().getLayoutInflater().inflate(R.layout.footer_add_chapter, listView, false);
            }

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
        if (mDfeGetBookDetail != null && mDfeGetBookDetail.isReady() && mDfeGetBookDetail.getCount() == 0) {
            mNoData.setVisibility(View.VISIBLE);
        } else {
            mNoData.setVisibility(View.GONE);
        }
    }

    @Override
    protected FlPaginatedList getFlPaginatedList() {
        return mDfeGetBookDetail = new DfeGetBookDetail(mApi, mUserId, mBookId);
    }

    @Override
    public void onDataChanged() {
        super.onDataChanged();
        if (!isOwner())
            syncNoDataView();
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_book_detail;
    }

    private boolean isOwner() {
        if (mUserId == FlavorLifeApplication.get().getUser().getId()) {
            return true;
        } else {
            return false;
        }
    }
}
