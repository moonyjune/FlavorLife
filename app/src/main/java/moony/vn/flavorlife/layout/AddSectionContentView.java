package moony.vn.flavorlife.layout;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.widget.LinearLayout;

import moony.vn.flavorlife.R;

/**
 * Created by moony on 4/8/15.
 */
public class AddSectionContentView extends LinearLayout {
    public AddSectionContentView(Context context) {
        super(context);
        init();
    }

    public AddSectionContentView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public AddSectionContentView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public AddSectionContentView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    private void init() {
        inflate(getContext(), R.layout.add_section_content_layout, this);
    }
}
