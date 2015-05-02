package moony.vn.flavorlife.layout;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ntq.imageloader.NImageLoader;

import moony.vn.flavorlife.R;
import moony.vn.flavorlife.entities.Cookbook;

/**
 * Created by moony on 4/30/15.
 */
public class ItemChooseBookView extends LinearLayout {
    private Cookbook mCookbook;
    private NImageLoader mImageLoader;
    private ImageView mBookImage;
    private TextView mBookName;

    public ItemChooseBookView(Context context) {
        super(context);
        init();
    }

    public ItemChooseBookView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public ItemChooseBookView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    public ItemChooseBookView(Context context, NImageLoader imageLoader) {
        super(context);
        init();
        mImageLoader = imageLoader;
    }

    private void init() {
        inflate(getContext(), R.layout.item_book_dialog, this);
        mBookImage = (ImageView) findViewById(R.id.book_image);
        mBookName = (TextView) findViewById(R.id.book_name);
    }

    public void display(Cookbook cookbook) {
        if (cookbook == null) return;
        mCookbook = cookbook;
//        mBookName.setText(cookbook.getName());
//        if (cookbook.getImage() != null && !cookbook.getImage().isEmpty()) {
//            mImageLoader.display(cookbook.getImage(), mBookImage);
//        } else {
//            mBookImage.setImageResource(R.drawable.default_cookbook_image);
//        }
        if (cookbook.isChosen()) {
            setBackgroundColor(getResources().getColor(R.color.fl_color_gray_red_light));
        } else {
            setBackgroundColor(Color.WHITE);
        }
    }

    public void setImageLoader(NImageLoader mImageLoader) {
        this.mImageLoader = mImageLoader;
    }
}
