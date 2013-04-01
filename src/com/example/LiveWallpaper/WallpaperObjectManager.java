package com.example.LiveWallpaper;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.view.SurfaceHolder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

/**
 * Created with IntelliJ IDEA.
 * User: kangsangwook
 * Date: 13. 3. 29.
 * Time: 오후 2:01
 * To change this template use File | Settings | File Templates.
 */
public class WallpaperObjectManager {

    private static final long CREATE_LEAF_DURATION = 2000;      // millisec
    private static final long CREATE_LEAF_DURATION_VAR_1 = 300; // millisec

    private HashMap<WallpaperObject.type, Bitmap>       mBitmapMap;
    private ArrayList<WallpaperObject>  mObjectList;
    private SurfaceHolder               mSurfaceHolder;
    private int                         mSurfaceWidth = 0;
    private int                         mSurfaceHeight = 0;
    private Resources                   mResources;
    private Random                      mRandom;
    private long                        mNextLeafCreateTime = 0;

    public WallpaperObjectManager(Resources resources, SurfaceHolder surfaceHolder) {
        mBitmapMap = new HashMap<WallpaperObject.type, Bitmap>();
        mObjectList = new ArrayList<WallpaperObject>();
        mRandom = new Random();

        mResources = resources;
        mSurfaceHolder = surfaceHolder;
    }

    private void initBitmaps() {
        Bitmap backgroundBitmap = BitmapFactory.decodeResource(mResources, Background.getResourceId());
        mBitmapMap.put(WallpaperObject.type.BACKGROUND, backgroundBitmap);

        Bitmap leafBitmap = BitmapFactory.decodeResource(mResources, Leaf.getResourceId());
        leafBitmap = Bitmap.createScaledBitmap(leafBitmap, Leaf.width(), Leaf.height(), true);
        mBitmapMap.put(WallpaperObject.type.LEAF, leafBitmap);
    }

    public void initialize() {
        initBitmaps();

        mObjectList.clear();
        createWallpaperObject(WallpaperObject.type.BACKGROUND);
    }

    private WallpaperObject createWallpaperObject(WallpaperObject.type type) {
        WallpaperObject newObject = WallpaperObject.createInstanceFromBitmap(type, mBitmapMap.get(type));
        mObjectList.add(newObject);

        return newObject;
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
        if ( mNextLeafCreateTime <= 0 ) {

            Leaf newLeaf = (Leaf)createWallpaperObject(WallpaperObject.type.LEAF);
            assert( newLeaf != null );

            newLeaf.initialize( mRandom.nextInt(mSurfaceWidth), 0 );

            final long range = ((long)mRandom.nextInt((int)CREATE_LEAF_DURATION_VAR_1*2) - CREATE_LEAF_DURATION_VAR_1);
            mNextLeafCreateTime = CREATE_LEAF_DURATION + range;
        }

        mNextLeafCreateTime -= milliseconds;
    }
}