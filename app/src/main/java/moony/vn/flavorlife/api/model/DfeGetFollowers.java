package moony.vn.flavorlife.api.model;

import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Collection;
import java.util.Date;
import java.util.List;

import moony.vn.flavorlife.api.Api;
import moony.vn.flavorlife.api.ApiKey;
import moony.vn.flavorlife.entities.Follower;

/**
 * Created by moony on 3/4/15.
 */
public class DfeGetFollowers extends FlPaginatedList<Follower> {
    private Api mApi;

    public DfeGetFollowers(Api api) {
        super();
        mApi = api;
    }

    @Override
    protected Request<JSONObject> makeRequest(int skip, int take, Date requestDate) {
        return mApi.getFollowers(skip, take, requestDate, this, this);
    }

    @Override
    protected List<Follower> parseResponse(JSONObject response) {
        try {
            JSONArray data = response.getJSONArray(ApiKey.DATA);
            Gson gson = new Gson();
            List<Follower> followers = gson.fromJson(data.toString(), new TypeToken<Collection<Follower>>() {
            }.getType());
            return followers;
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
