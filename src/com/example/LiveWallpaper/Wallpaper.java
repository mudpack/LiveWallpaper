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
    private Bitmap                      mBackgroundImage = null;
//    private boolean                    mUpdated = false;

    public Wallpaper(Resources resources, int backgroundImageId) {
        mResources =resources;
        mBackgroundImage = BitmapFactory.decodeResource(mResources, backgroundImageId);
    }

    /*public void refresh() {
        mUpdated = true;
    }*/

    public void render(SurfaceHolder surfaceHolder, int width, int height) {
  //      if ( mUpdated ) {

            assert ( surfaceHolder != null && mBackgroundImage != null );

            Canvas canvas = null;
            try {
                canvas = surfaceHolder.lockCanvas();
                if ( canvas != null ) {
                    Rect source = new Rect(0, 0, mBackgroundImage.getWidth(), mBackgroundImage.getHeight());
                    Rect destination = new Rect(0, 0, width, height);
                    canvas.drawBitmap(mBackgroundImage, source, destination, null);
                }
            } finally {
                if(canvas != null) {
                    surfaceHolder.unlockCanvasAndPost(canvas);
                }
            }

 //           mUpdated = false;
 //       }
    }
}
