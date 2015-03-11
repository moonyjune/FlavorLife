package com.ntq.mediapicker;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.provider.MediaStore;
import android.provider.MediaStore.Images;
import android.provider.MediaStore.MediaColumns;
import android.provider.MediaStore.Video;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.AbsListView.LayoutParams;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.TextView;

import moony.vn.flavorlife.R;

import com.ntq.fragments.NFragment;
import com.ntq.utils.Utils;
import com.ntq.widget.HeaderGridView;

/**
 *
 * @author TUNGDX
 *
 */

/**
 * Display list of videos, photos from {@link MediaStore} and select one or many
 * item from list depends on {@link NMediaOptions} that passed when open media
 * picker.
 */
public class NMediaPickerFragment extends NFragment implements
        LoaderCallbacks<Cursor>, OnItemClickListener {
    private static final String LOADER_EXTRA_URI = "loader_extra_uri";
    private static final String LOADER_EXTRA_PROJECT = "loader_extra_project";
    private static final String KEY_MEDIA_TYPE = "media_type";
    private static final String KEY_GRID_STATE = "grid_state";
    private static final String KEY_MEDIA_SELECTED_LIST = "media_selected_list";

    private HeaderGridView mGridView;
    private TextView mNoItemView;
    private NMediaAdapter mMediaAdapter;
    private NMediaOptions mMediaOptions;
    private NMediaSelectedListener mMediaSelectedListener;
    private Bundle mSavedInstanceState;
    private List<NMediaItem> mMediaselectedList;

    private int mMediaType;
    private int mPhotoSize, mPhotoSpacing;

    public NMediaPickerFragment() {
        mSavedInstanceState = new Bundle();
    }

    public static NMediaPickerFragment newInstance(NMediaOptions options) {
        Bundle bundle = new Bundle();
        bundle.putParcelable(NMediaPickerActivity.EXTRA_MEDIA_OPTIONS, options);
        NMediaPickerFragment fragment = new NMediaPickerFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mMediaSelectedListener = (NMediaSelectedListener) activity;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null) {
            mMediaOptions = savedInstanceState
                    .getParcelable(NMediaPickerActivity.EXTRA_MEDIA_OPTIONS);
            mMediaType = savedInstanceState.getInt(KEY_MEDIA_TYPE);
            mMediaselectedList = savedInstanceState
                    .getParcelableArrayList(KEY_MEDIA_SELECTED_LIST);
            mSavedInstanceState = savedInstanceState;
        } else {
            mMediaOptions = getArguments().getParcelable(
                    NMediaPickerActivity.EXTRA_MEDIA_OPTIONS);
            if (mMediaOptions.canSelectPhotoAndVideo()
                    || mMediaOptions.canSelectPhoto()) {
                mMediaType = NMediaItem.PHOTO;
            } else {
                mMediaType = NMediaItem.VIDEO;
            }
            mMediaselectedList = mMediaOptions.getMediaListSelected();
            // Override mediaType by 1st item media if has media selected.
            if (mMediaselectedList != null && mMediaselectedList.size() > 0) {
                mMediaType = mMediaselectedList.get(0).getType();
            }
        }
        // get the photo size and spacing
        mPhotoSize = getResources().getDimensionPixelSize(
                R.dimen.picker_photo_size);
        mPhotoSpacing = getResources().getDimensionPixelSize(
                R.dimen.picker_photo_spacing);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_mediapicker, container,
                false);
        initView(root);
        return root;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (mMediaType == NMediaItem.PHOTO) {
            requestPhotos(false);
        } else {
            requestVideos(false);
        }
    }

    private void requestPhotos(boolean isRestart) {
        requestMedia(Images.Media.EXTERNAL_CONTENT_URI,
                NMediaUtils.PROJECT_PHOTO, isRestart);
    }

    private void requestVideos(boolean isRestart) {
        requestMedia(Video.Media.EXTERNAL_CONTENT_URI,
                NMediaUtils.PROJECT_VIDEO, isRestart);
    }

    private void requestMedia(Uri uri, String[] projects, boolean isRestart) {
        Bundle bundle = new Bundle();
        bundle.putStringArray(LOADER_EXTRA_PROJECT, projects);
        bundle.putString(LOADER_EXTRA_URI, uri.toString());
        if (isRestart)
            getLoaderManager().restartLoader(0, bundle, this);
        else
            getLoaderManager().initLoader(0, bundle, this);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (mGridView != null) {
            mSavedInstanceState.putParcelable(KEY_GRID_STATE,
                    mGridView.onSaveInstanceState());
        }
        mSavedInstanceState.putParcelable(
                NMediaPickerActivity.EXTRA_MEDIA_OPTIONS, mMediaOptions);
        mSavedInstanceState.putInt(KEY_MEDIA_TYPE, mMediaType);
        mSavedInstanceState.putParcelableArrayList(KEY_MEDIA_SELECTED_LIST,
                (ArrayList<NMediaItem>) mMediaselectedList);
        outState.putAll(mSavedInstanceState);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle bundle) {
        Uri uri = Uri.parse(bundle.getString(LOADER_EXTRA_URI));
        String[] projects = bundle.getStringArray(LOADER_EXTRA_PROJECT);
        String order = MediaColumns.DATE_ADDED + " DESC";
        return new CursorLoader(mContext, uri, projects, null, null, order);
    }

    private void bindData(Cursor cursor) {
        if (cursor == null || cursor.getCount() <= 0) {
            switchToError();
            return;
        }
        switchToData();
        if (mMediaAdapter == null) {
            mMediaAdapter = new NMediaAdapter(mContext, cursor, 0,
                    mImageLoader, mMediaType, mMediaOptions);
        } else {
            mMediaAdapter.setMediaType(mMediaType);
            mMediaAdapter.swapCursor(cursor);
        }
        if (mGridView.getAdapter() == null) {
            mGridView.setAdapter(mMediaAdapter);
            mGridView.setRecyclerListener(mMediaAdapter);
        }
        Parcelable state = mSavedInstanceState.getParcelable(KEY_GRID_STATE);
        if (state != null) {
            mGridView.onRestoreInstanceState(state);
        }
        if (mMediaselectedList != null) {
            mMediaAdapter.setMediaSelectedList(mMediaselectedList);
        }
        mMediaAdapter.notifyDataSetChanged();
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
        bindData(cursor);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        // Preference:http://developer.android.com/guide/components/loaders.html#callback
        if (mMediaAdapter != null)
            mMediaAdapter.swapCursor(null);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position,
                            long id) {
        Object object = parent.getAdapter().getItem(position);
        if (object instanceof Cursor) {
            Uri uri;
            if (mMediaType == NMediaItem.PHOTO) {
                uri = NMediaUtils.getPhotoUri((Cursor) object);
            } else {
                uri = NMediaUtils.getVideoUri((Cursor) object);
            }
            PickerImageView pickerImageView = (PickerImageView) view
                    .findViewById(R.id.thumbnail);
            NMediaItem mediaItem = new NMediaItem(mMediaType, uri);
            mMediaAdapter.updateMediaSelected(mediaItem, pickerImageView);
            mMediaselectedList = mMediaAdapter.getMediaSelectedList();

            if (mMediaAdapter.hasSelected()) {
                mMediaSelectedListener.onHasSelected(mMediaAdapter
                        .getMediaSelectedList());
            } else {
                mMediaSelectedListener.onHasNoSelected();
            }
        }
    }

    public void switchMediaSelector() {
        if (!mMediaOptions.canSelectPhotoAndVideo())
            return;
        if (mMediaType == NMediaItem.PHOTO) {
            mMediaType = NMediaItem.VIDEO;
        } else {
            mMediaType = NMediaItem.PHOTO;
        }
        switch (mMediaType) {
            case NMediaItem.PHOTO:
                requestPhotos(true);
                break;
            case NMediaItem.VIDEO:
                requestVideos(true);
                break;
            default:
                break;
        }
    }

    public List<NMediaItem> getMediaSelectedList() {
        return mMediaselectedList;
    }

    public boolean hasMediaSelected() {
        return mMediaselectedList != null && mMediaselectedList.size() > 0 ? true
                : false;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (mGridView != null) {
            mSavedInstanceState.putParcelable(KEY_GRID_STATE,
                    mGridView.onSaveInstanceState());
            mGridView = null;
        }
        if (mMediaAdapter != null) {
            mMediaAdapter.onDestroyView();
        }
    }

    public int getMediaType() {
        return mMediaType;
    }

    private void switchToData() {
        mNoItemView.setVisibility(View.GONE);
        mNoItemView.setText(null);
        mGridView.setVisibility(View.VISIBLE);
    }

    private void switchToError() {
        mNoItemView.setVisibility(View.VISIBLE);
        mNoItemView.setText(R.string.picker_no_items);
        mGridView.setVisibility(View.GONE);
    }

    private void initView(View view) {
        mGridView = (HeaderGridView) view.findViewById(R.id.grid);
        View header = new View(getActivity());
        ViewGroup.LayoutParams params = new LayoutParams(
                android.view.ViewGroup.LayoutParams.MATCH_PARENT,
                Utils.getActionbarHeight(getActivity()));
        header.setLayoutParams(params);
        mGridView.addHeaderView(header);

        mGridView.setOnItemClickListener(this);
        mNoItemView = (TextView) view.findViewById(R.id.no_data);

        // get the view tree observer of the grid and set the height and numcols
        // dynamically
        mGridView.getViewTreeObserver().addOnGlobalLayoutListener(
                new ViewTreeObserver.OnGlobalLayoutListener() {
                    @Override
                    public void onGlobalLayout() {
                        if (mMediaAdapter != null
                                && mMediaAdapter.getNumColumns() == 0) {
                            final int numColumns = (int) Math.floor(mGridView
                                    .getWidth() / (mPhotoSize + mPhotoSpacing));
                            if (numColumns > 0) {
                                final int columnWidth = (mGridView.getWidth() / numColumns)
                                        - mPhotoSpacing;
                                Log.i("NMediaPickerFragment", String.format(
                                        "Column Width=[%s]", columnWidth));
                                mMediaAdapter.setNumColumns(numColumns);
                                mMediaAdapter.setItemHeight(columnWidth);
                            }
                        }
                    }
                });
    }
}
