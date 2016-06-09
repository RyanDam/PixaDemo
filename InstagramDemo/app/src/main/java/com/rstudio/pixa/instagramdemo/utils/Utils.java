package com.rstudio.pixa.instagramdemo.utils;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by Ryan on 6/9/16.
 */
public class Utils {
    public static void makeToast(Context c, String s) {
        Toast.makeText(c, s, Toast.LENGTH_LONG).show();
        Log.d("TOAST", s);
    }
}
