package moony.vn.flavorlife.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.EditText;
import android.widget.TextView;

import moony.vn.flavorlife.R;
import moony.vn.flavorlife.utils.FontCache;

/**
 * Created by moony on 5/8/15.
 */
public class FlEditText extends EditText {
    public static final int ELLE_BASKERVILLE_BOLD = 0;
    public static final int ELLE_BASKERVILLE_BOLD_IT = 1;
    public static final int ELLE_BASKERVILLE_ITALIC = 2;
    public static final int ELLE_BASKERVILLE_REGULAR = 3;
    public static final int ELLE_BASKERVILLE_SEMIBOLD = 4;
    public static final int ELLE_BASKERVILLE_SEMIBOLD_IT = 5;
    private static final int BOLD = 0;
    private static final int ITALIC = 1;
    private static final int NORMAL = 2;
    private static final int BOLD_ITALIC = 3;

    private static final String FONT_FILE_ELLE_BASKERVILLE_BOLD = "fonts/Elle Baskerville-Bold.otf";
    private static final String FONT_FILE_ELLE_BASKERVILLE_BOLD_IT = "fonts/Elle Baskerville-BoldIt.otf";
    private static final String FONT_FILE_ELLE_BASKERVILLE_ITALIC = "fonts/Elle Baskerville-Italic.otf";
    private static final String FONT_FILE_ELLE_BASKERVILLE_REGULAR = "fonts/Elle Baskerville-Regular.otf";
    private static final String FONT_FILE_ELLE_BASKERVILLE_SEMIBOLD = "fonts/Elle Baskerville-Semibold.otf";
    private static final String FONT_FILE_ELLE_BASKERVILLE_SEMIBOLD_IT = "fonts/Elle Baskerville-SemiboldIt.otf";

    private void setTextStyle(int style) {
        switch (style) {
            case BOLD:
                setTypeface(getTypeface(), Typeface.BOLD);
                break;
            case ITALIC:
                setTypeface(getTypeface(), Typeface.ITALIC);
                break;
            case NORMAL:
                setTypeface(getTypeface(), Typeface.NORMAL);
                break;
            case BOLD_ITALIC:
                setTypeface(getTypeface(), Typeface.BOLD_ITALIC);
                break;
        }
    }

    public FlEditText(Context context) {
        super(context);
        init(context, null);
    }

    public FlEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public FlEditText(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        int font = ELLE_BASKERVILLE_REGULAR;
        int style = NORMAL;
        boolean underLine = false;
        if (attrs != null) {
            TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.FlTextView);
            font = ta.getInt(R.styleable.FlTextView_font, ELLE_BASKERVILLE_BOLD);
            underLine = ta.getBoolean(R.styleable.FlTextView_underLine, false);
            style = ta.getInt(R.styleable.FlTextView_txtStyle, NORMAL);
            ta.recycle();
        }
        //setup underline: refer from: http://stackoverflow.com/a/10947374/4069201
        if (underLine) setPaintFlags(getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        setTextStyle(style);
        setUpFont(context, font);
    }

    public void setUpFont(Context context, int font) {
        switch (font) {
            case ELLE_BASKERVILLE_BOLD:
                setTypeface(FontCache.get(FONT_FILE_ELLE_BASKERVILLE_BOLD, context));
                setTextStyle(BOLD);
                break;
            case ELLE_BASKERVILLE_BOLD_IT:
                setTypeface(FontCache.get(FONT_FILE_ELLE_BASKERVILLE_BOLD_IT, context));
                setTextStyle(BOLD_ITALIC);
                break;
            case ELLE_BASKERVILLE_ITALIC:
                setTypeface(FontCache.get(FONT_FILE_ELLE_BASKERVILLE_ITALIC, context));
                setTextStyle(ITALIC);
                break;
            case ELLE_BASKERVILLE_REGULAR:
                setTypeface(FontCache.get(FONT_FILE_ELLE_BASKERVILLE_REGULAR, context));
                setTextStyle(NORMAL);
                break;
            case ELLE_BASKERVILLE_SEMIBOLD:
                setTypeface(FontCache.get(FONT_FILE_ELLE_BASKERVILLE_SEMIBOLD, context));
                break;
            case ELLE_BASKERVILLE_SEMIBOLD_IT:
                setTypeface(FontCache.get(FONT_FILE_ELLE_BASKERVILLE_SEMIBOLD_IT, context));
                break;
        }
    }

}
