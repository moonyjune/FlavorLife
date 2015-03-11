package com.ntq.uploadmanager;

/**
 * All state must call in UI Thread.
 * 
 * @author TUNGDX
 * 
 */
interface IUploadCallback {
	public void onPending(long uploadId);

	public void onRunnning(long uploadId, int progress);

	public void onSuccess(long uploadId, int responseCode, Object response);

	public void onFailed(long uploadId, int responseCode, Object response);

	public void onPaused(long uploadId, int responseCode);
}
