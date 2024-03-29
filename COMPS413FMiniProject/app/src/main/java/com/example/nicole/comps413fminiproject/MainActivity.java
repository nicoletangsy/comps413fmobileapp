package com.example.nicole.comps413fminiproject;

import android.app.Activity;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.content.Intent;

public class MainActivity extends AppCompatActivity {
    /** The animation view. */
    private MainView animationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        animationView = new MainView(this);
        setContentView(animationView);

        // Find the toolbar view inside the activity layout
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        // Sets the Toolbar to act as the ActionBar for this Activity window.
        // Make sure the toolbar exists in the activity and is not null
        setSupportActionBar(toolbar);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    /**
     * Handles the option menu selection. This method is called when an options
     * menu item is selected.
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_restart:
                //newGame(true);
                animationView.newGame(false);
                return true;
            case R.id.action_pause:
                onPause();
                return true;
            case R.id.action_resume:
                onResume();
                return true;
            case R.id.action_about:
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    /** Resumes the animation. This method is called when the activity is resumed. */
    @Override
    protected void onResume() {
        super.onResume();
        animationView.resume();
    }

    /** Pauses the animation. This method is called when the activity is paused. */
    @Override
    protected void onPause() {
        super.onPause();
        animationView.pause();
    }

    public void about (MenuItem item) {
        Intent intent = new Intent(this,About.class);
        startActivity(intent);
    }
}