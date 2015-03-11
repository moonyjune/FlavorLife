package moony.vn.flavorlife.api;

import com.ntq.Config;

/**
 * Created by moony on 3/4/15.
 */
public interface ApiKey {
    public static final String REQUEST_TIME = "requested_at";
    public static final String EMAIL = "email";
    public static final String PWD = "password";
    public static final String INTRODUCTION = "introduction";
    public static final String USER_ID = "user_id";
    public static final String SKIP = "skip";
    public static final String TAKE = "take";


    public static final String BASE_URL = Config.SERVER_URL;

    public static final String API_LOGIN = "login";
    public static final String API_REGISTER = "register";
    public static final String API_GET_NEW_RECIPES = "get_new_recipes";
    public static final String API_GET_USER_RECIPES = "get_user_recipes";
    public static final String API_GET_COOKBOOKS = "get_cookbooks";
}
