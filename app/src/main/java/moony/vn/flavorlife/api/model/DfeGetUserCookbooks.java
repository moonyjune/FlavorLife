package moony.vn.flavorlife.api.model;

import com.android.volley.Response;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.ntq.api.model.DfeModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import moony.vn.flavorlife.api.Api;
import moony.vn.flavorlife.api.ApiImpl;
import moony.vn.flavorlife.entities.Cookbook;
import moony.vn.flavorlife.entities.Recipe;
import moony.vn.flavorlife.utils.DateFormatUtils;

/**
 * Created by moony on 4/30/15.
 */
public class DfeGetUserCookbooks extends DfeModel implements Response.Listener<JSONObject> {
    private Api mApi;
    private List<Cookbook> listCookbooks;

    public DfeGetUserCookbooks(Api mApi) {
        this.mApi = mApi;
    }

    @Override
    public boolean isReady() {
        return listCookbooks != null;
    }

    public void makeRequest() {
        mApi.getUserCookbooks(this, this);
    }

    @Override
    public void onResponse(JSONObject response) {
        listCookbooks = new ArrayList<Cookbook>();
        try {
            JSONArray data = response.getJSONArray(ApiImpl.DATA);
            Gson gson = new GsonBuilder().setDateFormat(DateFormatUtils.DATE_FORMAT).create();
            listCookbooks = gson.fromJson(data.toString(), new TypeToken<Collection<Cookbook>>() {
            }.getType());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        notifyDataSetChanged();
    }

    public List<Cookbook> getListCookbooks() {
        return listCookbooks;
    }
}
