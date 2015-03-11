package com.ntq.uploadmanager;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;

import com.ntq.uploadmanager.db.UploadDbManager;
import com.ntq.uploadmanager.db.UploadTable;

/**
 * 
 * @author TUNGDX
 *
 */
/**
 * Manage state of upload resource. This contains one database to save state of
 * upload, state as table {@link UploadTable}. Your app should extends this
 * class to handle your upload service. There is default class this is
 * {@link NUploadService}
 * <p/>
 * Contains some features:
 * <ul>
 * <li>Execute upload {@link #executeUpload(IUploadResource)}</li>
 * <li>
 * Query state upload via uploadId {@link #getState(long)}</li>
 * <li>
 * Check uploadId success or not</li>
 * <li>Get upload progress percent via uploadId</li>
 * </ul>
 * <p/>
 * <i>Note: Must initialize implementor of {@link AbstractUploader} in
 * {@link #onCreate()}</i>
 * 
 * @see NUploadService
 */
public class UploadService extends Service implements IUploadCallback {
	protected UploadDbManager mUploadDBManager;
	protected AbstractUploader mUploader;
	private final IBinder mBinder = new UploadServiceBinder();

	@Override
	public void onCreate() {
		super.onCreate();
		mUploadDBManager = UploadDbManager.getInstance(getApplicationContext());
		mUploadDBManager.open();
		// TODO must initialize implementor of UploadManager here.
		mUploader = null;
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		mUploadDBManager.close();
	}

	public class UploadServiceBinder extends Binder {
		public UploadService getService() {
			return UploadService.this;
		}
	}

	@Override
	public IBinder onBind(Intent intent) {
		return mBinder;
	}

	@Override
	public void onPending(long uploadId) {
		mUploadDBManager.updateUploadRequest(uploadId, UploadState.PENDING, 0,
				UploadResponseCode.NO_RESPONSE, null);
	}

	@Override
	public void onRunnning(long uploadId, int progress) {
		mUploadDBManager.updateUploadRequest(uploadId, UploadState.RUNNING,
				progress, UploadResponseCode.NO_RESPONSE, null);
	}

	@Override
	public void onSuccess(long uploadId, int responseCode, Object response) {
		mUploadDBManager.updateUploadRequest(uploadId, UploadState.SUCCESSFUL,
				100, responseCode, response);
	}

	@Override
	public void onFailed(long uploadId, int responseCode, Object response) {
		mUploadDBManager.updateUploadRequest(uploadId, UploadState.FAILED, 0,
				responseCode, response);
	}

	/**
	 * Upload added to database.
	 * 
	 * @param uploadId
	 * @param uploadResource
	 */
	public void onAdded(long uploadId, IUploadResource uploadResource) {
		/*
		 * Do nothing, useful for class that extended to manage relationship
		 * between uploadId and uploadResource.
		 */
	}

	@Override
	public void onPaused(long uploadId, int responseCode) {
		mUploadDBManager.updateUploadRequest(uploadId, UploadState.PAUSED, 0,
				responseCode, null);
	}

	/**
	 * Cancel upload
	 * 
	 * @param uploadId
	 *            Id to cancel
	 */
	public void cancle(long uploadId) {
		mUploadDBManager.cancleUploadRequest(uploadId);
		mUploader.cancle(uploadId);
	}

	/**
	 * Execute upload
	 * 
	 * @param uploadResource
	 * @return uploadId
	 * @see #cancle(long)
	 */
	public synchronized long executeUpload(IUploadResource uploadResource) {
		long uploadId = mUploadDBManager.addUploadRequest(uploadResource);
		onAdded(uploadId, uploadResource);
		mUploader.upload(uploadId, uploadResource);
		return uploadId;
	}

	/**
	 * Get state of upload.
	 * 
	 * @param uploadId
	 * @return state of upload. State in {@link UploadState}
	 * @see #isUploadSuccess(long)
	 * @see #getProgress(long)
	 */
	public int getState(long uploadId) {
		return mUploadDBManager.queryUploadState(uploadId);
	}

	/**
	 * Check upload success or not.
	 * 
	 * @param uploadId
	 * @return true if success, false otherwise.
	 * @see #getState(long)
	 * @see #getProgress(long)
	 */
	public boolean isUploadSuccess(long uploadId) {
		return mUploadDBManager.queryUploadState(uploadId) == UploadState.SUCCESSFUL ? true
				: false;
	}

	/**
	 * Get upload progress percent.
	 * 
	 * @param uploadId
	 * @return percent of progress (range: 0-100)
	 * @see #getState(long)
	 * @see #isUploadSuccess(long)
	 */
	public int getProgress(long uploadId) {
		return mUploadDBManager.queryUploadProgress(uploadId);
	}
}
