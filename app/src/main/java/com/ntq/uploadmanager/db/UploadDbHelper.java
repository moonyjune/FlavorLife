package com.ntq.uploadmanager.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class UploadDbHelper extends SQLiteOpenHelper {
	private static final String DB_NAME = "upload_db";
	private static final int DB_VERSION = 1;
	private static String DB_CREATE;
	public static final String TABLE_UPLOAD = "upload";
	static {
		StringBuilder builder = new StringBuilder();
		builder.append("create table ");
		builder.append(TABLE_UPLOAD);
		builder.append("(");
		builder.append(UploadTable.ID);
		builder.append(" integer primary key autoincrement,");
		builder.append(UploadTable.STATE);
		builder.append(" integer not null,");
		builder.append(UploadTable.PROGRESS);
		builder.append(" integer,");
		builder.append(UploadTable.FILEPATH);
		builder.append(" text,");
		builder.append(UploadTable.RESPONSECODE);
		builder.append(" integer,");
		builder.append(UploadTable.RESPONSE);
		builder.append(" text");
		builder.append(");");
		DB_CREATE = builder.toString();
	}

	public UploadDbHelper(Context context) {
		super(context, DB_NAME, null, DB_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(DB_CREATE);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_UPLOAD);
		onCreate(db);
	}

}
