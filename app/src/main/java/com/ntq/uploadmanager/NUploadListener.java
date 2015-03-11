package com.ntq.uploadmanager;

/**
 * 
 * @author TUNGDX
 * 
 */
/**
 * 
 * Listener when upload.
 */
public interface NUploadListener {
	/**
	 * Upload added to database.
	 * 
	 * @param uploadId
	 * @param uploadResource
	 */
	public void onUploadAdded(long uploadId, IUploadResource uploadResource);

	/**
	 * Upload pending in uploader. {@link AbstractUploader}
	 * 
	 * @param uploadId
	 * @param uploadResource
	 */
	public void onUploadPending(long uploadId, IUploadResource uploadResource);

	/**
	 * Uploading
	 * 
	 * @param uploadId
	 * @param uploadResource
	 * @param progress
	 *            percent of upload (range: 0-100)
	 */
	public void onUploadInprogress(long uploadId,
			IUploadResource uploadResource, int progress);

	/**
	 * Upload success
	 * 
	 * @param uploadId
	 * @param uploadResource
	 * @param responseCode
	 *            response code.
	 * @param response
	 *            response after upload success (maybe from backend: json,
	 *            text...)
	 */
	public void onUploadSuccess(long uploadId, IUploadResource uploadResource,
			int responseCode, Object response);

	/**
	 * Upload failed
	 * 
	 * @param uploadId
	 * @param uploadResource
	 * @param responseCode
	 *            response code.
	 * @param response
	 *            response after upload success (maybe from backend: json,
	 *            text...)
	 */
	public void onUploadFailed(long uploadId, IUploadResource uploadResource,
			int responseCode, Object response);

	/**
	 * Upload paused
	 * 
	 * @param uploadId
	 * @param uploadResource
	 * @param responseCode
	 *            response code.
	 */
	public void onUploadPaused(long uploadId, IUploadResource uploadResource,
			int responseCode);
}
