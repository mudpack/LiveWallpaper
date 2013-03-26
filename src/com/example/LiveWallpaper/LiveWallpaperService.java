package com.example.LiveWallpaper;

import android.service.wallpaper.WallpaperService;
import android.view.SurfaceHolder;

/**
 * Created with IntelliJ IDEA.
 * User: airsulg
 * Date: 13. 3. 2.
 * Time: 오후 7:46
 * To change this template use File | Settings | File Templates.
 */
public class LiveWallpaperService extends WallpaperService {

    @Override
    public Engine onCreateEngine() {
        return new LiveWallpaperServiceEngine();  //To change body of implemented methods use File | Settings | File Templates.
    }

    public class LiveWallpaperServiceEngine extends WallpaperService.Engine {

        private WallpaperThread             mWallpaperThread = null;

        public LiveWallpaperServiceEngine() {
        }

        private void resume(SurfaceHolder surfaceHolder) {

            if ( mWallpaperThread == null ) {
                mWallpaperThread = new WallpaperThread(getResources(), surfaceHolder);
            }

            mWallpaperThread.start();
        }

        private void pause() {
            if ( mWallpaperThread != null ) {
                mWallpaperThread.stopTrigger();
                mWallpaperThread = null;
            }
        }

        @Override
        public void onSurfaceCreated(SurfaceHolder holder) {
            super.onSurfaceCreated(holder);    //To change body of overridden methods use File | Settings | File Templates.

            resume(holder);
        }

        @Override
        public void onSurfaceChanged(SurfaceHolder holder, int format, int width, int height) {
            super.onSurfaceChanged(holder, format, width, height);    //To change body of overridden methods use File | Settings | File Templates.
            mWallpaperThread.setSurfaceSize(width, height);
        }

        @Override
        public void onSurfaceDestroyed(SurfaceHolder holder) {
            super.onSurfaceDestroyed(holder);    //To change body of overridden methods use File | Settings | File Templates.

            pause();
        }
    }
}
