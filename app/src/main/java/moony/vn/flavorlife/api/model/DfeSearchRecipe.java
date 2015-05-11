package moony.vn.flavorlife.api.model;

import com.android.volley.Request;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import moony.vn.flavorlife.api.Api;
import moony.vn.flavorlife.api.ApiImpl;
import moony.vn.flavorlife.entities.RecipeChapter;
import moony.vn.flavorlife.entities.SearchRecipe;
import moony.vn.flavorlife.entities.SearchRecipeCondition;
import moony.vn.flavorlife.utils.DateFormatUtils;

/**
 * Created by moony on 5/11/15.
 */
public class DfeSearchRecipe extends FlPaginatedList<SearchRecipe> {
    private Api mApi;
    private SearchRecipeCondition mSearchRecipeCondition;

    public DfeSearchRecipe(Api mApi, SearchRecipeCondition searchRecipeCondition) {
        this.mApi = mApi;
        mSearchRecipeCondition = searchRecipeCondition;
    }

    @Override
    protected Request<JSONObject> makeRequest(int skip, int take, Date requestDate) {
        return mApi.searchRecipes(mSearchRecipeCondition, skip, take, requestDate, this, this);
    }

    @Override
    protected List<SearchRecipe> parseResponse(JSONObject response) {
        JSONArray data = null;
        try {
            data = response.getJSONArray(ApiImpl.DATA);
            Gson gson = new GsonBuilder().setDateFormat(DateFormatUtils.DATE_FORMAT).create();
            List<SearchRecipe> searchRecipes = gson.fromJson(data.toString(), new TypeToken<Collection<SearchRecipe>>() {
            }.getType());
            return searchRecipes;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }
}
