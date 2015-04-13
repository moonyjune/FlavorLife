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
import java.util.Date;

import moony.vn.flavorlife.FlavorLifeApplication;
import moony.vn.flavorlife.entities.Recipe;
import moony.vn.flavorlife.utils.DateFormatUtils;

public class ApiImpl implements Api, ApiKey {

    private final RequestQueue mQueue;
    private final SimpleDateFormat mRequestTimeFormat;

    public ApiImpl(RequestQueue requestQueue) {
        mQueue = requestQueue;
        mRequestTimeFormat = new SimpleDateFormat(DateFormatUtils.REQUEST_TIME_FORMAT);
    }

    public String toURL(String api) {
        return new StringBuilder(BASE_URL).append(api).toString();
    }

    @Override
    public Request<JSONObject> register(String email, Response.Listener<JSONObject> listener, Response.ErrorListener errorListener) {
        ArrayList<NameValuePair> list = new ArrayList<NameValuePair>();
        list.add(new BasicNameValuePair(EMAIL, email));
        String url = AppRequest.getUrl(toURL(API_REGISTER), list);

        AppRequest appRequest = new AppRequest(Request.Method.POST, url, null, listener, errorListener);
        return mQueue.add(appRequest);
    }

    @Override
    public Request<JSONObject> getNewRecipes(int skip, int take, Date requestDate, Response.Listener<JSONObject> listener, Response.ErrorListener errorListener) {
        ArrayList<NameValuePair> list = new ArrayList<NameValuePair>();
        list.add(new BasicNameValuePair(SKIP, String.valueOf(skip)));
        list.add(new BasicNameValuePair(TAKE, String.valueOf(take)));
        if (requestDate != null) {
            list.add(new BasicNameValuePair(REQUEST_TIME, mRequestTimeFormat.format(requestDate)));
        }
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
    public Request<JSONObject> getUserCookbooks(int user_id, Response.Listener<JSONObject> listener, Response.ErrorListener errorListener) {
        ArrayList<NameValuePair> list = new ArrayList<NameValuePair>();
        list.add(new BasicNameValuePair(USER_ID, String.valueOf(user_id)));
        String url = AppRequest.getUrl(toURL(API_GET_COOKBOOKS), list);

        AppRequest appRequest = new AppRequest(Request.Method.GET, url, null, listener, errorListener);
        return mQueue.add(appRequest);
    }

    @Override
    public Request<JSONObject> getRecipeDetail(int recipeId, Response.Listener<JSONObject> listener, Response.ErrorListener errorListener) {
        return null;
    }

    @Override
    public Request<JSONObject> getFollows(int skip, int take, Date requestDate, Response.Listener<JSONObject> listener, Response.ErrorListener errorListener) {
        ArrayList<NameValuePair> list = new ArrayList<NameValuePair>();
        list.add(new BasicNameValuePair(SKIP, String.valueOf(skip)));
        list.add(new BasicNameValuePair(TAKE, String.valueOf(take)));
        list.add(new BasicNameValuePair(USER_ID, String.valueOf(FlavorLifeApplication.get().getUser().getId())));
        if (requestDate != null) {
            list.add(new BasicNameValuePair(REQUEST_TIME, mRequestTimeFormat.format(requestDate)));
        }
        String url = AppRequest.getUrl(toURL(API_GET_FOLLOWS), list);

        AppRequest appRequest = new AppRequest(Request.Method.GET, url, null, listener, errorListener);
        return mQueue.add(appRequest);
    }

    @Override
    public Request<JSONObject> getFollowers(int user_id, Response.Listener<JSONObject> listener, Response.ErrorListener errorListener) {
        return null;
    }

    @Override
    public Request<JSONObject> getBookDetail(int bookId, Response.Listener<JSONObject> listener, Response.ErrorListener errorListener) {
        return null;
    }

    @Override
    public Request<JSONObject> registerGcm(int user_id, String registerId, Response.Listener<JSONObject> listener, Response.ErrorListener errorListener) {
        ArrayList<NameValuePair> list = new ArrayList<NameValuePair>();
        list.add(new BasicNameValuePair(USER_ID, String.valueOf(user_id)));
        list.add(new BasicNameValuePair(GCM_REGISTER_ID, registerId));
        String url = AppRequest.getUrl(toURL(API_REGISTER_GCM), list);

        AppRequest appRequest = new AppRequest(Request.Method.POST, url, null, listener, errorListener);
        return mQueue.add(appRequest);
    }

    @Override
    public Request<JSONObject> login(String email, Response.Listener<JSONObject> listener, Response.ErrorListener errorListener) {
        ArrayList<NameValuePair> list = new ArrayList<NameValuePair>();
        list.add(new BasicNameValuePair(EMAIL, email));
        String url = AppRequest.getUrl(toURL(API_LOGIN), list);

        AppRequest appRequest = new AppRequest(Request.Method.POST, url, null, listener, errorListener);
        return mQueue.add(appRequest);
    }

    @Override
    public Request<JSONObject> updateProfile(String email, Response.Listener<JSONObject> listener, Response.ErrorListener errorListener) {
        return null;
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

    @Override
    public Request<JSONObject> editRecipe(Recipe recipe, Response.Listener<JSONObject> listener, Response.ErrorListener errorListener) {
        return null;
    }

    @Override
    public Request<JSONObject> upgradeRecipe(Recipe recipe, Response.Listener<JSONObject> listener, Response.ErrorListener errorListener) {
        return null;
    }

    @Override
    public Request<JSONObject> likeRecipe(int recipeId, Response.Listener<JSONObject> listener, Response.ErrorListener errorListener) {
        return null;
    }

    @Override
    public Request<JSONObject> unlikeRecipe(int recipeId, Response.Listener<JSONObject> listener, Response.ErrorListener errorListener) {
        return null;
    }

    @Override
    public Request<JSONObject> useRecipe(int recipeId, Response.Listener<JSONObject> listener, Response.ErrorListener errorListener) {
        return null;
    }

    @Override
    public Request<JSONObject> unUseRecipe(int recipeId, Response.Listener<JSONObject> listener, Response.ErrorListener errorListener) {
        return null;
    }

    @Override
    public Request<JSONObject> deleteRecipe(int recipeId, Response.Listener<JSONObject> listener, Response.ErrorListener errorListener) {
        return null;
    }

    @Override
    public Request<JSONObject> follow(int follow_user_id, Response.Listener<JSONObject> listener, Response.ErrorListener errorListener) {
        ArrayList<NameValuePair> list = new ArrayList<NameValuePair>();
        list.add(new BasicNameValuePair(USER_ID, String.valueOf(FlavorLifeApplication.get().getUser().getId())));
        list.add(new BasicNameValuePair(FOLLOW_USER_ID, String.valueOf(follow_user_id)));
        String url = AppRequest.getUrl(toURL(API_FOLLOW), list);

        AppRequest appRequest = new AppRequest(Request.Method.POST, url, null, listener, errorListener);
        return mQueue.add(appRequest);
    }

    @Override
    public Request<JSONObject> unFollow(int user_id, Response.Listener<JSONObject> listener, Response.ErrorListener errorListener) {
        return null;
    }

    @Override
    public Request<JSONObject> searchUsers(int recipeId, Response.Listener<JSONObject> listener, Response.ErrorListener errorListener) {
        return null;
    }

    @Override
    public Request<JSONObject> searchRecipes(int recipeId, Response.Listener<JSONObject> listener, Response.ErrorListener errorListener) {
        return null;
    }

    @Override
    public Request<JSONObject> getUserInformation(int user_id, Response.Listener<JSONObject> listener, Response.ErrorListener errorListener) {
        return null;
    }
}
