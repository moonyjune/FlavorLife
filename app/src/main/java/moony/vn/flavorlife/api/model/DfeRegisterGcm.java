package moony.vn.flavorlife.api.model;

import com.android.volley.Response;
import com.ntq.api.model.DfeModel;

import org.json.JSONObject;

import moony.vn.flavorlife.api.Api;

/**
 * Created by moony on 4/10/15.
 */
    public class DfeRegisterGcm extends DfeModel implements Response.Listener<JSONObject> {
    private Api mApi;
    private boolean isReady;

    public DfeRegisterGcm(Api mApi) {
        this.mApi = mApi;
    }

    @Override
    public boolean isReady() {
        return isReady;
    }

    public void makeRequest(int userId, String registerId) {
        mApi.registerGcm(userId, registerId, this, this);
    }

    @Override
    public void onResponse(JSONObject response) {
        isReady = true;
        notifyDataSetChanged();
    }
}
