package com.ntq.uploadmanager;

import java.util.HashMap;
import java.util.Map;

/**
 * 
 * @author TUNGDX
 *
 */
/**
 * Handle upload. Extends this class to handle upload logic for specific
 * application. Class inherit should handle {@link #mIUploadCallback} to supply
 * facilities for {@link UploadService}
 * 
 */
abstract class AbstractUploader {
	protected IUploadCallback mIUploadCallback;
	protected Map<Long, IUploadResource> mUploadResources = new HashMap<Long, IUploadResource>();

	public AbstractUploader(IUploadCallback uploadCallback) {
		mIUploadCallback = uploadCallback;
	}

	/**
	 * Start upload.
	 * 
	 * @param uploadId
	 * @param uploadResource
	 */
	public void upload(long uploadId, IUploadResource uploadResource) {
		mUploadResources.put(uploadId, uploadResource);
		execute(uploadId, uploadResource);
	}

	protected abstract void execute(long uploadId,
			IUploadResource uploadResource);

	public void cancle(long uploadId) {
		mUploadResources.remove(uploadId);
	}
}
