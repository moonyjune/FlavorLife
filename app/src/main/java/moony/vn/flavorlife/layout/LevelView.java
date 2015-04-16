package moony.vn.flavorlife.layout;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

import moony.vn.flavorlife.R;

/**
 * Created by moony on 3/27/15.
 */
public class LevelView extends LinearLayout {
    private ImageView mLevel_1, mLevel_2, mLevel_3, mLevel_4, mLevel_5;
    private List<ImageView> mListLevels;

    public LevelView(Context context) {
        super(context);
        init(context);
    }

    public LevelView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public LevelView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        inflate(context, R.layout.level_layout, this);
        mLevel_1 = (ImageView) findViewById(R.id.level_1);
        mLevel_2 = (ImageView) findViewById(R.id.level_2);
        mLevel_3 = (ImageView) findViewById(R.id.level_3);
        mLevel_4 = (ImageView) findViewById(R.id.level_4);
        mLevel_5 = (ImageView) findViewById(R.id.level_5);
        mListLevels = new ArrayList<ImageView>();
        mListLevels.add(mLevel_1);
        mListLevels.add(mLevel_2);
        mListLevels.add(mLevel_3);
        mListLevels.add(mLevel_4);
        mListLevels.add(mLevel_5);

        for (int i = 0; i < mListLevels.size(); i++) {
            final int position = i;
            mListLevels.get(i).setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View view) {
                    setLevel(position);
                }
            });
        }
    }

    private void setLevel(int level) {
        for (int i = 0; i < mListLevels.size(); i++) {
            if (i <= level) {
                mListLevels.get(i).setImageResource(R.drawable.ic_launcher);
                mListLevels.get(i).setTag(R.drawable.ic_launcher);
            } else {
                mListLevels.get(i).setImageResource(R.drawable.ic_launcher_2);
                mListLevels.get(i).setTag(R.drawable.ic_launcher_2);
            }
        }
    }

    public int getLevel() {
//        int level = 0;ge
//        for (int i = 0; i < mListLevels.size(); i++) {
//            if (mListLevels.get(i).getTag() == R.drawable.ic_launcher) {
//                level++;
//            }
//        }
        return 2;
    }
}
