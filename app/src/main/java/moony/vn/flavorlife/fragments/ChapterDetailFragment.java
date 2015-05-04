package moony.vn.flavorlife.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import moony.vn.flavorlife.FlavorLifeApplication;
import moony.vn.flavorlife.R;
import moony.vn.flavorlife.api.DfeGetChapterDetail;
import moony.vn.flavorlife.api.model.FlPaginatedList;

/**
 * Created by moony on 4/30/15.
 */
public class ChapterDetailFragment extends FlListFragment {
    private static final String KEY_CHAPTER_ID = "chapter_id";
    private static final String KEY_USER_ID = "user_id";
    private int mChapterId;
    private int mUserId;
    private View mFooter;
    private TextView mNoData;
    private DfeGetChapterDetail mDfeGetChapterDetail;

    public static ChapterDetailFragment newInstance(int userId, int chapterId) {
        ChapterDetailFragment chapterDetailFragment = new ChapterDetailFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(KEY_USER_ID, userId);
        bundle.putInt(KEY_CHAPTER_ID, chapterId);
        chapterDetailFragment.setArguments(bundle);
        return chapterDetailFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState == null) {
            mChapterId = getArguments().getInt(KEY_CHAPTER_ID);
            mUserId = getArguments().getInt(KEY_USER_ID);
        } else {
            mChapterId = savedInstanceState.getInt(KEY_CHAPTER_ID);
            mUserId = savedInstanceState.getInt(KEY_USER_ID);
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(KEY_CHAPTER_ID, mChapterId);
        outState.putInt(KEY_USER_ID, mUserId);
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
            ListView listView = getListView();
            ListAdapter listAdapter = listView.getAdapter();
            if (mFooter == null) {
                mFooter = getActivity().getLayoutInflater().inflate(R.layout.footer_add_recipe, listView, false);
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
        if (mDfeGetChapterDetail != null && mDfeGetChapterDetail.isReady() && mDfeGetChapterDetail.getCount() == 0) {
            mNoData.setVisibility(View.VISIBLE);
        } else {
            mNoData.setVisibility(View.GONE);
        }
    }

    @Override
    public void onDataChanged() {
        super.onDataChanged();
        if (!isOwner())
            syncNoDataView();
    }

    @Override
    protected FlPaginatedList getFlPaginatedList() {
        return mDfeGetChapterDetail = new DfeGetChapterDetail(mApi, mUserId, mChapterId);
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_chapter_detail;
    }

    private boolean isOwner() {
        if (mUserId == FlavorLifeApplication.get().getUser().getId()) {
            return true;
        } else {
            return false;
        }
    }

}
