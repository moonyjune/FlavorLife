package moony.vn.flavorlife.api;

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

import moony.vn.flavorlife.api.model.FlPaginatedList;
import moony.vn.flavorlife.entities.Chapter;
import moony.vn.flavorlife.entities.RecipeChapter;
import moony.vn.flavorlife.utils.DateFormatUtils;

/**
 * Created by moony on 4/30/15.
 */
public class DfeGetChapterDetail extends FlPaginatedList<RecipeChapter> {
    private Api mApi;
    private int mChapterId;
    private int mUserId;

    public DfeGetChapterDetail(Api mApi, int userId, int mChapterId) {
        this.mApi = mApi;
        this.mChapterId = mChapterId;
        mUserId = userId;

    }

    @Override
    protected Request<JSONObject> makeRequest(int skip, int take, Date requestDate) {
        return mApi.getChapterDetail(mUserId, mChapterId, this, this);
    }

    @Override
    protected List<RecipeChapter> parseResponse(JSONObject response) {
        JSONArray data = null;
        try {
            data = response.getJSONArray(ApiImpl.DATA);
            Gson gson = new GsonBuilder().setDateFormat(DateFormatUtils.DATE_FORMAT).create();
            List<RecipeChapter> recipeChapters = gson.fromJson(data.toString(), new TypeToken<Collection<RecipeChapter>>() {
            }.getType());
            return recipeChapters;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }


}
