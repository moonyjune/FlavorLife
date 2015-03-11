package com.ntq.utils;

import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;

/**
 * 
 * @author TUNGDX
 *
 */

/**
 * 
 * All utils for keyboard.
 * 
 */
public class KeyboardUtils {
	/**
	 * Hide keyboard.
	 * 
	 * @param activity
	 */
	public static void hideSoftKeyboard(Activity activity) {
		InputMethodManager inputMethodManager = (InputMethodManager) activity
				.getSystemService(Activity.INPUT_METHOD_SERVICE);
		if (activity.getCurrentFocus() != null
				&& activity.getCurrentFocus().getWindowToken() != null)
			inputMethodManager.hideSoftInputFromWindow(activity
					.getCurrentFocus().getWindowToken(), 0);
	}

	/**
	 * Hide keyboard when touch outside view.
	 * 
	 * @param activity
	 * @param view
	 *            view root
	 */
	public static void hideKeyboard(final Activity activity, View view) {
		if (activity == null) {
			return;
		}
		// Set up touch listener for non-text box views to hide keyboard.
		if (!(view instanceof EditText) && !(view instanceof Button)) {
			view.setOnTouchListener(new OnTouchListener() {

				@Override
				public boolean onTouch(View v, MotionEvent event) {
					hideSoftKeyboard(activity);
					return false;
				}
			});
		}

		// If a layout container, iterate over children and seed recursion.
		if (view instanceof ViewGroup) {
			int size = ((ViewGroup) view).getChildCount();
			for (int i = 0; i < size; i++) {
				View innerView = ((ViewGroup) view).getChildAt(i);
				hideKeyboard(activity, innerView);
			}
		}
	}

	/**
	 * Show keyboard
	 * 
	 * @param view
	 */
	public static void showKeyboard(View view) {
		if (view.isFocused()) {
			view.clearFocus();
		}
		view.requestFocus();
		InputMethodManager keyboard = (InputMethodManager) view.getContext()
				.getSystemService(Context.INPUT_METHOD_SERVICE);
		keyboard.showSoftInput(view, InputMethodManager.SHOW_FORCED);
	}

	/**
	 * Show keyboard after a delay
	 * 
	 * @param view
	 * @param timeDelay
	 * @return {@link Handler}. Should call
	 *         {@link Handler#removeCallbacksAndMessages(Object)} with
	 *         parameter= null when view is destroyed to avoid memory leak.
	 */
	public static Handler showDelayKeyboard(final View view, long timeDelay) {
		Handler handler = new Handler();
		if (view == null || view.getContext() == null) {
			return handler;
		}

		if (timeDelay < 0) {
			timeDelay = 0;
		}
		view.requestFocus();
		Runnable delayRunnable = new Runnable() {

			@Override
			public void run() {
				InputMethodManager keyboard = (InputMethodManager) view
						.getContext().getSystemService(
								Context.INPUT_METHOD_SERVICE);
				keyboard.showSoftInput(view, InputMethodManager.SHOW_FORCED);
			}
		};
		handler.postDelayed(delayRunnable, timeDelay);
		return handler;
	}
}
