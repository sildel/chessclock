package com.jsdm.spark.chessclock;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.Timer;
import java.util.TimerTask;


public class GameActivity extends AppCompatActivity {
    private static final int DEFAULT_SECONDS = 5 * 60;
    private static final String GAME_OVER = "GAME OVER";
    private static final String PLAYER_WINS = " Player Wins!!!";
    private static final String WHITE_PLAYER = "White";
    private static final String BLACK_PLAYER = "Black";
    private Timer timer;
    int playerWhite;
    int playerBlack;
    boolean whitePlaying;
    boolean endedGame;
    Handler handler;
    private MediaPlayer mediaPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        Log.d("GameActivity", "onCreate - > GameActivity");

        handler = new Handler();
        Intent intent = getIntent();
        String initTime = (String) intent.getExtras().get(MenuActivity.EXTRA_INIT_TIME);
        if (initTime != null) {
            int initSeconds = stringToSeconds(initTime);
            playerBlack = playerWhite = initSeconds;
            displayTimes();
            playSound(R.raw.start);
            whitePlaying = true;
            endedGame = false;
            timer = new Timer();
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    timerTick();
                }
            }, 1000, 1000);
        }
    }

    private void timerTick() {
        if (endedGame) {
            return;
        }
        if (whitePlaying) {
            playerWhite--;
        } else {
            playerBlack--;
        }
        handler.post(new Runnable() {
            @Override
            public void run() {
                displayTimes();
                checkEndGame(whitePlaying);
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d("Game Activity", "onStart - > GameActivity");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d("Game Activity", "onResume - > GameActivity");
    }

    @Override
    protected void onRestart() {
        super.onRestart();

        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                timerTick();
            }
        }, 1000, 1000);
        Log.d("Game Activity", "onRestart - > GameActivity");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d("Game Activity", "onPause - > GameActivity");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d("Game Activity", "onStop - > GameActivity");
        if (timer != null) {
            timer.cancel();
            timer.purge();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d("Game Activity", "onDestroy - > GameActivity");
    }

    private void checkEndGame(boolean whitePlaying) {
        if ((whitePlaying && playerWhite == 0) || (!whitePlaying && playerBlack == 0)) {
            endedGame = true;
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle(GAME_OVER);
            builder.setMessage((whitePlaying ? BLACK_PLAYER : WHITE_PLAYER) + PLAYER_WINS);
            builder.setNeutralButton(R.string.ok, null);
            AlertDialog dialog = builder.create();
            playSound(R.raw.over);
            dialog.show();
        }
    }

    private void playSound(int resource) {
        if (mediaPlayer != null) {
            mediaPlayer.stop();
            mediaPlayer.release();
        }
        mediaPlayer = MediaPlayer.create(this, resource);
        mediaPlayer.start();
    }

    private void displayTimes() {
        Button white = (Button) findViewById(R.id.buttonWhite);
        Button black = (Button) findViewById(R.id.buttonBlack);

        white.setText(secondsToString(playerWhite));
        black.setText(secondsToString(playerBlack));
    }

    private String secondsToString(int seconds) {
        int hours = seconds / 3600;
        int minutes = (seconds % 3600) / 60;
        int dseconds = (seconds % 3600) % 60;

        if (hours == 0) {
            return Integer.toString(minutes) + ":" + ((dseconds > 9) ? "" : "0") + Integer.toString(dseconds);
        }
        return Integer.toString(hours) + ":" + Integer.toString(minutes) + ":" + ((dseconds > 9) ? "" : "0") + Integer.toString(dseconds);
    }

    private int stringToSeconds(String time) {
        try {
            String[] split = time.split(":");
            if (split.length == 1) {
                int minutes = Integer.parseInt(split[0]);
                return minutes * 60;
            }
            if (split.length == 2) {
                int minutes = Integer.parseInt(split[0]);
                int seconds = Integer.parseInt(split[1]);
                return minutes * 60 + seconds;
            }
            if (split.length == 3) {
                int hours = Integer.parseInt(split[0]);
                int minutes = Integer.parseInt(split[1]);
                int seconds = Integer.parseInt(split[2]);
                return hours * 60 * 60 + minutes * 60 + seconds;
            }
            return DEFAULT_SECONDS;
        } catch (Exception e) {
            return DEFAULT_SECONDS;
        }
    }

    public void makePlay(View view) {
        playSound(R.raw.swap);
        whitePlaying = view.getId() != R.id.buttonWhite;
    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.sure).setMessage(R.string.game_lost).setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finishGame();
            }
        }).setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        }).create().show();
    }

    public void finishGame() {
        super.onBackPressed();
    }
}

// TODO: fix buttons style
