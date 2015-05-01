package moony.vn.flavorlife.fragments;

import android.os.Bundle;
import android.view.View;
import android.widget.ListAdapter;
import android.widget.ListView;

import moony.vn.flavorlife.R;
import moony.vn.flavorlife.api.model.DfeGetBookDetail;
import moony.vn.flavorlife.api.model.FlPaginatedList;

/**
 * Created by moony on 4/30/15.
 */
public class CookBookDetailFragment extends FlListFragment {
    private static final String KEY_BOOK_ID = "book_id";
    private int mBookId;
    private View mFooter;

    public static CookBookDetailFragment newInstance(int bookId) {
        CookBookDetailFragment cookBookDetailFragment = new CookBookDetailFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(KEY_BOOK_ID, bookId);
        cookBookDetailFragment.setArguments(bundle);
        return cookBookDetailFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState == null) {
            mBookId = getArguments().getInt(KEY_BOOK_ID);
        } else {
            mBookId = savedInstanceState.getInt(KEY_BOOK_ID);
        }
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
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
    }

    @Override
    protected FlPaginatedList getFlPaginatedList() {
        return new DfeGetBookDetail(mApi, mBookId);
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_book_detail;
    }
}
