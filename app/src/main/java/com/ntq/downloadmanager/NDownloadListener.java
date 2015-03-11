package com.ntq.downloadmanager;

import android.app.DownloadManager;
import android.net.Uri;

/**
 * 
 * @author TUNGDX
 * 
 */
/**
 * Listener when download.
 * 
 */
public interface NDownloadListener {
	public void onDownloadPending(long downloadId, int progress);

	public void onDownloadRunning(long downloadId, int progress);

	public void onDownloadPaused(long downloadId, int progress);

	/**
	 * 
	 * @param downloadId
	 * @param fileUri
	 *            Value in column {@link DownloadManager#COLUMN_LOCAL_URI}
	 */
	public void onDownloadSuccessful(long downloadId, Uri fileUri);

	/**
	 * 
	 * @param downloadId
	 * @param progress
	 * @param reason
	 *            Value in column {@link DownloadManager#COLUMN_REASON}
	 */
	public void onDownloadFailed(long downloadId, int progress, int reason);
}
