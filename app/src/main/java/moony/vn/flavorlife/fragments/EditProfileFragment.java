package moony.vn.flavorlife.fragments;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.ntq.api.model.OnDataChangedListener;
import com.ntq.fragments.NFragmentSwitcher;
import com.ntq.mediapicker.NMediaItem;
import com.ntq.mediapicker.NMediaOptions;
import com.ntq.mediapicker.NMediaPickerActivity;

import org.json.JSONObject;

import java.util.ArrayList;

import moony.vn.flavorlife.FlavorLifeApplication;
import moony.vn.flavorlife.R;
import moony.vn.flavorlife.activities.MainActivity;
import moony.vn.flavorlife.api.ApiKey;
import moony.vn.flavorlife.api.UploadImage;
import moony.vn.flavorlife.api.model.DfeEditUserProfile;
import moony.vn.flavorlife.api.model.DfeGetUserProfile;
import moony.vn.flavorlife.entities.User;
import moony.vn.flavorlife.utils.DialogUtils;
import moony.vn.flavorlife.utils.ToastUtils;
import moony.vn.flavorlife.utils.ValidateDataUtils;

/**
 * Created by moony on 5/10/15.
 */
public class EditProfileFragment extends NFragmentSwitcher implements View.OnClickListener {
    private static final String TAG = "EditProfileFragment";
    private static final String KEY_USER = "user";
    private static final String KEY_IMAGE_URI = "image_uri";
    private static final String FLAG = "flag";
    private static final int REQUEST_PROFILE_IMAGE = 900;
    private User mUser;
    private User mOldUser;
    private DfeGetUserProfile mDfeGetUserProfile;
    private DfeEditUserProfile mDfeEditUserProfile;
    private EditText mUsername, mEmail, mInspiration;
    private ImageView mUserImage;
    private Uri mImageURI;
    private String mUriString;
    private int mFlag;

    private OnDataChangedListener onEditProfileDataChangeListener = new OnDataChangedListener() {
        @Override
        public void onDataChanged() {
            hideDialogLoading();
            if (mDfeEditUserProfile != null && mDfeEditUserProfile.isReady()) {
                FlavorLifeApplication.get().updateUserName(mUser.getUserName());
                FlavorLifeApplication.get().updateCheckUpdatedProfile(true);
                if (checkChangeImage()) {
                    uploadImage();
                } else {
                    if (!mUser.getEmail().equals(mOldUser.getEmail())) {
                        //TODO logout
                        MainActivity.startMainActivity(getActivity(), MainActivity.EXTRA_OPEN_LOGIN);
                    } else {
                        ToastUtils.showToastShort(getActivity(), "Edit profile successfully");
                        getActivity().finish();
                    }
                }
            }
        }
    };

    private Response.ErrorListener onEditProfileErrorListener = new Response.ErrorListener() {
        @Override
        public void onErrorResponse(VolleyError error) {
            hideDialogLoading();
            showDialogMessageError(error);
        }
    };

    public static EditProfileFragment newInstance(User user) {
        EditProfileFragment editProfileFragment = new EditProfileFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable(KEY_USER, user);
        editProfileFragment.setArguments(bundle);
        return editProfileFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState == null) {
            mUser = (User) getArguments().getSerializable(KEY_USER);
            mOldUser = (User) getArguments().getSerializable(KEY_USER);
        } else {
            mUriString = (String) savedInstanceState.get(KEY_IMAGE_URI);
            mUser = (User) savedInstanceState.getSerializable(KEY_USER);
            mOldUser = (User) savedInstanceState.getSerializable(KEY_USER);
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (mUser != null)
            outState.putSerializable(KEY_USER, mUser);
        if (mImageURI != null)
            outState.putString(KEY_IMAGE_URI, mImageURI.toString());
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_edit_profile;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mUsername = (EditText) view.findViewById(R.id.user_name);
        mEmail = (EditText) view.findViewById(R.id.user_email);
        mInspiration = (EditText) view.findViewById(R.id.user_inspiration);
        mUserImage = (ImageView) view.findViewById(R.id.user_image);
        view.findViewById(R.id.edit_image).setOnClickListener(this);
        view.findViewById(R.id.change_profile).setOnClickListener(this);
    }

    @Override
    protected void requestData() {
        switchToLoading();
        if (mDfeGetUserProfile == null) {
            mDfeGetUserProfile = new DfeGetUserProfile(mApi);
            mDfeGetUserProfile.addDataChangedListener(this);
            mDfeGetUserProfile.addErrorListener(this);
        }
        mDfeGetUserProfile.makeRequest(mUser.getId());
    }

    private void requestEditProfile() {
        showDialogLoading();
        if (mDfeEditUserProfile == null) {
            mDfeEditUserProfile = new DfeEditUserProfile(mApi);
            mDfeEditUserProfile.addDataChangedListener(onEditProfileDataChangeListener);
            mDfeEditUserProfile.addErrorListener(onEditProfileErrorListener);
        }
        mDfeEditUserProfile.makeRequest(mUser);
    }

    @Override
    protected boolean isDataReady() {
        if (mDfeGetUserProfile != null && mDfeGetUserProfile.isReady())
            return true;
        return false;
    }

