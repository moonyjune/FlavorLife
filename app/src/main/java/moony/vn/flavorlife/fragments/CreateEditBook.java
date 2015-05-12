package moony.vn.flavorlife.fragments;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.ntq.api.model.OnDataChangedListener;
import com.ntq.fragments.NFragment;
import com.ntq.mediapicker.NMediaItem;
import com.ntq.mediapicker.NMediaOptions;
import com.ntq.mediapicker.NMediaPickerActivity;

import org.json.JSONObject;

import java.util.ArrayList;

import moony.vn.flavorlife.FlavorLifeApplication;
import moony.vn.flavorlife.R;
import moony.vn.flavorlife.api.ApiKey;
import moony.vn.flavorlife.api.UploadImage;
import moony.vn.flavorlife.api.model.DfeCreateBook;
import moony.vn.flavorlife.api.model.DfeEditBook;
import moony.vn.flavorlife.entities.Cookbook;
import moony.vn.flavorlife.utils.DialogUtils;
import moony.vn.flavorlife.utils.ValidateDataUtils;

/**
 * Created by moony on 5/12/15.
 */
public class CreateEditBook extends NFragment implements View.OnClickListener {
    private static final String KEY_IMAGE_URI = "image_uri";
    private static final String FLAG = "flag";
    private static final String BOOK = "book";
    private static final String OLD_BOOK = "old_book";
    public static final int FLAG_CREATE = 0;
    public static final int FLAG_EDIT = 1;
    private int mFlag;
    private Cookbook mBook;
    private Cookbook mOldBook;
    private EditText mName, mIntro;
    private static final int REQUEST_IMAGE = 10000;
    private View mLayoutChangeImage;
    private View mAddBookCover;
    private ImageView mBookImage;
    private Uri mImageURI;
    private String mUriString;
    private DfeCreateBook mDfeCreateBook;
    private DfeEditBook mDfeEditBook;

    private OnDataChangedListener onCreateBookDataChanged = new OnDataChangedListener() {
        @Override
        public void onDataChanged() {
            hideDialogLoading();
            if (mDfeCreateBook != null && mDfeCreateBook.isReady())
                uploadImage();
        }
    };
    private OnDataChangedListener onEditBookDataChanged = new OnDataChangedListener() {
        @Override
        public void onDataChanged() {
            hideDialogLoading();
            if (mDfeEditBook != null && mDfeEditBook.isReady())
                if (checkChangeImage()) {
                    uploadImage();
                } else {
                    Toast.makeText(getActivity(), "Edit book successfully!", Toast.LENGTH_SHORT).show();
                    getActivity().finish();
                }
        }
    };

    private Response.ErrorListener onErrorListener = new Response.ErrorListener() {
        @Override
        public void onErrorResponse(VolleyError error) {
            hideDialogLoading();
            showDialogMessageError(error);
        }
    };

    public static CreateEditBook newInstance(int flag) {
        CreateEditBook createEditBook = new CreateEditBook();
        Bundle bundle = new Bundle();
        bundle.putInt(FLAG, flag);
        createEditBook.setArguments(bundle);
        return createEditBook;
    }

