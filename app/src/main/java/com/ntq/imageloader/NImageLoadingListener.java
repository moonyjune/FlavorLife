package com.ntq.imageloader;

import android.graphics.Bitmap;
import android.view.View;

public interface NImageLoadingListener {
	/**
	 * Is called when image loading task was started
	 * 
	 * @param imageUri
	 *            Loading image URI
	 * @param view
	 *            View for image
	 */
	void onLoadingStarted(String imageUri, View view);

	/**
	 * Is called when an error was occurred during image loading
	 * 
	 * @param imageUri
	 *            Loading image URI
	 * @param view
	 *            View for image. Can be <b>null</b>.
	 */
	public void onLoadingFailed(String imageUri, View view);

	/**
	 * Is called when image is loaded successfully (and displayed in View if one
	 * was specified)
	 * 
	 * @param imageUri
	 *            Loaded image URI
	 * @param view
	 *            View for image. Can be <b>null</b>.
	 * @param loadedImage
	 *            Bitmap of loaded and decoded image
	 */
	public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage);

	/**
	 * Is called when image loading task was cancelled because View for image
	 * was reused in newer task
	 * 
	 * @param imageUri
	 *            Loading image URI
	 * @param view
	 *            View for image. Can be <b>null</b>.
	 */
	public void onLoadingCancelled(String imageUri, View view);
}
