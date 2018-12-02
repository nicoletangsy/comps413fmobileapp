package com.example.nicole.comps413fminiproject;

import android.content.Context;
import android.graphics.Point;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.Drawable.Callback;
import java.util.Enumeration;
import java.util.Vector;

import java.util.Vector;

public class Pikachu extends Sprite {
    private int lane = 2;
    public Pikachu(Callback callback, Context context) {
        drawable = (AnimationDrawable) context.getResources().getDrawable(R.drawable.pikachu);
        drawable.setCallback(callback);
    }

    public void reset() {
        float x = (MainView.arenaWidth) * lane * 1.f / 5.f;
        float y = 0;
        setPosition(x, y);
    }

    public void gotoLane(int i) {
        float x = (MainView.arenaWidth) * i * 1.f / 5.f;
        float y = 0;
        setPosition(x, y);
    }

    public void updateLane(int x) {
        float halfscreen = (MainView.arenaWidth) / 2.f;

        if (x > halfscreen){
            switch (lane) {
                case 1:
                    lane = 2;
                    break;
                case 2:
                    lane = 3;
                    break;
                case 3:
                    lane = 3;
                    break;
                }

        }
        else if (x < halfscreen) {
            switch (lane) {
                case 1:
                    lane = 1;
                    break;
                case 2:
                    lane = 1;
                    break;
                case 3:
                    lane = 2;
                    break;
            }

        }
        //if (x > halfscreen && lane < 3) {
        //    lane += 1;
        //}
        //if (x < halfscreen && lane > 1) {
        //    lane -= 1;
        //}
        gotoLane(lane);
    }


    public void move() {
        updateBounds();
    }
}