package com.example.nicole.comps413fminiproject;

import android.content.Context;
import android.graphics.Canvas;

/** The obstacles. */
public class Obstacles {
    /** Pair of obstacles. */
    private int numberOfObsatcle = (int)(Math.random()*2 + 1);
    private Obstacle [] obstacles = new Obstacle[numberOfObsatcle];


    /** Constructor. */
    public Obstacles(Context context) {
        float x_center = MainView.arenaWidth * 2.f / 5.f;
        float x_left = MainView.arenaWidth / 5.f;
        float x_right = MainView.arenaWidth * 3.f / 5.f;
        int y = MainView.arenaHeight;

        for (int i=0; i<obstacles.length; i++) {
            obstacles[i] = new Obstacle(context, R.drawable.car);
            int position = (int)(Math.random()*3);
            float x;
            if (position == 0) {
                x = x_center;
            } else if (position == 1) {
                x = x_left;
            } else {
                x = x_right;
            }
            obstacles[i].setPosition(x, y);
        }
    }

    /** Move the obstacles. */
    public void move() {
        for (Obstacle obstacle:obstacles) {
            obstacle.move();
        }
    }

    /** Draw the obstacles. */
    public void drawOn(Canvas canvas) {
        for (Obstacle obstacle:obstacles) {
            obstacle.drawOn(canvas);
        }
    }

    /** Evaluate if obstacles moved out from the arena. */
    public boolean isOutOfArena() {
        if (obstacles[0].isOutOfArena())
            return true;
        return false;
    }

    /** Evaluate if the input sprite collided with any obstacle. */
    public boolean collideWith(Sprite sprite) {
        for (Obstacle obstacle:obstacles) {
            if (obstacle.collideWith(sprite))
                return true;
        }
        return false;
    }

    /** Inner class, represents an individual obstacle, i.e., one pipe. */
    class Obstacle extends Sprite {
        /** Moving speed of obstacle, which is same as the one in scrolling background. */
        private float dy = Background.SpeedYMagnitude;

        /** Constructor. */
        public Obstacle(Context context, int resId) {
            drawable = context.getResources().getDrawable(resId);
        }

        @Override
        /** Move the obstacle. */
        public void move() {
            if (dy != 0) {
                dy = Background.SpeedYMagnitude;
                // Update the new x position of the obstacle
                curPos.y += dy;

                // Update the boundary of the obstacle drawable
                updateBounds();
            }
        }

        /** Evaluate if the obstacle has moved out from the arena. */
        public boolean isOutOfArena() {
            if (getCurPos().y + getHeight() < 0)
                return true;
            return false;
        }
    }
}