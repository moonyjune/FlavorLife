package moony.vn.flavorlife.api;

import com.android.volley.VolleyError;

import moony.vn.flavorlife.R;

@SuppressWarnings("serial")
public class ReceivedServerError extends VolleyError {
    public static final int FAIL = -1;
    public static final int UNKNOW_ERROR = -9;
    public static final int USER_NOT_EXIST = -10;
    public static final int RECIPE_NOT_EXIST = -11;
    public static final int CHAPTER_NOT_EXIST = -12;
    public static final int BOOK_NOT_EXIST = -13;
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
            case FAIL:
                return R.string.error_server;
            case UNKNOW_ERROR:
                return R.string.unknow_error;
            case USER_NOT_EXIST:
                return R.string.user_not_exist;
            case RECIPE_NOT_EXIST:
                return R.string.recipe_not_exist;
            case CHAPTER_NOT_EXIST:
                return R.string.chapter_not_exist;
            case BOOK_NOT_EXIST:
                return R.string.book_not_exist;
        }
        return 0;
    }
}
