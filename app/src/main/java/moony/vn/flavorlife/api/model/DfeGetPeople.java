package moony.vn.flavorlife.api.model;

import com.android.volley.Request;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import moony.vn.flavorlife.api.Api;
import moony.vn.flavorlife.api.ApiKey;
import moony.vn.flavorlife.entities.Follow;
import moony.vn.flavorlife.entities.People;
import moony.vn.flavorlife.entities.SearchRecipe;

/**
 * Created by moony on 5/11/15.
 */
public class DfeGetPeople extends FlPaginatedList<People> {
    private Api mApi;

    public DfeGetPeople(Api mApi) {
        this.mApi = mApi;
    }

    @Override
    protected Request<JSONObject> makeRequest(int skip, int take, Date requestDate) {
        return mApi.getPeople(skip, take, requestDate, this, this);
    }

    @Override
    protected List<People> parseResponse(JSONObject response) {
//        try {
//            JSONArray data = response.getJSONArray(ApiKey.DATA);
//            Gson gson = new Gson();
//            List<People> people = gson.fromJson(data.toString(), new TypeToken<Collection<People>>() {
//            }.getType());
//            return people;
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//        return null;
        List<People> searchRecipes = new ArrayList<People>();
        return searchRecipes;
    }
}
