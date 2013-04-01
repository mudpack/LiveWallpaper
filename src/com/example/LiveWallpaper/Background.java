package com.example.LiveWallpaper;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;

/**
 * Created with IntelliJ IDEA.
 * User: kangsangwook
 * Date: 13. 3. 23.
 * Time: 오후 5:49
 * To change this template use File | Settings | File Templates.
 */
public class Background extends WallpaperObject {


    public static int getResourceId() {
        return R.drawable.elin_3;
    }

    public Background(Bitmap _bitmap) {
        super(type.BACKGROUND, _bitmap);
    }

    @Override
    public void tick(long milliseconds) {
        super.tick(milliseconds);

        // background image는 tick에서 처리할 일은 없음
    }

    @Override
    public void render(Canvas canvas, int width, int height) {
        assert ( canvas != null && mBitmap != null );

        if ( canvas == null || mBitmap == null )
            return;

        Rect source = new Rect(0, 0, mBitmap.getWidth(), mBitmap.getHeight());
        Rect destination = new Rect(0, 0, width, height);
        canvas.drawBitmap(mBitmap, source, destination, null);
    }
}
