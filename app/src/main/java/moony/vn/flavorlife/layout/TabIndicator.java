package moony.vn.flavorlife.layout;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
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

/**
 * Created by moony on 3/4/15.
 */
public class TabIndicator extends LinearLayout {
    private static final int MAX_NUM_LINES = 2;
    private PagerAdapter mPagerAdapter;
    private ViewPager mViewPager;
    private List<TextViewSwitcher> mListItemIndicator;
    private int mNumTab;
    private int mGapBetweenItems = 0;
    private int[] mListDefaultDrawable = new int[]{R.drawable.first_item_default, R.drawable.middle_item_default, R.drawable.last_item_default};
    private int[] mListSelectedDrawable = new int[]{R.drawable.first_item_selected, R.drawable.middle_item_selected, R.drawable.last_item_selected};
    private int[] mListOneItemDrawble = new int[]{R.drawable.one_item_default, R.drawable.one_item_selected};
    private int[] mListTextColors = new int[]{Color.WHITE, getResources().getColor(R.color.pink)};
    private int mTextColorDefault, mTextColorSelected;
    private List<String> mListTabName;

    public TabIndicator(Context context, ViewPager viewPager) {
        super(context);
        mViewPager = viewPager;
        mPagerAdapter = mViewPager.getAdapter();
        initView(mListDefaultDrawable, mListSelectedDrawable, mListOneItemDrawble, null);
    }

    public TabIndicator(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(mListDefaultDrawable, mListSelectedDrawable, mListOneItemDrawble, attrs);
    }

    public void initView(int[] listDefaultDrawable, int[] listSelectedDrawable, int[] listOneItemDrawable, AttributeSet attributeSet) {
        removeAllViews();
        if (mPagerAdapter != null)
            mNumTab = mPagerAdapter.getCount();
        setWeightSum(mNumTab);
        mTextColorDefault = mListTextColors[0];
        mTextColorSelected = mListTextColors[1];

        if (attributeSet != null) {
            loadStateFromAttrs(attributeSet);
        }

        mListItemIndicator = new ArrayList<TextViewSwitcher>();
        TextViewSwitcher mItemIndicator = null;

        if (mNumTab == 1) {
            mItemIndicator = new TextViewSwitcher(getContext(), listOneItemDrawable[0], listOneItemDrawable[1], mTextColorDefault, mTextColorSelected);
            mItemIndicator.setText(mListTabName.get(0));

            mItemIndicator.setDrawabeSelected(true);
            mListItemIndicator.add(mItemIndicator);
            addView(mItemIndicator);
        } else if (mNumTab > 1) {
            for (int i = 0; i < mNumTab; i++) {
                LinearLayout mLayoutContentItem = new LinearLayout(getContext());
                LayoutParams mLayoutContentItemParams = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT, 1);
                mLayoutContentItem.setLayoutParams(mLayoutContentItemParams);

                LayoutParams mLayoutParams = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                if (i == 0) {
                    mItemIndicator = new TextViewSwitcher(getContext(), this.mListDefaultDrawable[0], this.mListSelectedDrawable[0], mTextColorDefault, mTextColorSelected);
                    mLayoutParams.setMargins(0, 0, mGapBetweenItems / 2, 0);
                } else {
                    if (i == (mNumTab - 1)) {
                        mItemIndicator = new TextViewSwitcher(getContext(), this.mListDefaultDrawable[2], this.mListSelectedDrawable[2], mTextColorDefault, mTextColorSelected);
                        mLayoutParams.setMargins(mGapBetweenItems / 2, 0, 0, 0);
                    } else {
                        mItemIndicator = new TextViewSwitcher(getContext(), this.mListDefaultDrawable[1], this.mListSelectedDrawable[1], mTextColorDefault, mTextColorSelected);
                        mLayoutParams.setMargins(mGapBetweenItems / 2, 0, mGapBetweenItems / 2, 0);
                    }
                }

                mItemIndicator.setText(mListTabName.get(i));
                mItemIndicator.setLayoutParams(mLayoutParams);
                mItemIndicator.setGravity(Gravity.CENTER);
                mItemIndicator.setMaxLines(MAX_NUM_LINES);
                mItemIndicator.setEllipsize(TextUtils.TruncateAt.END);
                mListItemIndicator.add(mItemIndicator);

                mLayoutContentItem.addView(mItemIndicator);
                addView(mLayoutContentItem);
            }

        }

        setCurrentSelectedItem(0);

        for (int i = 0; i < mListItemIndicator.size(); i++) {
            final int mCurrentItem = i;
            mListItemIndicator.get(i).setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (!mListItemIndicator.get(mCurrentItem).isDrawableSelected()) {
                        setCurrentSelectedItem(mCurrentItem);
                        mViewPager.setCurrentItem(mCurrentItem);
                    }
                }
            });
        }

    }

    private ViewPager.OnPageChangeListener onPageChangeListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        }

        @Override
        public void onPageSelected(int position) {
            setCurrentSelectedItem(position);
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };

    private void setCurrentSelectedItem(int mCurrentItem) {
        for (int j = 0; j < mListItemIndicator.size(); j++) {
            if (j == mCurrentItem) {
                mListItemIndicator.get(j).setDrawabeSelected(true);
            } else {
                mListItemIndicator.get(j).setDrawabeSelected(false);
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
        mViewPager.setOnPageChangeListener(onPageChangeListener);
        mNumTab = mPagerAdapter.getCount();
        mListTabName = listTabName;
        initView(mListDefaultDrawable, mListSelectedDrawable, mListOneItemDrawble, null);
    }

}