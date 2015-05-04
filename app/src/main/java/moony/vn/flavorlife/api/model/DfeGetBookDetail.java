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
import moony.vn.flavorlife.entities.Chapter;
import moony.vn.flavorlife.utils.DateFormatUtils;

/**
 * Created by moony on 4/30/15.
 */
public class DfeGetBookDetail extends FlPaginatedList<Chapter> {
    private Api mApi;
    private int mBookId;
    private int mUserId;

    public DfeGetBookDetail(Api mApi, int userId, int mBookId) {
        this.mApi = mApi;
        this.mBookId = mBookId;
        mUserId = userId;
    }

    @Override
    protected Request<JSONObject> makeRequest(int skip, int take, Date requestDate) {
        return mApi.getBookDetail(mUserId, mBookId, this, this);
    }

    @Override
    protected List<Chapter> parseResponse(JSONObject response) {
        JSONArray data = null;
        try {
            data = response.getJSONArray(ApiImpl.DATA);
            Gson gson = new GsonBuilder().setDateFormat(DateFormatUtils.DATE_FORMAT).create();
            List<Chapter> chapters = gson.fromJson(data.toString(), new TypeToken<Collection<Chapter>>() {
            }.getType());
            for (int i = 0; i < chapters.size(); i++) {
                chapters.get(i).setUserId(mUserId);
            }
            return chapters;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean isMoreAvailable() {
        return false;
    }
}