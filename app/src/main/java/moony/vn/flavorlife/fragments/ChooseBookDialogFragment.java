package moony.vn.flavorlife.fragments;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.ntq.api.model.OnDataChangedListener;
import com.ntq.utils.OSUtils;
import com.ntq.utils.Utils;

import java.util.ArrayList;
import java.util.List;

import moony.vn.flavorlife.FlavorLifeApplication;
import moony.vn.flavorlife.R;
import moony.vn.flavorlife.adapters.BookAdapter;
import moony.vn.flavorlife.api.model.DfeGetUserCookbooks;
import moony.vn.flavorlife.entities.Cookbook;

/**
 * Created by moony on 4/30/15.
 */
public class ChooseBookDialogFragment extends DialogFragment implements OnDataChangedListener, Response.ErrorListener, View.OnClickListener {
    public static final String KEY_BOOK = "book";
    public static final String KEY_DATA = "data";
    private static final int REQUEST_CREATE_BOOK = 9898;
    private ListView mListBooks;
    private BookAdapter mBookAdapter;
    private View mLayoutContent, mLayoutRetry;
    private View mLayoutLoading;
    private TextView mError;
    private Button mRetry;
    private DfeGetUserCookbooks mDfeGetUserCookbooks;
    private List<Cookbook> mListCookbooks;
    private View mFooter;
    private Cookbook mCookbook;

    public static ChooseBookDialogFragment newInstance(Cookbook cookbook) {
        ChooseBookDialogFragment chooseBookDialogFragment = new ChooseBookDialogFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable(KEY_BOOK, cookbook);
        chooseBookDialogFragment.setArguments(bundle);
        return chooseBookDialogFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(STYLE_NO_TITLE, STYLE_NORMAL);
        if (savedInstanceState == null) {
            mCookbook = (Cookbook) getArguments().getSerializable(KEY_BOOK);
        } else {
            mCookbook = (Cookbook) savedInstanceState.getSerializable(KEY_BOOK);
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (mCookbook != null)
            outState.putSerializable(KEY_BOOK, mCookbook);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.dialog_fragment_choose_book, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mListBooks = (ListView) view.findViewById(R.id.list);
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
        if (mListCookbooks == null)
            mListCookbooks = new ArrayList<Cookbook>();
        if (mBookAdapter == null)
            mBookAdapter = new BookAdapter(getActivity(), 0, mListCookbooks);
        if (mFooter == null) {
            mFooter = getActivity().getLayoutInflater().inflate(R.layout.footer_add_book, mListBooks, false);
            mFooter.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    CreateNewBookDialogFragment createNewBookDialogFragment = new CreateNewBookDialogFragment();
                    createNewBookDialogFragment.setTargetFragment(ChooseBookDialogFragment.this, REQUEST_CREATE_BOOK);
                    createNewBookDialogFragment.show(getChildFragmentManager(), null);
                }
            });
        }
        if (mListBooks.getFooterViewsCount() == 0) {
            mListBooks.setAdapter(null);
            mListBooks.addFooterView(mFooter);
        }
        mListBooks.setAdapter(mBookAdapter);
        if (mDfeGetUserCookbooks != null && mDfeGetUserCookbooks.isReady()) {
            switchToContent();
            mListCookbooks.addAll(mDfeGetUserCookbooks.getListCookbooks());
            mBookAdapter.notifyDataSetChanged();
        } else {
            switchToLoading();
            requestData();
        }

        mListBooks.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent data = new Intent();
                Bundle bundle = new Bundle();
                bundle.putSerializable(KEY_BOOK, mListCookbooks.get(i));
                mListCookbooks.get(i).setChosen(true);
                data.putExtra(KEY_DATA, bundle);
                getTargetFragment().onActivityResult(IntroductionFragment.REQUEST_BOOK, Activity.RESULT_OK, data);
                dismiss();
            }
        });
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
        switchToLoading();
        if (mDfeGetUserCookbooks == null) {
            mDfeGetUserCookbooks = new DfeGetUserCookbooks(FlavorLifeApplication.get().getDfeApi(), FlavorLifeApplication.get().getUser().getId());
            mDfeGetUserCookbooks.addDataChangedListener(this);
            mDfeGetUserCookbooks.addErrorListener(this);
        }
        mDfeGetUserCookbooks.makeRequest();
    }

    @Override
    public void onDataChanged() {
        switchToContent();
        if (mDfeGetUserCookbooks.isReady()) {
            mListCookbooks.addAll(mDfeGetUserCookbooks.getListCookbooks());
            if (mCookbook != null) {
                for (int i = 0; i < mListCookbooks.size(); i++) {
                    if (mListCookbooks.get(i).getId() == mCookbook.getId()) {
                        mListCookbooks.get(i).setChosen(true);
                    } else {
                        mListCookbooks.get(i).setChosen(false);
                    }
                }
            }
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

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CREATE_BOOK) {
            if (resultCode == Activity.RESULT_OK && data != null) {
                Bundle bundle = data.getBundleExtra(CreateNewBookDialogFragment.DATA);
                if (bundle != null) {
                    Cookbook cookbook = (Cookbook) bundle.getSerializable(CreateNewBookDialogFragment.BOOK);
                    mBookAdapter.addItem(cookbook);
                }
            }
        }
    }

}
