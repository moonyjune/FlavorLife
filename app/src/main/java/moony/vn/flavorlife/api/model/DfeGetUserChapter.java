package moony.vn.flavorlife.api.model;

import com.android.volley.Response;
import com.ntq.api.model.DfeModel;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import moony.vn.flavorlife.api.Api;
import moony.vn.flavorlife.entities.Chapter;

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
        Chapter chapter = new Chapter();
        listChapters.add(chapter);
        listChapters.add(chapter);
        listChapters.add(chapter);
        listChapters.add(chapter);
        notifyDataSetChanged();
    }

    public List<Chapter> getListChapters() {
        return listChapters;
    }
}
