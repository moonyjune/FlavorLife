package moony.vn.flavorlife.adapters;

import android.content.Context;
import android.view.View;

import com.ntq.imageloader.NImageLoader;

import moony.vn.flavorlife.entities.Cookbook;
import moony.vn.flavorlife.entities.Follow;
import moony.vn.flavorlife.entities.Follower;
import moony.vn.flavorlife.entities.Recipe;
import moony.vn.flavorlife.layout.CookbookView;
import moony.vn.flavorlife.layout.FollowsView;
import moony.vn.flavorlife.layout.ItemFollowView;
import moony.vn.flavorlife.layout.ItemFollowerView;
import moony.vn.flavorlife.layout.NewRecipesView;
import moony.vn.flavorlife.navigationmanager.NavigationManager;

/**
 * Created by moony on 3/4/15.
 */
public class AdapterViewUtils {
    public static View getRecipes(Context context, Recipe recipe, View convertView, NImageLoader imageLoader, NavigationManager navigationManager) {
        if (convertView == null) {
            convertView = new NewRecipesView(context, imageLoader, navigationManager);
        }
        ((NewRecipesView) convertView).display(recipe);
        return convertView;
    }

    public static View getCookbooks(Context context, Cookbook cookbook, View convertView, NImageLoader imageLoader, NavigationManager navigationManager) {
        if (convertView == null) {
            convertView = new CookbookView(context, imageLoader, navigationManager);
        }
        ((CookbookView) convertView).display(cookbook);
        return convertView;
    }

    public static View getFollows(Context context, Follow follow, View convertView, NImageLoader imageLoader, NavigationManager navigationManager) {
        if (convertView == null) {
            convertView = new ItemFollowView(context, imageLoader, navigationManager);
        }
        ((ItemFollowView) convertView).display(follow);
        return convertView;
    }

    public static View getFollowers(Context context, Follower follower, View convertView, NImageLoader imageLoader, NavigationManager navigationManager) {
        if (convertView == null) {
            convertView = new ItemFollowerView(context, imageLoader, navigationManager);
        }
        ((ItemFollowerView) convertView).display(follower);
        return convertView;
    }


}
