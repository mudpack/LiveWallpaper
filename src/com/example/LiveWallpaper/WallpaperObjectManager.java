package com.example.LiveWallpaper;

import android.content.res.Resources;
import android.graphics.Canvas;
import android.view.SurfaceHolder;

import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: kangsangwook
 * Date: 13. 3. 29.
 * Time: 오후 2:01
 * To change this template use File | Settings | File Templates.
 */
public class WallpaperObjectManager {

    private ArrayList<WallpaperObject>  mObjectList;
    private SurfaceHolder               mSurfaceHolder;
    private int                         mSurfaceWidth = 0;
    private int                         mSurfaceHeight = 0;
    private Resources                   mResources;

    public WallpaperObjectManager(Resources resources, SurfaceHolder surfaceHolder) {
        mObjectList = new ArrayList<WallpaperObject>();
        mResources = resources;
        mSurfaceHolder = surfaceHolder;
    }

    public void initialize() {
        mObjectList.clear();
        mObjectList.add(WallpaperObject.createInstanceFromResource(WallpaperObject.type.BACKGROUND, mResources, R.drawable.elin_3));
    }

    public void setSurfaceSize(int width, int height) {
        mSurfaceWidth = width;
        mSurfaceHeight = height;
    }

    public void tick(long milliseconds) {

        removeLeaves();
        createLeaves(milliseconds);

        for(WallpaperObject object : mObjectList) {
            object.tick(milliseconds);
        }
    }

    public void render() {

        assert ( mSurfaceHolder != null );

        if ( mSurfaceHolder == null )
            return;

        Canvas canvas = null;
        try {
            canvas = mSurfaceHolder.lockCanvas();
            if ( canvas != null ) {

                for(WallpaperObject object : mObjectList) {
                    object.render(canvas, mSurfaceWidth, mSurfaceHeight);
                }
            }
        } finally {
            if(canvas != null) {
                mSurfaceHolder.unlockCanvasAndPost(canvas);
            }
        }
    }

    private void removeLeaves() {
        for ( int i = 0; i < mObjectList.size(); ++i ) {

            WallpaperObject object = mObjectList.get(i);
            if ( object.getType() == WallpaperObject.type.LEAF ) {

                Leaf leaf = (Leaf)object;
                if ( leaf.isExpired(mSurfaceWidth, mSurfaceHeight) ) {
                    mObjectList.remove(i--);
                }

            }

        }
    }

    private void createLeaves(long milliseconds) {

    }
}
