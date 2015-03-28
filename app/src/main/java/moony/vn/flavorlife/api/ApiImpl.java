package moony.vn.flavorlife.api;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import moony.vn.flavorlife.FlavorLifeApplication;
import moony.vn.flavorlife.entities.Recipe;
import moony.vn.flavorlife.utils.DateFormatUtils;

public class ApiImpl implements Api, ApiKey {

    private final RequestQueue mQueue;

    public ApiImpl(RequestQueue requestQueue) {
        mQueue = requestQueue;
    }

    public String toURL(String api) {
        return new StringBuilder(BASE_URL).append(api).toString();
    }

    @Override
    public Request<JSONObject> register(String email, String introduction, Response.Listener<JSONObject> listener, Response.ErrorListener errorListener) {
        ArrayList<NameValuePair> list = new ArrayList<NameValuePair>();
        list.add(new BasicNameValuePair(EMAIL, email));
        list.add(new BasicNameValuePair(INTRODUCTION, introduction));
        String url = AppRequest.getUrl(toURL(API_REGISTER), list);

        AppRequest appRequest = new AppRequest(Request.Method.POST, url, null, listener, errorListener);
        return mQueue.add(appRequest);
    }

    @Override
    public Request<JSONObject> getNewRecipes(int skip, int take, Response.Listener<JSONObject> listener, Response.ErrorListener errorListener) {
        ArrayList<NameValuePair> list = new ArrayList<NameValuePair>();
        list.add(new BasicNameValuePair(SKIP, String.valueOf(skip)));
        list.add(new BasicNameValuePair(TAKE, String.valueOf(take)));
        String url = AppRequest.getUrl(toURL(API_GET_NEW_RECIPES), list);

        AppRequest appRequest = new AppRequest(Request.Method.GET, url, null, listener, errorListener);
        return mQueue.add(appRequest);
    }

    @Override
    public Request<JSONObject> getUserRecipes(int user_id, int skip, int take, Response.Listener<JSONObject> listener, Response.ErrorListener errorListener) {
        ArrayList<NameValuePair> list = new ArrayList<NameValuePair>();
        list.add(new BasicNameValuePair(USER_ID, String.valueOf(user_id)));
        list.add(new BasicNameValuePair(SKIP, String.valueOf(skip)));
        list.add(new BasicNameValuePair(TAKE, String.valueOf(take)));
        String url = AppRequest.getUrl(toURL(API_GET_USER_RECIPES), list);

        AppRequest appRequest = new AppRequest(Request.Method.GET, url, null, listener, errorListener);
        return mQueue.add(appRequest);
    }

    @Override
    public Request<JSONObject> getCookbooks(int user_id, Response.Listener<JSONObject> listener, Response.ErrorListener errorListener) {
        ArrayList<NameValuePair> list = new ArrayList<NameValuePair>();
        list.add(new BasicNameValuePair(USER_ID, String.valueOf(user_id)));
        String url = AppRequest.getUrl(toURL(API_GET_COOKBOOKS), list);

        AppRequest appRequest = new AppRequest(Request.Method.GET, url, null, listener, errorListener);
        return mQueue.add(appRequest);
    }

    @Override
    public Request<JSONObject> createRecipe(Recipe recipe, Response.Listener<JSONObject> listener, Response.ErrorListener errorListener) {
        ArrayList<NameValuePair> list = new ArrayList<NameValuePair>();
        Gson gson = new GsonBuilder().setDateFormat(DateFormatUtils.DATE_FORMAT).create();
        String data = gson.toJson(recipe);
        list.add(new BasicNameValuePair(DATA, data));
        String url = AppRequest.getUrl(toURL(API_CREATE_RECIPE), list);

        AppRequest appRequest = new AppRequest(Request.Method.POST, url, null, listener, errorListener);
        return mQueue.add(appRequest);
    }
}
