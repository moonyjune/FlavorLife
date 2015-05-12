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
import android.widget.TextView;

import com.android.volley.Cache;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.ntq.api.model.OnDataChangedListener;

import moony.vn.flavorlife.FlavorLifeApplication;
import moony.vn.flavorlife.R;
import moony.vn.flavorlife.activities.MainActivity;
import moony.vn.flavorlife.api.model.DfeCreateChapter;
import moony.vn.flavorlife.api.model.DfeEditChapter;
import moony.vn.flavorlife.entities.Chapter;
import moony.vn.flavorlife.utils.DialogUtils;
import moony.vn.flavorlife.utils.ToastUtils;
import moony.vn.flavorlife.utils.ValidateDataUtils;

/**
 * Created by moony on 5/12/15.
 */
public class CreateEditChapterDialogFragment extends DialogFragment implements OnDataChangedListener, Response.ErrorListener, View.OnClickListener {

    public static final String DATA = "data";
    public static final String CHAPTER = "chapter";
    public static final String OLD_CHAPTER = "old_chapter";
    public static final int FLAG_CREATE = 0;
    public static final int FLAG_EDIT = 1;
    private static final String FLAG = "flag";
    private EditText mName;
    private Chapter mChapter;
    private Chapter mOldChapter;
    private DfeCreateChapter mDfeCreateChapter;
    private ProgressDialog mProgressDialog;
    private int mFlag;
    private DfeEditChapter mDfeEditChapter;

    private OnDataChangedListener onEditChapterDataChanged = new OnDataChangedListener() {
        @Override
        public void onDataChanged() {
            hideDialogLoading();
            ToastUtils.showToastLong(getActivity(), "You've edited a chapter.");
            dismiss();
            getActivity().finish();
//            MainActivity.startMainActivity(getActivity(), MainActivity.EXTRA_OPEN_HOME);
        }
    };

    private TextView mNumChapter;

    public static CreateEditChapterDialogFragment newInstance(int flag, Chapter chapter) {
        CreateEditChapterDialogFragment createEditChapterDialogFragment = new CreateEditChapterDialogFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(FLAG, flag);
        bundle.putSerializable(CHAPTER, chapter);
        createEditChapterDialogFragment.setArguments(bundle);
        return createEditChapterDialogFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(STYLE_NO_TITLE, STYLE_NORMAL);
        if (savedInstanceState != null) {
            mChapter = (Chapter) savedInstanceState.getSerializable(CHAPTER);
            mOldChapter = (Chapter) savedInstanceState.getSerializable(OLD_CHAPTER);
            mFlag = savedInstanceState.getInt(FLAG);
        } else {
            mChapter = (Chapter) getArguments().getSerializable(CHAPTER);
            mOldChapter = (Chapter) getArguments().getSerializable(CHAPTER);
            mFlag = getArguments().getInt(FLAG);
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (mChapter != null) {
            outState.putSerializable(CHAPTER, mChapter);
        }
        outState.putInt(FLAG, mFlag);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.dialog_fragment_create_chapter, container, false);
    }

    @Override
    public void onResume() {
        super.onResume();
        int height = getResources().getDimensionPixelSize(R.dimen.dialog_create_chapter_height);
        int width = getResources().getDimensionPixelSize(R.dimen.dialog_create_chapter_width);
        getDialog().getWindow().setLayout(width, height);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mName = (EditText) view.findViewById(R.id.chapter_name);
        mNumChapter = (TextView) view.findViewById(R.id.chapter_num);
        view.findViewById(R.id.finish).setOnClickListener(this);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (mChapter != null) {
            if (mChapter.getNumChapter() < 10) {
                mNumChapter.setText("Chapter 0" + mChapter.getNumChapter());
            } else {
                mNumChapter.setText("Chapter " + mChapter.getNumChapter());
            }
            if (mFlag == FLAG_EDIT) {
                mName.setText(mChapter.getName());
                mChapter = new Chapter();
                mChapter.updateChapter(mOldChapter);
            }
        }
    }

    private void requestCreateChapter() {
        showDialogLoading();
        if (mDfeCreateChapter == null) {
            mDfeCreateChapter = new DfeCreateChapter(FlavorLifeApplication.get().getDfeApi());
            mDfeCreateChapter.addDataChangedListener(this);
            mDfeCreateChapter.addErrorListener(this);
        }
        mDfeCreateChapter.makeRequest(mChapter);
    }

    private void collectData() {
        if (mChapter == null)
            mChapter = new Chapter();
        mChapter.setName(mName.getText().toString());
    }

    private boolean checkChangeData() {
        if (!mChapter.getName().equals(mOldChapter.getName()))
            return true;
        return false;
    }

    private int validateChapter() {
        if (mChapter.getName() != null && !mChapter.getName().isEmpty()) {

        } else {
            return ValidateDataUtils.CHAPTER_NAME_EMPTY;
        }

        return ValidateDataUtils.VALID_DATA;
    }

    @Override
    public void onDataChanged() {
        hideDialogLoading();
        if (mDfeCreateChapter != null && mDfeCreateChapter.isReady()) {
            mChapter.setId(mDfeCreateChapter.getChapterId());
            Intent data = new Intent();
            Bundle bundle = new Bundle();
            bundle.putSerializable(CHAPTER, mChapter);
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
        int code = validateChapter();
        if (code == ValidateDataUtils.VALID_DATA) {
            switch (mFlag) {
                case FLAG_CREATE:
                    requestCreateChapter();
                    break;
                case FLAG_EDIT:

                    if (checkChangeData()) {
                        requestEditChapter();
                    } else {
                        DialogUtils.getInstance().showDialogMessageError(getActivity(), "You haven't change data yet...");
                    }
                    break;
            }
        } else {
            DialogUtils.getInstance().showDialogMessageError(getActivity(), ValidateDataUtils.get(getActivity(), code));
        }
    }

    private void requestEditChapter() {
        showDialogLoading();
        if (mDfeEditChapter == null) {
            mDfeEditChapter = new DfeEditChapter(FlavorLifeApplication.get().getDfeApi());
            mDfeEditChapter.addDataChangedListener(onEditChapterDataChanged);
            mDfeEditChapter.addErrorListener(this);
        }
        mDfeEditChapter.makeRequest(mChapter);
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
