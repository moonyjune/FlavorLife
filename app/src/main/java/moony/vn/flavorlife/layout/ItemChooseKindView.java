package moony.vn.flavorlife.layout;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import android.widget.TextView;

import moony.vn.flavorlife.R;
import moony.vn.flavorlife.entities.Chapter;
import moony.vn.flavorlife.entities.Kind;

/**
 * Created by moony on 4/30/15.
 */
public class ItemChooseKindView extends LinearLayout {
    private Kind mKind;
    private TextView mKindName;

    public ItemChooseKindView(Context context) {
        super(context);
        init();
    }

    public ItemChooseKindView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public ItemChooseKindView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    private void init() {
        inflate(getContext(), R.layout.item_kind_dialog, this);
        mKindName = (TextView) findViewById(R.id.kind_name);
    }

    public void display(Kind kind) {
        if (kind == null) return;
        mKind = kind;
        mKindName.setText(kind.getName());
        if (kind.isChose()) {
            setBackgroundColor(getResources().getColor(R.color.fl_color_gray_red_light));
        } else {
            setBackgroundColor(Color.WHITE);
        }
    }

}
