package com.example.LiveWallpaper;

import android.graphics.Canvas;

/**
 * Created with IntelliJ IDEA.
 * User: airsulg
 * Date: 13. 3. 26
 * Time: 오후 8:28
 * To change this template use File | Settings | File Templates.
 */
public interface Renderable {
    public void render(Canvas canvas, int width, int height);
}
