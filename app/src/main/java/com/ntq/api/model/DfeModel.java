package com.ntq.api.model;

import java.util.HashSet;

import com.android.volley.Response.ErrorListener;
import com.android.volley.VolleyError;

/**
 * 
 * @author TUNGDX
 * 
 */
public abstract class DfeModel implements ErrorListener {
	private HashSet<ErrorListener> mErrorListeners;
	private HashSet<OnDataChangedListener> mListeners;
	private VolleyError mVolleyError;

	public DfeModel() {
		mErrorListeners = new HashSet<ErrorListener>();
		mListeners = new HashSet<OnDataChangedListener>();
	}

	public final void addDataChangedListener(
			OnDataChangedListener ondatachangedlistener) {
		mListeners.add(ondatachangedlistener);
	}

	public final void addErrorListener(ErrorListener errorlistener) {
		mErrorListeners.add(errorlistener);
	}

	protected void clearErrors() {
		mVolleyError = null;
	}

	public VolleyError getVolleyError() {
		return mVolleyError;
	}

	public boolean inErrorState() {
		return mVolleyError == null ? false : true;
	}

	public abstract boolean isReady();

	protected void notifyDataSetChanged() {
		for (OnDataChangedListener onDataChangedListener : mListeners) {
			onDataChangedListener.onDataChanged();
		}
	}

	protected void notifyErrorOccured(VolleyError volleyerror) {
		for (ErrorListener errorListener : mErrorListeners) {
			errorListener.onErrorResponse(volleyerror);
		}
	}

	@Override
	public void onErrorResponse(VolleyError volleyerror) {
		mVolleyError = volleyerror;
		notifyErrorOccured(volleyerror);
	}

	public final void removeDataChangedListener(
			OnDataChangedListener ondatachangedlistener) {
		mListeners.remove(ondatachangedlistener);
	}

	public final void removeErrorListener(ErrorListener errorlistener) {
		mErrorListeners.remove(errorlistener);
	}

	public final void unregisterAll() {
		mListeners.clear();
		mErrorListeners.clear();
	}
}
