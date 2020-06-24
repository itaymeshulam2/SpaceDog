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
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity{

    List<Map<String,Object>> data;
    HashMap<String,Object> temp1;
    HashMap<String,Object> temp2;
    HashMap<String,Object> temp3;
    HashMap<String,Object> temp4;
    HashMap<String,Object> temp5;
    HashMap<String,Object> temp6;
    HashMap<String,Object> temp7;
    HashMap<String,Object> temp8;
    HashMap<String,Object> temp9;
    HashMap<String,Object> temp10;

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

        data = new ArrayList<>();
        temp1 = new HashMap<>();
        temp2 = new HashMap<>();
        temp3 = new HashMap<>();
        temp4 = new HashMap<>();
        temp5 = new HashMap<>();
        temp6 = new HashMap<>();
        temp7 = new HashMap<>();
        temp8 = new HashMap<>();
        temp9 = new HashMap<>();
        temp10 = new HashMap<>();


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
                ListView listView = dialogViewHighScore.findViewById(R.id.listView);

                ArrayList<String> HighScore = new ArrayList<>();

                for(int j = 0; j < 10; j++ ){
                    HighScore.add(String.valueOf(j+1) + " " + sharedPreferences.getString("namePlayer"+(j+1),"") + " " +sharedPreferences.getInt("score"+(j+1),0));
                }

                temp1.put("id","1.");
                temp1.put("name",sharedPreferences.getString("namePlayer"+(1),""));
                temp1.put("score",sharedPreferences.getInt("score"+(1),0));
                data.add(temp1);

                temp2.put("id","2.");
                temp2.put("name",sharedPreferences.getString("namePlayer"+(2),""));
                temp2.put("score",sharedPreferences.getInt("score"+(2),0));
                data.add(temp2);

                temp3.put("id","3.");
                temp3.put("name",sharedPreferences.getString("namePlayer"+(3),""));
                temp3.put("score",sharedPreferences.getInt("score"+(3),0));
                data.add(temp3);

                temp4.put("id","4.");
                temp4.put("name",sharedPreferences.getString("namePlayer"+(4),""));
                temp4.put("score",sharedPreferences.getInt("score"+(4),0));
                data.add(temp4);

                temp5.put("id","5.");
                temp5.put("name",sharedPreferences.getString("namePlayer"+(5),""));
                temp5.put("score",sharedPreferences.getInt("score"+(5),0));
                data.add(temp5);

                temp6.put("id","6.");
                temp6.put("name",sharedPreferences.getString("namePlayer"+(6),""));
                temp6.put("score",sharedPreferences.getInt("score"+(6),0));
                data.add(temp6);

                temp7.put("id","7.");
                temp7.put("name",sharedPreferences.getString("namePlayer"+(7),""));
                temp7.put("score",sharedPreferences.getInt("score"+(7),0));
                data.add(temp7);

                temp8.put("id","8.");
                temp8.put("name",sharedPreferences.getString("namePlayer"+(8),""));
                temp8.put("score",sharedPreferences.getInt("score"+(8),0));
                data.add(temp8);

                temp9.put("id","9.");
                temp9.put("name",sharedPreferences.getString("namePlayer"+(9),""));
                temp9.put("score",sharedPreferences.getInt("score"+(9),0));
                data.add(temp9);

                temp10.put("id","10.");
                temp10.put("name",sharedPreferences.getString("namePlayer"+(10),""));
                temp10.put("score",sharedPreferences.getInt("score"+(10),0));
                data.add(temp10);


                String [] from = {"id" , "name" , "score"};
                int [] ids = {R.id.number,R.id.name,R.id.score};

                SimpleAdapter simpleAdapter = new SimpleAdapter(MainActivity.this,data,R.layout.score_cell,from,ids);
                listView.setAdapter(simpleAdapter);
                //ArrayAdapter<String> adapter = new ArrayAdapter<String>(MainActivity.this,android.R.layout.simple_list_item_1, HighScore);
                //listView.setAdapter(adapter);
                builder.setView(dialogViewHighScore).show();

                //AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                //View dialogViewHighScore = getLayoutInflater().inflate(R.layout.dialog_high_score, null);

                //TextView score1TV = dialogViewHighScore.findViewById(R.id.textView);
                //TextView score2TV =  dialogViewHighScore.findViewById(R.id.textView2);
                //TextView score3TV = dialogViewHighScore.findViewById(R.id.textView3);
                //TextView score4TV = dialogViewHighScore.findViewById(R.id.textView4);

                //builder.setView(dialogViewHighScore).show();

                //setting the values to the textViews
                //score1TV.setText("1."+sharedPreferences.getInt("score1",0));
                //score2TV.setText("2."+sharedPreferences.getInt("score2",0));
                // score3TV.setText("3."+sharedPreferences.getInt("score3",0));
                //score4TV.setText("4."+sharedPreferences.getInt("score4",0));


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


