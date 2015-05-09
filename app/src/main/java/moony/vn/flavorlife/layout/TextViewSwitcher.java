package moony.vn.flavorlife.layout;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import moony.vn.flavorlife.R;
import moony.vn.flavorlife.widget.FlTextView;

/**
 * Created by moony on 3/4/15.
 */
public class TextViewSwitcher extends FlTextView {

    private boolean isDrawableSelected = false;
    private int mDrawableIdSelected;
    private int mDrawableIdDefault;
    private int mTextColorDefault;
    private int mTextColorSelected;
    private int mGapBetweenDrawables;

    public TextViewSwitcher(Context context, int drawableIdDefault, int drawableIdSelected, int textColorDefault, int textColorSelected) {
        super(context);
        this.mDrawableIdDefault = drawableIdDefault;
        this.mDrawableIdSelected = drawableIdSelected;
        this.mTextColorDefault = textColorDefault;
        this.mTextColorSelected = textColorSelected;
        initView(context, null);
    }

    public TextViewSwitcher(Context context) {
        this(context, null);
    }

    public TextViewSwitcher(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TextViewSwitcher(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context, attrs);
    }

    public boolean isDrawableSelected() {
        return isDrawableSelected;
    }

    public void setDrawabeSelected(boolean isDrawableSelected) {
        this.isDrawableSelected = isDrawableSelected;
        changeItem(isDrawableSelected);
    }

    private void changeItem(boolean isDrawableSelected) {
        if (isDrawableSelected) {
            setTextColor(mTextColorSelected);
            setBackgroundResource(mDrawableIdSelected);
            setPadding(0, 0, 0, mGapBetweenDrawables);
        } else {
            setTextColor(mTextColorDefault);
            setBackgroundResource(mDrawableIdDefault);
            setPadding(0, 0, 0, 0);
        }
    }

    public void setDrawableBackgroundResources(int drawableIdDefault, int drawableIdSelected) {
        this.mDrawableIdSelected = drawableIdSelected;
        this.mDrawableIdDefault = drawableIdDefault;
        setOnClickEvent();
    }

    private void setOnClickEvent() {
        setClickable(true);
        setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isDrawableSelected) {
                    setDrawabeSelected(false);
                } else {
                    setDrawabeSelected(true);
                }
            }
        });
    }

    private void initView(Context context, AttributeSet attributeSet) {
        if (attributeSet == null) {
            LinearLayout.LayoutParams mLayoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            mLayoutParams.gravity = Gravity.TOP;
            setLayoutParams(mLayoutParams);
            setGravity(Gravity.CENTER);
        } else {
            loadStateFromAttrs(attributeSet);
        }
        mGapBetweenDrawables = getGapBetweenDrawables(mDrawableIdDefault, mDrawableIdSelected);
        setOnClickEvent();
        changeItem(isDrawableSelected);

    }

    private void loadStateFromAttrs(AttributeSet attributeSet) {
        if (attributeSet == null) {
            return;
        }
        TypedArray ta = null;
        try {
            ta = getContext().obtainStyledAttributes(attributeSet, R.styleable.TextViewSwitcher);
            mDrawableIdDefault = ta.getResourceId(R.styleable.TextViewSwitcher_defaultBackgroundDrawable, 0);
            mDrawableIdSelected = ta.getResourceId(R.styleable.TextViewSwitcher_selectedBackgroundDrawable, 0);
            isDrawableSelected = ta.getBoolean(R.styleable.TextViewSwitcher_selected, false);
            mTextColorDefault = ta.getColor(R.styleable.TextViewSwitcher_textColorDefault, Color.BLACK);
            mTextColorSelected = ta.getColor(R.styleable.TextViewSwitcher_textColorSelected, Color.BLACK);
            changeItem(isDrawableSelected);
        } finally {
            if (ta != null) {
                ta.recycle();
            }
        }
    }

    private int getGapBetweenDrawables(int idDrawable1, int idDrawable2) {
        int gap = 0;
        int tempHeight1 = BitmapFactory.decodeResource(getResources(), idDrawable1).getHeight();
        int tempHeight2 = BitmapFactory.decodeResource(getResources(), idDrawable2).getHeight();
        gap = Math.abs(tempHeight1 - tempHeight2);
        return gap;
    }

}
