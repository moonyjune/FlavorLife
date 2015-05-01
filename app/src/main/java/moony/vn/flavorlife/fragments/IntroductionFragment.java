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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;

import com.ntq.fragments.NFragment;
import com.ntq.fragments.NFragmentSwitcher;
import com.ntq.mediapicker.NMediaItem;
import com.ntq.mediapicker.NMediaOptions;
import com.ntq.mediapicker.NMediaPickerActivity;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import moony.vn.flavorlife.FlavorLifeApplication;
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
    private static final String KEY_RECIPE = "recipe";
    private static final String KEY_IMAGE_URI = "image_uri";
    private Uri mImageURI;
    //    private TypeView mChooseType;
    private Recipe mRecipe;
    private ImageView mImageRecipe, mAddPhoto;
    private EditText mName, mIntroductionOfDish, mCookingTime, mTipNote, mAuthorEvaluation, mChooseBook, mChooseChapter;
    private LevelView mLevels;
    private View mLayoutChangeImage;
    private RatingBar mLevel;
    private int mChapterId;
    private EditText mChooseKind;

    public static IntroductionFragment newInstance() {
        IntroductionFragment introductionFragment = new IntroductionFragment();
        Bundle bundle = new Bundle();
        introductionFragment.setArguments(bundle);
        return introductionFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null) {
            mImageURI = Uri.parse((String) savedInstanceState.get(KEY_IMAGE_URI));
            mRecipe = (Recipe) savedInstanceState.getSerializable(KEY_RECIPE);
        }
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
//        mChooseType = (TypeView) view.findViewById(R.id.choose_type);
        mCookingTime = (EditText) view.findViewById(R.id.recipe_cooking_time);
        mTipNote = (EditText) view.findViewById(R.id.recipe_tip_note);
        mAuthorEvaluation = (EditText) view.findViewById(R.id.author_evaluation);
        mLayoutChangeImage = view.findViewById(R.id.layout_change_picture);
        mChooseBook = (EditText) view.findViewById(R.id.choose_book);
        mChooseChapter = (EditText) view.findViewById(R.id.choose_chapter);
        mChooseKind = (EditText) view.findViewById(R.id.choose_kind);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mChooseBook.setOnClickListener(this);
        mChooseChapter.setOnClickListener(this);
        mChooseKind.setOnClickListener(this);
        if (mImageURI != null) {
            mLayoutChangeImage.setVisibility(View.VISIBLE);
            mAddPhoto.setVisibility(View.GONE);
            mImageLoader.display(mImageURI, mImageRecipe);
        } else {
            mAddPhoto.setVisibility(View.VISIBLE);
            mLayoutChangeImage.setVisibility(View.GONE);
        }
        if (mRecipe != null) {
            mName.setText(mRecipe.getName());
            mIntroductionOfDish.setText(mRecipe.getIntroduction());
            mLevel.setNumStars(mRecipe.getLevel());
            if (mRecipe.getCookingTime() != 0)
                mCookingTime.setText(String.valueOf(mRecipe.getCookingTime()));
            mTipNote.setText(mRecipe.getTipNote());
            mAuthorEvaluation.setText(mRecipe.getAuthorComments());
        }
        mAddPhoto.setOnClickListener(this);
//        mChooseType.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                ChooseTypeRecipeDialogFragment chooseTypeRecipeDialogFragment = new ChooseTypeRecipeDialogFragment();
//                chooseTypeRecipeDialogFragment.setTargetFragment(IntroductionFragment.this, RequestCode.CODE_CHOOSE_TYPE);
//                chooseTypeRecipeDialogFragment.show(getFragmentManager(), null);
//            }
//        });
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_introduction;
    }

    @Override
    protected void requestData() {

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable(KEY_RECIPE, mRecipe);
        if (mImageURI != null)
            outState.putString(KEY_IMAGE_URI, mImageURI.toString());
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.add_photo:
            case R.id.choose_picture:
                NMediaOptions.Builder builder = new NMediaOptions.Builder();
                NMediaOptions options = NMediaOptions.createDefault();
                options = builder.setIsCropped(true).setFixAspectRatio(true)
                        .build();
                NMediaPickerActivity.open(mNavigationManager.getActivePage(),
                        REQUEST_IMAGE, options);
                System.out.println("Mj : active page - " + mNavigationManager.getActivePage());
                break;
            case R.id.choose_book:
                ChooseBookDialogFragment chooseBookDialogFragment = new ChooseBookDialogFragment();
                chooseBookDialogFragment.show(getFragmentManager(), null);
                break;
            case R.id.choose_chapter:
                //TODO truyen vao bookId
                ChooseChapterDialogFragment chooseChapterDialogFragment = ChooseChapterDialogFragment.newInstance(1);
                chooseChapterDialogFragment.show(getFragmentManager(), null);
                break;
            case R.id.choose_kind:
                ChooseKindDialogFragment chooseKindDialogFragment = ChooseKindDialogFragment.newInstance();
                chooseKindDialogFragment.show(getFragmentManager(), null);
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
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
            mImageURI = mediaItem.getUriOrigin();
            mImageLoader.display(mediaItem.getUriOrigin(), mImageRecipe);
        } else {
            mImageURI = mediaItem.getUriCropped();
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
        mRecipe.setName(mName.getText().toString());
        mRecipe.setIntroduction(mIntroductionOfDish.getText().toString());
        mRecipe.setAuthorComments("comment");
        mRecipe.setLevel(mLevel.getNumStars());
        mRecipe.setType(1);
        mRecipe.setCookingTime(Integer.valueOf(mCookingTime.getText().toString()));
        mRecipe.setKind(1);
        mRecipe.setCreateTime(new Date());
        mRecipe.setIdChapter(mChapterId);
        mRecipe.setIdUser(FlavorLifeApplication.get().getUser().getId());
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
