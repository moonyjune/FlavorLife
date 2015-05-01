package moony.vn.flavorlife.adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import java.util.List;

import moony.vn.flavorlife.FlavorLifeApplication;
import moony.vn.flavorlife.entities.Cookbook;
import moony.vn.flavorlife.entities.Ingredient;
import moony.vn.flavorlife.layout.CreateEditRecipeItemSectionIngredientView;
import moony.vn.flavorlife.layout.ItemChooseBookView;

/**
 * Created by moony on 3/14/15.
 */
public class BookAdapter extends ArrayAdapter<Cookbook> {
    private List<Cookbook> mListCookbooks;

    public BookAdapter(Context context, int resource, List<Cookbook> objects) {
        super(context, resource, objects);
        mListCookbooks = objects;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        Cookbook cookbook = mListCookbooks.get(position);
        if (convertView == null) {
            convertView = new ItemChooseBookView(getContext(), FlavorLifeApplication.get().getImageLoader());
        }
        ((ItemChooseBookView) convertView).display(cookbook);
        return convertView;
    }

    @Override
    public int getCount() {
        return mListCookbooks.size();
    }

    public void addItem(Cookbook cookbook) {
        mListCookbooks.add(cookbook);
        notifyDataSetChanged();
    }
}
