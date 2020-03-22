package com.othershe.combinebitmap.layout;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;

public class WechatLayoutManager implements ILayoutManager {
    @Override
    public Bitmap combineBitmap(int size, int subWidth, float heightWidthScale, int gap, int gapColor, Bitmap[] bitmaps) {
        Bitmap result = Bitmap.createBitmap(size, (int) (size * heightWidthScale), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(result);
        if (gapColor == 0) {
            gapColor = Color.WHITE;
        }
        canvas.drawColor(gapColor);

        int count = bitmaps.length;
        Bitmap subBitmap;

        for (int i = 0; i < count; i++) {
            if (bitmaps[i] == null) {
                continue;
            }
            subBitmap = Bitmap.createScaledBitmap(bitmaps[i], subWidth, (int) (subWidth * heightWidthScale), true);

            float x = 0;
            float y = 0;

            if (count == 2) {
                x = gap + i * (subWidth + gap);
                y = (size - subWidth) / 2.0f;
            } else if (count == 3) {
                if (i == 0) {
                    x = (size - subWidth) / 2.0f;
                    y = gap;
                } else {
                    x = gap + (i - 1) * (subWidth + gap);
                    y = subWidth * heightWidthScale + 2 * gap;
                }
            } else if (count == 4) {
                x = gap + (i % 2) * (subWidth + gap);
                if (i < 2) {
                    y = gap;
                } else {
                    y = subWidth * heightWidthScale + 2 * gap;
                }
            } else if (count == 5) {
                if (i == 0) {
                    x = (size - 2 * subWidth  - gap) / 2.0f;
                    y = (size - 2 * subWidth * heightWidthScale - gap) / 2.0f;
                } else if (i == 1) {
                    x = subWidth + (size - 2 * subWidth  - gap) / 2.0f; //(size + gap) / 2.0f;
                    y = (size - 2 * subWidth * heightWidthScale - gap) / 2.0f;
                } else if (i > 1) {
                    x = gap + (i - 2) * (subWidth + gap);
                    y = (size + gap) / 2.0f;
                }
            } else if (count == 6) {
                x = gap + (i % 3) * (subWidth + gap);
                if (i < 3) {
                    y = gap + (size + gap) / 2.0f - subWidth * heightWidthScale;//(size - 2 * subWidth * heightWidthScale - gap) / 2.0f;
                } else {
                    y = gap + (size + gap) / 2.0f;
                }
            } else if (count == 7) {
                if (i == 0) {
                    x = (size - subWidth * heightWidthScale) / 2.0f;
                    y = gap;
                } else if (i < 4) {
                    x = gap + (i - 1) * (subWidth * heightWidthScale + gap);
                    y = subWidth * heightWidthScale + 2 * gap;
                } else {
                    x = gap + (i - 4) * (subWidth * heightWidthScale + gap);
                    y = gap + 2 * (subWidth * heightWidthScale + gap);
                }
            } else if (count == 8) {
                if (i == 0) {
                    x = (size - 2 * subWidth * heightWidthScale - gap) / 2.0f;
                    y = gap;
                } else if (i == 1) {
                    x = (size - gap) / 2.0f;
                    y = gap;
                } else if (i < 5) {
                    x = gap + (i - 2) * (subWidth + gap);
                    y = subWidth * heightWidthScale + 2 * gap;
                } else {
                    x = gap + (i - 5) * (subWidth + gap);
                    y = gap + 2 * (subWidth * heightWidthScale + gap);
                }
            } else if (count == 9) {
                x = gap + (i % 3) * (subWidth + gap);
                if (i < 3) {
                    y = gap;
                } else if (i < 6) {
                    y = subWidth * heightWidthScale + 2 * gap;
                } else {
                    y = gap + 2 * (subWidth * heightWidthScale + gap);
                }
            }

            canvas.drawBitmap(subBitmap, x, y, null);
        }
        return result;
    }

}