    @Override
    public void onDataChanged() {
        super.onDataChanged();
        switchToData();
        if (isDataReady()) {
            mUser = new User();
            mUser.updateUser(mDfeGetUserProfile.getUser());
            setDataToView();
        }
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (isDataReady()) {
            setDataToView();
        } else {
            requestData();
        }
    }

    private void setDataToView() {
        if (mUser.getImageDisplay() != null && !mUser.getImageDisplay().isEmpty()) {
            mImageLoader.display(mUser.getImageDisplay(), mUserImage);
        } else {
            mUserImage.setImageResource(R.drawable.default_monkey_image);
        }

        mUsername.setText(mUser.getUserName());
        mEmail.setText(mUser.getEmail());
        mInspiration.setText(mUser.getInspiration());
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.edit_image:
                //TODO start choose image
                NMediaOptions.Builder builder = new NMediaOptions.Builder();
                NMediaOptions options = NMediaOptions.createDefault();
                options = builder.setIsCropped(true).setFixAspectRatio(true)
                        .build();
                NMediaPickerActivity.open(mNavigationManager.getActivePage(),
                        REQUEST_PROFILE_IMAGE, options);
                break;
            case R.id.change_profile:
                if (mUser != null) {
                    collectData();
                    int code = validateData();
                    if (code == ValidateDataUtils.VALID_DATA) {
                        if (checkChangeData()) {
                            requestEditProfile();
                        } else {
                            if (checkChangeImage()) {
                                uploadImage();
                            } else {
                                showDialogMessageError("You haven't change anything yet...");
                            }
                        }
                    } else {
                        showDialogMessageError(ValidateDataUtils.get(getActivity(), code));
                    }
                }
                break;
        }
    }

    private boolean checkChangeData() {
        if (!mOldUser.getUserName().equals(mUser.getUserName()))
            return true;
        if (!mOldUser.getEmail().equals(mUser.getEmail()))
            return true;
        if (!mOldUser.getInspiration().equals(mUser.getInspiration()))
            return true;
        return false;
    }

    private void collectData() {
        mUser.setUserName(mUsername.getText().toString());
        mUser.setEmail(mEmail.getText().toString());
        mUser.setInspiration(mInspiration.getText().toString());
    }

    private boolean checkChangeImage() {
        if (mUser.getImage() == null) {
            return false;
        } else {
            if (mOldUser.getImage() == null) {
                return true;
            } else {
                if (!mOldUser.getImage().equals(mUser.getImage())) {
                    return true;
                }
            }
        }
        return false;
    }

    private int validateData() {
        if (mUser.getUserName() != null && !mUser.getUserName().isEmpty()) {
            //valid
        } else {
            return ValidateDataUtils.USERNAME_EMPTY;
        }
        if (mUser.getEmail() != null && !mUser.getEmail().isEmpty()) {
            //valid
        } else {
            return ValidateDataUtils.EMAIL_EMPTY;
        }

        return ValidateDataUtils.VALID_DATA;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        ArrayList<NMediaItem> mMediaSelectedList;
        switch (requestCode) {
            case REQUEST_PROFILE_IMAGE:
                if (resultCode == Activity.RESULT_OK) {
                    mMediaSelectedList = NMediaPickerActivity
                            .getNMediaItemSelected(data);
                    if (mMediaSelectedList != null) {

                        StringBuilder builder = new StringBuilder();
                        for (NMediaItem mediaItem : mMediaSelectedList) {
                            Log.i(TAG, mediaItem.toString());
                            builder.append(mediaItem.toString());
                            builder.append(", PathOrigin=");
                            builder.append(mediaItem.getPathOrigin(getActivity()));
                            builder.append(", PathCropped=");
                            builder.append(mediaItem.getPathCropped(getActivity()));
                            builder.append("\n\n");

                            addImages(mediaItem);
                            mUser.setImage(mediaItem.getPathCropped(getActivity()));
                        }
                    } else {
                        Log.e(TAG, "Error to get media, NULL");
                    }
                } else {
                    Log.e(TAG, "Get media cancled.");
                }
                break;
        }
    }

    private void addImages(NMediaItem mediaItem) {
        if (mediaItem.getUriCropped() == null) {
            mImageURI = mediaItem.getUriOrigin();
            mImageLoader.display(mediaItem.getUriOrigin(), mUserImage);
        } else {
            mImageURI = mediaItem.getUriCropped();
            mImageLoader.display(mediaItem.getUriCropped(), mUserImage);
        }
    }

    private void uploadImage() {
        showDialogLoading();
        String[] params = new String[3];
        params[0] = ApiKey.API_UPLOAD_USER_IMAGE;
        params[1] = mUser.getImage();
        params[2] = String.valueOf(FlavorLifeApplication.get().getUser().getId());
        if (mUser.getImage() != null && !mUser.getImage().isEmpty()) {
            new UploadImage() {
                @Override
                protected void onSuccess(JSONObject jsonObject) {
                    super.onSuccess(jsonObject);
                    DialogUtils.getInstance().showDialogMessage(getActivity(), "Upload image fail. Do you want to retry ?", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            uploadImage();
                        }
                    }, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            getActivity().finish();
                        }
                    });
                }

                @Override
                protected void onFailed(JSONObject jsonObject) {
                    super.onFailed(jsonObject);
                    getActivity().finish();
                }
            }.execute(params);
        }
    }
}
