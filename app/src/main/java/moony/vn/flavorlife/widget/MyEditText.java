package moony.vn.flavorlife.widget;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.EditText;

import moony.vn.flavorlife.R;

/**
 * Created by moony on 4/27/15.
 */
public class MyEditText extends FlEditText {
    public MyEditText(Context context) {
        super(context);
    }

    public MyEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyEditText(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected void onTextChanged(CharSequence text, int start, int lengthBefore, int lengthAfter) {
        super.onTextChanged(text, start, lengthBefore, lengthAfter);
        if (getText().toString().isEmpty()) {
            if (getCurrentTextColor() == getResources().getColor(R.color.fl_color)) {
                setTextColor(getResources().getColor(R.color.fl_color_hint));
                setUpFont(getContext(), ELLE_BASKERVILLE_SEMIBOLD_IT);
            }
        } else {
            if (getCurrentTextColor() == getResources().getColor(R.color.fl_color_hint)) {
                setTextColor(getResources().getColor(R.color.fl_color));
                setUpFont(getContext(), ELLE_BASKERVILLE_BOLD);
            }
        }
    }
}
