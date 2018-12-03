package com.example.nicole.comps413fminiproject;

import android.content.Context;
import android.graphics.Canvas;

/** Bombs. */
public class Bomb {
    private Obstacle [] Bomb = new Obstacle[1];


    /** Constructor. */
    public Bomb(Context context) {
        float x = MainView.arenaWidth * 4.f / 5.f;
        int y = 0;

        Bomb[0] = new Obstacle(context, R.drawable.bomb1);
        Bomb[0].setPosition(x, y);
    }

    /** Move the obstacles. */
    public void move() {
        for (Obstacle obstacle:Bomb) {
            obstacle.move();
        }
    }

    /** Draw the obstacles. */
    public void drawOn(Canvas canvas) {
        for (Obstacle obstacle:Bomb) {
            obstacle.drawOn(canvas);
        }
    }


    /** Inner class, represents an individual obstacle, i.e., one pipe. */
    class Obstacle extends Sprite {
        /** Moving speed of obstacle, which is same as the one in scrolling background. */
        private float dy = 0;

        /** Constructor. */
        public Obstacle(Context context, int resId) {
            drawable = context.getResources().getDrawable(resId);
        }

        @Override
        /** Move the obstacle. */
        public void move() {
            if (dy == 0) {

                // Update the new x position of the obstacle
                curPos.y += dy;

                // Update the boundary of the obstacle drawable
                updateBounds();
            }
        }

    }
}