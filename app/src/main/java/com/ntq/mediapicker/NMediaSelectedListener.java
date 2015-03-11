package com.ntq.mediapicker;

import java.util.List;

/**
 * 
 * @author TUNGDX
 *
 */
/**
 * Listener for select media item.
 * 
 */
interface NMediaSelectedListener {
	public void onHasNoSelected();

	public void onHasSelected(List<NMediaItem> mediaSelectedList);
}
