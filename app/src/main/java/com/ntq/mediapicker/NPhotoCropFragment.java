package com.ntq.mediapicker;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.ref.WeakReference;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;

import com.edmodo.cropper.CropImageView;

import moony.vn.flavorlife.R;
import moony.vn.flavorlife.widget.CircleCropImageView;

import com.edmodo.cropper.cropwindow.CropOverlayView;
import com.ntq.fragments.NFragment;
import com.ntq.utils.PhotoUtils;
import com.ntq.utils.StorageUtils;

/**
 *
 * @author TUNGDX
 *
 */

/**
 * For crop photo. Only crop one item at same time.
 */
public class NPhotoCropFragment extends NFragment implements OnClickListener {
    private static final String EXTRA_MEDIA_SELECTED = "extra_media_selected";
    private static final String EXTRA_MEDIA_OPTIONS = "extra_media_options";

    private NCropListener mCropListener;
    private NMediaOptions mMediaOptions;
    private NMediaItem mMediaItemSelected;
    private CropImageView mCropImageView;
    private View mRotateLeft, mRotateRight;
    private View mCancle;
    private View mSave;
    private ProgressDialog mDialog;
    private SaveFileCroppedTask mSaveFileCroppedTask;

    public static NPhotoCropFragment newInstance(NMediaItem item,
                                                 NMediaOptions options) {
        Bundle bundle = new Bundle();
        bundle.putParcelable(EXTRA_MEDIA_SELECTED, item);
        bundle.putParcelable(EXTRA_MEDIA_OPTIONS, options);
        NPhotoCropFragment cropFragment = new NPhotoCropFragment();
        cropFragment.setArguments(bundle);
        return cropFragment;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mCropListener = (NCropListener) activity;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null) {
            mMediaItemSelected = savedInstanceState
                    .getParcelable(EXTRA_MEDIA_SELECTED);
            mMediaOptions = savedInstanceState
                    .getParcelable(EXTRA_MEDIA_OPTIONS);
        } else {
            Bundle bundle = getArguments();
            mMediaItemSelected = bundle.getParcelable(EXTRA_MEDIA_SELECTED);
            mMediaOptions = bundle.getParcelable(EXTRA_MEDIA_OPTIONS);
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable(EXTRA_MEDIA_OPTIONS, mMediaOptions);
        outState.putParcelable(EXTRA_MEDIA_SELECTED, mMediaItemSelected);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_mediapicker_crop,
                container, false);
        init(root);
        return root;
    }

    private void init(View view) {
        mCropImageView = (CropImageView) view.findViewById(R.id.crop);
        mRotateLeft = view.findViewById(R.id.rotate_left);
        mRotateRight = view.findViewById(R.id.rotate_right);
        mCancle = view.findViewById(R.id.cancel);
        mSave = view.findViewById(R.id.save);

        mRotateLeft.setOnClickListener(this);
        mRotateRight.setOnClickListener(this);
        mSave.setOnClickListener(this);
        mCancle.setOnClickListener(this);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mCropImageView.setFixedAspectRatio(mMediaOptions.isFixAspectRatio());
        mCropImageView.setAspectRatio(mMediaOptions.getAspectX(),
                mMediaOptions.getAspectY());
        if (mMediaOptions.isCropCircleImage()) {
            mCropImageView.setShape(CropOverlayView.SHAPE_CIRCLE);
        } else {
            mCropImageView.setShape(CropOverlayView.SHAPE_RECT);
//            mCropImageView.setShape(CropOverlayView.SHAPE_RECT);
        }
        String filePath = null;
        String scheme = mMediaItemSelected.getUriOrigin().getScheme();
        if (scheme.equals(ContentResolver.SCHEME_CONTENT)) {
            filePath = PhotoUtils.getRealPathFromURI(getActivity()
                    .getContentResolver(), mMediaItemSelected.getUriOrigin());
        } else if (scheme.equals(ContentResolver.SCHEME_FILE)) {
            filePath = mMediaItemSelected.getUriOrigin().getPath();
        }
        if (TextUtils.isEmpty(filePath)) {
            Log.e("PhotoCrop", "not found file path");
            getFragmentManager().popBackStack();
            return;
        }
        int width = getResources().getDisplayMetrics().widthPixels / 3 * 2;
        Bitmap bitmap = PhotoUtils.decodeSampledBitmapFromFile(filePath, width,
                width);
        try {
            ExifInterface exif = new ExifInterface(filePath);
            mCropImageView.setImageBitmap(bitmap, exif);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rotate_left:
                // must catch exception, maybe bitmap in CropImage null
                try {
                    mCropImageView.rotateImage(-90);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case R.id.rotate_right:
                try {
                    mCropImageView.rotateImage(90);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case R.id.cancel:
                getFragmentManager().popBackStack();
                break;
            case R.id.save:
                mSaveFileCroppedTask = new SaveFileCroppedTask(getActivity());
                mSaveFileCroppedTask.execute();
                break;

            default:
                break;
        }
    }

    private Uri saveBitmapCropped(Bitmap bitmap) {
        if (bitmap == null)
            return null;
        try {
            File file;
            if (mMediaOptions.getCroppedFile() != null) {
                file = mMediaOptions.getCroppedFile();
            } else {
                file = StorageUtils.createTempFile(mContext);
            }
            boolean success = bitmap.compress(CompressFormat.JPEG, 100,
                    new FileOutputStream(file));
            if (success) {
                return Uri.fromFile(file);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private class SaveFileCroppedTask extends AsyncTask<Void, Void, Uri> {
        private WeakReference<Activity> reference;

        public SaveFileCroppedTask(Activity activity) {
            reference = new WeakReference<Activity>(activity);
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            if (reference.get() != null && mDialog == null
                    || !mDialog.isShowing()) {
                mDialog = ProgressDialog.show(reference.get(), null, reference
                        .get().getString(R.string.waiting), false, false);
            }
        }

        @Override
        protected Uri doInBackground(Void... params) {
            Uri uri = null;
            // must try-catch, maybe getCroppedImage() method crash because not
            // set bitmap in mCropImageView
            try {
                Bitmap bitmap = null;
//                if (mMediaOptions.isCropCircleImage()) {
//                    bitmap = mCropImageView.getCroppedCircleImage();
//                } else {
                bitmap = mCropImageView.getCroppedImage();
//                }
                uri = saveBitmapCropped(bitmap);
                if (bitmap != null) {
                    bitmap.recycle();
                    bitmap = null;
                }
            } catch (Exception exception) {
                exception.printStackTrace();
            }
            return uri;
        }

        @Override
        protected void onPostExecute(Uri result) {
            super.onPostExecute(result);
            if (mDialog != null) {
                mDialog.dismiss();
                mDialog = null;
            }
            mMediaItemSelected.setUriCropped(result);
            mCropListener.onSuccess(mMediaItemSelected);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mSaveFileCroppedTask != null) {
            mSaveFileCroppedTask.cancel(true);
            mSaveFileCroppedTask = null;
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mCropImageView = null;
        mDialog = null;
        mSave = null;
        mCancle = null;
        mRotateLeft = null;
        mRotateRight = null;
    }
}
