package moony.vn.flavorlife.layout;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

import moony.vn.flavorlife.R;
import moony.vn.flavorlife.widget.FlTextView;

/**
 * Created by moony on 5/11/15.
 */
public class SimpleTabIndicator extends LinearLayout {
    private static final int MAX_NUM_LINES = 2;
    private final int mDrawableSelectorId = R.drawable.bg_selector_2;
    private PagerAdapter mPagerAdapter;
    private ViewPager mViewPager;
    private List<FlTextView> mListItemIndicator;
    private int mNumTab;
    private int mGapBetweenItems = 0;
    private int[] mListTextColors = new int[]{android.R.color.white, R.color.fl_color};
    private int mTextColorDefault, mTextColorSelected;
    private List<String> mListTabName;
    private ViewPager.OnPageChangeListener mPageChangeListener;

    private ViewPager.OnPageChangeListener internalPageChangeListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            if (mPageChangeListener != null)
                mPageChangeListener.onPageScrolled(position, positionOffset, positionOffsetPixels);
        }

        @Override
        public void onPageSelected(int position) {
            setCurrentSelectedItem(position);
            if (mPageChangeListener != null) mPageChangeListener.onPageSelected(position);
        }

        @Override
        public void onPageScrollStateChanged(int state) {
            if (mPageChangeListener != null) mPageChangeListener.onPageScrollStateChanged(state);
        }
    };

    public SimpleTabIndicator(Context context, ViewPager viewPager) {
        super(context);
        mViewPager = viewPager;
        mPagerAdapter = mViewPager.getAdapter();
        initView(mDrawableSelectorId, null);
    }

    public SimpleTabIndicator(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(mDrawableSelectorId, attrs);
    }

    public void initView(int drawableSelectorId, AttributeSet attributeSet) {
        removeAllViews();
        if (mPagerAdapter != null)
            mNumTab = mPagerAdapter.getCount();
        setWeightSum(mNumTab);
        mTextColorDefault = mListTextColors[0];
        mTextColorSelected = mListTextColors[1];

        if (attributeSet != null) {
            loadStateFromAttrs(attributeSet);
        }

        mListItemIndicator = new ArrayList<FlTextView>();
        FlTextView mItemIndicator = null;

        for (int i = 0; i < mNumTab; i++) {
            LinearLayout mLayoutContentItem = new LinearLayout(getContext());
            LayoutParams mLayoutContentItemParams = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT, 1);
            mLayoutContentItem.setLayoutParams(mLayoutContentItemParams);

            LayoutParams mLayoutParams = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            if (i == 0) {
                mItemIndicator = new FlTextView(getContext());
                mLayoutParams.setMargins(0, 0, mGapBetweenItems / 2, 0);
            } else {
                if (i == (mNumTab - 1)) {
                    mItemIndicator = new FlTextView(getContext());
                    mLayoutParams.setMargins(mGapBetweenItems / 2, 0, 0, 0);
                } else {
                    mItemIndicator = new FlTextView(getContext());
                    mLayoutParams.setMargins(mGapBetweenItems / 2, 0, mGapBetweenItems / 2, 0);
                }
            }

            mItemIndicator.setText(mListTabName.get(i));
            mItemIndicator.setLayoutParams(mLayoutParams);
            mItemIndicator.setGravity(Gravity.CENTER);
            mItemIndicator.setMaxLines(MAX_NUM_LINES);
            mItemIndicator.setEllipsize(TextUtils.TruncateAt.END);
            mItemIndicator.setBackgroundResource(drawableSelectorId);
            mItemIndicator.setUpFont(getContext(), FlTextView.ELLE_BASKERVILLE_BOLD);
            int padding = getResources().getDimensionPixelSize(R.dimen.fl_textview_padding_ver);
            mItemIndicator.setPadding(0, padding, 0, padding);
            mItemIndicator.setTextSize(getResources().getDimension(R.dimen.fl_textview_text_size_default));
            mListItemIndicator.add(mItemIndicator);

            mLayoutContentItem.addView(mItemIndicator);
            addView(mLayoutContentItem);
        }

        setCurrentSelectedItem(0);

        for (int i = 0; i < mListItemIndicator.size(); i++) {
            final int mCurrentItem = i;
            mListItemIndicator.get(i).setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (!mListItemIndicator.get(mCurrentItem).isSelected()) {
                        setCurrentSelectedItem(mCurrentItem);
                        mViewPager.setCurrentItem(mCurrentItem);
                    }
                }
            });
        }

    }

    private void setCurrentSelectedItem(int mCurrentItem) {
        for (int j = 0; j < mListItemIndicator.size(); j++) {
            if (j == mCurrentItem) {
                mListItemIndicator.get(j).setSelected(true);
                mListItemIndicator.get(j).setTextColor(getResources().getColor(mTextColorSelected));
            } else {
                mListItemIndicator.get(j).setSelected(false);
                mListItemIndicator.get(j).setTextColor(getResources().getColor(mTextColorDefault));
            }
        }
    }

    private void loadStateFromAttrs(AttributeSet attributeSet) {
        if (attributeSet == null) {
            return;
        }
        TypedArray ta = null;
        try {
            ta = getContext().obtainStyledAttributes(attributeSet, R.styleable.TabIndicator);
            mGapBetweenItems = ta.getDimensionPixelSize(R.styleable.TabIndicator_gapBetweenItems, 0);
        } finally {
            if (ta != null) {
                ta.recycle();
            }
        }
    }

    public void setViewPager(ViewPager viewPager, List<String> listTabName) {
        mViewPager = viewPager;
        mPagerAdapter = viewPager.getAdapter();
        mViewPager.setOnPageChangeListener(internalPageChangeListener);
        mNumTab = mPagerAdapter.getCount();
        mListTabName = listTabName;
        initView(mDrawableSelectorId, null);
    }

    public void setOnPageChangeListener(ViewPager.OnPageChangeListener pageChangeListener) {
        this.mPageChangeListener = pageChangeListener;
    }
}