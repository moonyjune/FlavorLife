package moony.vn.flavorlife.fragments;

import android.app.Activity;
import android.content.Intent;
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
import moony.vn.flavorlife.entities.Chapter;
import moony.vn.flavorlife.entities.Cookbook;

/**
 * Created by moony on 4/30/15.
 */
public class CookBookDetailFragment extends FlListFragment {
    private static final String KEY_BOOK = "book";
    private static final String KEY_USER_ID = "user_id";
    private static final int REQUEST_CREATE_CHAPTER = 8022;
    private Cookbook mBook;
    private int mUserId;
    private View mFooter;
    private TextView mNoData;
    private DfeGetBookDetail mDfeGetBookDetail;

    public static CookBookDetailFragment newInstance(int userId, Cookbook book) {
        CookBookDetailFragment cookBookDetailFragment = new CookBookDetailFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable(KEY_BOOK, book);
        bundle.putInt(KEY_USER_ID, userId);
        cookBookDetailFragment.setArguments(bundle);
        return cookBookDetailFragment;
    }

    public static CookBookDetailFragment newInstance(int userId, int bookId) {
        CookBookDetailFragment cookBookDetailFragment = new CookBookDetailFragment();
        Bundle bundle = new Bundle();
        Cookbook book = new Cookbook();
        book.setId(bookId);
        bundle.putSerializable(KEY_BOOK, book);
        bundle.putInt(KEY_USER_ID, userId);
        cookBookDetailFragment.setArguments(bundle);
        return cookBookDetailFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState == null) {
            mBook = (Cookbook) getArguments().getSerializable(KEY_BOOK);
            mUserId = getArguments().getInt(KEY_USER_ID);
        } else {
            mBook = (Cookbook) savedInstanceState.getSerializable(KEY_BOOK);
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
        if (mBook != null)
            outState.putSerializable(KEY_BOOK, mBook);
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
                mFooter.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Chapter chapter = new Chapter();
                        chapter.setBookId(mBook.getId());
                        chapter.setNumChapter(mDfeGetBookDetail.getCount() +1);
                        CreateEditChapterDialogFragment createEditChapterDialogFragment = CreateEditChapterDialogFragment.newInstance(CreateEditChapterDialogFragment.FLAG_CREATE, chapter);
                        createEditChapterDialogFragment.setTargetFragment(CookBookDetailFragment.this, REQUEST_CREATE_CHAPTER);
                        createEditChapterDialogFragment.show(getFragmentManager(), null);
                    }
                });
            }
            mNoData.setVisibility(View.GONE);
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
        return mDfeGetBookDetail = new DfeGetBookDetail(mApi, mUserId, mBook.getId());
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
        return R.layout.fragment_book_detail;
    }

    private boolean isOwner() {
        if (mUserId == FlavorLifeApplication.get().getUser().getId()) {
            return true;
        } else {
            return false;
        }
    }

    public String getTitle() {
        if (mBook != null)
            return mBook.getName();
        return null;
    }

    public void editCookbook() {
        mNavigationManager.showPage(CreateEditBook.newInstance(CreateEditBook.FLAG_EDIT, mBook));
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CREATE_CHAPTER) {
            if (resultCode == Activity.RESULT_OK && data != null) {
                Bundle bundle = data.getBundleExtra(CreateNewBookDialogFragment.DATA);
                if (bundle != null) {
                    Chapter chapter = (Chapter) bundle.getSerializable(CreateEditChapterDialogFragment.CHAPTER);
                    onRefresh();
                }
            }
        }
    }

}
