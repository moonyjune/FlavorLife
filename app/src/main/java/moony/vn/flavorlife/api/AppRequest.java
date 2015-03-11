package moony.vn.flavorlife.api;

import android.text.TextUtils;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.http.NameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonObjectRequest;

/**
 *
 * @author TUNGDX
 *
 */

/**
 * Parse response from backend.
 */
public class AppRequest extends Request<JSONObject> {
    private final Listener<JSONObject> mListener;
    private Map<String, String> mParams;
    /**
     * This params use only when customboy=true
     */
    private List<NameValuePair> mNameValuePairs;
    private boolean mCustomBody = false;
    /**
     * The default socket timeout in milliseconds
     */
    public static final int DEFAULT_TIMEOUT_MS = 10 * 1000;

    public AppRequest(int method, String url, Map<String, String> params, Listener<JSONObject> mListener, ErrorListener listener) {
        super(method, url, listener);
        this.mListener = mListener;
        mParams = params;
        setRetryPolicy(new DefaultRetryPolicy(DEFAULT_TIMEOUT_MS, 1, 1));
        mCustomBody = false;
    }

    /**
     * Use when params need list pair value.
     *
     * @param method
     * @param url
     * @param params
     * @param mListener
     * @param listener
     * @param customBody
     */
    public AppRequest(int method, String url, List<NameValuePair> params, Listener<JSONObject> mListener, ErrorListener listener, boolean customBody) {
        super(method, url, listener);
        this.mListener = mListener;
        setRetryPolicy(new DefaultRetryPolicy(DEFAULT_TIMEOUT_MS, 1, 1));
        this.mCustomBody = customBody;
        mNameValuePairs = params;
    }

    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        return mParams;
    }

    @Override
    public byte[] getBody() throws AuthFailureError {
        if (mCustomBody) {
            return encodeParameters(mNameValuePairs, getParamsEncoding());
        }
        return super.getBody();
    }

    /**
     * Converts <code>params</code> into an application/x-www-form-urlencoded encoded string.
     */
    private byte[] encodeParameters(List<NameValuePair> params, String paramsEncoding) {
        StringBuilder encodedParams = new StringBuilder();
        try {
            for (NameValuePair nameValuePair : params) {
                encodedParams.append(URLEncoder.encode(nameValuePair.getName(), paramsEncoding));
                encodedParams.append('=');
                encodedParams.append(URLEncoder.encode(nameValuePair.getValue(), paramsEncoding));
                encodedParams.append('&');
            }
            return encodedParams.toString().getBytes(paramsEncoding);
        } catch (UnsupportedEncodingException uee) {
            throw new RuntimeException("Encoding not supported: " + paramsEncoding, uee);
        }
    }

    /**
     * Check response from backend success or not.
     *
     * @return
     * @throws JSONException
     */
    private boolean isSuccess(int code) {
        return code == 0;
    }

    /**
     * Get response code from backend.
     *
     * @param jsonObject
     * @return
     * @throws JSONException
     */
    private int getResponseCode(JSONObject jsonObject) throws JSONException {
        return jsonObject.getInt("code");
    }

    @Override
    protected Response<JSONObject> parseNetworkResponse(NetworkResponse response) {
        try {
            String jsonString = new String(response.data,
                    HttpHeaderParser.parseCharset(response.headers));
            JSONObject jsonObject = new JSONObject(jsonString);
            int code = getResponseCode(jsonObject);
            if (isSuccess(code))
                return Response.success(jsonObject,
                        HttpHeaderParser.parseCacheHeaders(response));
            else
                return Response.error(new ReceivedServerError(new Exception(""), code));
        } catch (UnsupportedEncodingException e) {
            return Response.error(new ParseError(e));
        } catch (JSONException je) {
            return Response.error(new ParseError(je));
        }
    }

    @Override
    protected void deliverResponse(JSONObject response) {
        mListener.onResponse(response);
    }


    public static String getUrl(String url, List<NameValuePair> values) {
        Iterator<NameValuePair> iterator = values.iterator();
        StringBuilder builder = new StringBuilder(url);
        builder.append("?");
        while (iterator.hasNext()) {
            NameValuePair pair = iterator.next();
            builder.append(pair.getName());
            builder.append("=");
            String value = pair.getValue();
            //encode value if not empty
            if (!TextUtils.isEmpty(value)) {
                try {
                    value = URLEncoder.encode(pair.getValue(), "UTF-8");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            }
            builder.append(value);
            if (iterator.hasNext())
                builder.append("&");
        }
        return builder.toString();
    }
}
