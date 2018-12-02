package com.example.nicole.comps413fminiproject;

import android.content.Context;
import android.graphics.Canvas;

/** The obstacles. */
public class Bomb {
    private Obstacle [] bomb = new Obstacle[1];


    /** Constructor. */
    public Bomb (Context context) {
        float x = MainView.arenaWidth;
        int y = MainView.arenaHeight;

        bomb[0] = new Obstacle(context, R.drawable.bomb2);
        bomb[0].setPosition(x, y);
    }

    /** Move the obstacles. */
    public void move() {
        for (Obstacle obstacle:bomb) {
            obstacle.move();
        }
    }

    /** Draw the obstacles. */
    public void drawOn(Canvas canvas) {
        for (Obstacle obstacle:bomb) {
            obstacle.drawOn(canvas);
        }
    }

    /** Inner class, represents an individual obstacle, i.e., one pipe. */
    class Obstacle extends Sprite {

        /** Constructor. */
        public Obstacle(Context context, int resId) {
            drawable = context.getResources().getDrawable(resId);
        }

        @Override
        /** Move the obstacle. */
        public void move() {
                updateBounds();
        }

    }
}