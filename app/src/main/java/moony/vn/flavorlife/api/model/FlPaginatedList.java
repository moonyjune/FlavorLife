package moony.vn.flavorlife.api.model;

import android.util.Log;

import com.android.volley.Request;
import com.ntq.api.model.PaginatedList;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import moony.vn.flavorlife.api.Api;
import moony.vn.flavorlife.api.ApiImpl;
import moony.vn.flavorlife.utils.DateFormatUtils;

/**
 * Created by moony on 3/4/15.
 */
public abstract class FlPaginatedList<T> extends PaginatedList<T> {
    private static final String TAG = "FlPaginatedList";
    private static final int DEFAULT_TAKE = 20;
    protected Api mApi;
    protected int mSkip = 0;
    protected int mTake = getTake();
    protected List<T> mLastList;
    protected Date mRequestDate;
    protected int mTotalItems = 0;

    public FlPaginatedList(boolean autoLoadNextPage) {
        super(autoLoadNextPage);
    }

    public FlPaginatedList() {
        super(true);
    }

    @Override
    protected boolean checkMoreAvaiable(List<T> response) {
        return mLastList != null && mLastList.size() >= mTake;
    }

    @Override
    public Request<JSONObject> makeRequest() {
        mSkip = 0;
        Log.i(TAG, String.format("Start Request, skip[%s] take[%s]", mSkip, mTake));
        return makeRequest(mSkip, mTake, mRequestDate);
    }

    @Override
    protected Request<JSONObject> makeMoreRequest() {
        mSkip = mTotalItems;
        Log.i(TAG, String.format("Load more, skip[%s] take[%s]", mSkip, mTake));
        return makeRequest(mSkip, mTake, mRequestDate);
    }

    @Override
    protected Request<JSONObject> makeRetryRequest() {
        Log.i(TAG, String.format("Retry, skip[%s] take[%s]", mSkip, mTake));
        return makeRequest(mSkip, mTake, mRequestDate);
    }

    public int getTake() {
        return DEFAULT_TAKE;
    }

    protected abstract Request<JSONObject> makeRequest(int skip, int take, Date requestDate);

    @Override
    protected List<T> getItemsFromResponse(JSONObject response) {
        mLastList = parseResponse(response);
        parseRequestTime(response);
        if (mLastList != null) mTotalItems += mLastList.size();
        return mLastList;
    }

    protected abstract List<T> parseResponse(JSONObject response);

    protected void parseRequestTime(JSONObject response) {
        if (response.has(ApiImpl.REQUEST_TIME)) {
            try {
                String requestTime = response.getString(ApiImpl.REQUEST_TIME);
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat(DateFormatUtils.REQUEST_TIME_FORMAT);
                mRequestDate = simpleDateFormat.parse(requestTime);
            } catch (JSONException e) {
                e.printStackTrace();
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
    }
}
