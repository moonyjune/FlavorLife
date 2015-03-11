package com.ntq.uploadmanager;

/**
 * 
 * @author TUNGDX
 *
 */
/**
 * Response code when upload. Use in {@link AbstractUploader} and inherit class.
 * 
 */
public interface UploadResponseCode {
	public int NO_RESPONSE = 0;
	public int ERROR_UNKNOWN = 100;
	public int ERROR_LOST_CONNECTION = 101;
}
