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
public class WallpaperThread implements Runnable {

    private Wallpaper                           mWallpaper;
    private boolean                             mRunning = false;

    public WallpaperThread(Resources resources) {
        mWallpaper = new Wallpaper(resources, R.drawable.elin_3);
    }

    public void setSurfaceHolder(SurfaceHolder surfaceHolder) {
        mWallpaper.setSurfaceHolder(surfaceHolder);
    }

    public void onSurfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        mWallpaper.onSurfaceChanged(holder, format, width, height);
    }

    public void start() {
        mRunning = true;

        synchronized(this){
            notify();
        }
    }

    public void stop() {
        mRunning = false;

        synchronized(this){
            notify();
        }
    }

    @Override
    public void run() {
        while (mRunning) {
            mWallpaper.render();
        }
    }
}
