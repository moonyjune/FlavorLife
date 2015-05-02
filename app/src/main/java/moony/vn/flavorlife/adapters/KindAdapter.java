package moony.vn.flavorlife.adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import java.util.List;

import moony.vn.flavorlife.entities.Chapter;
import moony.vn.flavorlife.entities.Kind;
import moony.vn.flavorlife.layout.ItemChooseChapterView;
import moony.vn.flavorlife.layout.ItemChooseKindView;

/**
 * Created by moony on 3/14/15.
 */
public class KindAdapter extends ArrayAdapter<Kind> {
    private List<Kind> mListKinds;

    public KindAdapter(Context context, int resource, List<Kind> objects) {
        super(context, resource, objects);
        mListKinds = objects;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        Kind kind = mListKinds.get(position);
        if (convertView == null) {
            convertView = new ItemChooseKindView(getContext());
        }
        ((ItemChooseKindView) convertView).display(kind);
        return convertView;
    }

    @Override
    public int getCount() {
        return mListKinds.size();
    }

    public void addItem(Kind chapter) {
        mListKinds.add(chapter);
        notifyDataSetChanged();
    }
}
