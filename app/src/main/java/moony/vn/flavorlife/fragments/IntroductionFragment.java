package moony.vn.flavorlife.fragments;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.ntq.fragments.NFragmentSwitcher;
import com.ntq.mediapicker.NMediaItem;
import com.ntq.mediapicker.NMediaOptions;
import com.ntq.mediapicker.NMediaPickerActivity;

import java.util.ArrayList;
import java.util.Date;

import moony.vn.flavorlife.FlavorLifeApplication;
import moony.vn.flavorlife.R;
import moony.vn.flavorlife.entities.Chapter;
import moony.vn.flavorlife.entities.Cookbook;
import moony.vn.flavorlife.entities.Kind;
import moony.vn.flavorlife.entities.Recipe;
import moony.vn.flavorlife.layout.LevelView;

/**
 * Created by moony on 3/4/15.
 */
public class IntroductionFragment extends NFragmentSwitcher implements View.OnClickListener {
    public static final int REQUEST_KIND = 10001;
    public static final int REQUEST_BOOK = 10002;
    public static final int REQUEST_CHAPTER = 10003;
    private static final int REQUEST_IMAGE = 10000;
    private static final String TAG = "IntroductionFragment";
    private static final String FLAG = "flag";
    private static final String KEY_RECIPE = "recipe";
    private static final String KEY_IMAGE_URI = "image_uri";
    private static final String KEY_CHOSEN_BOOK = "chosen_book";
    private static final String KEY_CHOSEN_KIND = "chosen_kind";
    private static final String KEY_CHOSEN_CHAPTER = "chosen_chapter";
    private Uri mImageURI;
    //    private TypeView mChooseType;
    private Recipe mRecipe;
    private ImageView mImageRecipe, mAddPhoto;
    private EditText mName, mIntroductionOfDish, mCookingTime, mTipNote, mAuthorEvaluation;
    private LevelView mLevels;
    private View mLayoutChangeImage;
    private RatingBar mLevel;
    private TextView mChooseKind, mChooseBook, mChooseChapter;
    private Kind mChosenKind;
    private Cookbook mChosenBook;
    private Chapter mChosenChapter;
    private String mUriString;
    private int mFlag;

