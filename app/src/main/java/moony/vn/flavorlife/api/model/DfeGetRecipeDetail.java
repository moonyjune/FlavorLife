package moony.vn.flavorlife.api.model;

import com.android.volley.Response;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.ntq.api.model.DfeModel;

import org.json.JSONException;
import org.json.JSONObject;

import moony.vn.flavorlife.api.Api;
import moony.vn.flavorlife.api.ApiKey;
import moony.vn.flavorlife.entities.Recipe;
import moony.vn.flavorlife.utils.DateFormatUtils;

/**
 * Created by moony on 4/22/15.
 */
public class DfeGetRecipeDetail extends DfeModel implements Response.Listener<JSONObject> {
    private Api mApi;
    private Recipe mRecipe;

    public DfeGetRecipeDetail(Api mApi) {
        this.mApi = mApi;
    }

    @Override
    public boolean isReady() {
        return mRecipe != null;
    }

    public void makeRequest(int userId, int recipeId) {
        mApi.getRecipeDetail(userId, recipeId, this, this);
    }

    @Override
    public void onResponse(JSONObject response) {
        try {
            JSONObject data = response.getJSONObject(ApiKey.DATA);
            Gson gson = new GsonBuilder().setDateFormat(DateFormatUtils.DATE_FORMAT).create();
            mRecipe = gson.fromJson(data.toString(), Recipe.class);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        notifyDataSetChanged();
    }

    public Recipe getRecipe() {
        return mRecipe;
    }
}
