package moony.vn.flavorlife.api.model;

import com.android.volley.Request;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import moony.vn.flavorlife.api.Api;
import moony.vn.flavorlife.entities.Cookbook;

/**
 * Created by moony on 3/11/15.
 */
public class DfeGetCookbooks extends FlPaginatedList<Cookbook> {
    private Api mApi;

    public DfeGetCookbooks(Api api) {
        mApi = api;
    }

    @Override
    protected Request<JSONObject> makeRequest(int skip, int take, Date requestDate) {
        return mApi.getUserCookbooks(this, this);
    }

    @Override
    protected List<Cookbook> parseResponse(JSONObject response) {
        List<Cookbook> cookbooks = new ArrayList<Cookbook>();
        Cookbook cookbook = new Cookbook();
        cookbooks.add(cookbook);
        cookbooks.add(cookbook);
        cookbooks.add(cookbook);
        cookbooks.add(cookbook);
        return cookbooks;
    }
}
