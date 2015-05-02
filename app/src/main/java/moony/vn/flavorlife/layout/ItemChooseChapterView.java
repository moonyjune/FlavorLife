package moony.vn.flavorlife.layout;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import android.widget.TextView;

import moony.vn.flavorlife.R;
import moony.vn.flavorlife.entities.Chapter;

/**
 * Created by moony on 4/30/15.
 */
public class ItemChooseChapterView extends LinearLayout {
    private Chapter mChapter;
    private TextView mChapterName;

    public ItemChooseChapterView(Context context) {
        super(context);
        init();
    }

    public ItemChooseChapterView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public ItemChooseChapterView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    private void init() {
        inflate(getContext(), R.layout.item_chapter_dialog, this);
        mChapterName = (TextView) findViewById(R.id.chapter_name);
    }

    public void display(Chapter chapter) {
        if (chapter == null) return;
        mChapter = chapter;

        if (chapter.isChosen()) {
            setBackgroundColor(getResources().getColor(R.color.fl_color_gray_red_light));
        } else {
            setBackgroundColor(Color.WHITE);
        }
//        mChapterName.setText(chapter.getName());
    }

}
