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

    private WallpaperObjectManager                 mWallpaperObjectManager;
    private boolean                                mRun = false;
    private boolean                                mVisible = true;
    private long                                   mPreviousTime = 0;
    private long                                   mCurrentTime = 0;

    public WallpaperThread(Resources resources, SurfaceHolder surfaceHolder) {
        mWallpaperObjectManager = new WallpaperObjectManager(resources, surfaceHolder);
    }

    public void setSurfaceSize(int width, int height) {
        mWallpaperObjectManager.setSurfaceSize(width, height);

        synchronized (this) {
            notify();
        }
    }

    public void onVisibilityChanged(boolean visible) {
        mVisible = visible;

        synchronized (this) {
            notify();
        }
    }

    @Override
    public synchronized void start() {
        super.start();    //To change body of overridden methods use File | Settings | File Templates.

        mRun = true;
        mVisible = true;
    }

    public void stopTrigger() {
        mRun = false;
        mVisible = false;

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
        mWallpaperObjectManager.initialize();

        while (mRun) {
            mCurrentTime = System.currentTimeMillis();
            final long deltaTime = mCurrentTime - mPreviousTime;
            mPreviousTime = mCurrentTime;

            mWallpaperObjectManager.tick(deltaTime);

            if ( mVisible ) {
                mWallpaperObjectManager.render();
            }
        }
    }

}
