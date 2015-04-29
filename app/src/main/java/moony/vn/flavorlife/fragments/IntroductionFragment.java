package moony.vn.flavorlife.fragments;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Parcelable;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;

import com.ntq.fragments.NFragmentSwitcher;
import com.ntq.mediapicker.NMediaItem;
import com.ntq.mediapicker.NMediaOptions;
import com.ntq.mediapicker.NMediaPickerActivity;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import moony.vn.flavorlife.R;
import moony.vn.flavorlife.RequestCode;
import moony.vn.flavorlife.entities.Recipe;
import moony.vn.flavorlife.layout.LevelView;
import moony.vn.flavorlife.layout.TypeView;

/**
 * Created by moony on 3/4/15.
 */
public class IntroductionFragment extends NFragmentSwitcher implements View.OnClickListener {
    private static final int REQUEST_IMAGE = 10000;
    private static final String TAG = "IntroductionFragment";
    protected static final String IMAGE_PATH = "image_path";
    private Uri mImageURI;
    private TypeView mChooseType;
    private Recipe mRecipe;
    private ImageView mImageRecipe, mAddPhoto;
    private EditText mName, mIntroductionOfDish, mCookingTime, mTipNote, mAuthorEvaluation;
    private LevelView mLevels;
    private View mLayoutChangeImage;
    private RatingBar mLevel;

