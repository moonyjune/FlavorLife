package moony.vn.flavorlife.api.model;

import com.android.volley.Response;
import com.ntq.api.model.DfeModel;

import org.json.JSONObject;

import moony.vn.flavorlife.api.Api;
import moony.vn.flavorlife.entities.User;

/**
 * Created by moony on 5/10/15.
 */
public class DfeEditUserProfile extends DfeModel implements Response.Listener<JSONObject> {
    private Api mApi;
    private boolean isSuccess;

    public DfeEditUserProfile(Api mApi) {
        this.mApi = mApi;
    }

    @Override
    public boolean isReady() {
        return isSuccess;
    }

    public void makeRequest(User user){
        mApi.editUserProfile(user, this, this);
    }

    @Override
    public void onResponse(JSONObject response) {
        isSuccess = true;
        notifyDataSetChanged();
    }
}
