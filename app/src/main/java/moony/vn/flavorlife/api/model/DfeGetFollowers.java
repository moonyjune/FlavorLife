package moony.vn.flavorlife.api.model;

import com.android.volley.Request;
import com.android.volley.VolleyError;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import moony.vn.flavorlife.api.Api;
import moony.vn.flavorlife.entities.Follow;
import moony.vn.flavorlife.entities.Follower;

/**
 * Created by moony on 3/4/15.
 */
public class DfeGetFollowers extends FlPaginatedList<Follower> {
    private Api mApi;

    public DfeGetFollowers(Api api) {
        super();
        mApi = api;
    }

    @Override
    protected Request<JSONObject> makeRequest(int skip, int take, Date requestDate) {
        return mApi.getNewRecipes(skip, take,requestDate, this, this);
    }

    @Override
    protected List<Follower> parseResponse(JSONObject response) {
        List<Follower> followers = new ArrayList<Follower>();
        Follower follower = new Follower();
        followers.add(follower);
        followers.add(follower);
        followers.add(follower);
        followers.add(follower);
        followers.add(follower);
        followers.add(follower);
        followers.add(follower);
        followers.add(follower);
        return followers;
    }

    @Override
    public void onErrorResponse(VolleyError volleyerror) {
        super.onErrorResponse(volleyerror);
    }
}
