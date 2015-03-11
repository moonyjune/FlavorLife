package com.ntq.utils;

import java.io.File;
import java.io.IOException;

import android.content.Context;
import android.os.Environment;

/**
 * 
 * @author TUNGDX
 *
 */

/**
 * 
 * All utils for storage
 * 
 */
public class StorageUtils {
	/**
	 * Create temp file. If has external storage create in external else create
	 * in internal.
	 * 
	 * @param context
	 * @return
	 * @throws IOException
	 */
	public static File createTempFile(Context context) throws IOException {
		if (!hasExternalStorage()) {
			return createTempFile(context, context.getCacheDir());
		} else {
			return createTempFile(context,
					context.getExternalFilesDir("caches"));
		}
	}

	/**
	 * Create temp file in folder
	 * 
	 * @param context
	 * @param folder
	 *            where place temp file
	 * @return
	 * @throws IOException
	 */
	public static File createTempFile(Context context, File folder)
			throws IOException {
		String prefix = String.valueOf(System.currentTimeMillis());
		return File.createTempFile(prefix, null, folder);
	}

	/**
	 * Check external exist or not.
	 * 
	 * @return
	 */
	public static boolean hasExternalStorage() {
		return Environment.getExternalStorageState().equals(
				Environment.MEDIA_MOUNTED);
	}

	/**
	 * Delete file regardless of file is dictionary (folder).
	 * 
	 * @param file
	 */
	public void delete(File file) {
		if (file == null)
			return;
		if (file.isFile()) {
			file.delete();
			return;
		}
		File[] childs = file.listFiles();
		if (childs == null)
			return;

		for (File f : childs) {
			delete(f);
		}
		file.delete();
	}

}
