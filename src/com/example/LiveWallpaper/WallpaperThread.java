package com.example.LiveWallpaper;

import android.content.res.Resources;
import android.view.SurfaceHolder;

/**
 * Created with IntelliJ IDEA.
 * User: kangsangwook
 * Date: 13. 3. 23.
 * Time: 오후 5:48
 * To change this template use File | Settings | File Templates.
 */
public class WallpaperThread extends Thread {

    private Wallpaper                               mWallpaper;
    private boolean                                mRun = false;
//    private boolean                                mWait = false;
    private long                                   mPreviousTime = 0;
    private long                                   mCurrentTime = 0;
    private SurfaceHolder                           mSurfaceHolder = null;
    private int                                     mSurfaceWidth = 0;
    private int                                     mSurfaceHeight = 0;


    public WallpaperThread(Resources resources, SurfaceHolder surfaceHolder) {
        mWallpaper = new Wallpaper(resources, R.drawable.elin_3);
        mSurfaceHolder = surfaceHolder;
        //mWait = false;
    }
/*
    public void setSurfaceHolder(SurfaceHolder surfaceHolder) {
        Log.d("class WallpaperThread", "setSurfaceHolder(SurfaceHolder surfaceHolder)");

        if ( mSurfaceHolder != surfaceHolder ) {
            synchronized (this) {
                mSurfaceHolder = surfaceHolder;
                notify();
            }
        }

        //mWallpaper.refresh();
    }
*/
    public void setSurfaceSize(int width, int height) {
        mSurfaceWidth = width;
        mSurfaceHeight = height;

        synchronized (this) {
            notify();
        }
    }
    /*
        public void onVisibilityChanged(boolean visible) {

            if ( visible ) {
                resumeRender();
            } else {
                pauseRender();
            }
        }    */
/*
    public void pauseRender() {
        mWait = true;
        synchronized (this) {
            notify();
        }
    }

    public void resumeRender() {
        mWait = false;
        synchronized (this) {
            notify();
        }
    }
*/
    @Override
    public synchronized void start() {
        super.start();    //To change body of overridden methods use File | Settings | File Templates.

        mRun = true;
        //mWallpaper.refresh();
    }

    public void stopTrigger() {
        mRun = false;
        synchronized(this){
            notify();
        }

        boolean retry = true;
        while (retry) {
            try {
                join();
                retry = false;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void run() {
        while (mRun) {
            mCurrentTime = System.currentTimeMillis();

            synchronized (mSurfaceHolder) {
                mWallpaper.render(mSurfaceHolder, mSurfaceWidth, mSurfaceHeight);
            }

            mPreviousTime = mCurrentTime;
/*
            synchronized (this) {
                if ( mWait ) {
                    try {
                        wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
*/
        }
    }

}
