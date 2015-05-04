package moony.vn.flavorlife.api.model;

import com.android.volley.Response;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.ntq.api.model.DfeModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import moony.vn.flavorlife.api.Api;
import moony.vn.flavorlife.api.ApiImpl;
import moony.vn.flavorlife.entities.Chapter;
import moony.vn.flavorlife.utils.DateFormatUtils;

/**
 * Created by moony on 4/30/15.
 */
public class DfeGetUserChapter extends DfeModel implements Response.Listener<JSONObject> {
    private Api mApi;
    private List<Chapter> listChapters;

    public DfeGetUserChapter(Api mApi) {
        this.mApi = mApi;
    }

    @Override
    public boolean isReady() {
        return listChapters != null && listChapters.size() != 0;
    }

    public void makeRequest(int bookId) {
        mApi.getBookDetail(bookId, this, this);
    }

    @Override
    public void onResponse(JSONObject response) {
        listChapters = new ArrayList<Chapter>();
        try {
            JSONArray data = response.getJSONArray(ApiImpl.DATA);
            Gson gson = new GsonBuilder().setDateFormat(DateFormatUtils.DATE_FORMAT).create();
            listChapters = gson.fromJson(data.toString(), new TypeToken<Collection<Chapter>>() {
            }.getType());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        notifyDataSetChanged();
    }

    public List<Chapter> getListChapters() {
        return listChapters;
    }
}
