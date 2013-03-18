package com.example.LiveWallpaper;

//import android.app.Service;
//import android.content.Intent;
//import android.os.IBinder;
import android.graphics.*;
import android.service.wallpaper.WallpaperService;
//import android.util.DisplayMetrics;
import android.view.Display;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.WindowManager;

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
        /*public IBinder onBind(Intent intent) {
            return null;
        }*/

        private int mCount = 0;

        @Override
        public void onCreate(SurfaceHolder surfaceHolder) {
            super.onCreate(surfaceHolder);    //To change body of overridden methods use File | Settings | File Templates.
        }

        @Override
        public void onOffsetsChanged(float xOffset, float yOffset, float xOffsetStep, float yOffsetStep, int xPixelOffset, int yPixelOffset) {
            super.onOffsetsChanged(xOffset, yOffset, xOffsetStep, yOffsetStep, xPixelOffset, yPixelOffset);    //To change body of overridden methods use File | Settings | File Templates.

            final SurfaceHolder surfaceHolder = getSurfaceHolder();
            Canvas canvas = surfaceHolder.lockCanvas();
            if ( canvas != null )
            {
                /*Paint paint = new Paint();
                paint.setColor(Color.RED);
                paint.setStrokeWidth(3.f);
                */



                /*
                canvas.drawRect(rect.exactCenterX() - rect.width()/4.f,
                                rect.exactCenterY() - rect.height()/4.f,
                                rect.exactCenterX() + rect.width()/4.f,
                                rect.exactCenterY() + rect.height()/4.f,
                                paint);
                */
                Bitmap image = BitmapFactory.decodeResource(getResources(), R.drawable.elin_3);

                Rect source = new Rect(0, 0, image.getWidth(), image.getHeight());
                //Rect destination = new Rect(0, 0, getWidth(), getHeight());
                Rect destination = new Rect();
                Display display = ((WindowManager) getSystemService(WINDOW_SERVICE)).getDefaultDisplay();
                display.getRectSize(destination);

                canvas.drawBitmap(image, source, destination, null);



            }
            surfaceHolder.unlockCanvasAndPost(canvas);
        }

        @Override
        public void onTouchEvent(MotionEvent event) {
            super.onTouchEvent(event);    //To change body of overridden methods use File | Settings | File Templates.

            if ( event.getAction() == MotionEvent.ACTION_DOWN)
            {
                final SurfaceHolder surfaceHolder = getSurfaceHolder();
                Canvas canvas = surfaceHolder.lockCanvas();
                if ( canvas != null )
                {
                Bitmap image = BitmapFactory.decodeResource(getResources(), R.drawable.elin_3);

                Rect source = new Rect(0, 0, image.getWidth(), image.getHeight());
                //Rect destination = new Rect(0, 0, getWidth(), getHeight());
                Rect destination = new Rect();
                Display display = ((WindowManager) getSystemService(WINDOW_SERVICE)).getDefaultDisplay();
                display.getRectSize(destination);

                canvas.drawBitmap(image, source, destination, null);

                    Paint paint = new Paint();
                    paint.setColor(Color.RED);
                    paint.setStrokeWidth(20.f);
                    paint.setTextSize(70.f);

                    String text = "default";
                    switch (mCount++%5) {
                        case 0: {
                            text = "   뭐야?  ";
                            break;
                        }
                        case 1: {
                            text = "   건드리지마!!!  ";
                            break;
                        }
                        case 2: {
                            text = "   야메떼!  ";
                            break;
                        }
                        case 3: {
                            text = "   야메떼 구다사이~~  ";
                            break;
                        }
                        case 4: {
                            text = "   흥!  ";
                            break;
                        }
                        default: {
                            // do nothing
                            text = "   뭘봐?  ";
                            break;
                        }

                    }


                    canvas.drawText(text, 20, 200, paint);

                }
                surfaceHolder.unlockCanvasAndPost(canvas);

            }
        }

        @Override
        public void onSurfaceCreated(SurfaceHolder holder) {
            super.onSurfaceCreated(holder);    //To change body of overridden methods use File | Settings | File Templates.
        }
    }
}
