package moony.vn.flavorlife.api.model;

import com.android.volley.Request;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import moony.vn.flavorlife.api.Api;
import moony.vn.flavorlife.entities.Chapter;

/**
 * Created by moony on 4/30/15.
 */
public class DfeGetBookDetail extends FlPaginatedList<Chapter> {
    private Api mApi;
    private int mBookId;

    public DfeGetBookDetail(Api mApi, int mBookId) {
        this.mApi = mApi;
        this.mBookId = mBookId;
    }

    @Override
    protected Request<JSONObject> makeRequest(int skip, int take, Date requestDate) {
        return mApi.getBookDetail(mBookId, this, this);
    }

    @Override
    protected List<Chapter> parseResponse(JSONObject response) {
        List<Chapter> chapters = new ArrayList<Chapter>();
        Chapter chapter = new Chapter();
        chapter.setId(1);
        chapter.setNumChapter(1);
        chapter.setName("For newbie...");
        chapters.add(chapter);
        chapters.add(chapter);
        chapters.add(chapter);
        chapters.add(chapter);
        chapters.add(chapter);
        chapters.add(chapter);
        return chapters;
    }

    @Override
    public boolean isMoreAvailable() {
        return false;
    }
}