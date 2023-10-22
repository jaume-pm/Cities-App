package com.example.cities_app;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

public class ImageUtility {

    public static byte[] drawableToArray(Drawable drawable) {
        if (drawable instanceof BitmapDrawable) {
            // If the Drawable is a BitmapDrawable, get the bytes from the bitmap
            Bitmap bitmap = ((BitmapDrawable) drawable).getBitmap();
            return bitmapToByteArray(bitmap);
        } else {
            // If not a BitmapDrawable, convert the Drawable to Bitmap and get bytes
            Bitmap bitmap = drawableToBitmap(drawable);
            return bitmapToByteArray(bitmap);
        }
    }

    public static byte[] bitmapToByteArray(Bitmap bitmap) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        return stream.toByteArray();
    }

    public static Bitmap drawableToBitmap(Drawable drawable) {
        if (drawable instanceof BitmapDrawable) {
            return ((BitmapDrawable) drawable).getBitmap();
        } else {
            Bitmap bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
            Canvas canvas = new Canvas(bitmap);
            drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
            drawable.draw(canvas);
            return bitmap;
        }
    }

    public static Bitmap byteArrayToBitmap(byte[] byteArray) {
        ByteArrayInputStream arrayInputStream = new ByteArrayInputStream(byteArray);
        return BitmapFactory.decodeStream(arrayInputStream);
    }

}
