package com.example.LiveWallpaper;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;

/**
 * Created with IntelliJ IDEA.
 * User: kangsangwook
 * Date: 13. 3. 29.
 * Time: 오후 1:53
 * To change this template use File | Settings | File Templates.
 */
public class WallpaperObject implements Renderable {

    public enum type {
        BACKGROUND,
        LEAF
    }

    protected type                            mType;
    protected Bitmap                          mBitmap;

    public static WallpaperObject createInstanceFromResource(type _type, Resources resources, int id) {
        Bitmap bitmap = BitmapFactory.decodeResource(resources, id);
        return createInstanceFromBitmap(_type, bitmap);
    }

    public static WallpaperObject createInstanceFromBitmap(type _type, Bitmap bitmap) {
        assert (bitmap != null);

        WallpaperObject newObject = null;
        switch (_type) {
            case BACKGROUND: {
                newObject = new Background(bitmap);
            } break;

            case LEAF: {
                newObject = new Leaf(bitmap);
            } break;

            default: {
                //do nothing
            }
        }

        return newObject;
    }


    public WallpaperObject(type _type, Bitmap _bitmap) {
        mType = _type;
        mBitmap = _bitmap;
    }


    public type getType() {
        return mType;
    }

    public void tick(long milliseconds) {}

    @Override
    public void render(Canvas canvas, int width, int height) {}
}
