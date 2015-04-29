package moony.vn.flavorlife.api.model;

import com.android.volley.Response;
import com.ntq.api.model.DfeModel;

import org.json.JSONException;
import org.json.JSONObject;

import moony.vn.flavorlife.api.Api;
import moony.vn.flavorlife.api.ApiKey;
import moony.vn.flavorlife.entities.Cookbook;

/**
 * Created by moony on 3/25/15.
 */
public class DfeCreateBook extends DfeModel implements Response.Listener<JSONObject> {
    private Api mApi;
    private int mCookbookId;

    public DfeCreateBook(Api mApi) {
        this.mApi = mApi;
    }

    @Override
    public boolean isReady() {
        return mCookbookId != -1;
    }

    public void makeRequest(Cookbook cookbook) {
        mApi.createCookbook(cookbook, this, this);
    }

    @Override
    public void onResponse(JSONObject response) {
        System.out.println(response.toString());
        try {
            mCookbookId = response.getInt(ApiKey.COOKBOOK_ID);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        notifyDataSetChanged();
    }

    public int getRecipeId() {
        return mCookbookId;
    }
}
