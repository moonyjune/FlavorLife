package moony.vn.flavorlife.adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import java.util.List;

import moony.vn.flavorlife.entities.Chapter;
import moony.vn.flavorlife.layout.ItemChooseChapterView;

/**
 * Created by moony on 3/14/15.
 */
public class ChapterAdapter extends ArrayAdapter<Chapter> {
    private List<Chapter> mListChapters;

    public ChapterAdapter(Context context, int resource, List<Chapter> objects) {
        super(context, resource, objects);
        mListChapters = objects;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        Chapter chapter = mListChapters.get(position);
        if (convertView == null) {
            convertView = new ItemChooseChapterView(getContext());
        }
        ((ItemChooseChapterView) convertView).display(chapter);
        return convertView;
    }

    @Override
    public int getCount() {
        return mListChapters.size();
    }

    public void addItem(Chapter chapter) {
        mListChapters.add(chapter);
        notifyDataSetChanged();
    }
}
