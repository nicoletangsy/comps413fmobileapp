package com.example.nicole.comps413fminiproject;

import android.content.Context;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;

/** Pause manager for managin the pause/playing status and drawables. */
public class pauseManager {
    /** The drawable when the game pause. */
    private Drawable pauseDrawable;
    /** The drawable when the game start. */
    private Drawable startDrawable;
    /** Whether pause is on. */
    private boolean pause;

    /** Constructs a pause manager object. */
    public pauseManager(Context context, boolean pause) {
        this.pause = pause;
        //pauseDrawable = context.getResources().getDrawable(R.drawable.pause);
        //startDrawable = context.getResources().getDrawable(R.drawable.start);
    }

    /** Returns whether game is pause. */
    public boolean getPause() {
        return pause;
    }

    /** Sets whether game is pause. */
    public void setPause(boolean pause) {
        this.pause = pause;
    }

    /** Returns the current drawable according to the playing status. */
    public Drawable getDrawable() {
        return pause ? startDrawable : pauseDrawable;
    }

    /** Returns the bounds of the current drawable. */
    public Rect getBounds() {
        return pauseDrawable.getBounds();
    }

    /** Sets the bounds of the drawable. */
    public void setBounds (Rect bounds) {
        pauseDrawable.setBounds(bounds);
        startDrawable.setBounds(bounds);
    }

}
