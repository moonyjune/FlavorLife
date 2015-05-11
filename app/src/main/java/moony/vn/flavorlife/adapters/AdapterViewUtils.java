package moony.vn.flavorlife.adapters;

import android.content.Context;
import android.view.View;

import com.ntq.imageloader.NImageLoader;

import moony.vn.flavorlife.entities.Chapter;
import moony.vn.flavorlife.entities.People;
import moony.vn.flavorlife.entities.RecipeChapter;
import moony.vn.flavorlife.entities.Cookbook;
import moony.vn.flavorlife.entities.Follow;
import moony.vn.flavorlife.entities.Follower;
import moony.vn.flavorlife.entities.Message;
import moony.vn.flavorlife.entities.Recipe;
import moony.vn.flavorlife.entities.SearchRecipe;
import moony.vn.flavorlife.layout.CookbookView;
import moony.vn.flavorlife.layout.ItemChapterView;
import moony.vn.flavorlife.layout.ItemFollowView;
import moony.vn.flavorlife.layout.ItemFollowerView;
import moony.vn.flavorlife.layout.ItemMessageView;
import moony.vn.flavorlife.layout.ItemPeopleView;
import moony.vn.flavorlife.layout.ItemRecipeInChapterView;
import moony.vn.flavorlife.layout.ItemSearchRecipesView;
import moony.vn.flavorlife.layout.NewsRecipesView;
import moony.vn.flavorlife.layout.UserNewsRecipesView;
import moony.vn.flavorlife.navigationmanager.NavigationManager;

/**
 * Created by moony on 3/4/15.
 */
public class AdapterViewUtils {
    public static View getRecipes(Context context, Recipe recipe, View convertView, NImageLoader imageLoader, NavigationManager navigationManager) {
        if (convertView == null) {
            convertView = new NewsRecipesView(context, imageLoader, navigationManager);
        }
        ((NewsRecipesView) convertView).display(recipe);
        return convertView;
    }

    public static View getRecipesForGridView(Context context, Recipe recipe, View convertView, NImageLoader imageLoader, NavigationManager navigationManager) {
        if (convertView == null) {
            convertView = new UserNewsRecipesView(context, imageLoader, navigationManager);
        }
        ((UserNewsRecipesView) convertView).display(recipe);
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

    public static View getMessages(Context context, Message message, View convertView, NImageLoader imageLoader, NavigationManager navigationManager) {
        if (convertView == null) {
            convertView = new ItemMessageView(context, imageLoader, navigationManager);
        }
        ((ItemMessageView) convertView).display(message);
        return convertView;
    }

    public static View getBookDetail(Context context, Chapter chapter, View convertView, NImageLoader imageLoader, NavigationManager navigationManager) {
        if (convertView == null) {
            convertView = new ItemChapterView(context, imageLoader, navigationManager);
        }
        ((ItemChapterView) convertView).display(chapter);
        return convertView;
    }

    public static View getChapterDetail(Context context, RecipeChapter chapterRecipe, View convertView, NImageLoader imageLoader, NavigationManager navigationManager) {
        if (convertView == null) {
            convertView = new ItemRecipeInChapterView(context, imageLoader, navigationManager);
        }
        ((ItemRecipeInChapterView) convertView).display(chapterRecipe);
        return convertView;
    }

    public static View getPeople(Context context, People people, View convertView, NImageLoader imageLoader, NavigationManager navigationManager) {
        if (convertView == null) {
            convertView = new ItemPeopleView(context, imageLoader, navigationManager);
        }
        ((ItemPeopleView) convertView).display(people);
        return convertView;
    }

    public static View getSearchRecipe(Context context, SearchRecipe searchRecipe, View convertView, NImageLoader imageLoader, NavigationManager navigationManager) {
        if (convertView == null) {
            convertView = new ItemSearchRecipesView(context, imageLoader, navigationManager);
        }
        ((ItemSearchRecipesView) convertView).display(searchRecipe);
        return convertView;
    }

}
