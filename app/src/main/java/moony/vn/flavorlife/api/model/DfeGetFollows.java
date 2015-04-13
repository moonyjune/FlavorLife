package moony.vn.flavorlife.api.model;

import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import moony.vn.flavorlife.api.Api;
import moony.vn.flavorlife.api.ApiKey;
import moony.vn.flavorlife.entities.Follow;
import moony.vn.flavorlife.entities.Recipe;

/**
 * Created by moony on 3/4/15.
 */
public class DfeGetFollows extends FlPaginatedList<Follow> {
    private Api mApi;

    public DfeGetFollows(Api api) {
        super();
        mApi = api;
    }

    @Override
    protected Request<JSONObject> makeRequest(int skip, int take, Date requestDate) {
        return mApi.getFollows(skip, take, requestDate, this, this);
    }

    @Override
    protected List<Follow> parseResponse(JSONObject response) {
        try {
            JSONArray data = response.getJSONArray(ApiKey.DATA);
            Gson gson = new Gson();
            List<Follow> follows = gson.fromJson(data.toString(), new TypeToken<Collection<Follow>>() {
            }.getType());
            return follows;
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
