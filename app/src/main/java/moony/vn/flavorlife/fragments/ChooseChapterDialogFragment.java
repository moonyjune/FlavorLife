package moony.vn.flavorlife.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.ntq.api.model.OnDataChangedListener;

import java.util.ArrayList;
import java.util.List;

import moony.vn.flavorlife.FlavorLifeApplication;
import moony.vn.flavorlife.R;
import moony.vn.flavorlife.adapters.ChapterAdapter;
import moony.vn.flavorlife.api.model.DfeGetUserChapter;
import moony.vn.flavorlife.api.model.DfeGetUserCookbooks;
import moony.vn.flavorlife.entities.Chapter;

/**
 * Created by moony on 4/30/15.
 */
public class ChooseChapterDialogFragment extends DialogFragment implements OnDataChangedListener, Response.ErrorListener, View.OnClickListener {
    private static final String KEY_BOOK_ID = "book_id";
    private ListView mListViewChapters;
    private ChapterAdapter mBookAdapter;
    private View mLayoutContent, mLayoutRetry;
    private View mLayoutLoading;
    private TextView mError;
    private Button mRetry;
    private DfeGetUserChapter mDfeGetUserChapters;
    private List<Chapter> mListChapters;
    private View mFooter;
    private int mBookId;

    public static ChooseChapterDialogFragment newInstance(int bookId) {
        ChooseChapterDialogFragment chooseChapterDialogFragment = new ChooseChapterDialogFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(KEY_BOOK_ID, bookId);
        chooseChapterDialogFragment.setArguments(bundle);
        return chooseChapterDialogFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(STYLE_NO_TITLE, STYLE_NORMAL);
        if (savedInstanceState != null) {
            mBookId = savedInstanceState.getInt(KEY_BOOK_ID);
        } else {
            mBookId = getArguments().getInt(KEY_BOOK_ID);
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(KEY_BOOK_ID, mBookId);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.dialog_fragment_choose_book, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mListViewChapters = (ListView) view.findViewById(R.id.list);
        mLayoutContent = view.findViewById(R.id.dialog_content);
        mLayoutRetry = view.findViewById(R.id.layout_retry);
        mError = (TextView) view.findViewById(R.id.error_message);
        mRetry = (Button) view.findViewById(R.id.retry_button);
        mLayoutLoading = view.findViewById(R.id.layout_loading);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mRetry.setOnClickListener(this);
        if (mListChapters == null)
            mListChapters = new ArrayList<Chapter>();
        if (mBookAdapter == null)
            mBookAdapter = new ChapterAdapter(getActivity(), 0, mListChapters);
        if (mFooter == null) {
            mFooter = getActivity().getLayoutInflater().inflate(R.layout.footer_add_chapter, mListViewChapters, false);
        }
        if (mListViewChapters.getFooterViewsCount() == 0) {
            mListViewChapters.setAdapter(null);
            mListViewChapters.addFooterView(mFooter);
        }
        mListViewChapters.setAdapter(mBookAdapter);
        if (mDfeGetUserChapters != null && mDfeGetUserChapters.isReady()) {
            switchToContent();
            mListChapters.addAll(mDfeGetUserChapters.getListChapters());
            mBookAdapter.notifyDataSetChanged();
        } else {
            switchToLoading();
            requestData();
        }

    }

    @Override
    public void onResume() {
        super.onResume();
        int height = getResources().getDimensionPixelSize(R.dimen.dialog_height);
        int width = getResources().getDimensionPixelSize(R.dimen.dialog_width);
        getDialog().getWindow().setLayout(width, height);
    }

    private void switchToContent() {
        mLayoutRetry.setVisibility(View.GONE);
        mLayoutContent.setVisibility(View.VISIBLE);
        mLayoutLoading.setVisibility(View.GONE);
    }

    private void switchToLoading() {
        mLayoutLoading.setVisibility(View.VISIBLE);
        mLayoutRetry.setVisibility(View.GONE);
        mLayoutContent.setVisibility(View.GONE);
    }

    private void switchToRetry() {
        mLayoutContent.setVisibility(View.GONE);
        mLayoutLoading.setVisibility(View.GONE);
        mLayoutRetry.setVisibility(View.VISIBLE);
    }

    private void requestData() {
        if (mDfeGetUserChapters == null) {
            mDfeGetUserChapters = new DfeGetUserChapter(FlavorLifeApplication.get().getDfeApi());
            mDfeGetUserChapters.addDataChangedListener(this);
            mDfeGetUserChapters.addErrorListener(this);
        }
        mDfeGetUserChapters.makeRequest(mBookId);
    }

    @Override
    public void onDataChanged() {
        if (mDfeGetUserChapters.isReady()) {
            switchToContent();
            mListChapters.addAll(mDfeGetUserChapters.getListChapters());
            mBookAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        switchToRetry();
//        mError.setText(ErrorStrings.get(getActivity(), error));
        mError.setText("Error");
    }

    @Override
    public void onClick(View view) {
        switchToLoading();
        requestData();
    }
}
