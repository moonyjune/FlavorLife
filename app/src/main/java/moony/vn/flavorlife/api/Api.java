package moony.vn.flavorlife.api;

import com.android.volley.Request;
import com.android.volley.Response;

import org.json.JSONObject;

import java.util.Date;

import moony.vn.flavorlife.entities.Chapter;
import moony.vn.flavorlife.entities.Cookbook;
import moony.vn.flavorlife.entities.Recipe;

public interface Api {
    public Request<JSONObject> register(String email,
                                        Response.Listener<JSONObject> listener, Response.ErrorListener errorListener);

    public Request<JSONObject> login(String email,
                                     Response.Listener<JSONObject> listener, Response.ErrorListener errorListener);

    public Request<JSONObject> updateProfile(String email,
                                             Response.Listener<JSONObject> listener, Response.ErrorListener errorListener);

    public Request<JSONObject> createCookbook(Cookbook cookbook,
                                              Response.Listener<JSONObject> listener, Response.ErrorListener errorListener);

    public Request<JSONObject> editCookbook(Cookbook cookbook,
                                            Response.Listener<JSONObject> listener, Response.ErrorListener errorListener);

    public Request<JSONObject> createChapter(Chapter chapter,
                                             Response.Listener<JSONObject> listener, Response.ErrorListener errorListener);

    public Request<JSONObject> editChapter(Chapter chapter,
                                           Response.Listener<JSONObject> listener, Response.ErrorListener errorListener);

    public Request<JSONObject> createRecipe(Recipe recipe,
                                            Response.Listener<JSONObject> listener, Response.ErrorListener errorListener);

    public Request<JSONObject> editRecipe(Recipe recipe,
                                          Response.Listener<JSONObject> listener, Response.ErrorListener errorListener);

    public Request<JSONObject> upgradeRecipe(Recipe recipe,
                                             Response.Listener<JSONObject> listener, Response.ErrorListener errorListener);

    public Request<JSONObject> likeRecipe(int recipeId,
                                          Response.Listener<JSONObject> listener, Response.ErrorListener errorListener);

    public Request<JSONObject> unlikeRecipe(int recipeId,
                                            Response.Listener<JSONObject> listener, Response.ErrorListener errorListener);

    public Request<JSONObject> useRecipe(int recipeId,
                                         Response.Listener<JSONObject> listener, Response.ErrorListener errorListener);

    public Request<JSONObject> unUseRecipe(int recipeId,
                                           Response.Listener<JSONObject> listener, Response.ErrorListener errorListener);

    public Request<JSONObject> bookmarkRecipe(int recipeId,
                                              Response.Listener<JSONObject> listener, Response.ErrorListener errorListener);

    public Request<JSONObject> unBookmarkRecipe(int recipeId,
                                                Response.Listener<JSONObject> listener, Response.ErrorListener errorListener);

    public Request<JSONObject> deleteRecipe(int recipeId,
                                            Response.Listener<JSONObject> listener, Response.ErrorListener errorListener);

    public Request<JSONObject> follow(int followUserId,
                                      Response.Listener<JSONObject> listener, Response.ErrorListener errorListener);

    public Request<JSONObject> unFollow(int userId,
                                        Response.Listener<JSONObject> listener, Response.ErrorListener errorListener);

    //TODO search
    public Request<JSONObject> searchUsers(int recipeId,
                                           Response.Listener<JSONObject> listener, Response.ErrorListener errorListener);

    public Request<JSONObject> searchRecipes(int recipeId,
                                             Response.Listener<JSONObject> listener, Response.ErrorListener errorListener);

    //------------------------------------------------------------

    public Request<JSONObject> getUserInformation(int userId,
                                                  Response.Listener<JSONObject> listener, Response.ErrorListener errorListener);

    public Request<JSONObject> getNewRecipes(int skip, int take, Date requestDate,
                                             Response.Listener<JSONObject> listener, Response.ErrorListener errorListener);

    public Request<JSONObject> getUserRecipes(int skip, int take, Date requestedDate,
                                              Response.Listener<JSONObject> listener, Response.ErrorListener errorListener);

    public Request<JSONObject> getUserCookbooks(int userId,
                                                Response.Listener<JSONObject> listener, Response.ErrorListener errorListener);

    public Request<JSONObject> getRecipeDetail(int recipeId,
                                               Response.Listener<JSONObject> listener, Response.ErrorListener errorListener);

    public Request<JSONObject> getFollows(int skip, int take, Date requestDate,
                                          Response.Listener<JSONObject> listener, Response.ErrorListener errorListener);

    public Request<JSONObject> getFollowers(int skip, int take, Date requestDate,
                                            Response.Listener<JSONObject> listener, Response.ErrorListener errorListener);

    public Request<JSONObject> getBookDetail(int bookId,
                                             Response.Listener<JSONObject> listener, Response.ErrorListener errorListener);

    public Request<JSONObject> registerGcm(int userId, String registerId,
                                           Response.Listener<JSONObject> listener, Response.ErrorListener errorListener);


}
