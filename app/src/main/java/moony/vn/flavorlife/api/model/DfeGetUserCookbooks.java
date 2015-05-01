package moony.vn.flavorlife.api.model;

import com.android.volley.Response;
import com.ntq.api.model.DfeModel;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import moony.vn.flavorlife.api.Api;
import moony.vn.flavorlife.entities.Cookbook;

/**
 * Created by moony on 4/30/15.
 */
public class DfeGetUserCookbooks extends DfeModel implements Response.Listener<JSONObject> {
    private Api mApi;
    private List<Cookbook> listCookbooks;

    public DfeGetUserCookbooks(Api mApi) {
        this.mApi = mApi;
    }

    @Override
    public boolean isReady() {
        return listCookbooks != null && listCookbooks.size() != 0;
    }

    public void makeRequest() {
        mApi.getUserCookbooks(this, this);
    }

    @Override
    public void onResponse(JSONObject response) {
        listCookbooks = new ArrayList<Cookbook>();
        Cookbook cookbook = new Cookbook();
        listCookbooks.add(cookbook);
        listCookbooks.add(cookbook);
        listCookbooks.add(cookbook);
        listCookbooks.add(cookbook);
        notifyDataSetChanged();
    }

    public List<Cookbook> getListCookbooks() {
        return listCookbooks;
    }
}
