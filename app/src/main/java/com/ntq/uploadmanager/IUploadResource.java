package com.ntq.uploadmanager;

import android.net.Uri;

/**
 * 
 * @author TUNGDX
 *
 */
/**
 * Resource for upload.
 * 
 */
public interface IUploadResource {
	/**
	 * Path file to upload. Maybe think about {@link Uri} instead of path file
	 * in higher version.
	 * 
	 * @return path file to upload.
	 */
	public String getFilePath();

	/**
	 * Extra resource
	 * 
	 * @return
	 */
	public Object getResourceExtras();
}
