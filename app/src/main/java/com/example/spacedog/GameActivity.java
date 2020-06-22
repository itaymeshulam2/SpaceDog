package com.example.spacedog;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Point;
import android.os.Bundle;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class GameActivity extends AppCompatActivity {

    ImageButton pauseBtn;
    FrameLayout frameLayout;
    String s;
    //declaring gameView
    private GameView gameView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Getting display object
        Display display = getWindowManager().getDefaultDisplay();

        //Getting the screen resolution into point object
        Point size = new Point();
        display.getSize(size);

        frameLayout = new FrameLayout(this);

        //Initializing game view object
        //this time we are also passing the screen size to the GameView constructor
        gameView = new GameView(this, size.x, size.y);

        pauseBtn = new ImageButton(this);
//        pauseBtn.setClipBounds(new Rect(20,20,20,20));
        pauseBtn.setBackgroundResource(R.drawable.stop);


        frameLayout.addView(gameView);
        frameLayout.addView(pauseBtn,200,200);

        //adding it to contentView
//        setContentView(gameView);
        setContentView(frameLayout);

        pauseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gameView.pause();
                AlertDialog.Builder builder = new AlertDialog.Builder(GameActivity.this);
                builder.setTitle("Game Pause").setMessage("Do you want to exit?")
                        .setCancelable(false)
                        .setPositiveButton("Exit", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                GameView.stopMusic();
                                finish();
                            }
                        })
                        .setNegativeButton("Resume", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                                gameView.resume();
                            }
                        });
                AlertDialog alert = builder.create();
                alert.show();
            }
        });
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {

        //if the game's over, tapping on game Over will open dialog
        if (gameView.isGameOver()) {

            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            final View dialog_gameover = getLayoutInflater().inflate(R.layout.dialog_gameover, null);
            builder.setView(dialog_gameover)
                    .setCancelable(false)
                    .show();

            s = String.valueOf(gameView.getScore());
            TextView scoreTv = dialog_gameover.findViewById(R.id.score_tv);
            scoreTv.append(s);

            Button playAgainBtn = dialog_gameover.findViewById(R.id.play_again_btn);
            playAgainBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = getIntent();
                    finish();
                    startActivity(intent);
                }
            });

            Button mainMenuBtn = dialog_gameover.findViewById(R.id.main_menu_btn);
            mainMenuBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                }
            });

            final Button saveScoreBtn = dialog_gameover.findViewById(R.id.save_score_btn);
            saveScoreBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    EditText name = dialog_gameover.findViewById(R.id.name_et);
                    s = name.getText().toString();

                    gameView.saveHighScore(s);

                    saveScoreBtn.setVisibility(View.GONE);
                    name.setVisibility(View.GONE);
                }
            });
        }

        return false;
    }



    //pausing the game when activity is paused
    @Override
    protected void onPause() {
        super.onPause();
        gameView.pause();
    }

    //running the game when activity is resumed
    @Override
    protected void onResume() {
        super.onResume();
        gameView.resume();
    }

    @Override
    public void onBackPressed() {
//        super.onBackPressed();

        gameView.pause();

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Are you sure you want to exit?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                        GameView.stopMusic();
//                        Intent startMain = new Intent(Intent.ACTION_MAIN);
//                        startMain.addCategory(Intent.CATEGORY_HOME);
//                        startMain.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                        startActivity(startMain);
                        finish();
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();

                        if (!gameView.isGameOver())
                            gameView.resume();
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();

    }
}
