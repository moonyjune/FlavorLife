package moony.vn.flavorlife.api.model;

import com.android.volley.Response;
import com.ntq.api.model.DfeModel;

import org.json.JSONObject;

import moony.vn.flavorlife.api.Api;
import moony.vn.flavorlife.entities.Recipe;

/**
 * Created by moony on 3/25/15.
 */
public class DfeCreateRecipe extends DfeModel implements Response.Listener<JSONObject> {
    private Api mApi;

    public DfeCreateRecipe(Api mApi) {
        this.mApi = mApi;
    }

    @Override
    public boolean isReady() {
        return false;
    }

    public void makeRequest(Recipe recipe){
        mApi.createRecipe(recipe, this, this);
    }

    @Override
    public void onResponse(JSONObject response) {
        System.out.println(response.toString());
    }
}
