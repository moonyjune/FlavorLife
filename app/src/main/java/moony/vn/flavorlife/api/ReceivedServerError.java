package moony.vn.flavorlife.api;

import com.android.volley.VolleyError;

@SuppressWarnings("serial")
public class ReceivedServerError extends VolleyError {
    private int code;

    public ReceivedServerError(Throwable throwable, int code) {
        super(throwable);
        this.code = code;
    }

    /**
     * Resource of message that describe error.
     *
     * @return
     */
    public int getMessageRes() {
        // TODO handle error code
        switch (code) {
            default:
                break;
        }
        return 0;
    }
}
