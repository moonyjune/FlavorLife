package moony.vn.flavorlife.api.model;

import com.android.volley.Response;
import com.ntq.api.model.DfeModel;

import org.json.JSONObject;

import moony.vn.flavorlife.api.Api;

/**
 * Created by moony on 5/9/15.
 */
public class DfeDeleteRecipe extends DfeModel implements Response.Listener<JSONObject> {
    private Api mApi;
    private boolean isSuccess;

    public DfeDeleteRecipe(Api mApi) {
        this.mApi = mApi;
    }

    @Override
    public boolean isReady() {
        return isSuccess;
    }

    public void makeRequest(int recipeId){
        mApi.deleteRecipe(recipeId, this, this);
    }

    @Override
    public void onResponse(JSONObject response) {
        isSuccess = true;
        notifyDataSetChanged();
    }
}
