package moony.vn.flavorlife.api;

import com.android.volley.Request;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import moony.vn.flavorlife.api.model.FlPaginatedList;
import moony.vn.flavorlife.entities.RecipeChapter;

/**
 * Created by moony on 4/30/15.
 */
public class DfeGetChapterDetail extends FlPaginatedList<RecipeChapter> {
    private Api mApi;
    private int mChapterId;

    public DfeGetChapterDetail(Api mApi, int mChapterId) {
        this.mApi = mApi;
        this.mChapterId = mChapterId;
    }

    @Override
    protected Request<JSONObject> makeRequest(int skip, int take, Date requestDate) {
        return mApi.getChapterDetail(mChapterId, this, this);
    }

    @Override
    protected List<RecipeChapter> parseResponse(JSONObject response) {
        List<RecipeChapter> recipeChapters = new ArrayList<RecipeChapter>();
        RecipeChapter recipeChapter = new RecipeChapter();
        recipeChapters.add(recipeChapter);
        recipeChapters.add(recipeChapter);
        recipeChapters.add(recipeChapter);
        recipeChapters.add(recipeChapter);
        recipeChapters.add(recipeChapter);
        return recipeChapters;
    }


}
