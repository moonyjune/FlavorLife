package moony.vn.flavorlife.layout;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.LinearLayout;

import moony.vn.flavorlife.R;

/**
 * Created by moony on 4/8/15.
 */
public class AddSectionView extends LinearLayout {
    public AddSectionView(Context context) {
        super(context);
        init();
    }

    public AddSectionView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public AddSectionView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        inflate(getContext(), R.layout.add_section_layout, this);
    }
}
