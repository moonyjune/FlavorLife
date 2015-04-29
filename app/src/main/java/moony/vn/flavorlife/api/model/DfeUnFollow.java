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
public class DfeUnFollow extends DfeModel implements Response.Listener<JSONObject> {

    private Api mApi;
    private int numberFollowers = -1;

    public DfeUnFollow(Api mApi) {
        this.mApi = mApi;
    }

    @Override
    public boolean isReady() {
        return numberFollowers != -1;
    }

    public void makeRequest(int followUserId) {
        mApi.unFollow(followUserId, this, this);
    }

    @Override
    public void onResponse(JSONObject response) {
        try {
            numberFollowers = response.getInt(ApiKey.NUMBER_FOLLOWERS);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        notifyDataSetChanged();
    }

    public int getNumberFollowers() {
        return numberFollowers;
    }
}
