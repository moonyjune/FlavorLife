package moony.vn.flavorlife.api.model;

import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import moony.vn.flavorlife.api.Api;
import moony.vn.flavorlife.api.ApiImpl;
import moony.vn.flavorlife.entities.Recipe;
import moony.vn.flavorlife.utils.DateFormatUtils;

/**
 * Created by moony on 3/4/15.
 */
public class DfeGetUserRecipes extends FlPaginatedList<Recipe> {
    private Api mApi;
    private int mUserId;

    public DfeGetUserRecipes(Api api, int userId) {
        super();
        mApi = api;
        mUserId = userId;
    }

    @Override
    protected Request<JSONObject> makeRequest(int skip, int take, Date requestDate) {
        return mApi.getUserRecipes(mUserId, skip, take, requestDate, this, this);
    }

    @Override
    protected List<Recipe> parseResponse(JSONObject response) {
        try {
            JSONArray data = response.getJSONArray(ApiImpl.DATA);
            Gson gson = new GsonBuilder().setDateFormat(DateFormatUtils.DATE_FORMAT).create();
            List<Recipe> recipes = gson.fromJson(data.toString(), new TypeToken<Collection<Recipe>>() {
            }.getType());
            return recipes;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void onErrorResponse(VolleyError volleyerror) {
        super.onErrorResponse(volleyerror);
    }
}