    public static IntroductionFragment newInstance() {
        IntroductionFragment introductionFragment = new IntroductionFragment();
        Bundle bundle = new Bundle();
        introductionFragment.setArguments(bundle);
        return introductionFragment;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        view.findViewById(R.id.choose_picture).setOnClickListener(this);
        mAddPhoto = (ImageView) view.findViewById(R.id.add_photo);
        mImageRecipe = (ImageView) view.findViewById(R.id.image_recipe);
        mName = (EditText) view.findViewById(R.id.recipe_name);
        mIntroductionOfDish = (EditText) view.findViewById(R.id.introduction_dish);
//        mLevels = (LevelView) view.findViewById(R.id.recipe_level);
        mLevel = (RatingBar) view.findViewById(R.id.recipe_level);
        mChooseType = (TypeView) view.findViewById(R.id.choose_type);
        mCookingTime = (EditText) view.findViewById(R.id.recipe_cooking_time);
        mTipNote = (EditText) view.findViewById(R.id.recipe_tip_note);
        mAuthorEvaluation = (EditText) view.findViewById(R.id.author_evaluation);
        mLayoutChangeImage = view.findViewById(R.id.layout_change_picture);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mAddPhoto.setOnClickListener(this);
        mChooseType.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ChooseTypeRecipeDialogFragment chooseTypeRecipeDialogFragment = new ChooseTypeRecipeDialogFragment();
                chooseTypeRecipeDialogFragment.setTargetFragment(IntroductionFragment.this, RequestCode.CODE_CHOOSE_TYPE);
                chooseTypeRecipeDialogFragment.show(getFragmentManager(), null);
            }
        });
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_introduction;
    }

    @Override
    protected void requestData() {

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.add_photo:
            case R.id.choose_picture:
//                startActivityCamera();
                NMediaOptions.Builder builder = new NMediaOptions.Builder();
                NMediaOptions options = NMediaOptions.createDefault();
                options = builder.setIsCropped(true).setFixAspectRatio(true)
                        .build();
                NMediaPickerActivity.open(mNavigationManager.getActivePage(),
                        REQUEST_IMAGE, options);
                System.out.println("Mj : active page - " + mNavigationManager.getActivePage());
                break;
        }
    }

    public void startActivityCamera() {
        mImageURI = getOutputFilePath("FlavorLife");
        if (mImageURI != null) {
            final Intent captureIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
            captureIntent.putExtra(MediaStore.EXTRA_OUTPUT, mImageURI);
            captureIntent.putExtra(IMAGE_PATH, mImageURI.getPath());
            captureIntent.putExtra("return-data", true);
            Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            Intent chooserIntent = Intent.createChooser(intent, "Choose a picture from");
            // Set camera intent to file chooser
            chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, new Parcelable[]{});
            startActivityForResult(chooserIntent, RequestCode.CODE_CHOOSE_PICTURE);
        }
    }

    private Uri getOutputFilePath(String appName) {
        File dir = null;
        String state = Environment.getExternalStorageState();

        if (Environment.MEDIA_MOUNTED.equals(state)) {
            dir = new File(Environment.getExternalStorageDirectory().toString() + "/DCIM/" + appName);
            dir.mkdirs();
        } else {
            dir = new File(getActivity().getCacheDir().toString() + "/" + appName);
        }
        String timeStamp = new SimpleDateFormat("yyyy/MM/dd_HH:mm:ss").format(new Date());
        String fileName = appName + "_" + timeStamp;
        Uri mImageUri = Uri.fromFile(new File(dir, fileName + ".jpg"));

        galleryAddPic(getActivity(), mImageUri);
        return mImageUri;
    }

    public static void galleryAddPic(Context context, Uri uriFile) {
        Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        mediaScanIntent.setData(uriFile);
        context.sendBroadcast(mediaScanIntent);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
//        System.out.println("Mj : onActivityResult : " + requestCode);
//        if (resultCode == Activity.RESULT_OK && data != null) {
//            switch (requestCode) {
//                case RequestCode.CODE_CHOOSE_TYPE:
//                    List<Integer> listTypes = data.getIntegerArrayListExtra("list_types");
//                    mChooseType.setTypes(listTypes);
//                    break;
//                case RequestCode.CODE_CHOOSE_PICTURE:
//
//                    break;
//                default:
//                    break;
//            }
//        }

        System.out.println("Mj : onActivityResult");
        ArrayList<NMediaItem> mMediaSelectedList;
        if (requestCode == REQUEST_IMAGE) {
            if (resultCode == Activity.RESULT_OK) {
                mLayoutChangeImage.setVisibility(View.VISIBLE);
                mAddPhoto.setVisibility(View.GONE);
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
                        if (mRecipe == null)
                            mRecipe = new Recipe();
                        mRecipe.setImages(mediaItem.getPathCropped(getActivity()));
                    }
                } else {
                    Log.e(TAG, "Error to get media, NULL");
                }
            } else {
                Log.e(TAG, "Get media cancled.");
            }
        }
    }

    private void addImages(NMediaItem mediaItem) {
        if (mediaItem.getUriCropped() == null) {
            mImageLoader.display(mediaItem.getUriOrigin(), mImageRecipe);
        } else {
            mImageLoader.display(mediaItem.getUriCropped(), mImageRecipe);
        }
    }

    private void clearImages() {
        mImageRecipe.setImageResource(R.drawable.ic_launcher);
    }


    public Recipe getRecipe() {
//        if (validInformation()) {
        if (mRecipe == null) {
            mRecipe = new Recipe();
        }
        mRecipe.setName("name");
        mRecipe.setIntroduction("intro");
        mRecipe.setAuthorComments("comment");
        mRecipe.setLevel(1);
        mRecipe.setType(1);
        mRecipe.setCookingTime(90);
        mRecipe.setKind(1);
        mRecipe.setCreateTime(new Date());
        mRecipe.setIdChapter(1);
        mRecipe.setIdUser(1);
        return mRecipe;
//        } else {
//            return null;
//        }
    }

    private boolean validInformation() {
        if (mName.getText().toString() == null || mName.getText().toString().isEmpty())
            return false;
        if (mIntroductionOfDish.getText().toString() == null || mIntroductionOfDish.getText().toString().isEmpty())
            return false;
        if (mLevels.getLevel() == 0) return false;
//        if (mChooseType.getListTypes().size() == 0) return false;
        if (mCookingTime.getText().toString() == null || mCookingTime.getText().toString().isEmpty() ||
                Integer.valueOf(mCookingTime.getText().toString()) <= 0)
            return false;
        return true;
    }
}
