package moony.vn.flavorlife.mediapicker;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;

import com.ntq.fragments.NFragment;
import com.ntq.mediapicker.NMediaItem;
import com.ntq.mediapicker.NMediaPickerActivity;

import java.util.ArrayList;

import moony.vn.flavorlife.R;

public class DemoMediaPickerFragment extends NFragment {
	private static final int REQUEST_MEDIA = 100;
	private static final String TAG = "DemoMediaPickerFragment";
	private TextView mMessage;
	private LinearLayout mLinearLayout;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return inflater
				.inflate(R.layout.demo_picker_fragment, container, false);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		getView().findViewById(R.id.all_default).setOnClickListener(
				new OnClickListener() {

					@Override
					public void onClick(View v) {
						NMediaPickerActivity.open(DemoMediaPickerFragment.this,
                                REQUEST_MEDIA);
						clearImages();
					}
				});
		mMessage = (TextView) getView().findViewById(R.id.textView1);
		mLinearLayout = (LinearLayout) getView().findViewById(R.id.list_image);
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		ArrayList<NMediaItem> mMediaSelectedList;
		if (requestCode == REQUEST_MEDIA) {
			if (resultCode == Activity.RESULT_OK) {
				mMediaSelectedList = NMediaPickerActivity
						.getNMediaItemSelected(data);
				if (mMediaSelectedList != null) {

					StringBuilder builder = new StringBuilder();
					for (NMediaItem mediaItem : mMediaSelectedList) {
						Log.i(TAG, mediaItem.toString());
						builder.append(mediaItem.toString());
						builder.append(", PathOrigin=");
						builder.append(mediaItem.getPathOrigin(getActivity()));
						builder.append(", PathCropped=");
						builder.append(mediaItem.getPathCropped(getActivity()));
						builder.append("\n\n");

						addImages(mediaItem);
					}
					mMessage.setText(builder.toString());
				} else {
					Log.e(TAG, "Error to get media, NULL");
				}
			} else {
				Log.e(TAG, "Get media cancled.");
			}
		}
	}

	private void addImages(NMediaItem mediaItem) {
		ImageView imageView = new ImageView(getActivity());
		LayoutParams params = new LayoutParams(180, 180);
		imageView.setLayoutParams(params);
		mLinearLayout.addView(imageView);

		if (mediaItem.getUriCropped() == null) {
			mImageLoader.display(mediaItem.getUriOrigin(), imageView);
		} else {
			mImageLoader.display(mediaItem.getUriCropped(), imageView);
		}
	}

	private void clearImages() {
		mLinearLayout.removeAllViews();
	}
}
