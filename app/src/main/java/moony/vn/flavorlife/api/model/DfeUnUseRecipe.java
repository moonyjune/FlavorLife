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
public class DfeUnUseRecipe extends DfeModel implements Response.Listener<JSONObject> {
    private Api mApi;
    private int mNumUsed = -1;
    public DfeUnUseRecipe(Api mApi) {
        this.mApi = mApi;
    }

    @Override
    public boolean isReady() {
        return mNumUsed != -1;
    }

    public void makeRequest(int recipeId) {
        mApi.unUseRecipe(recipeId, this, this);
    }

    @Override
    public void onResponse(JSONObject response) {
        try {
            mNumUsed = response.getInt(ApiKey.NUMBER_USED);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        notifyDataSetChanged();
    }

    public int getNumUses() {
        return mNumUsed;
    }
}
