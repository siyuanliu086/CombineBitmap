package com.othershe.combinebitmap.helper;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.othershe.combinebitmap.listener.OnHandlerListener;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;


public class CombineHelper {
    public static CombineHelper init() {
        return CombineHelper.SingletonHolder.instance;
    }


    private CombineHelper(){

    }

    private static class SingletonHolder {
        private static final CombineHelper instance = new CombineHelper();
    }

    /**
     * 通过url加载
     *
     * @param builder
     */
    private void loadByUrls(final Builder builder) {
        int subSize = builder.subSize;
        Bitmap defaultBitmap = null;
        if (builder.placeholder != 0) {
            defaultBitmap = CompressHelper.getInstance()
                    .compressResource(builder.context.getResources(), builder.placeholder, subSize, subSize);
        }
        ProgressHandler handler = new ProgressHandler(defaultBitmap, builder.count, new OnHandlerListener() {
            @Override
            public void onComplete(Bitmap[] bitmaps) {
                setBitmap(builder, bitmaps);
            }
        });
        for (int i = 0; i < builder.count; i++) {
            BitmapLoader.getInstance(builder.context).asyncLoad(i, builder.urls[i], subSize, subSize, handler);
        }
    }

    /**
     * 通过filePaths加载
     *
     * @param builder
     */
    private void loadByFilePaths(final Builder builder) {
        int subSize = builder.subSize;
        Bitmap[] compressedBitmaps = new Bitmap[builder.count];
        for (int i = 0; i < builder.count; i++) {
            compressedBitmaps[i] = CompressHelper.getInstance().compressResource(getLocalBitmap(builder.filePaths[i]), subSize, subSize);
        }
        setBitmap(builder, compressedBitmaps);
    }

    /**
     * 加载本地图片
     * @param filePath
     * @return
     */
    private Bitmap getLocalBitmap(String filePath) {
        Bitmap bitmap = null;
        if(!new File(filePath).exists()) {
            return null;
        }
        try {
            FileInputStream fis = new FileInputStream(filePath);
            bitmap = BitmapFactory.decodeStream(fis);  ///把流转化为Bitmap图片
            fis.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bitmap;
    }

    /**
     * 通过图片的资源id、bitmap加载
     *
     * @param builder
     */
    private void loadByResBitmaps(Builder builder) {
        int subSize = builder.subSize;
        Bitmap[] compressedBitmaps = new Bitmap[builder.count];
        for (int i = 0; i < builder.count; i++) {
            if (builder.resourceIds != null) {
                compressedBitmaps[i] = CompressHelper.getInstance()
                        .compressResource(builder.context.getResources(), builder.resourceIds[i], subSize, subSize);
            } else if (builder.bitmaps != null) {
                compressedBitmaps[i] = CompressHelper.getInstance()
                        .compressResource(builder.bitmaps[i], subSize, subSize);
            }
        }
        setBitmap(builder, compressedBitmaps);
    }

    public void load(Builder builder) {
        if (builder.progressListener != null) {
            builder.progressListener.onStart();
        }

        if (builder.urls != null) {
            loadByUrls(builder);
        } else if(builder.filePaths != null){
            loadByFilePaths(builder);
        } else if(builder.bitmaps != null){
            loadByResBitmaps(builder);
        }
    }

    private void setBitmap(final Builder b, Bitmap[] bitmaps) {
        try {
            Bitmap placeHolderBitmap = CompressHelper.getInstance()
                    .compressResource(b.context.getResources(), b.placeholder, b.subSize, b.subSize);
            Bitmap result = b.layoutManager.combineBitmap(b.size, b.subSize, b.heightWidthScale, b.gap, b.gapColor, bitmaps, placeHolderBitmap);

            // 返回最终的组合Bitmap
            if (b.progressListener != null) {
                b.progressListener.onComplete(result);
            }

            // 给ImageView设置最终的组合Bitmap
            if (b.imageView != null) {
                b.imageView.setImageBitmap(result);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
