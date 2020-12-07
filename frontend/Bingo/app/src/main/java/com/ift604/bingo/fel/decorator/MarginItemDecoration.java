package com.ift604.bingo.fel.decorator;

import android.graphics.Rect;
import android.view.View;

import androidx.recyclerview.widget.RecyclerView;

public class MarginItemDecoration extends RecyclerView.ItemDecoration{

    private final int horizontalSpaceWidth;
    private final int verticalSpaceWidth;

    public MarginItemDecoration(int horizontalSpaceWidth, int verticalSpace) {
        this.horizontalSpaceWidth = horizontalSpaceWidth;
        this.verticalSpaceWidth = verticalSpace;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent,
                               RecyclerView.State state) {
        outRect.left = horizontalSpaceWidth;
        outRect.right = horizontalSpaceWidth;
        outRect.bottom = verticalSpaceWidth;
        outRect.top = verticalSpaceWidth;
    }
}
