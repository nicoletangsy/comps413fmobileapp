package com.example.nicole.comps413fminiproject;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.PointF;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;

abstract public class Sprite {
    final protected PointF curPos = new PointF();
    Drawable drawable;
    public int getWidth() {
        return drawable.getIntrinsicWidth();
    }
    public int getHeight() {
        return drawable.getIntrinsicHeight();
    }
    public PointF getCurPos() {
        return curPos;
    }
    public Rect getBounds() {
        return drawable.getBounds();
    }
    public Bitmap getBitmap() {
        return ((BitmapDrawable) drawable.getCurrent()).getBitmap();
    }
    public Drawable getDrawable() {
        return drawable;
    }

    public void setPosition(float x, float y) {
        curPos.set(x, y);
        updateBounds();
    }

    public void updateBounds() {
        if (drawable != null) {
            drawable.setBounds((int) curPos.x, (int) curPos.y, (int) curPos.x + getWidth(), (int) curPos.y + getHeight());
        }
    }

    public void drawOn(Canvas canvas) {
        if (drawable != null) {
            drawable.draw(canvas);
        }
    }

    public boolean collideWith(Sprite obstacle) {
        return collideWith(obstacle.getBounds(), obstacle.getBitmap());
    }

    public boolean collideWith(Rect bounds, Bitmap bitmap) {
        return CollisionDetection.collidePixel(getBounds(), bounds, getBitmap(), bitmap);
    }

    abstract public void move();
    abstract public void moveLeft();
    abstract public void moveRight();
}