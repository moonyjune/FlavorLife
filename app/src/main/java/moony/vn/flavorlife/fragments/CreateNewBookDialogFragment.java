package moony.vn.flavorlife.fragments;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.facebook.internal.Validate;
import com.ntq.api.model.OnDataChangedListener;

import moony.vn.flavorlife.FlavorLifeApplication;
import moony.vn.flavorlife.R;
import moony.vn.flavorlife.api.model.DfeCreateBook;
import moony.vn.flavorlife.entities.Cookbook;
import moony.vn.flavorlife.utils.DialogUtils;
import moony.vn.flavorlife.utils.ValidateDataUtils;

/**
 * Created by moony on 5/12/15.
 */
public class CreateNewBookDialogFragment extends DialogFragment implements OnDataChangedListener, Response.ErrorListener, View.OnClickListener {

    public static final String DATA = "data";
    public static final String BOOK = "book";
    private EditText mName;
    private EditText mIntro;
    private Cookbook mBook;
    private DfeCreateBook mDfeCreateBook;
    private ProgressDialog mProgressDialog;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(STYLE_NO_TITLE, STYLE_NORMAL);
        if (savedInstanceState != null) {
            mBook = (Cookbook) savedInstanceState.getSerializable(BOOK);
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (mBook != null) {
            outState.putSerializable(BOOK, mBook);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.dialog_fragment_create_book, container, false);
    }

    @Override
    public void onResume() {
        super.onResume();
        int height = getResources().getDimensionPixelSize(R.dimen.dialog_create_book_height);
        int width = getResources().getDimensionPixelSize(R.dimen.dialog_create_book_width);
        getDialog().getWindow().setLayout(width, height);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mName = (EditText) view.findViewById(R.id.book_name);
        mIntro = (EditText) view.findViewById(R.id.book_intro);
        view.findViewById(R.id.finish).setOnClickListener(this);
    }

    private void requestCreateBook() {
        showDialogLoading();
        if (mDfeCreateBook == null) {
            mDfeCreateBook = new DfeCreateBook(FlavorLifeApplication.get().getDfeApi());
            mDfeCreateBook.addDataChangedListener(this);
            mDfeCreateBook.addErrorListener(this);
        }
        mDfeCreateBook.makeRequest(mBook);
    }

    private void collectData() {
        if (mBook == null)
            mBook = new Cookbook();
        mBook.setName(mName.getText().toString());
        mBook.setIntro(mIntro.getText().toString());
    }

    private int validateBook() {
        if (mBook.getName() != null && !mBook.getName().isEmpty()) {

        } else {
            return ValidateDataUtils.BOOK_NAME_EMPTY;
        }

        if (mBook.getIntro() != null && !mBook.getIntro().isEmpty()) {

        } else {
            return ValidateDataUtils.BOOK_INTRO_EMPTY;
        }

        return ValidateDataUtils.VALID_DATA;
    }

    @Override
    public void onDataChanged() {
        hideDialogLoading();
        if (mDfeCreateBook != null && mDfeCreateBook.isReady()) {
            mBook.setId(mDfeCreateBook.getBookId());
            Intent data = new Intent();
            Bundle bundle = new Bundle();
            bundle.putSerializable(BOOK, mBook);
            data.putExtra(DATA, bundle);
            getTargetFragment().onActivityResult(getTargetRequestCode(), Activity.RESULT_OK, data);
            dismiss();
        }
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        hideDialogLoading();
        DialogUtils.getInstance().showDialogMessageError(getActivity(), error);
    }

    @Override
    public void onClick(View view) {
        collectData();
        int code = validateBook();
        if (code == ValidateDataUtils.VALID_DATA) {
            requestCreateBook();
        } else {
            DialogUtils.getInstance().showDialogMessageError(getActivity(), ValidateDataUtils.get(getActivity(), code));
        }
    }

    public void showDialogLoading() {
        if (mProgressDialog == null)
            mProgressDialog = new ProgressDialog(getActivity());
        mProgressDialog.setMessage(getString(R.string.waiting));
        mProgressDialog.setCanceledOnTouchOutside(true);
        mProgressDialog.show();
    }

    public void hideDialogLoading() {
        if (mProgressDialog != null)
            mProgressDialog.dismiss();
    }
}
