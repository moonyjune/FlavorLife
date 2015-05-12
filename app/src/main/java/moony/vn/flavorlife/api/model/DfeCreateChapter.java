package moony.vn.flavorlife.api.model;

import com.android.volley.Response;
import com.ntq.api.model.DfeModel;

import org.json.JSONException;
import org.json.JSONObject;

import moony.vn.flavorlife.api.Api;
import moony.vn.flavorlife.api.ApiKey;
import moony.vn.flavorlife.entities.Chapter;

/**
 * Created by moony on 3/25/15.
 */
public class DfeCreateChapter extends DfeModel implements Response.Listener<JSONObject> {
    private Api mApi;
    private int mChapterId;

    public DfeCreateChapter(Api mApi) {
        this.mApi = mApi;
    }

    @Override
    public boolean isReady() {
        return mChapterId != -1;
    }

    public void makeRequest(Chapter chapter) {
        mApi.createChapter(chapter, this, this);
    }

    @Override
    public void onResponse(JSONObject response) {
        System.out.println(response.toString());
        try {
            mChapterId = response.getInt(ApiKey.CHAPTER_ID);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        notifyDataSetChanged();
    }

    public int getChapterId() {
        return mChapterId;
    }
}
