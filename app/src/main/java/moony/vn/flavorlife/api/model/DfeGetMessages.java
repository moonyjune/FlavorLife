package moony.vn.flavorlife.api.model;

import com.android.volley.Request;
import com.android.volley.VolleyError;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import moony.vn.flavorlife.api.Api;
import moony.vn.flavorlife.entities.Follow;
import moony.vn.flavorlife.entities.Message;

/**
 * Created by moony on 3/4/15.
 */
public class DfeGetMessages extends FlPaginatedList<Message> {
    private Api mApi;

    public DfeGetMessages(Api api) {
        super();
        mApi = api;
    }

    @Override
    protected Request<JSONObject> makeRequest(int skip, int take, Date requestDate) {
        return mApi.getNewRecipes(skip, take, requestDate, this, this);
    }

    @Override
    protected List<Message> parseResponse(JSONObject response) {
        List<Message> messages = new ArrayList<>();
        Message message = new Message();
        messages.add(message);
        messages.add(message);
        messages.add(message);
        messages.add(message);
        messages.add(message);
        messages.add(message);
        messages.add(message);
        messages.add(message);
        return messages;
    }

    @Override
    public void onErrorResponse(VolleyError volleyerror) {
        super.onErrorResponse(volleyerror);
    }
}
