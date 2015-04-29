package moony.vn.flavorlife.api.model;

import com.android.volley.Response;
import com.ntq.api.model.DfeModel;

import org.json.JSONException;
import org.json.JSONObject;

import moony.vn.flavorlife.api.Api;
import moony.vn.flavorlife.api.ApiKey;

/**
 * Created by moony on 4/11/15.
 */
public class DfeFollow extends DfeModel implements Response.Listener<JSONObject> {
    private Api mApi;
    private int numFollowers = -1;

    public DfeFollow(Api mApi) {
        this.mApi = mApi;
    }

    @Override
    public boolean isReady() {
        return numFollowers != -1;
    }

    public void makeRequest(int followUserId) {
        mApi.follow(followUserId, this, this);
    }

    @Override
    public void onResponse(JSONObject response) {
        try {
            numFollowers = response.getInt(ApiKey.NUMBER_FOLLOWERS);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        notifyDataSetChanged();
    }

    public int getNumFollowers() {
        return numFollowers;
    }
}
