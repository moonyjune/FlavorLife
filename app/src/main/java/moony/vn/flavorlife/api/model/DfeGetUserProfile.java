package moony.vn.flavorlife.api.model;

import com.android.volley.Response;
import com.google.gson.Gson;
import com.ntq.api.model.DfeModel;

import org.json.JSONException;
import org.json.JSONObject;

import moony.vn.flavorlife.api.Api;
import moony.vn.flavorlife.api.ApiKey;
import moony.vn.flavorlife.entities.User;

/**
 * Created by moony on 5/3/15.
 */
public class DfeGetUserProfile extends DfeModel implements Response.Listener<JSONObject> {
    private Api mApi;
    private User mUser;

    public DfeGetUserProfile(Api mApi) {
        this.mApi = mApi;
    }

    @Override
    public boolean isReady() {
        return mUser != null;
    }

    public void makeRequest(int userId) {
        mApi.getUserInformation(userId, this, this);
    }

    @Override
    public void onResponse(JSONObject response) {
        try {
            JSONObject data = response.getJSONObject(ApiKey.DATA);
            Gson gson = new Gson();
            mUser = gson.fromJson(data.toString(), User.class);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        notifyDataSetChanged();
    }

    public User getUser() {
        return mUser;
    }
}
