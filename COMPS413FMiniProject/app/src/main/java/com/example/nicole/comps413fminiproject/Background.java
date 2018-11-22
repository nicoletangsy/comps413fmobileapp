package com.example.nicole.comps413fminiproject;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;

public class Background {
    static final int SpeedYMagnitude = -2;
    private static int speedY = 0;
    private Bitmap background;
    private static final int bgX = 0;
    private static int bgY = 0, bgY2;

    public Background(Context context) {
        background = BitmapFactory.decodeResource(context.getResources(), R.drawable.background);
        int scaledWidth = (int) (background.getWidth() * (MainView.arenaHeight / (float) background.getHeight()));
        background = Bitmap.createScaledBitmap(background, scaledWidth, MainView.arenaHeight, true);
    }

    public void stop(boolean stop) {
        if (stop)
            speedY = 0;
        else
            speedY = SpeedYMagnitude;
    }

    public void drawOn(Canvas canvas) {
        if (background != null) {
            bgY += speedY;
            bgY2 = background.getHeight() + bgY;
            if (bgY2 <= 0) {
                bgY = 0;
                canvas.drawBitmap(background, bgX, bgY, null);
            } else {
                canvas.drawBitmap(background, bgX, bgY, null);
                canvas.drawBitmap(background, bgX, bgY2, null);
            }
        }
    }
}