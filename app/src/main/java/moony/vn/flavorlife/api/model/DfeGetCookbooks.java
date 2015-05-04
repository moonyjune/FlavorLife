package moony.vn.flavorlife.api.model;

import com.android.volley.Request;
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
import moony.vn.flavorlife.entities.Cookbook;
import moony.vn.flavorlife.utils.DateFormatUtils;

/**
 * Created by moony on 3/11/15.
 */
public class DfeGetCookbooks extends FlPaginatedList<Cookbook> {
    private Api mApi;
    private int mUserId;

    public DfeGetCookbooks(Api api, int userId) {
        mApi = api;
        mUserId = userId;
    }

    @Override
    protected Request<JSONObject> makeRequest(int skip, int take, Date requestDate) {
        return mApi.getUserCookbooks(mUserId, this, this);
    }

    @Override
    protected List<Cookbook> parseResponse(JSONObject response) {
        JSONArray data = null;
        try {
            data = response.getJSONArray(ApiImpl.DATA);
            Gson gson = new GsonBuilder().setDateFormat(DateFormatUtils.DATE_FORMAT).create();
            List<Cookbook> cookbooks = gson.fromJson(data.toString(), new TypeToken<Collection<Cookbook>>() {
            }.getType());
            return cookbooks;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }
}
