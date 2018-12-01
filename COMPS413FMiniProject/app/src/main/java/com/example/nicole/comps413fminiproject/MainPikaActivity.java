package com.example.nicole.comps413fminiproject;

import android.app.Activity;
import android.os.Bundle;

public class MainPikaActivity extends Activity{
    /** The animation view. */
    private MainView animationView;

    /**
     * Sets up and displays the {@link MainView}. This method is called
     * when the activity is first created.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        animationView = new MainView(this);
        setContentView(animationView);
    }

    /**
     * Resumes the animation. This method is called when the activity is
     * resumed.
     */
    @Override
    protected void onResume() {
        super.onResume();
        animationView.resume();
    }

    /**
     * Pauses the animation. This method is called when the activity is paused.
     */
    @Override
    protected void onPause() {
        super.onPause();
        animationView.pause();
    }

}
