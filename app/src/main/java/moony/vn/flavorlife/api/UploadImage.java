package moony.vn.flavorlife.api;

import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;

import com.ntq.Config;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

public class UploadImage extends AsyncTask<String, Void, String> {
    private static final String SERVER_IMAGE = Config.SERVER_IMAGES_URL;
    private static final String TAG = "Mj";
    private String data;
    private int SIZE = 64 * 1024;
    private Bitmap bm;

    @Override
    protected String doInBackground(String... params) {
        String api = params[0];
        String s = uploadMultipart(SERVER_IMAGE + api, params[1]);
        return s;
    }

    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);
        Log.e("Mj", "Result : " + result);
    }

    private String uploadMultipart(String url, String imagePath) {
        HttpClient httpClient = new DefaultHttpClient();
        HttpPost httpPost = new HttpPost(url);
        try {
            FileBody bin = new FileBody(new File(imagePath));
            MultipartEntity reqEntity = new MultipartEntity();
            reqEntity.addPart("image", bin);
            reqEntity.addPart("ext", new StringBody("png"));
            reqEntity.addPart("file_name", new StringBody("image_test"));
            httpPost.setEntity(reqEntity);
            System.out.println("Requesting : " + httpPost.getRequestLine());
            ResponseHandler<String> responseHandler = new BasicResponseHandler();
            String responseBody = httpClient.execute(httpPost, responseHandler);
            System.out.println("responseBody : " + responseBody);
            return responseBody;
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public byte[] compressBitmap(Bitmap bitmap) {
        int quality = 100;
        ByteArrayOutputStream baos;
        baos = new ByteArrayOutputStream();
        bitmap.compress(CompressFormat.JPEG, quality, baos);
        while (baos.size() > SIZE) {
            quality -= 1;
            baos = new ByteArrayOutputStream();
            bitmap.compress(CompressFormat.JPEG, quality, baos);
        }
        return baos.toByteArray();
    }

    public Bitmap getBitmapFromPath(String path) {
        Log.i("Mj", "Path : " + path);
        File file = new File(path);
        if (file.exists()) {
            bm = BitmapFactory.decodeFile(file.getAbsolutePath());
            Log.e("Mj", file.getAbsolutePath() + " : " + bm);
        } else {
            Log.e("Mj", "File is null");
        }
        return bm;
    }

}
