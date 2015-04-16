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
public class DfeGetUserRecipes extends FlPaginatedList<Recipe> {
    private Api mApi;
    private int mUserId;

    public DfeGetUserRecipes(Api api, int user_id) {
        super();
        mApi = api;
        mUserId = user_id;
    }

    @Override
    protected Request<JSONObject> makeRequest(int skip, int take, Date requestDate) {
        return mApi.getUserRecipes(mUserId, skip, take, this, this);
    }

    @Override
    protected List<Recipe> parseResponse(JSONObject response) {
        List<Recipe> recipes = new ArrayList<Recipe>();
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
