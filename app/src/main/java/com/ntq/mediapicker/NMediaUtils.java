package com.ntq.mediapicker;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.MediaStore.Images;
import android.provider.MediaStore.MediaColumns;
import android.provider.MediaStore.Video;
import android.util.Log;

/**
 * 
 * @author TUNGDX
 * 
 */
/**
 * 
 * Utility for Media Picker module.
 * 
 */
class NMediaUtils {
	public static final String[] PROJECT_PHOTO = { MediaColumns._ID };
	public static final String[] PROJECT_VIDEO = { MediaColumns._ID };

	public static Uri getPhotoUri(Cursor cursor) {
		return getMediaUri(cursor, Images.Media.EXTERNAL_CONTENT_URI);
	}

	public static Uri getVideoUri(Cursor cursor) {
		return getMediaUri(cursor, Video.Media.EXTERNAL_CONTENT_URI);
	}

	public static Uri getMediaUri(Cursor cursor, Uri uri) {
		String id = cursor.getString(cursor.getColumnIndex(MediaColumns._ID));
		return Uri.withAppendedPath(uri, id);
	}

	/**
	 * Create an default file for save image from camera.
	 * 
	 * @return
	 * @throws IOException
	 */
	public static File createDefaultImageFile() throws IOException {
		String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss",
				Locale.getDefault()).format(new Date());
		String imageFileName = "JPEG_" + timeStamp + ".jpg";
		File storageDir = Environment
				.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
		if (!storageDir.exists()) {
			storageDir.mkdirs();
		}
		return new File(storageDir, imageFileName);
	}

	public static int getLastImageId(Context context) {
		final String[] imageColumns = { MediaStore.Images.Media._ID };
		final String imageOrderBy = MediaStore.Images.Media._ID + " DESC";
		Cursor cursor = context.getContentResolver().query(
				MediaStore.Images.Media.EXTERNAL_CONTENT_URI, imageColumns,
				null, null, imageOrderBy);
		if (cursor == null)
			return 0;
		int id = 0;
		if (cursor.moveToFirst()) {
			id = cursor.getInt(cursor
					.getColumnIndex(MediaStore.Images.Media._ID));
		}
		cursor.close();
		return id;
	}

	// Should use
	// this: (anwser 2)
	// http://stackoverflow.com/questions/6390163/deleting-a-gallery-image-after-camera-intent-photo-taken
	public static String checkNull(Context context, int lastImageId,
			File fileCapture) {
		final String[] imageColumns = { MediaStore.Images.Media._ID,
				MediaStore.Images.Media.DATA };
		final String imageOrderBy = MediaStore.Images.Media._ID + " DESC";
		final String imageWhere = MediaStore.Images.Media._ID + ">?";
		final String[] imageArguments = { Integer.toString(lastImageId) };

		ContentResolver contentResolver = context.getContentResolver();
		Cursor cursor = contentResolver.query(
				MediaStore.Images.Media.EXTERNAL_CONTENT_URI, imageColumns,
				imageWhere, imageArguments, imageOrderBy);
		if (cursor == null)
			return null;

		String newpath = null;
		Log.e("NMediaUtils", "getCount=" + cursor.getCount());
		if (cursor.getCount() >= 2) {
			for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor
					.moveToNext()) {
				int id = cursor.getInt(cursor
						.getColumnIndex(MediaStore.Images.Media._ID));
				String data = cursor.getString(cursor
						.getColumnIndex(MediaStore.Images.Media.DATA));
				if (data.equals(fileCapture.getPath())) {
					int rows = contentResolver.delete(
							MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
							MediaStore.Images.Media._ID + "=?",
							new String[] { Long.toString(id) });
					boolean ok = fileCapture.delete();
					Log.i("NMediaUtils",
							String.format("rows=%s and delete=%s", rows, ok));

				} else {
					newpath = data;
				}
			}
		} else {
			newpath = fileCapture.getPath();
			Log.e("NMediaUtils", "Not found duplicate.");
		}

		cursor.close();
		return newpath;
	}

	public static String getFileExtension(File file) {
		String name = file.getName();
		int lastIndexOf = name.lastIndexOf(".");
		if (lastIndexOf == -1) {
			return ""; // empty extension
		}
		return name.substring(lastIndexOf);
	}

	public static boolean isImageExtension(String extension) {
		String[] valid = { ".jpg", ".jpeg" };
		for (String ex : valid) {
			if (extension.equalsIgnoreCase(ex)) {
				return true;
			}
		}
		return false;
	}
}
