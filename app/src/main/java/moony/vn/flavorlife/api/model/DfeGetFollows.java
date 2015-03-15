package moony.vn.flavorlife.api.model;

import com.android.volley.Request;
import com.android.volley.VolleyError;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import moony.vn.flavorlife.api.Api;
import moony.vn.flavorlife.entities.Follow;
import moony.vn.flavorlife.entities.Recipe;

/**
 * Created by moony on 3/4/15.
 */
public class DfeGetFollows extends FlPaginatedList<Follow> {
    private Api mApi;

    public DfeGetFollows(Api api) {
        super();
        mApi = api;
    }

    @Override
    protected Request<JSONObject> makeRequest(int skip, int take, Date requestDate) {
        return mApi.getNewRecipes(skip, take, this, this);
    }

    @Override
    protected List<Follow> parseResponse(JSONObject response) {
        List<Follow> follows = new ArrayList<>();
        Follow follow = new Follow();
        follows.add(follow);
        follows.add(follow);
        follows.add(follow);
        follows.add(follow);
        follows.add(follow);
        follows.add(follow);
        follows.add(follow);
        follows.add(follow);
        return follows;
    }

    @Override
    public void onErrorResponse(VolleyError volleyerror) {
        super.onErrorResponse(volleyerror);
    }
}
