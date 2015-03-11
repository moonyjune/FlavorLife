package moony.vn.flavorlife.api.model;

import com.android.volley.Request;
import com.android.volley.VolleyError;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import moony.vn.flavorlife.api.Api;
import moony.vn.flavorlife.entities.Recipe;

/**
 * Created by moony on 3/4/15.
 */
public class DfeGetNewRecipes extends FlPaginatedList<Recipe> {
    private Api mApi;

    public DfeGetNewRecipes(Api api) {
        super();
        mApi = api;
    }

    @Override
    protected Request<JSONObject> makeRequest(int skip, int take, Date requestDate) {
        return mApi.getNewRecipes(skip, take, this, this);
    }

    @Override
    protected List<Recipe> parseResponse(JSONObject response) {
        List<Recipe> recipes = new ArrayList<>();
        Recipe recipe = new Recipe();
        recipes.add(recipe);
        recipes.add(recipe);
        recipes.add(recipe);
        recipes.add(recipe);
        recipes.add(recipe);
        recipes.add(recipe);
        recipes.add(recipe);
        recipes.add(recipe);
        return recipes;
    }

    @Override
    public void onErrorResponse(VolleyError volleyerror) {
        super.onErrorResponse(volleyerror);
    }
}
