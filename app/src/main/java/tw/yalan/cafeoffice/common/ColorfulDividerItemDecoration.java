package tw.yalan.cafeoffice.common;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ItemDecoration;
import android.support.v7.widget.RecyclerView.LayoutManager;
import android.support.v7.widget.RecyclerView.State;
import android.view.View;

/**
 * Created by Alan Ding on 2016/2/19.
 */
public class ColorfulDividerItemDecoration extends ItemDecoration {

    private int mDividerHeight;
    private int mColor;

    private int mOffsets;
    private int mAdjustedOffsets;
    private boolean mToAdjustOffsets = false;


    /**
     * Create new DividerItemDecoration with the specified height and color
     * without additional offsets
     */
    public ColorfulDividerItemDecoration(int dividerHeight, int color) {
        mDividerHeight = dividerHeight;
        mColor = color;
        mOffsets = mDividerHeight;
    }


    /**
     * Create new DividerItemDecoration with the specified height, color and offsets between
     * elements. Offsets can not be less than divider height.
     */
    public ColorfulDividerItemDecoration(int dividerHeight, int color, int offsets, boolean adjustOffsets) {
        super();

        if (offsets < dividerHeight) {
            throw new IllegalArgumentException("Offsets can not be less than divider height");
        } else if (adjustOffsets) {
            mToAdjustOffsets = true;
            mAdjustedOffsets = (offsets - dividerHeight) / 2;
        }

        mOffsets = offsets;
        mDividerHeight = dividerHeight;
        mColor = color;
    }


    /**
     * Create new DividerItemDecoration without any decor
     */
    public ColorfulDividerItemDecoration() {
        super();
        mDividerHeight = 1;
        mColor = 0x8a000000;
    }


    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent,
                               State state) {

        LayoutManager layoutManager = parent.getLayoutManager();
        if (layoutManager == null) {
            throw new RuntimeException("LayoutManager not found");
        }
        if (layoutManager.getPosition(view) != 0)
            outRect.top = mOffsets;
    }

    @Override
    public void onDraw(Canvas c, RecyclerView parent, State state) {

    }

    @Override
    public void onDrawOver(Canvas c, RecyclerView parent, State state) {

        Paint paint = new Paint();
        paint.setColor(mColor);

        int left = parent.getPaddingLeft();
        int right = left + parent.getWidth() - parent.getPaddingRight();

        for (int i = 0; i < parent.getChildCount(); i++) {

            View child = parent.getChildAt(i);

            int top = 0;
            //try to place divider in the middle of the space between elements
            if (!mToAdjustOffsets)
                top = child.getBottom();
            else top = child.getBottom() + mAdjustedOffsets;
            int bottom = top + mDividerHeight;

            c.drawRect(left, top, right, bottom, paint);
        }
    }

}
