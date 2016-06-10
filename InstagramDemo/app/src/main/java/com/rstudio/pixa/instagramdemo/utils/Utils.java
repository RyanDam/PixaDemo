package com.rstudio.pixa.instagramdemo.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;
import android.util.Log;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;

/**
 * Created by Ryan on 6/9/16.
 */
public class Utils {

    String TAG = "Utils Class";

    public static void makeToast(Context c, String s) {
        Toast.makeText(c, s, Toast.LENGTH_LONG).show();
        Log.d("TOAST", s);
    }

    public static Bitmap getBitmapFromStream(File f, int reqWidth) {
        try {
            FileInputStream fs = new FileInputStream(f);

            final BitmapFactory.Options op = new BitmapFactory.Options();
            op.inJustDecodeBounds = true;
            BitmapFactory.decodeStream(fs, null, op);

            int width = op.outWidth;
            int height = op.outHeight;
            float scale = (float)width/(float)height;

            op.inSampleSize = calculateInSampleSize(op, reqWidth, reqWidth/scale);

            op.inJustDecodeBounds = false;

            fs.close();
            fs = new FileInputStream(f);

            Bitmap re = BitmapFactory.decodeStream(fs, new Rect(0, 0, 0, 0), op);

            fs.close();

            if (re.getWidth() != reqWidth) {
                return Bitmap.createScaledBitmap(re, reqWidth, (int)(reqWidth/scale), false);
            }

            return re;
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * get scale value for bitmap
     *
     * @param options
     * @param reqWidth
     * @param reqHeight
     * @return
     */
    public static int calculateInSampleSize(BitmapFactory.Options options, float reqWidth, float reqHeight) {
        // Raw height and width of image
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {
            if (width > height) {
                inSampleSize = Math.round((float) height / (float) reqHeight);
            } else {
                inSampleSize = Math.round((float) width / (float) reqWidth);
            }
        }

        return inSampleSize;
    }
}