    public static IntroductionFragment newInstance(Recipe recipe, int flag) {
        IntroductionFragment introductionFragment = new IntroductionFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(FLAG, flag);
        bundle.putSerializable(KEY_RECIPE, recipe);
        introductionFragment.setArguments(bundle);
        return introductionFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null) {
            mFlag = savedInstanceState.getInt(FLAG);
            mUriString = (String) savedInstanceState.get(KEY_IMAGE_URI);
            mRecipe = (Recipe) savedInstanceState.getSerializable(KEY_RECIPE);
            mChosenBook = (Cookbook) savedInstanceState.getSerializable(KEY_CHOSEN_BOOK);
            mChosenChapter = (Chapter) savedInstanceState.getSerializable(KEY_CHOSEN_CHAPTER);
            mChosenKind = (Kind) savedInstanceState.getSerializable(KEY_CHOSEN_KIND);
        } else {
            mRecipe = (Recipe) getArguments().getSerializable(KEY_RECIPE);
            mFlag = getArguments().getInt(FLAG);
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
        mChooseBook = (TextView) view.findViewById(R.id.choose_book);
        mChooseChapter = (TextView) view.findViewById(R.id.choose_chapter);
        mChooseKind = (TextView) view.findViewById(R.id.choose_kind);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mChooseBook.setOnClickListener(this);
        mChooseChapter.setOnClickListener(this);
        mChooseKind.setOnClickListener(this);
        if (mRecipe != null) {
            mName.setText(mRecipe.getName());
            mIntroductionOfDish.setText(mRecipe.getIntroduction());
            mLevel.setProgress(mRecipe.getLevel());
            if (mRecipe.getCookingTime() != 0)
                mCookingTime.setText(String.valueOf(mRecipe.getCookingTime()));
            mTipNote.setText(mRecipe.getTipNote());
            mAuthorEvaluation.setText(mRecipe.getAuthorComments());
            mChooseBook.setText(mRecipe.getBookName());
            mChooseChapter.setText(mRecipe.getChapterName());
            mChooseKind.setText(mRecipe.getKindName());
            mLayoutChangeImage.setVisibility(View.VISIBLE);
            mAddPhoto.setVisibility(View.GONE);
            if (mRecipe.getImages() != null && !mRecipe.getImages().isEmpty())
                mImageLoader.display(mRecipe.getImages(), mImageRecipe);
            else
                mImageRecipe.setImageResource(R.drawable.default_recipe_image);
            if (mChosenBook == null) {
                mChosenBook = new Cookbook();
            }
            mChosenBook.setId(mRecipe.getIdBook());
            mChosenBook.setName(mRecipe.getBookName());

            if (mChosenChapter == null)
                mChosenChapter = new Chapter();
            mChosenChapter.setId(mRecipe.getIdChapter());
            mChosenChapter.setName(mRecipe.getChapterName());

            if (mChosenKind == null)
                mChosenKind = new Kind();
            mChosenKind.setKind(mRecipe.getKind());
        } else {
            if (mUriString != null && !mUriString.isEmpty())
                mImageURI = Uri.parse(mUriString);
            if (mImageURI != null) {
                mLayoutChangeImage.setVisibility(View.VISIBLE);
                mAddPhoto.setVisibility(View.GONE);
                mImageLoader.display(mImageURI, mImageRecipe);
            } else {
                mAddPhoto.setVisibility(View.VISIBLE);
                mLayoutChangeImage.setVisibility(View.GONE);
            }
        }
        mAddPhoto.setOnClickListener(this);
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
        if (mChosenBook != null)
            outState.putSerializable(KEY_CHOSEN_BOOK, mChosenBook);
        if (mChosenChapter != null)
            outState.putSerializable(KEY_CHOSEN_CHAPTER, mChosenChapter);
        if (mChosenKind != null)
            outState.putSerializable(KEY_CHOSEN_KIND, mChosenKind);
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
                break;
            case R.id.choose_book:
                ChooseBookDialogFragment chooseBookDialogFragment = ChooseBookDialogFragment.newInstance(mChosenBook);
                chooseBookDialogFragment.setTargetFragment(this, REQUEST_BOOK);
                chooseBookDialogFragment.show(getFragmentManager(), null);
                break;
            case R.id.choose_chapter:
                if (mChosenBook != null) {
                    ChooseChapterDialogFragment chooseChapterDialogFragment = ChooseChapterDialogFragment.newInstance(mChosenBook, mChosenChapter);
                    chooseChapterDialogFragment.setTargetFragment(this, REQUEST_CHAPTER);
                    chooseChapterDialogFragment.show(getFragmentManager(), null);
                } else {
                    showDialogMessageError("You haven't chosen book yet.");
                }
                break;
            case R.id.choose_kind:
                ChooseKindDialogFragment chooseKindDialogFragment = ChooseKindDialogFragment.newInstance(mChosenKind);
                chooseKindDialogFragment.setTargetFragment(this, REQUEST_KIND);
                chooseKindDialogFragment.show(getFragmentManager(), null);
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
                break;
            case REQUEST_KIND:
                if (resultCode == Activity.RESULT_OK && data != null) {
                    Bundle bundle = data.getBundleExtra(ChooseKindDialogFragment.KEY_DATA);
                    mChosenKind = (Kind) bundle.getSerializable(ChooseKindDialogFragment.KEY_KIND);
                    mChooseKind.setText(mChosenKind.getName());
                    if (mRecipe == null)
                        mRecipe = new Recipe();
                    mRecipe.setKind(mChosenKind.getKind());
                }
                break;
            case REQUEST_BOOK:
                if (resultCode == Activity.RESULT_OK && data != null) {
                    Bundle bundle = data.getBundleExtra(ChooseBookDialogFragment.KEY_DATA);
                    mChosenBook = (Cookbook) bundle.getSerializable(ChooseBookDialogFragment.KEY_BOOK);
                    mChooseBook.setText(mChosenBook.getName());
                    if (mRecipe == null)
                        mRecipe = new Recipe();
                    mRecipe.setIdBook(mChosenBook.getId());
                    mRecipe.setBookName(mChosenBook.getName());
                }
                break;
            case REQUEST_CHAPTER:
                if (resultCode == Activity.RESULT_OK && data != null) {
                    Bundle bundle = data.getBundleExtra(ChooseChapterDialogFragment.KEY_DATA);
                    mChosenChapter = (Chapter) bundle.getSerializable(ChooseChapterDialogFragment.KEY_CHAPTER);
                    mChooseChapter.setText(mChosenChapter.getName());
                    if (mRecipe == null)
                        mRecipe = new Recipe();
                    mRecipe.setIdChapter(mChosenChapter.getId());
                    mRecipe.setChapterName(mChosenChapter.getName());
                }
                break;
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
        if (mRecipe == null) {
            mRecipe = new Recipe();
        }
        if (mName != null) {
            mRecipe.setName(mName.getText().toString());
        }
        if (mIntroductionOfDish != null)
            mRecipe.setIntroduction(mIntroductionOfDish.getText().toString());
        if (mAuthorEvaluation != null)
            mRecipe.setAuthorComments(mAuthorEvaluation.getText().toString());
        if (mLevel != null)
            mRecipe.setLevel(mLevel.getProgress());
        //TODO set type;
//        mRecipe.setType(1);
        if (mCookingTime != null && !mCookingTime.getText().toString().isEmpty())
            mRecipe.setCookingTime(Integer.valueOf(mCookingTime.getText().toString()));
        if (mChosenKind != null)
            mRecipe.setKind(mChosenKind.getKind());
        mRecipe.setCreateTime(new Date());
        if (mChosenBook != null)
            mRecipe.setIdBook(mChosenBook.getId());
        if (mChosenChapter != null)
            mRecipe.setIdChapter(mChosenChapter.getId());
        mRecipe.setIdUser(FlavorLifeApplication.get().getUser().getId());
        if (mTipNote != null)
            mRecipe.setTipNote(mTipNote.getText().toString());
        return mRecipe;
    }

    public void clearData() {
        mName.setText("");
        mIntroductionOfDish.setText("");
        mTipNote.setText("");
        mLevel.setProgress(0);
        mChosenBook = null;
        mChooseBook.setText("");
        mChosenKind = null;
        mChooseKind.setText("");
        mChosenChapter = null;
        mChooseChapter.setText("");
        mCookingTime.setText("");
        mAuthorEvaluation.setText("");
        mLayoutChangeImage.setVisibility(View.GONE);
        mAddPhoto.setVisibility(View.VISIBLE);
    }

}
