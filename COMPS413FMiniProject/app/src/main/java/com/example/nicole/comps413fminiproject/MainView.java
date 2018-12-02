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
import java.util.concurrent.TimeUnit;

public class MainView extends SurfaceView {
    private static final int CYCLE_DELAY = 30;
    private static final int TEXT_SIZE = 24;
    public static int arenaWidth;
    public static int arenaHeight;
    private Pikachu pikachu;
    Background background;
    private Timer timer = null;
    private Context context;
    private long startTime = 0;
    private long pauseTime = 0;
    private float totalTime = 0;
    private Vector<Obstacles> obstacles = new Vector<Obstacles>();
    private Vector<Coin> coin = new Vector<Coin>();
    private float obstacleCreationTime;
    private float coinCreationTime;
    private boolean gameOver;
    private PauseManager pause;
    private boolean waitForTouch = true;
    private Drawable PikaDrawable;
    private int coinnum;

    private class UserInput {
        boolean present = false;
        int action;
        int x, y;
        /**
         * Sets the user input mouse event for later processing. This method is
         * called in event handlers, i.e., in the main UI thread.
         */
        synchronized void save(MotionEvent event) {
            present = true;
            action = event.getAction();
            x = (int) event.getX();
        }
        /**
         * Handles the user input to move the flying android upward. This method is
         * called in the thread of the game loop.
         */
        synchronized void handle() {
            if (present) {
                if (waitForTouch) {  // Start of the game
                    waitForTouch = false;
                    startTime = System.currentTimeMillis();
                    background.stop(false);
                    ((AnimationDrawable)(pikachu.getDrawable())).start();
                } else {
                    pikachu.updateLane(x);
                }
                present = false;
            }
        }
    }
    private UserInput userInput = new UserInput();

    /** Task for the game loop. */
    private class AnimationTask extends TimerTask {
        @Override
        public void run() {
            userInput.handle();
            if (!gameOver && !waitForTouch) {
                createObstacles();
                createCoin();
                pikachu.move();
            }
            for (int i=0; i<obstacles.size(); i++) {
                obstacles.get(i).move();
                if(obstacles.get(i).collideWith(pikachu)) {
                    gameOver();
                    break;
                }
                if (obstacles.get(i).isOutOfArena()) {
                    obstacles.remove(i);
                }
            }
            for (int i=0; i<coin.size(); i++) {
                coin.get(i).move();
                if(coin.get(i).collideWith(pikachu)) {
                    coinnum += 1;
                    coin.remove(i);
                }
                if (coin.get(i).isOutOfArena()) {
                    coin.remove(i);
                }
            }
            Canvas canvas = getHolder().lockCanvas();
            if (canvas != null) {
                background.drawOn(canvas);
                for (int i = 0; i < obstacles.size(); i++) {
                   obstacles.get(i).drawOn(canvas);
                }
                for (int i = 0; i < coin.size(); i++) {
                    coin.get(i).drawOn(canvas);
                }
                pikachu.drawOn(canvas);
                drawGameText(canvas);
                getHolder().unlockCanvasAndPost(canvas);
            }
        }

        private Paint textPaint = new Paint();
        private void drawGameText(Canvas canvas) {
            Resources res = getResources();
            textPaint.setColor(Color.BLACK);
            textPaint.setTextSize(TEXT_SIZE);
            if (gameOver) {
                if (startTime > 0) {
                    totalTime += (System.currentTimeMillis() - startTime);
                    startTime = 0;
                }
                float gameTime = totalTime / 1000.0f;
                textPaint.setTextSize(2 * TEXT_SIZE);
                textPaint.setTextAlign(Paint.Align.CENTER);
                canvas.drawText(res.getString(R.string.game_over), getWidth() / 2, getHeight() / 2, textPaint);
                canvas.drawText(res.getString(R.string.time_elapse, gameTime), getWidth() / 2, getHeight() / 2 + (2 * TEXT_SIZE), textPaint);
            }
            else if (waitForTouch) {
                textPaint.setTextSize(2 * TEXT_SIZE);
                textPaint.setTextAlign(Paint.Align.CENTER);
                canvas.drawText(res.getString(R.string.start), getWidth() / 2, getHeight() / 2, textPaint);
            }
            else {
                textPaint.setTextSize(TEXT_SIZE);
                textPaint.setTextAlign(Paint.Align.LEFT);
                float gameTime = (System.currentTimeMillis() - startTime + totalTime) / 1000.0f;
                canvas.drawText(res.getString(R.string.time_elapse, gameTime), TEXT_SIZE, TEXT_SIZE, textPaint);
                canvas.drawText(res.getString(R.string.coin_get), TEXT_SIZE, TEXT_SIZE*2, textPaint);
            }
        }
    }

    public void createObstacles() {
        // Task 2: Create one pair of pipes for every 15-25s randomly
        float gameTime = (System.currentTimeMillis() - startTime + totalTime);
        float timeDiff = gameTime - obstacleCreationTime;
        if (obstacleCreationTime == -1 || timeDiff > ((Math.random()*10000) + 10000)) {
            obstacleCreationTime = gameTime;
            Obstacles o = new Obstacles(context);
            obstacles.add(o);
        }
    }

    public void createCoin() {
        // Task 2: Create one pair of pipes for every 15-25s randomly
        float gameTime = (System.currentTimeMillis() - startTime + totalTime);
        float timeDiff = gameTime - coinCreationTime;
        if (coinCreationTime == -1 || timeDiff > ((Math.random()*5000) + 10000)) {
            coinCreationTime = gameTime;
            Coin o = new Coin(context);
            coin.add(o);
        }
    }

    /** Game over. */
    public void gameOver() {
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
        coinnum = 0;
        pikachu.reset();
        obstacles.clear();
        coin.clear();
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

        setFocusableInTouchMode(true); // For getting key events


        PikaDrawable = (AnimationDrawable) context.getResources().getDrawable(R.drawable.pikachu);

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
