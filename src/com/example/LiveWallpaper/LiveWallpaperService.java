package com.example.LiveWallpaper;

import android.service.wallpaper.WallpaperService;
import android.view.MotionEvent;
import android.view.SurfaceHolder;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created with IntelliJ IDEA.
 * User: airsulg
 * Date: 13. 3. 2.
 * Time: 오후 7:46
 * To change this template use File | Settings | File Templates.
 */
public class LiveWallpaperService extends WallpaperService {
    /*public IBinder onBind(Intent intent) {
        return null;
    }*/

    @Override
    public Engine onCreateEngine() {
        return new LiveWallpaperServiceEngine();  //To change body of implemented methods use File | Settings | File Templates.
    }

    public class LiveWallpaperServiceEngine extends WallpaperService.Engine {

        private WallpaperThread             mWallpaperThread;
        private ExecutorService             mExecutorService;

        public LiveWallpaperServiceEngine() {
            mWallpaperThread = new WallpaperThread(getResources());
            mExecutorService = Executors.newCachedThreadPool();
        }

        @Override
        public void onCreate(SurfaceHolder surfaceHolder) {
            super.onCreate(surfaceHolder);    //To change body of overridden methods use File | Settings | File Templates.
            mWallpaperThread.setSurfaceHolder(surfaceHolder);
        }

        @Override
        public void onOffsetsChanged(float xOffset, float yOffset, float xOffsetStep, float yOffsetStep, int xPixelOffset, int yPixelOffset) {
            super.onOffsetsChanged(xOffset, yOffset, xOffsetStep, yOffsetStep, xPixelOffset, yPixelOffset);    //To change body of overridden methods use File | Settings | File Templates.
        }

        @Override
        public void onTouchEvent(MotionEvent event) {
            super.onTouchEvent(event);    //To change body of overridden methods use File | Settings | File Templates.
        }

        @Override
        public void onSurfaceCreated(SurfaceHolder holder) {
            super.onSurfaceCreated(holder);    //To change body of overridden methods use File | Settings | File Templates.

            mWallpaperThread.start();
            mExecutorService.execute(mWallpaperThread);
        }

        @Override
        public void onSurfaceDestroyed(SurfaceHolder holder) {
            super.onSurfaceDestroyed(holder);    //To change body of overridden methods use File | Settings | File Templates.

            mWallpaperThread.stop();
        }

        @Override
        public void onSurfaceChanged(SurfaceHolder holder, int format, int width, int height) {
            super.onSurfaceChanged(holder, format, width, height);    //To change body of overridden methods use File | Settings | File Templates.
            mWallpaperThread.onSurfaceChanged(holder, format, width, height);
        }

        @Override
        public void onVisibilityChanged(boolean visible) {
            super.onVisibilityChanged(visible);    //To change body of overridden methods use File | Settings | File Templates.
        }
    }
}
