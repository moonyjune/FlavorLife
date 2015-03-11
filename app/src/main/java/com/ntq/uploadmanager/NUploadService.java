package com.ntq.uploadmanager;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentLinkedQueue;

import com.ntq.utils.Utils;

/**
 * 
 * @author TUNGDX
 *
 */
/**
 * Add some new features: Add listener to keep track state of upload.
 * <p/>
 * How to use:
 * <ul>
 * <li>Step 1: Declare this service in AndroidManifest.xml</li>
 * <li>Step 2: Create new class extends {@link AbstractUploader} and handle your
 * upload logic, after that assign this class in
 * {@link UploadService#onCreate()}</li>
 * <li>Step 3: Bind to this service to use.</li>
 * </ul>
 * 
 */
public class NUploadService extends UploadService {
	private Map<Long, IUploadResource> mUploadResources = new HashMap<Long, IUploadResource>();
	private ConcurrentLinkedQueue<NUploadListener> linkedQueue = new ConcurrentLinkedQueue<NUploadListener>();

	/**
	 * Add listener to keep track upload state. Must call
	 * {@link #removeUploadCustomListener(NUploadListener)} when not need keep
	 * track upload state (usually in onDetroy() method...) to avoid leak
	 * memory.
	 * 
	 * @param uploadCustom
	 */
	public synchronized void addUploadCustomListener(
			NUploadListener uploadCustom) {
		if (!linkedQueue.contains(uploadCustom))
			linkedQueue.add(uploadCustom);
	}

	/**
	 * Remove listener that keep track upload state.
	 * 
	 * @param uploadCustom
	 */
	public synchronized void removeUploadCustomListener(
			NUploadListener uploadCustom) {
		linkedQueue.remove(uploadCustom);
	}

	@Override
	public synchronized void onAdded(long uploadId,
			IUploadResource uploadResource) {
		super.onAdded(uploadId, uploadResource);
		Utils.ensureOnMainThread();
		mUploadResources.put(uploadId, uploadResource);
		Iterator<NUploadListener> iterator = linkedQueue.iterator();
		while (iterator.hasNext()) {
			NUploadListener custom = iterator.next();
			custom.onUploadAdded(uploadId, uploadResource);
		}
	}

	@Override
	public synchronized void onPending(long uploadId) {
		super.onPending(uploadId);
		Utils.ensureOnMainThread();
		Iterator<NUploadListener> iterator = linkedQueue.iterator();
		while (iterator.hasNext()) {
			NUploadListener custom = iterator.next();
			custom.onUploadPending(uploadId, mUploadResources.get(uploadId));
		}
	}

	@Override
	public synchronized void onRunnning(long uploadId, int progress) {
		super.onRunnning(uploadId, progress);
		Utils.ensureOnMainThread();
		Iterator<NUploadListener> iterator = linkedQueue.iterator();
		while (iterator.hasNext()) {
			NUploadListener custom = iterator.next();
			custom.onUploadInprogress(uploadId, mUploadResources.get(uploadId),
					progress);
		}
	}

	@Override
	public synchronized void onSuccess(long uploadId, int responseCode,
			Object response) {
		super.onSuccess(uploadId, responseCode, response);
		Utils.ensureOnMainThread();
		Iterator<NUploadListener> iterator = linkedQueue.iterator();
		while (iterator.hasNext()) {
			NUploadListener custom = iterator.next();
			custom.onUploadSuccess(uploadId, mUploadResources.get(uploadId),
					responseCode, response);
		}
	}

	@Override
	public synchronized void onFailed(long uploadId, int responseCode,
			Object response) {
		super.onFailed(uploadId, responseCode, response);
		Utils.ensureOnMainThread();
		Iterator<NUploadListener> iterator = linkedQueue.iterator();
		while (iterator.hasNext()) {
			NUploadListener custom = iterator.next();
			custom.onUploadFailed(uploadId, mUploadResources.get(uploadId),
					responseCode, response);
		}
	}

	@Override
	public synchronized void onPaused(long uploadId, int responseCode) {
		super.onPaused(uploadId, responseCode);
		Utils.ensureOnMainThread();
		Iterator<NUploadListener> iterator = linkedQueue.iterator();
		while (iterator.hasNext()) {
			NUploadListener custom = iterator.next();
			custom.onUploadPaused(uploadId, mUploadResources.get(uploadId),
					responseCode);
		}
	}
}
