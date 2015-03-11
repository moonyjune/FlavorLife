package com.ntq.uploadmanager.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.ntq.uploadmanager.IUploadResource;
import com.ntq.uploadmanager.UploadState;

public class UploadDbManager {
	private SQLiteDatabase mSqLiteDatabase;
	private UploadDbHelper mDbHelper;
	private static UploadDbManager mDbManager;

	private UploadDbManager(Context context) {
		mDbHelper = new UploadDbHelper(context);
	}

	public static UploadDbManager getInstance(Context context) {
		if (mDbManager == null) {
			mDbManager = new UploadDbManager(context);
		}
		return mDbManager;
	}

	public UploadDbManager open() {
		mSqLiteDatabase = mDbHelper.getWritableDatabase();
		return this;
	}

	public void close() {
		mDbHelper.close();
	}

	public long addUploadRequest(IUploadResource uploadResource) {
		ContentValues values = new ContentValues();
		values.put(UploadTable.FILEPATH, uploadResource.getFilePath());
		values.put(UploadTable.STATE, UploadState.PENDING);
		return mSqLiteDatabase
				.insert(UploadDbHelper.TABLE_UPLOAD, null, values);
	}

	public int queryUploadState(long uploadId) {
		int state = UploadState.UNKNOW;
		String[] columns = new String[] { UploadTable.STATE };
		String selections = UploadTable.ID + "=?";
		String[] selectArgs = new String[] { uploadId + "" };
		Cursor cursor = mSqLiteDatabase.query(UploadDbHelper.TABLE_UPLOAD,
				columns, selections, selectArgs, null, null, null);
		if (cursor != null && cursor.getCount() > 0) {
			cursor.moveToFirst();
			state = cursor.getInt(cursor.getColumnIndex(UploadTable.STATE));
			cursor.close();
			cursor = null;
		}
		return state;
	}

	public void cancleUploadRequest(long uploadId) {
		String whereClauses = UploadTable.ID + "=?";
		String[] args = new String[] { uploadId + "" };
		mSqLiteDatabase.delete(UploadDbHelper.TABLE_UPLOAD, whereClauses, args);
	}

	public void updateUploadRequest(long uploadId, int state, int progress,
			int responseCode, Object response) {
		ContentValues values = new ContentValues();
		values.put(UploadTable.STATE, state);
		values.put(UploadTable.PROGRESS, progress);
		values.put(UploadTable.RESPONSECODE, responseCode);
		if (response != null)
			values.put(UploadTable.RESPONSE, response.toString());
		String whereClauses = UploadTable.ID + "=?";
		String[] whereArgs = new String[] { uploadId + "" };
		mSqLiteDatabase.update(UploadDbHelper.TABLE_UPLOAD, values,
				whereClauses, whereArgs);
	}

	/**
	 * Return progress of uploadId, return 0 if not found
	 * 
	 * @param uploadId
	 * @return
	 */
	public int queryUploadProgress(long uploadId) {
		int progress = 0;
		String[] columns = new String[] { UploadTable.PROGRESS };
		String selections = UploadTable.ID + "=?";
		String[] selectArgs = new String[] { uploadId + "" };
		Cursor cursor = mSqLiteDatabase.query(UploadDbHelper.TABLE_UPLOAD,
				columns, selections, selectArgs, null, null, null);
		if (cursor != null && cursor.getCount() > 0) {
			cursor.moveToFirst();
			progress = cursor.getInt(cursor
					.getColumnIndex(UploadTable.PROGRESS));
			cursor.close();
			cursor = null;
		}
		return progress;
	}
}