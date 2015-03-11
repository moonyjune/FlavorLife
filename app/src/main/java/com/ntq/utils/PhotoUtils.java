package com.ntq.utils;

import java.io.File;
import java.io.IOException;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.provider.MediaStore;

/**
 * 
 * @author TUNGDX
 * 
 */

/**
 * 
 * All utils for image, bitmap.
 * 
 */
public class PhotoUtils {
	/**
	 * Add file photo to gallery after capture from camera or downloaded.
	 * 
	 * @param context
	 * @param file
	 */
	public static void galleryAddPic(Context context, File file) {
		Intent mediaScanIntent = new Intent(
				Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
		Uri contentUri = Uri.fromFile(file);
		mediaScanIntent.setData(contentUri);
		context.sendBroadcast(mediaScanIntent);
	}

	public static Bitmap decodeSampledBitmapFromFile(String pathFile,
			int reqWidth, int reqHeight) {

		// First decode with inJustDecodeBounds=true to check dimensions
		final BitmapFactory.Options options = new BitmapFactory.Options();
		options.inJustDecodeBounds = true;
		BitmapFactory.decodeFile(pathFile, options);

		// Calculate inSampleSize
		options.inSampleSize = calculateInSampleSize(options, reqWidth,
				reqHeight);

		// Decode bitmap with inSampleSize set
		options.inJustDecodeBounds = false;
		return BitmapFactory.decodeFile(pathFile, options);
	}

	public static int calculateInSampleSize(BitmapFactory.Options options,
			int reqWidth, int reqHeight) {
		// Raw height and width of image
		final int height = options.outHeight;
		final int width = options.outWidth;
		int inSampleSize = 1;

		if (height > reqHeight || width > reqWidth) {

			final int halfHeight = height / 2;
			final int halfWidth = width / 2;

			// Calculate the largest inSampleSize value that is a power of 2 and
			// keeps both
			// height and width larger than the requested height and width.
			while ((halfHeight / inSampleSize) > reqHeight
					&& (halfWidth / inSampleSize) > reqWidth) {
				inSampleSize *= 2;
			}
		}

		return inSampleSize;
	}

	/**
	 * Get path of image from uri
	 * 
	 * @param contentResolver
	 * @param contentURI
	 * @return path of image. Null if not found.
	 */
	public static String getRealPathFromURI(ContentResolver contentResolver,
			Uri contentURI) {
		Cursor cursor = contentResolver.query(contentURI, null, null, null,
				null);
		if (cursor == null)
			return contentURI.getPath();
		else {
			cursor.moveToFirst();
			int idx = cursor
					.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
			try {
				return cursor.getString(idx);
			} catch (Exception exception) {
				return null;
			}
		}
	}

	/**
	 * Get angle of image.
	 * 
	 * @param path
	 *            Path of image.
	 * @return
	 */
	public static int getAngle(String path) {
		int angle = 0;
		ExifInterface exif;
		try {
			exif = new ExifInterface(path);
			int orientation = exif.getAttributeInt(
					ExifInterface.TAG_ORIENTATION,
					ExifInterface.ORIENTATION_NORMAL);

			if (orientation == ExifInterface.ORIENTATION_ROTATE_90) {
				angle = 90;
			} else if (orientation == ExifInterface.ORIENTATION_ROTATE_180) {
				angle = 180;
			} else if (orientation == ExifInterface.ORIENTATION_ROTATE_270) {
				angle = 270;
			}
			return angle;
		} catch (IOException e) {
			e.printStackTrace();
			return 0;
		}
	}

	/**
	 * Rotate image
	 * 
	 * @param mBitmap
	 * @param angle
	 *            The angle desire of bitmap
	 * @return
	 */
	public static Bitmap rotateImage(Bitmap mBitmap, int angle) {
		if (mBitmap != null && angle != 0) {
			Matrix mat = new Matrix();
			mat.postRotate(angle);

			mBitmap = Bitmap.createBitmap(mBitmap, 0, 0, mBitmap.getWidth(),
					mBitmap.getHeight(), mat, true);
		}
		return mBitmap;
	}
}
