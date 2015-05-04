package moony.vn.flavorlife.layout;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ntq.imageloader.NImageLoader;

import moony.vn.flavorlife.R;
import moony.vn.flavorlife.entities.Chapter;
import moony.vn.flavorlife.fragments.ChapterDetailFragment;
import moony.vn.flavorlife.navigationmanager.NavigationManager;

/**
 * Created by moony on 4/30/15.
 */
public class ItemChapterView extends LinearLayout implements View.OnClickListener {
    private Chapter mChapter;
    private TextView mChapterName;
    private NavigationManager mNavigationManager;

    public ItemChapterView(Context context) {
        super(context);
        init();
    }

    public ItemChapterView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public ItemChapterView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    public ItemChapterView(Context context, NImageLoader imageLoader, NavigationManager navigationManager) {
        super(context);
        init();
        mNavigationManager = navigationManager;
    }

    private void init() {
        inflate(getContext(), R.layout.item_chapter_dialog, this);
        mChapterName = (TextView) findViewById(R.id.chapter_name);
    }

    public void display(Chapter chapter) {
        if (chapter == null) return;
        mChapter = chapter;
//        mChapterName.setText(chapter.getName());
        if (chapter.isChosen()) {
            setBackgroundColor(getResources().getColor(R.color.fl_color_gray_red_light));
        } else {
            setBackgroundColor(Color.WHITE);
        }
        setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        mNavigationManager.showPage(ChapterDetailFragment.newInstance(mChapter.getUserId(), mChapter.getId()));
    }
}
