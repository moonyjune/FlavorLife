package com.ntq.mediapicker;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.widget.ImageView;

import moony.vn.flavorlife.R;

/**
 *
 * @author TUNGDX
 *
 */

/**
 * Display thumbnail of video, photo and state when video, photo selected or
 * not.
 */
class PickerImageView extends ImageView {
    private Paint paintBorder;

    private boolean isSelected;
    private int mediaType;
    private int order;
    private int borderSize = 1;

    public PickerImageView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    public PickerImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        paintBorder = new Paint();
        paintBorder.setAntiAlias(true);
        paintBorder.setColor(getResources().getColor(R.color.picker_color));
        borderSize = getResources().getDimensionPixelSize(
                R.dimen.picker_border_size);
    }

    public PickerImageView(Context context) {
        super(context);
        init();
    }

    public void setSelected(boolean isSelected) {
        if (isSelected != this.isSelected) {
            this.isSelected = isSelected;
            invalidate();
        }
    }

    public void setMediaType(int mediaType) {
        this.mediaType = mediaType;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public int getMediaType() {
        return mediaType;
    }

    public int getOrder() {
        return order;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int width = getMeasuredWidth();
        setMeasuredDimension(width, width);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (isSelected) {
            canvas.drawRect(0, 0, borderSize, getHeight(), paintBorder);
            canvas.drawRect(getWidth() - borderSize, 0, getWidth(),
                    getHeight(), paintBorder);
            canvas.drawRect(0, 0, getWidth(), borderSize, paintBorder);
            canvas.drawRect(0, getHeight() - borderSize, getWidth(),
                    getHeight(), paintBorder);
        }
    }

}
