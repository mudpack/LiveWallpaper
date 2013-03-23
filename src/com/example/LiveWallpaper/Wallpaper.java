package com.example.LiveWallpaper;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.view.SurfaceHolder;

/**
 * Created with IntelliJ IDEA.
 * User: kangsangwook
 * Date: 13. 3. 23.
 * Time: 오후 5:49
 * To change this template use File | Settings | File Templates.
 */
public class Wallpaper {

    private Resources                   mResources = null;
    private SurfaceHolder               mSurfaceHolder = null;
    private Bitmap                      mBackgroundImage = null;
    private int                         mSurfaceFormat = -1;
    private int                         mSurfaceWidth = 0;
    private int                         mSurfaceHeight = 0;
    private boolean                     mUpdated = false;

    public Wallpaper(Resources resources, int backgroundImageId) {
        mResources =resources;
        mBackgroundImage = BitmapFactory.decodeResource(mResources, backgroundImageId);
    }

    public void setSurfaceHolder(SurfaceHolder surfaceHolder) {
        if ( mSurfaceHolder != surfaceHolder ) {
            synchronized (this) {
                mSurfaceHolder = surfaceHolder;
            }
            mUpdated = true;
        }
    }

    public void onSurfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        setSurfaceHolder(holder);

        if ( mSurfaceFormat != format ) {
            mSurfaceFormat = format;
            mUpdated = true;
        }

        if ( mSurfaceWidth != width ) {
            mSurfaceWidth = width;
            mUpdated = true;
        }

        if ( mSurfaceHeight != height ) {
            mSurfaceHeight = height;
            mUpdated = true;
        }
    }

    public void render() {
        if ( mUpdated ) {

            assert ( mSurfaceHolder != null && mBackgroundImage != null && mSurfaceWidth != 0 && mSurfaceHeight != 0 );

            Canvas canvas = null;
            try {
                canvas = mSurfaceHolder.lockCanvas();
                if ( canvas != null ) {
                    Rect source = new Rect(0, 0, mBackgroundImage.getWidth(), mBackgroundImage.getHeight());
                    Rect destination = new Rect(0, 0, mSurfaceWidth, mSurfaceHeight);
                    canvas.drawBitmap(mBackgroundImage, source, destination, null);
                }
            } finally {
                if(canvas != null) {
                    mSurfaceHolder.unlockCanvasAndPost(canvas);
                }
            }

            mUpdated = false;
        }
    }
}
