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
public class DfeLikeRecipe extends DfeModel implements Response.Listener<JSONObject> {
    private Api mApi;
    private int mNumLikes = -1;
    public DfeLikeRecipe(Api mApi) {
        this.mApi = mApi;
    }

    @Override
    public boolean isReady() {
        return mNumLikes != -1;
    }

    public void makeRequest(int recipeId) {
        mApi.likeRecipe(recipeId, this, this);
    }

    @Override
    public void onResponse(JSONObject response) {
        try {
            mNumLikes = response.getInt(ApiKey.NUMBER_LIKES);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        notifyDataSetChanged();
    }

    public int getNumLikes() {
        return mNumLikes;
    }
}
