package moony.vn.flavorlife.fragments;

import android.os.Bundle;
import android.view.View;
import android.widget.ListAdapter;
import android.widget.ListView;

import moony.vn.flavorlife.R;
import moony.vn.flavorlife.api.DfeGetChapterDetail;
import moony.vn.flavorlife.api.model.FlPaginatedList;

/**
 * Created by moony on 4/30/15.
 */
public class ChapterDetailFragment extends FlListFragment {
    private static final String KEY_CHAPTER_ID = "chapter_id";
    private int mChapterId;
    private View mFooter;

    public static ChapterDetailFragment newInstance(int chapterId) {
        ChapterDetailFragment chapterDetailFragment = new ChapterDetailFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(KEY_CHAPTER_ID, chapterId);
        chapterDetailFragment.setArguments(bundle);
        return chapterDetailFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState == null) {
            mChapterId = getArguments().getInt(KEY_CHAPTER_ID);
        } else {
            mChapterId = savedInstanceState.getInt(KEY_CHAPTER_ID);
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(KEY_CHAPTER_ID, mChapterId);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
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
    }

    @Override
    protected FlPaginatedList getFlPaginatedList() {
        return new DfeGetChapterDetail(mApi, mChapterId);
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_chapter_detail;
    }
}
