package moony.vn.flavorlife.api;

import com.android.volley.Request;
import com.android.volley.Response;

import org.json.JSONObject;

import java.util.Date;

import moony.vn.flavorlife.entities.Recipe;

public interface Api {
    public Request<JSONObject> register(String email,
                                        Response.Listener<JSONObject> listener, Response.ErrorListener errorListener);

    public Request<JSONObject> login(String email,
                                     Response.Listener<JSONObject> listener, Response.ErrorListener errorListener);

    public Request<JSONObject> updateProfile(String email,
                                             Response.Listener<JSONObject> listener, Response.ErrorListener errorListener);

    public Request<JSONObject> createRecipe(Recipe recipe,
                                            Response.Listener<JSONObject> listener, Response.ErrorListener errorListener);

    public Request<JSONObject> editRecipe(Recipe recipe,
                                          Response.Listener<JSONObject> listener, Response.ErrorListener errorListener);

    public Request<JSONObject> upgradeRecipe(Recipe recipe,
                                             Response.Listener<JSONObject> listener, Response.ErrorListener errorListener);

    public Request<JSONObject> likeRecipe(int recipe_id,
                                          Response.Listener<JSONObject> listener, Response.ErrorListener errorListener);

    public Request<JSONObject> unlikeRecipe(int recipe_id,
                                            Response.Listener<JSONObject> listener, Response.ErrorListener errorListener);

    public Request<JSONObject> useRecipe(int recipe_id,
                                         Response.Listener<JSONObject> listener, Response.ErrorListener errorListener);

    public Request<JSONObject> unUseRecipe(int recipe_id,
                                           Response.Listener<JSONObject> listener, Response.ErrorListener errorListener);

    public Request<JSONObject> deleteRecipe(int recipe_id,
                                            Response.Listener<JSONObject> listener, Response.ErrorListener errorListener);

    public Request<JSONObject> follow(int followUserId,
                                      Response.Listener<JSONObject> listener, Response.ErrorListener errorListener);

    public Request<JSONObject> unFollow(int user_id,
                                        Response.Listener<JSONObject> listener, Response.ErrorListener errorListener);

    //TODO search
    public Request<JSONObject> searchUsers(int recipe_id,
                                           Response.Listener<JSONObject> listener, Response.ErrorListener errorListener);

    public Request<JSONObject> searchRecipes(int recipe_id,
                                             Response.Listener<JSONObject> listener, Response.ErrorListener errorListener);

    //------------------------------------------------------------

    public Request<JSONObject> getUserInformation(int user_id,
                                                  Response.Listener<JSONObject> listener, Response.ErrorListener errorListener);

    public Request<JSONObject> getNewRecipes(int skip, int take, Date requestDate,
                                             Response.Listener<JSONObject> listener, Response.ErrorListener errorListener);

    public Request<JSONObject> getUserRecipes(int user_id, int skip, int take,
                                              Response.Listener<JSONObject> listener, Response.ErrorListener errorListener);

    public Request<JSONObject> getUserCookbooks(int user_id,
                                                Response.Listener<JSONObject> listener, Response.ErrorListener errorListener);

    public Request<JSONObject> getRecipeDetail(int recipeId,
                                               Response.Listener<JSONObject> listener, Response.ErrorListener errorListener);

    public Request<JSONObject> getFollows(int skip, int take, Date requestDate,
                                          Response.Listener<JSONObject> listener, Response.ErrorListener errorListener);

    public Request<JSONObject> getFollowers(int user_id,
                                            Response.Listener<JSONObject> listener, Response.ErrorListener errorListener);

    public Request<JSONObject> getBookDetail(int bookId,
                                             Response.Listener<JSONObject> listener, Response.ErrorListener errorListener);

    public Request<JSONObject> registerGcm(int user_id, String registerId,
                                           Response.Listener<JSONObject> listener, Response.ErrorListener errorListener);


}
