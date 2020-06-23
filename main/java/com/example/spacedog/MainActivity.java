package com.example.spacedog;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity{

    private ImageButton buttonPlay;
    private ImageButton buttonScore;
    private ImageButton buttonSettings;
    MediaPlayer mediaPlayer;

    boolean gameSound;
    boolean gameMusic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //setting the orientation to landscape
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        final SharedPreferences sharedPreferences = getSharedPreferences("high_score", MODE_PRIVATE);
        final SharedPreferences.Editor editor = sharedPreferences.edit();

        gameSound = sharedPreferences.getBoolean("sound",true);
        gameMusic = sharedPreferences.getBoolean("music",true);

        //getting the button
        buttonPlay = (ImageButton) findViewById(R.id.play_btn);
        buttonPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mediaPlayer.stop();
                //the transition from MainActivity to GameActivity
                startActivity(new Intent(MainActivity.this, GameActivity.class));
            }
        });

        buttonScore = (ImageButton) findViewById(R.id.score_btn);
        buttonScore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                View dialogViewHighScore = getLayoutInflater().inflate(R.layout.dialog_high_score, null);

                TextView score1TV = dialogViewHighScore.findViewById(R.id.textView);
                TextView score2TV =  dialogViewHighScore.findViewById(R.id.textView2);
                TextView score3TV = dialogViewHighScore.findViewById(R.id.textView3);
                TextView score4TV = dialogViewHighScore.findViewById(R.id.textView4);

                builder.setView(dialogViewHighScore).show();

                //setting the values to the textViews
                score1TV.setText("1."+sharedPreferences.getInt("score1",0));
                score2TV.setText("2."+sharedPreferences.getInt("score2",0));
                score3TV.setText("3."+sharedPreferences.getInt("score3",0));
                score4TV.setText("4."+sharedPreferences.getInt("score4",0));


            }
        });

        buttonSettings = findViewById(R.id.settings_btn);
        buttonSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                View dialogViewSettings = getLayoutInflater().inflate(R.layout.dialog_settings, null);

                builder.setView(dialogViewSettings)
                        .setTitle("Settings")
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                }).show();

                CheckBox soundCheckBox = dialogViewSettings.findViewById(R.id.sound_cb);
                soundCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        gameSound = isChecked;
                        editor.putBoolean("sound",gameSound);
                        editor.commit();
                    }
                });
                CheckBox musicCheckBox = dialogViewSettings.findViewById(R.id.music_cb);
                musicCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        gameMusic = isChecked;
                        if (gameMusic) {
                            mediaPlayer.start();
                            mediaPlayer.setLooping(true);
                        } else {
                            mediaPlayer.pause();
                        }
                        editor.putBoolean("music",gameMusic);
                        editor.commit();
                    }
                });

                musicCheckBox.setChecked(gameMusic);
                soundCheckBox.setChecked(gameSound);

            }
        });

        mediaPlayer = MediaPlayer.create(this, R.raw.space_groove);
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (gameMusic)
            mediaPlayer.pause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (gameMusic)
            mediaPlayer.start();
    }

    @Override
    public void onBackPressed() {
//        super.onBackPressed();

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Are you sure you want to exit?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                        GameView.stopMusic();
                        Intent startMain = new Intent(Intent.ACTION_MAIN);
                        startMain.addCategory(Intent.CATEGORY_HOME);
                        startMain.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(startMain);
                        finish();
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();
    }
}
