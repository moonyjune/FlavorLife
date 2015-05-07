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
public class AddSectionContentView extends LinearLayout {
    private OnClickListener onAddSectionContentListener;
    private ImageView mAddSectionContent;

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

    private void init() {
        inflate(getContext(), R.layout.add_section_content_layout, this);
        mAddSectionContent = (ImageView) findViewById(R.id.add_section_content);
        setOnClickListener(null);
    }

    public void setOnAddSectionContentListener(OnClickListener onAddSectionContentListener) {
        this.onAddSectionContentListener = onAddSectionContentListener;
        mAddSectionContent.setOnClickListener(onAddSectionContentListener);
    }
}
