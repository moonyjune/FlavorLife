package moony.vn.flavorlife.api;


import moony.vn.flavorlife.Config;

/**
 * Created by moony on 3/4/15.
 */
public interface ApiKey {
    public static final String EMAIL = "email";
    public static final String PWD = "password";
    public static final String INTRODUCTION = "introduction";
    public static final String USER_ID = "user_id";
    public static final String FOLLOW_USER_ID = "follow_user_id";
    public static final String SKIP = "skip";
    public static final String TAKE = "take";
    public static final String GCM_REGISTER_ID = "gcm_register_id";
    public static final String RECIPE_ID = "recipe_id";
    public static final String COOKBOOK_ID = "cookbook_id";
    public static final String CHAPTER_ID = "chapter_id";
    public static final String NUMBER_LIKES = "number_likes";
    public static final String NUMBER_USED = "number_used";
    public static final String NUMBER_FOLLOWERS = "number_followers";

    public static final String REQUEST_TIME = "requested_at";
    public static final String DATA = "data";
    public static final String BASE_URL = Config.SERVER_URL;

    public static final String API_GET_RECIPE_DETAIL = "get_recipe_detail";
    public static final String API_GET_NEWS_RECIPES = "get_news_recipes";
    public static final String API_GET_USER_RECIPES = "get_user_recipes";
    public static final String API_GET_COOKBOOKS = "get_cookbooks";
    public static final String API_GET_FOLLOWS = "get_follows";
    public static final String API_GET_FOLLOWERS = "get_followers";
    public static final String API_GET_BOOK_DETAIL = "get_book_detail";
    public static final String API_GET_CHAPTER_DETAIL = "get_chapter_detail";

    public static final String API_REGISTER_GCM = "register_gcm";
    public static final String API_LOGIN = "login";
    public static final String API_REGISTER = "register";
    public static final String API_FOLLOW = "follow_recipe";
    public static final String API_CREATE_RECIPE = "create_recipe";
    public static final String API_UNFOLLOW = "unfollow_recipe";
    public static final String API_LIKE_RECIPE = "like_recipe";
    public static final String API_UNLIKE_RECIPE = "unlike_recipe";
    public static final String API_USE_RECIPE = "use_recipe";
    public static final String API_UNUSE_RECIPE = "unuse_recipe";
    public static final String API_BOOKMARK_RECIPE = "bookmark_recipe";
    public static final String API_UNBOOKMARK_RECIPE = "unbookmark_recipe";
    public static final String API_EDIT_RECIPE = "edit_recipe";
    public static final String API_UPGRADE_RECIPE = "upgrade_recipe";
    public static final String API_UPLOAD_IMAGE = "upload_image";
}