package com.ntq.downloadmanager;

import android.app.DownloadManager;

/**
 * 
 * @author TUNGDX
 *
 */
/**
 * State of download.
 * 
 */
public interface NDownloadState {
	int PENDING = DownloadManager.STATUS_PENDING;
	int RUNNING = DownloadManager.STATUS_RUNNING;
	int SUCCESSFUL = DownloadManager.STATUS_SUCCESSFUL;
	int PAUSED = DownloadManager.STATUS_PAUSED;
	int FAILED = DownloadManager.STATUS_FAILED;
	int UNKNOW = -1;
}
