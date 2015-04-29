package moony.vn.flavorlife.api;

import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;


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
import java.text.SimpleDateFormat;
import java.util.Date;

import moony.vn.flavorlife.Config;
import moony.vn.flavorlife.FlavorLifeApplication;
import moony.vn.flavorlife.utils.DateFormatUtils;

public class UploadImage extends AsyncTask<String, Void, String> {
    private static final String SERVER_IMAGE = Config.SERVER_IMAGES_URL;
    private static final String TAG = "Mj";
    private String data;
    private int SIZE = 64 * 1024;
    private Bitmap bm;
    private String APP_NAME = "flavorlife";

    @Override
    protected String doInBackground(String... params) {
        String api = params[0];
        String recipeId = params[2];
        String s = uploadMultipart(recipeId, SERVER_IMAGE + api, params[1]);
        return s;
    }

    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);
        Log.e("Mj", "Result : " + result);
    }

    private String uploadMultipart(String recipeId, String url, String imagePath) {
        HttpClient httpClient = new DefaultHttpClient();
        HttpPost httpPost = new HttpPost(url);
        try {
            FileBody bin = new FileBody(new File(imagePath));
            MultipartEntity reqEntity = new MultipartEntity();
            reqEntity.addPart("image", bin);
            reqEntity.addPart("ext", new StringBody("png"));
            String imageName = getImageFileName(String.valueOf(FlavorLifeApplication.get().getUser().getId()), recipeId);
            reqEntity.addPart("file_name", new StringBody(imageName));
            reqEntity.addPart("recipe_id", new StringBody(recipeId));

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

    private String getImageFileName(String userId, String recipeId) {
        String timeStamp = new SimpleDateFormat(DateFormatUtils.DATE_FORMAT).format(new Date());
        String fileName = APP_NAME + "_" + userId + "_" + recipeId + "_" + timeStamp;
        return fileName;
    }

}
