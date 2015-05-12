package moony.vn.flavorlife.api;

import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
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
import org.json.JSONException;
import org.json.JSONObject;

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
    private String api;
    private String recipeId;
    private String userId;
    private String bookId;

    @Override
    protected String doInBackground(String... params) {
        String s = "";
        api = params[0];
        if (api.equals(ApiKey.API_UPLOAD_RECIPE_IMAGE)) {
            recipeId = params[2];
            s = uploadMultipart(recipeId, SERVER_IMAGE + api, params[1]);
        } else if (api.equals(ApiKey.API_UPLOAD_USER_IMAGE)) {
            userId = params[2];
            s = uploadMultipart(userId, SERVER_IMAGE + api, params[1]);
        } else if (api.equals(ApiKey.API_UPLOAD_BOOK_IMAGE)) {
            bookId = params[2];
            s = uploadMultipart(bookId, SERVER_IMAGE + api, params[1]);
        }

        return s;
    }

    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);
        Log.e("Mj", "Result : " + result);
        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject(result);
            int code = jsonObject.getInt(ApiKey.CODE);
            if (code != ApiKey.SUCCESS) {
                onFail(jsonObject);
            } else {
                onSuccess(jsonObject);
            }
        } catch (JSONException e) {
            e.printStackTrace();
            onException(e);
        }
    }

    protected void onException(JSONException e) {

    }

    protected void onSuccess(JSONObject jsonObject) {

    }

    protected void onFail(JSONObject jsonObject) {

    }

    private String uploadMultipart(String id, String url, String imagePath) {
        HttpClient httpClient = new DefaultHttpClient();
        HttpPost httpPost = new HttpPost(url);
        try {
            FileBody bin = new FileBody(new File(imagePath));
            MultipartEntity reqEntity = new MultipartEntity();
            reqEntity.addPart("image", bin);
            reqEntity.addPart("ext", new StringBody("png"));
            String imageName = "";
            if (id.equals(recipeId)) {
                reqEntity.addPart("recipe_id", new StringBody(id));
                imageName = getImageFileName(String.valueOf(FlavorLifeApplication.get().getUser().getId()), id);
            } else if (id.equals(userId)) {
                reqEntity.addPart("user_id", new StringBody(id));
                imageName = getImageFileName(String.valueOf(FlavorLifeApplication.get().getUser().getId()));
            } else if (id.equals(bookId)) {
                reqEntity.addPart("book_id", new StringBody(id));
                imageName = getImageFileName(String.valueOf(FlavorLifeApplication.get().getUser().getId()), id);
            }
            reqEntity.addPart("file_name", new StringBody(imageName));
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

    private String getImageFileName(String userId, String recipeOrBookId) {
        String timeStamp = new SimpleDateFormat(DateFormatUtils.IMAGE_DATE_FORMAT).format(new Date());
        String fileName = APP_NAME + "_" + userId + "_" + recipeOrBookId + "_" + timeStamp;
        return fileName;
    }

    private String getImageFileName(String userId) {
        String timeStamp = new SimpleDateFormat(DateFormatUtils.IMAGE_DATE_FORMAT).format(new Date());
        String fileName = APP_NAME + "_" + userId + "_" + timeStamp;
        return fileName;
    }

}
