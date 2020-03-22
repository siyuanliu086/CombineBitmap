package com.othershe.combinebitmap.layout;

import android.graphics.Bitmap;

public interface ILayoutManager {
    Bitmap combineBitmap(int size, int width, float heightWidthScale, int gap, int gapColor, Bitmap[] bitmaps);
}
