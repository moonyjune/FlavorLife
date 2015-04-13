package moony.vn.flavorlife.api.model;

import com.android.volley.Response;
import com.ntq.api.model.DfeModel;

import org.json.JSONException;
import org.json.JSONObject;

import moony.vn.flavorlife.api.Api;
import moony.vn.flavorlife.api.ApiKey;

public class DfeLogin extends DfeModel implements Response.Listener<JSONObject> {
    private Api mApi;
    private int userId = -1;

    public DfeLogin(Api mApi) {
        this.mApi = mApi;
    }

    @Override
    public boolean isReady() {
        return userId != -1;
    }

    public void makeRequest(String email) {
        mApi.login(email, this, this);
    }

    @Override
    public void onResponse(JSONObject response) {
        try {
            JSONObject data = response.getJSONObject(ApiKey.DATA);
            userId = data.getInt(ApiKey.USER_ID);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        notifyDataSetChanged();
    }

    public int getUserId() {
        return userId;
    }
}
