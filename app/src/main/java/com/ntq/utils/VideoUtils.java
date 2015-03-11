package com.ntq.utils;

import android.content.ContentProvider;
import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.media.MediaPlayer;
import android.net.Uri;
import android.provider.MediaStore;

/**
 * 
 * @author TUNGDX
 *
 */

/**
 * 
 * All utils for videos.
 * 
 */
public class VideoUtils {
	/**
	 * Get video's duration from {@link ContentProvider}
	 * 
	 * @param context
	 * @param uri
	 *            must has {@link Uri#getScheme()} equals
	 *            {@link ContentResolver#SCHEME_CONTENT}
	 * @return Duration of video, in milliseconds.
	 */
	public static long getDuration(Context context, Uri uri) {
		long duration = 0L;
		Cursor cursor = MediaStore.Video.query(context.getContentResolver(),
				uri, new String[] { MediaStore.Video.VideoColumns.DURATION });
		if (cursor != null) {
			cursor.moveToFirst();
			duration = cursor.getLong(cursor
					.getColumnIndex(MediaStore.Video.VideoColumns.DURATION));
			cursor.close();
		}
		return duration;
	}

	/**
	 * Get video's duration without {@link ContentProvider}. Because not know
	 * {@link Uri} of video.
	 * 
	 * @param context
	 * @param path
	 *            Path of video file.
	 * @return Duration of video, in milliseconds. Return 0 if path is null.
	 */
	public static long getDuration(Context context, String path) {
		MediaPlayer mMediaPlayer = null;
		long duration = 0;
		try {
			mMediaPlayer = new MediaPlayer();
			mMediaPlayer.setDataSource(context, Uri.parse(path));
			mMediaPlayer.prepare();
			duration = mMediaPlayer.getDuration();
		} catch (Exception e) {
			if (e != null)
				e.printStackTrace();
		} finally {
			if (mMediaPlayer != null) {
				mMediaPlayer.reset();
				mMediaPlayer.release();
				mMediaPlayer = null;
			}
		}
		return duration;
	}

	/**
	 * Get path of video from uri
	 * 
	 * @param contentResolver
	 * @param contentURI
	 * @return path of video. Null if not found.
	 */
	public static String getRealPathFromURI(ContentResolver contentResolver,
			Uri contentURI) {
		Cursor cursor = contentResolver.query(contentURI, null, null, null,
				null);
		if (cursor == null)
			return contentURI.getPath();
		else {
			cursor.moveToFirst();
			int idx = cursor.getColumnIndex(MediaStore.Video.VideoColumns.DATA);
			try {
				return cursor.getString(idx);
			} catch (Exception exception) {
				return null;
			}
		}
	}

}