    public static CreateEditBook newInstance(int flag, Cookbook book) {
        CreateEditBook createEditBook = new CreateEditBook();
        Bundle bundle = new Bundle();
        bundle.putInt(FLAG, flag);
        bundle.putSerializable(BOOK, book);
        createEditBook.setArguments(bundle);
        return createEditBook;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState == null) {
            mFlag = getArguments().getInt(FLAG);
            mBook = (Cookbook) getArguments().getSerializable(BOOK);
            mOldBook = (Cookbook) getArguments().getSerializable(BOOK);
        } else {
            mUriString = (String) savedInstanceState.get(KEY_IMAGE_URI);
            mFlag = savedInstanceState.getInt(FLAG);
            mBook = (Cookbook) savedInstanceState.getSerializable(BOOK);
            mOldBook = (Cookbook) savedInstanceState.getSerializable(BOOK);
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (mBook != null)
            outState.putSerializable(BOOK, mBook);
        if (mOldBook != null)
            outState.putSerializable(OLD_BOOK, mOldBook);
        if (mImageURI != null)
            outState.putString(KEY_IMAGE_URI, mImageURI.toString());
        outState.putInt(FLAG, mFlag);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_create_edit_book, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mName = (EditText) view.findViewById(R.id.book_name);
        mIntro = (EditText) view.findViewById(R.id.book_intro);
        mAddBookCover = view.findViewById(R.id.add_photo);
        mLayoutChangeImage = view.findViewById(R.id.layout_change_picture);
        mBookImage = (ImageView) view.findViewById(R.id.book_image);
        view.findViewById(R.id.choose_picture).setOnClickListener(this);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (mBook != null) {
            mName.setText(mBook.getName());
            mIntro.setText(mBook.getIntro());
            if (mBook.getImage() != null && !mBook.getImage().isEmpty()) {
                mAddBookCover.setVisibility(View.GONE);
                mLayoutChangeImage.setVisibility(View.VISIBLE);
                mImageLoader.display(mBook.getImage(), mBookImage);
            } else {
                mAddBookCover.setVisibility(View.VISIBLE);
                mLayoutChangeImage.setVisibility(View.GONE);
            }
            mBook = new Cookbook();
            mBook.update(mOldBook);
        }
        mAddBookCover.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.add_photo:
            case R.id.choose_picture:
                NMediaOptions.Builder builder = new NMediaOptions.Builder();
                NMediaOptions options = NMediaOptions.createDefault();
                options = builder.setIsCropped(true).setFixAspectRatio(true).setCircleCrop(false)
                        .build();
                NMediaPickerActivity.open(mNavigationManager.getActivePage(),
                        REQUEST_IMAGE, options);
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        ArrayList<NMediaItem> mMediaSelectedList;
        switch (requestCode) {
            case REQUEST_IMAGE:
                if (resultCode == Activity.RESULT_OK) {
                    mLayoutChangeImage.setVisibility(View.VISIBLE);
                    mAddBookCover.setVisibility(View.GONE);
                    mMediaSelectedList = NMediaPickerActivity
                            .getNMediaItemSelected(data);
                    if (mMediaSelectedList != null) {

                        StringBuilder builder = new StringBuilder();
                        for (NMediaItem mediaItem : mMediaSelectedList) {
                            builder.append(mediaItem.toString());
                            builder.append(", PathOrigin=");
                            builder.append(mediaItem.getPathOrigin(getActivity()));
                            builder.append(", PathCropped=");
                            builder.append(mediaItem.getPathCropped(getActivity()));
                            builder.append("\n\n");

                            addImages(mediaItem);
                            if (mFlag == FLAG_CREATE) {
                                if (mBook == null)
                                    mBook = new Cookbook();
                            }
                            mBook.setImage(mediaItem.getPathCropped(getActivity()));
                        }
                    } else {
                    }
                } else {
                }
                break;
        }
    }

    private void addImages(NMediaItem mediaItem) {
        if (mediaItem.getUriCropped() == null) {
            mImageURI = mediaItem.getUriOrigin();
            mImageLoader.display(mediaItem.getUriOrigin(), mBookImage);
        } else {
            mImageURI = mediaItem.getUriCropped();
            mImageLoader.display(mediaItem.getUriCropped(), mBookImage);
        }
    }

    private void collectData() {
        mBook.setName(mName.getText().toString());
        mBook.setIntro(mIntro.getText().toString());
    }


    private boolean hasData() {
        return false;
    }

    private boolean checkChangeData() {
        if (!mBook.getName().equals(mOldBook.getName()))
            return true;
        if (!mBook.getIntro().equals(mOldBook.getIntro()))
            return true;
        return false;
    }

