package com.example.nicole.comps413fminiproject;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.Drawable;
import android.view.MotionEvent;
import android.view.SurfaceView;
import android.view.View;

import java.util.Timer;
import java.util.TimerTask;
import java.util.Vector;

/** View of flying android animation. */
public class MainView extends SurfaceView {
    /** Delay in each animation cycle, in ms. */
    private static final int CYCLE_DELAY = 30;
    /** Text size. */
    private static final int TEXT_SIZE = 24;

    /** Width and height of the arena. */
    public static int arenaWidth;
    public static int arenaHeight;

    /** Animation object, the flying android. */
    private Pikachu pikachu;
    /** List of obstacles objects, i.e., pairs of pipes. */
    //private Vector<Obstacles> obstacles = new Vector<Obstacles>();
    /** Scrolling background of the view. */
    Background background;

    /** Timer for the game loop. */
    private Timer timer = null;

    private Context context;

    /** Start time of the game. */
    private long startTime = 0;
    /** Pause time of the game. */
    private long pauseTime = 0;
    /** Total time elapsed of the game. */
    private float totalTime = 0;
    /** Obstacle creation time. */
    private float obstacleCreationTime;

    /** Whether the game is over. */
    private boolean gameOver;

    /** Whether the game is paused and waiting for touching to start. */
    private boolean waitForTouch = true;
    /** Saving and handling of user input of touch events. */
    private class UserInput {
        /** Whether there is a user input present. */
        boolean present = false;

        /**
         * Sets the user input mouse event for later processing. This method is
         * called in event handlers, i.e., in the main UI thread.
         */
        synchronized void save(MotionEvent event) {
            present = true;
        }

        /**
         * Handles the user input to move the flying android upward. This method is
         * called in the thread of the game loop.
         */
        synchronized void handle() {
            if (present) {
                // Add code here
                // Task 4: Handling of user input
                // If (waitForTouch)
                // - Set waitForTouch to false
                // - Get current time and assign to startTime
                // - Start scrolling background
                // - Start animation of the flying android
                // Otherwise
                // - Move the flying android upward
                if (waitForTouch) {  // Start of the game
                    waitForTouch = false;
                    startTime = System.currentTimeMillis();
                    background.stop(false);
                    ((AnimationDrawable)(pikachu.getDrawable())).start();
                }
                present = false;
            }
        }
    }
    /** User input object of touch events. */
    private UserInput userInput = new UserInput();

    /** Task for the game loop. */
    private class AnimationTask extends TimerTask {
        @Override
        public void run() {
            userInput.handle();
            if (!gameOver && !waitForTouch) {
                // Add code here
                // Task 5: Game loop implementation

                // i. Create obstacles
                //createObstacles();

                // ii. Move the flying android
                pikachu.move();
            }

            // v. Draw the game objects
            Canvas canvas = getHolder().lockCanvas();
            if (canvas != null) {
                // a. Draw the scrolling background
                background.drawOn(canvas);

                // b. Draw the obstacles
                //for (int i = 0; i < obstacles.size(); i++) {
                //    obstacles.get(i).drawOn(canvas);
                //}

                // c. Draw the flying android
                pikachu.drawOn(canvas);

                // d. Draw game text
                drawGameText(canvas);

                getHolder().unlockCanvasAndPost(canvas);
            }
        }

        /** Paint object for painting text. */
        private Paint textPaint = new Paint();
        /** Draws text for the game. */
        private void drawGameText(Canvas canvas) {
            Resources res = getResources();
            textPaint.setColor(Color.BLACK);
            textPaint.setTextSize(TEXT_SIZE);

            // Add code here
            // Task 1: Draw game information
            // If game over
            // - Draw "Game Over" and the total time elapsed
            // Else if wait for touch
            // - Draw "Touch to Start!"
            // Else
            // - Draw the total time elapsed on the top left corner of the arena
            if (gameOver) {
                if (startTime > 0) {
                    totalTime += (System.currentTimeMillis() - startTime);
                    startTime = 0;
                }
                float gameTime = totalTime / 1000.0f;
                textPaint.setTextSize(2 * TEXT_SIZE);
                textPaint.setTextAlign(Paint.Align.CENTER);
                //canvas.drawText(res.getString(R.string.game_over), getWidth() / 2, getHeight() / 2, textPaint);
                //canvas.drawText(res.getString(R.string.time_elapse, gameTime), getWidth() / 2, getHeight() / 2 + (2 * TEXT_SIZE), textPaint);
            }
            else if (waitForTouch) {
                textPaint.setTextSize(2 * TEXT_SIZE);
                textPaint.setTextAlign(Paint.Align.CENTER);
                //canvas.drawText(res.getString(R.string.start), getWidth() / 2, getHeight() / 2, textPaint);
            }
            else {
                textPaint.setTextSize(TEXT_SIZE);
                textPaint.setTextAlign(Paint.Align.LEFT);
                float gameTime = (System.currentTimeMillis() - startTime + totalTime) / 1000.0f;
                //canvas.drawText(res.getString(R.string.time_elapse, gameTime), TEXT_SIZE, TEXT_SIZE, textPaint);
            }
        }
    }

    /** Create obstacles randomly. */
    /*public void createObstacles() {
        // Add code here
        // Task 2: Create one pair of pipes for every 15-25s randomly
        float gameTime = (System.currentTimeMillis() - startTime + totalTime);
        float timeDiff = gameTime - obstacleCreationTime;
        if (obstacleCreationTime == -1 || timeDiff > ((Math.random()*10000) + 15000)) {
            obstacleCreationTime = gameTime;
            Obstacles o = new Obstacles(context);
            obstacles.add(o);
        }
    }*/

    /** Game over. */
    public void gameOver() {
        // Add code here
        // Task 3: Game over handling
        // i. Set gameOver to true
        // ii. Stop the animation of the flying andriod
        // iii. Stop the scrolling background
        gameOver = true;
        ((AnimationDrawable)(pikachu.getDrawable())).stop();
        background.stop(true);
    }

    /** Resume or start the animation. */
    public void resume() {
        if (timer == null)
            timer = new Timer();
        timer.schedule(new AnimationTask(), 0, CYCLE_DELAY);
    }

    /** Pause or stop the animation. */
    public void pause() {
        totalTime += (System.currentTimeMillis() - startTime);
        waitForTouch = true;

        background.stop(true);
        ((AnimationDrawable) (pikachu.getDrawable())).stop();

        timer.cancel();
        timer = null;
    }

    /**
     * Start a new game.
     */
    public void newGame(boolean newGame) {
        if (newGame) {
            arenaWidth = getWidth();
            arenaHeight = getHeight();

            background = new Background(context);
            pikachu = new Pikachu(this, context);
        }

        gameOver = false;
        waitForTouch = true;
        totalTime = 0;
        startTime = -1;
        obstacleCreationTime = -1;
        pikachu.reset();

        ((AnimationDrawable)(pikachu.getDrawable())).stop();
        background.stop(true);
    }

    /**
     * Constructs an animation view. This performs initialization including the
     * event handlers for key presses and touches.
     */
    public MainView(Context context) {
        super(context);
        this.context = context;

        setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                userInput.save(event);
                return true;
            }
        });

        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                while (getWidth() == 0)
                    ; // Wait for layout
                newGame(true);
            }
        }, 0);
    }

    protected boolean verifyDrawable(Drawable who) {
        super.verifyDrawable(who);
        return who == pikachu.getDrawable();
    }

    public void invalidateDrawable(Drawable drawable) {
    }
}
