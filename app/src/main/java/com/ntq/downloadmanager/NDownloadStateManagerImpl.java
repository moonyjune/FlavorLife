package com.ntq.downloadmanager;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import android.app.DownloadManager;
import android.app.DownloadManager.Query;
import android.content.Context;
import android.database.ContentObserver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Handler;

/**
 * 
 * @author TUNGDX
 *
 */

/**
 * Get out state of download from {@link DownloadManager}
 * 
 */
public class NDownloadStateManagerImpl extends ContentObserver implements
		NDownloadStateManager {
	private Cursor mCursor;
	private DownloadManager mDownloadManager;
	private final List<Long> mListDownloadIds;
	private final List<NDownloadListener> mIDownloadProgresses;

	private static NDownloadStateManagerImpl sAndGDownloadStateManager;

	public static NDownloadStateManagerImpl getInstance(Handler handler,
			Context context) {
		if (sAndGDownloadStateManager == null)
			sAndGDownloadStateManager = new NDownloadStateManagerImpl(handler,
					context);
		return sAndGDownloadStateManager;
	}

	private NDownloadStateManagerImpl(Handler handler, Context context) {
		super(handler);
		context = context.getApplicationContext();
		mDownloadManager = (DownloadManager) context
				.getSystemService(Context.DOWNLOAD_SERVICE);
		mListDownloadIds = new ArrayList<Long>();
		mIDownloadProgresses = new ArrayList<NDownloadListener>();
	}

	@Override
	public int getState(long downloadId) {
		int state = NDownloadState.UNKNOW;
		Query query = new Query();
		query.setFilterById(downloadId);
		Cursor cursor = mDownloadManager.query(query);
		if (cursor != null && cursor.getCount() > 0) {
			try {
				cursor.moveToFirst();
				state = cursor.getInt(cursor
						.getColumnIndex(DownloadManager.COLUMN_STATUS));
			} catch (Exception exception) {
				exception.printStackTrace();
			}
		}
		if (cursor != null) {
			cursor.close();
			cursor = null;
		}
		return state;
	}

	@Override
	public int getProgress(long downloadId) {
		int progress = 0;
		Query query = new Query();
		query.setFilterById(downloadId);
		Cursor cursor = mDownloadManager.query(query);
		if (cursor != null && cursor.getCount() > 0) {
			try {
				cursor.moveToFirst();
				long bytesDownloaded = cursor
						.getLong(cursor
								.getColumnIndex(DownloadManager.COLUMN_BYTES_DOWNLOADED_SO_FAR));
				long bytesTotal = cursor
						.getLong(cursor
								.getColumnIndex(DownloadManager.COLUMN_TOTAL_SIZE_BYTES));
				progress = calculateProgress(bytesDownloaded, bytesTotal);
			} catch (Exception exception) {
				exception.printStackTrace();
			}
		}
		if (cursor != null) {
			cursor.close();
			cursor = null;
		}
		return progress;
	}

	@Override
	public void registerDownloadProgressChange(NDownloadListener progress) {
		synchronized (mIDownloadProgresses) {
			mIDownloadProgresses.add(progress);
		}
	}

	private void syncCursor() {
		if (mCursor != null) {
			mCursor.unregisterContentObserver(this);
			mCursor.close();
			mCursor = null;
		}
		if (mListDownloadIds == null || mListDownloadIds.size() <= 0)
			return;
		long[] ids = toArray(mListDownloadIds);
		Query query = new Query();
		query.setFilterById(ids);
		mCursor = mDownloadManager.query(query);
		if (mCursor != null) {
			if (mCursor.getCount() > 0)
				mCursor.registerContentObserver(this);
			else {
				mCursor.close();
				mCursor = null;
			}
		}
	}

	private long[] toArray(List<Long> list) {
		long[] ids = new long[list.size()];
		int i = 0;
		for (Long item : list) {
			ids[i] = item;
			i++;
		}
		return ids;
	}

	@Override
	public void appendDownloadId(long downloadId) {
		synchronized (mListDownloadIds) {
			// takecare only one downloadId (rightnow)
			mListDownloadIds.clear();
			mListDownloadIds.add(downloadId);
			syncCursor();
		}
	}

	private int calculateProgress(long bytesDownloaded, long bytesTotal) {
		if (bytesTotal == 0)
			return 0;
		return (int) ((bytesDownloaded * 1.0f / bytesTotal) * 100);
	}

	private void notifyState(int state, long downloadId, int progress,
			int reason, Uri fileUri) {
		synchronized (mIDownloadProgresses) {
			Iterator<NDownloadListener> iterator = mIDownloadProgresses
					.iterator();
			while (iterator.hasNext()) {
				NDownloadListener downloadProgress = iterator.next();
				switch (state) {
				case DownloadManager.STATUS_PENDING:
					downloadProgress.onDownloadRunning(downloadId, progress);
					break;
				case DownloadManager.STATUS_RUNNING:
					downloadProgress.onDownloadRunning(downloadId, progress);
					break;
				case DownloadManager.STATUS_PAUSED:
					downloadProgress.onDownloadPaused(downloadId, progress);
					break;
				case DownloadManager.STATUS_SUCCESSFUL:
					downloadProgress.onDownloadSuccessful(downloadId, fileUri);
					break;
				case DownloadManager.STATUS_FAILED:
					downloadProgress.onDownloadFailed(downloadId, progress,
							reason);
					break;
				default:
					break;
				}
			}
		}
	}

	@Override
	public void onChange(boolean selfChange) {
		super.onChange(selfChange);
		if (mListDownloadIds.size() <= 0)
			return;
		long[] ids = toArray(mListDownloadIds);
		Query query = new Query();
		query.setFilterById(ids);
		Cursor cursor = mDownloadManager.query(query);
		if (cursor != null && cursor.getCount() > 0) {
			for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor
					.moveToNext()) {
				int state = cursor.getInt(cursor
						.getColumnIndex(DownloadManager.COLUMN_STATUS));
				long bytesDownloaded = cursor
						.getLong(cursor
								.getColumnIndex(DownloadManager.COLUMN_BYTES_DOWNLOADED_SO_FAR));
				long bytesTotal = cursor
						.getLong(cursor
								.getColumnIndex(DownloadManager.COLUMN_TOTAL_SIZE_BYTES));
				long downloadId = cursor.getLong(cursor
						.getColumnIndex(DownloadManager.COLUMN_ID));
				int progress = calculateProgress(bytesDownloaded, bytesTotal);
				int reason = cursor.getInt(cursor
						.getColumnIndex(DownloadManager.COLUMN_REASON));
				Uri uri = null;
				if (state == DownloadManager.STATUS_SUCCESSFUL) {
					String path = cursor.getString(cursor
							.getColumnIndex(DownloadManager.COLUMN_LOCAL_URI));
					uri = Uri.parse(path);
				}
				notifyState(state, downloadId, progress, reason, uri);
			}
		}
		if (cursor != null) {
			cursor.close();
			cursor = null;
		}
	}

	@Override
	public void unregisterDownloadProgressChange(NDownloadListener progress) {
		synchronized (mIDownloadProgresses) {
			mIDownloadProgresses.remove(progress);
		}
	}

	@Override
	public void removeDownloadId(long downloadId) {
		synchronized (mListDownloadIds) {
			mListDownloadIds.remove(downloadId);
			syncCursor();
		}
	}

	@Override
	public void clearDownloadIds() {
		synchronized (mListDownloadIds) {
			mListDownloadIds.clear();
		}
	}

	@Override
	public void terminate() {
		synchronized (mListDownloadIds) {
			if (mCursor != null) {
				mCursor.close();
				mCursor = null;
			}
			mListDownloadIds.clear();
			mIDownloadProgresses.clear();
		}
	}
}