    private boolean checkChangeImage() {
        if (mBook.getImage() == null) {
            return false;
        } else {
            if (mOldBook.getImage() == null) {
                return true;
            } else {
                if (!mBook.getImage().equals(mOldBook.getImage())) {
                    return true;
                }
            }
        }
        return false;
    }

    public void finish() {
        collectData();
        int code = validateBook();
        if (code == ValidateDataUtils.VALID_DATA) {
            switch (mFlag) {
                case CreateRecipeFragment.FLAG_CREATE:
                    requestCreateBook();
                    break;
                case CreateRecipeFragment.FLAG_EDIT:
                    if (checkChangeData()) {
                        requestEditBook();
                    } else {
                        if (checkChangeImage()) {
                            uploadImage();
                        } else {
                            showDialogMessageError("You haven't changed anything yet...");
                        }
                    }
                    break;
            }
        } else {
            showDialogMessageError(ValidateDataUtils.get(mContext, code));
        }
    }

    private void requestCreateBook() {
        showDialogLoading();
        if (mDfeCreateBook == null) {
            mDfeCreateBook = new DfeCreateBook(mApi);
            mDfeCreateBook.addDataChangedListener(onCreateBookDataChanged);
            mDfeCreateBook.addErrorListener(onErrorListener);
        }
        mDfeCreateBook.makeRequest(mBook);
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
        if (mBook.getImage() != null && !mBook.getImage().isEmpty()) {

        } else {
            return ValidateDataUtils.BOOK_IMAGE_EMPTY;
        }

        return ValidateDataUtils.VALID_DATA;
    }

    private void uploadImage() {
        showDialogLoading();
        String[] params = new String[3];
        params[0] = ApiKey.API_UPLOAD_BOOK_IMAGE;
        params[1] = mBook.getImage();
        switch (mFlag) {
            case FLAG_CREATE:
                params[2] = String.valueOf(mDfeCreateBook.getBookId());
                break;
            case FLAG_EDIT:
                params[2] = String.valueOf(mBook.getId());
                break;
        }
        if (mBook.getImage() != null && !mBook.getImage().isEmpty()) {
            new UploadImage() {
                @Override
                protected void onFail(JSONObject jsonObject) {
                    super.onFail(jsonObject);
                    hideDialogLoading();
                    DialogUtils.getInstance().showDialogMessage(getActivity(), "Upload image fail. Do you want to retry ?", new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            uploadImage();
                        }
                    }, new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            if (mFlag == FLAG_CREATE) {
                                Toast.makeText(getActivity(), "You've created a new book!", Toast.LENGTH_SHORT).show();
                                getActivity().finish();
                            } else if (mFlag == FLAG_EDIT) {
                                Toast.makeText(getActivity(), "You've edit a book!", Toast.LENGTH_SHORT).show();
                                getActivity().finish();
                            }
                        }
                    });
                }

                @Override
                protected void onSuccess(JSONObject jsonObject) {
                    super.onSuccess(jsonObject);
                    hideDialogLoading();
                    if (mFlag == FLAG_CREATE) {
                        Toast.makeText(getActivity(), "You've created a new book!", Toast.LENGTH_SHORT).show();
                        getActivity().finish();
                    } else if (mFlag == FLAG_EDIT) {
                        Toast.makeText(getActivity(), "You've edited a book successfully!", Toast.LENGTH_SHORT).show();
                        getActivity().finish();
                    }
                }

            }.execute(params);
        }
    }

    private void requestEditBook() {
        showDialogLoading();
        if (mDfeEditBook == null) {
            mDfeEditBook = new DfeEditBook(mApi);
            mDfeEditBook.addDataChangedListener(onEditBookDataChanged);
            mDfeEditBook.addErrorListener(onErrorListener);
        }
        mDfeEditBook.makeRequest(mBook);
    }

    public int getTitle() {
        if (mFlag == FLAG_CREATE) {
            return R.string.action_bar_title_create_book;
        } else if (mFlag == FLAG_EDIT) {
            return R.string.action_bar_title_edit_book;
        }
        return 0;
    }
}
