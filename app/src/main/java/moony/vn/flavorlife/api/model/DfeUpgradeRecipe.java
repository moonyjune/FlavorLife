package moony.vn.flavorlife.api.model;

import com.android.volley.Response;
import com.ntq.api.model.DfeModel;

import org.json.JSONException;
import org.json.JSONObject;

import moony.vn.flavorlife.api.Api;
import moony.vn.flavorlife.api.ApiKey;
import moony.vn.flavorlife.entities.Recipe;

/**
 * Created by moony on 3/25/15.
 */
public class DfeUpgradeRecipe extends DfeModel implements Response.Listener<JSONObject> {
    private Api mApi;
    private int mRecipeId;

    public DfeUpgradeRecipe(Api mApi) {
        this.mApi = mApi;
    }

    @Override
    public boolean isReady() {
        return mRecipeId != -1;
    }

    public void makeRequest(Recipe recipe) {
        mApi.upgradeRecipe(recipe, this, this);
    }

    @Override
    public void onResponse(JSONObject response) {
        try {
            mRecipeId = response.getInt(ApiKey.RECIPE_ID);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        notifyDataSetChanged();
    }

    public int getRecipeId() {
        return mRecipeId;
    }
}
