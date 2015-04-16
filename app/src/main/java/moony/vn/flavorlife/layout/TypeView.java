package moony.vn.flavorlife.layout;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

import moony.vn.flavorlife.R;

/**
 * Created by moony on 3/27/15.
 */
public class TypeView extends LinearLayout {
    private ImageView mType1, mType2, mType3;
    private List<ImageView> mListTypes = new ArrayList<ImageView>();

    public TypeView(Context context) {
        super(context);
        init(context);
    }

    public TypeView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public TypeView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        inflate(context, R.layout.type_layout, this);
        mType1 = (ImageView) findViewById(R.id.type_1);
        mType2 = (ImageView) findViewById(R.id.type_2);
        mType3 = (ImageView) findViewById(R.id.type_3);
        mListTypes.add(mType1);
        mListTypes.add(mType2);
        mListTypes.add(mType3);
    }

    public void setTypes(List<Integer> listTypes) {
        for (int i = 0; i < mListTypes.size(); i++) {
            mListTypes.get(i).setVisibility(GONE);
        }
        for (int i = 0; i < listTypes.size(); i++) {
            mListTypes.get(listTypes.get(i)).setVisibility(VISIBLE);
        }
    }

    public List<ImageView> getListTypes() {
        return mListTypes;
    }

    public int getType(){
        return 1;
    }
}
