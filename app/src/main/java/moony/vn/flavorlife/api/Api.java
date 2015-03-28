package moony.vn.flavorlife.api;

import com.android.volley.Request;
import com.android.volley.Response;

import org.json.JSONObject;

import moony.vn.flavorlife.entities.Recipe;

public interface Api {
    public Request<JSONObject> register(String email, String introduction,
                                        Response.Listener<JSONObject> listener, Response.ErrorListener errorListener);
    public Request<JSONObject> getNewRecipes(int skip, int take,
                                        Response.Listener<JSONObject> listener, Response.ErrorListener errorListener);
    public Request<JSONObject> getUserRecipes(int user_id, int skip, int take,
                                        Response.Listener<JSONObject> listener, Response.ErrorListener errorListener);
    public Request<JSONObject> getCookbooks(int user_id,
                                        Response.Listener<JSONObject> listener, Response.ErrorListener errorListener);
    public Request<JSONObject> createRecipe(Recipe recipe,
            Response.Listener<JSONObject> listener, Response.ErrorListener errorListener);
}
