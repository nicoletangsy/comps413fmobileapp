package com.example.nicole.comps413fminiproject;

import android.content.Context;
import android.graphics.Point;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.Drawable.Callback;

import java.util.Vector;

public class Pikachu extends Sprite {
    public Pikachu(Callback callback, Context context) {
        drawable = (AnimationDrawable) context.getResources().getDrawable(R.drawable.pikachu);
        drawable.setCallback(callback);
    }

    public void reset() {
        float x = (MainView.arenaWidth - (getWidth()*3/2)) / 2.f;
        float y = 0;
        setPosition(x, y);
    }

    public void moveLeft() {
        float x = (MainView.arenaWidth - (getWidth()*3/2) - 100) / 2.f;
        float y = 0;
        setPosition(x, y);
    }
    public void moveRight() {
        float x = (MainView.arenaWidth - (getWidth()*3/2) + 100) / 2.f;
        float y = 0;
        setPosition(x, y);
    }

    public void move() {
        updateBounds();
    }
}