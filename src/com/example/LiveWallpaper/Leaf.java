package com.example.LiveWallpaper;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;

/**
 * Created with IntelliJ IDEA.
 * User: kangsangwook
 * Date: 13. 3. 29.
 * Time: 오후 9:42
 * To change this template use File | Settings | File Templates.
 */
public class Leaf extends WallpaperObject {

    float                       mPosX = 0.f;
    float                       mPosY = 0.f;

    final static float          mVelocity = 50.f;// pixels per second
    final static int            mSize     = 128;

    public static int getResourceId() {
        return R.drawable.leaf;
    }

    public static int width() {
        return 128;
    }

    public static int height() {
        return 128;
    }


    public Leaf(Bitmap _bitmap) {
        super(type.LEAF, _bitmap);
    }

    public void initialize(int initPosX, int initPosY) {
        mPosX = initPosX;
        mPosY = initPosY;
    }

    public boolean isExpired(int width, int height) {
        if ( mPosY > height ) {
            return true;
        }

        return false;
    }

    @Override
    public void tick(long milliseconds) {
        super.tick(milliseconds);

        mPosY += mVelocity * (milliseconds/1000.f);
    }

    @Override
    public void render(Canvas canvas, int width, int height) {
        assert ( canvas != null && mBitmap != null );

        if ( canvas == null || mBitmap == null )
            return;

        //Rect source = new Rect(0, 0, mBitmap.getWidth(), mBitmap.getHeight());
        //Rect destination = new Rect((int)mPosX, (int)mPosY, (int)(mPosX+mSize), (int)(mPosY+mSize));
        //canvas.drawBitmap(mBitmap, source, destination, null);
        canvas.drawBitmap(mBitmap, mPosX, mPosY, null);
    }
}
