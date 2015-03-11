package moony.vn.flavorlife.fragments;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Parcelable;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.view.View;

import com.ntq.fragments.NFragmentSwitcher;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import moony.vn.flavorlife.R;
import moony.vn.flavorlife.activities.MainActivity;

/**
 * Created by moony on 3/4/15.
 */
public class InstructionFragment extends NFragmentSwitcher {
    protected static final String IMAGE_PATH = "image_path";
    private Uri mImageURI;

    public static InstructionFragment newInstance() {
        InstructionFragment instructionFragment = new InstructionFragment();
        Bundle bundle = new Bundle();
        instructionFragment.setArguments(bundle);
        return instructionFragment;
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_instruction;
    }

    @Override
    protected void requestData() {

    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivityCamera();
            }
        });
    }

    public void startActivityCamera() {
        mImageURI = getOutputFilePath("FlavorLife");
        if (mImageURI != null) {
            final Intent captureIntent = new Intent(
                    android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
            captureIntent.putExtra(MediaStore.EXTRA_OUTPUT, mImageURI);
            captureIntent.putExtra(IMAGE_PATH, mImageURI.getPath());
            captureIntent.putExtra("return-data", true);
            Intent intent = new Intent(
                    Intent.ACTION_PICK,
                    android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            Intent chooserIntent = Intent.createChooser(intent,
                    "Choose a picture from");
            // Set camera intent to file chooser
            chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS,
                    new Parcelable[]{captureIntent});
            getActivity().startActivityForResult(chooserIntent, 101);
        }
    }

    private Uri getOutputFilePath(String appName) {
        File dir = null;
        String state = Environment.getExternalStorageState();

        if (Environment.MEDIA_MOUNTED.equals(state)) {
            dir = new File(Environment.getExternalStorageDirectory().toString() + "/DCIM/" + appName);
            dir.mkdirs();
        } else {
            dir = new File(getActivity().getCacheDir().toString() + "/" + appName);
        }
        String timeStamp = new SimpleDateFormat("yyyy/MM/dd_HH:mm:ss").format(new Date());
        String fileName = appName + "_" + timeStamp;
        Uri mImageUri = Uri.fromFile(new File(dir, fileName + ".jpg"));

        galleryAddPic(getActivity(), mImageUri);
        return mImageUri;
    }

    public static void galleryAddPic(Context context, Uri uriFile) {
        Intent mediaScanIntent = new Intent(
                Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        mediaScanIntent.setData(uriFile);
        context.sendBroadcast(mediaScanIntent);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }
}
