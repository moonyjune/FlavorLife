package com.ntq.utils;

import android.media.MediaPlayer;
import android.text.TextUtils;

/**
 * 
 * @author TUNGDX
 *
 */

/**
 * All utils for Audio.
 * 
 * 
 */
public class AudioUtils {
	/**
	 * Get duration of audio.
	 * 
	 * @param path
	 *            Path of audio.
	 * @return duration of auido. 0 if path is empty.
	 */
	public static long getDuration(String path) {
		long result = 0;
		if (!TextUtils.isEmpty(path)) {
			try {
				MediaPlayer mMediaPlayer = new MediaPlayer();
				mMediaPlayer.setDataSource(path);
				mMediaPlayer.prepare();
				result = mMediaPlayer.getDuration();
				if (mMediaPlayer.isPlaying())
					mMediaPlayer.pause();
				mMediaPlayer.stop();
				mMediaPlayer.reset();
				mMediaPlayer.release();
				mMediaPlayer = null;
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return result;
	}
}
