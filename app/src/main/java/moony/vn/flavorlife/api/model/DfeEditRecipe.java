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
public class DfeEditRecipe extends DfeModel implements Response.Listener<JSONObject> {
    private Api mApi;
    private boolean isSuccess;

    public DfeEditRecipe(Api mApi) {
        this.mApi = mApi;
    }

    @Override
    public boolean isReady() {
        return isSuccess;
    }

    public void makeRequest(Recipe recipe) {
        mApi.editRecipe(recipe, this, this);
    }

    @Override
    public void onResponse(JSONObject response) {
        isSuccess = true;
        notifyDataSetChanged();
    }

}
