package com.ntq.downloadmanager;

import android.app.DownloadManager;

/**
 * 
 * @author TUNGDX
 * 
 */
/**
 * Help to get state of download id that download by {@link DownloadManager}
 * 
 */
public interface NDownloadStateManager {
	/**
	 * Get state of download.
	 * 
	 * @param downloadId
	 *            id of download to check. Id is value when call
	 *            {@link DownloadManager#enqueue(android.app.DownloadManager.Request)}
	 *            method.
	 * @return Value in {@link NDownloadState}
	 */
	public int getState(long downloadId);

	/**
	 * Get progress percent of download.
	 * 
	 * @param downloadId
	 *            id of download to check. Id is value when call
	 *            {@link DownloadManager#enqueue(android.app.DownloadManager.Request)}
	 *            method.
	 * @return percent downloaded (range: 0-100)
	 */
	public int getProgress(long downloadId);

	/**
	 * Register listener to get progress download.
	 * 
	 * @param progress
	 */
	public void registerDownloadProgressChange(NDownloadListener progress);

	/**
	 * Remove listener that registered by
	 * {@link #registerDownloadProgressChange(NDownloadListener)} method.
	 * 
	 * @param progress
	 */
	public void unregisterDownloadProgressChange(NDownloadListener progress);

	/**
	 * Register id of download to check
	 * 
	 * @param downloadId
	 */
	public void appendDownloadId(long downloadId);

	/**
	 * Remove id of download that registered by {@link #appendDownloadId(long)}.
	 * When remove download id will be not tracked.
	 * 
	 * @param downloadId
	 */
	public void removeDownloadId(long downloadId);

	/**
	 * Stop all track download state.
	 */
	public void terminate();

	/**
	 * Clear all download id that tracked before.
	 */
	public void clearDownloadIds();
}
