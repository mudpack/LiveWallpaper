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

    private static final float MIN_DELTA_TIME = 1000.f / 60.f;

    private WallpaperObjectManager                 mWallpaperObjectManager;
    private boolean                                mRun = false;
    private boolean                                mVisible = true;
    private long                                   mPreviousTime = 0;

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

    private long calcDeltaTime() {

        long currentTime = System.currentTimeMillis();
        long deltaTime = currentTime - mPreviousTime;

        // max fps를 60으로 고정시키도록...
        if ( deltaTime > MIN_DELTA_TIME ) {
            mPreviousTime = currentTime;
            return deltaTime;
        } else {
            return -1;
        }
    }




    @Override
    public void run() {
        mWallpaperObjectManager.initialize();

        while (mRun) {

            final long deltaTime = calcDeltaTime();
            if ( deltaTime == -1 ) {
                continue;
            }

            mWallpaperObjectManager.tick(deltaTime);

            if ( mVisible ) {
                mWallpaperObjectManager.render();
            }
        }
    }

}
