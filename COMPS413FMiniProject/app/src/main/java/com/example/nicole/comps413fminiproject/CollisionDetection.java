package com.example.nicole.comps413fminiproject;

import android.graphics.*;

public class CollisionDetection {
    private static int[] pixels1 = null, pixels2 = null;
    private static final Rect rect = new Rect();
    public static boolean collidePixel(Rect rect1, Rect rect2, Bitmap bitmap1, Bitmap bitmap2) {
        if (!rect.setIntersect(rect1, rect2)) // No intercept in Rects
            return false;
        int numPixels = rect.width() * rect.height();
        if (pixels1 == null || pixels1.length < numPixels)
            pixels1 = new int[numPixels];
        bitmap1.getPixels(pixels1, 0, rect.width(), rect.left - rect1.left, rect.top - rect1.top, rect.width(), rect.height());
        if (pixels2 == null || pixels2.length < numPixels)
            pixels2 = new int[numPixels];
        bitmap2.getPixels(pixels2, 0, rect.width(), rect.left - rect2.left, rect.top - rect2.top, rect.width(), rect.height());
        for (int i = 0; i < numPixels; i++) {
            if ((pixels1[i] & 0xff000000) != 0 && (pixels2[i] & 0xff000000) != 0) // Both not transparent
                return true;
        }
        return false;
    }

}
