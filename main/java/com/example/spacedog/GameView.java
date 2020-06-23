package com.example.spacedog;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.media.MediaPlayer;
import android.os.Build;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.Toast;

import java.util.ArrayList;

import static android.content.Context.MODE_PRIVATE;

public class GameView extends SurfaceView implements Runnable {

    // adjust by level and difficulty
    private int enemyCount = 3;
    private int friendCount = 2;
    private int livesCount = 3;

    // sound settings
    boolean gameSound;
    boolean gameMusic;

    //the mediaPlayer objects to configure the background music
    static MediaPlayer gameBackgroundMusic;
    final MediaPlayer killEnemySound;
    final MediaPlayer gameOversound;


//-----Score:---
    //the score holder
    int score;

    //the high Scores Holder
    int highScore[] = new int[4];

    //Shared Preferences to store the High Scores
    SharedPreferences sharedPreferences;

//----------

//-----Game Rules:---
    //a screenX holder
    int screenX;

    //to count the number of Misses
    int countMisses;

    Life[] life = new Life[livesCount];
    
    //indicator that the enemy has just entered the game screen
    boolean flag ;

    //an indicator if the game is Over
    private boolean isGameOver ;

    public boolean isGameOver() {
        return isGameOver;
    }

    //context to be used in onTouchEvent to cause the activity transition from GameAvtivity to MainActivity.
    Context context;
//----------


    //boolean variable to track if the game is playing or not
    volatile boolean playing;

    //the game thread
    private Thread gameThread = null;

    //adding the player to this class
    private Player player;

    //These objects will be used for drawing
    private Paint paint;
    private Canvas canvas;
    private SurfaceHolder surfaceHolder;

    //Adding enemies object array
    private Enemy[] enemies;

    //Adding an stars list
    private ArrayList<Star> stars = new ArrayList<Star>();

    //defining a boom object to display blast
    private Boom boom;

    //created a reference of the class Friend
//    private Friend friend;
    private Friend[] friends;

    public GameView(Context context, int screenX, int screenY) {
        super(context);

//        ImageButton imageButton = new ImageButton(context);
//        imageButton.setBackgroundResource(R.drawable.heart);

        //initializing context
        this.context = context;

        //initializing player object
        //this time also passing screen size to player constructor
        player = new Player(context, screenX, screenY);

        //initializing drawing objects
        surfaceHolder = getHolder();
        paint = new Paint();

        //adding 100 stars you may increase the number
        int starNums = 100;
        for (int i = 0; i < starNums; i++) {
            Star s  = new Star(screenX, screenY);
            stars.add(s);
        }

        enemies = new Enemy[enemyCount];
        for(int i=0; i<enemyCount; i++){
            enemies[i] = new Enemy(context, screenX, screenY);
        }

        //initializing boom object
        boom = new Boom(context);

        //initializing the Friend class object
//        friend = new Friend(context, screenX, screenY);
        friends = new Friend[friendCount];
        for(int i=0; i<friendCount; i++){
            friends[i] = new Friend(context, screenX, screenY);
        }

        this.screenX = screenX;
        countMisses = 0;
        isGameOver = false;

        int xpos = 1300;
        for (int i=0;i<livesCount;i++){
            life[i] = new Life(context, xpos,50);
            xpos += 250;
        }

        //setting the score to 0 initially
        score = 0;

        sharedPreferences = context.getSharedPreferences("high_score", MODE_PRIVATE);
        //initializing the array high scores with the previous values
        highScore[0] = sharedPreferences.getInt("score1",0);
        highScore[1] = sharedPreferences.getInt("score2",0);
        highScore[2] = sharedPreferences.getInt("score3",0);
        highScore[3] = sharedPreferences.getInt("score4",0);

        gameSound = sharedPreferences.getBoolean("sound",true);
        gameMusic = sharedPreferences.getBoolean("music",true);

        //initializing the media players for the game sounds
        gameBackgroundMusic = MediaPlayer.create(context,R.raw.game_background_music);
        killEnemySound = MediaPlayer.create(context,R.raw.killedenemy);
        gameOversound = MediaPlayer.create(context,R.raw.gameover);

        if (gameMusic) {
            //starting the game music as the game starts
            gameBackgroundMusic.start();
            gameBackgroundMusic.setLooping(true);
        }

    }

    @Override
    public void run() {
        while (playing) {
            //to update the frame
            update();
            //to draw the frame
            draw();
            //to control
            control();
        }
    }


    private void update() {

        //incrementing score as time passes
        score++;

        //updating player position
        player.update();

        //setting boom outside the screen
        boom.setX(-1250);
        boom.setY(-1250);

        //Updating the stars with player speed
        for (Star s : stars) {
            s.update(player.getSpeed());
        }

//        //setting the flag true when the enemy just enters the screen
//        if(enemies.getX()==screenX){
//            flag = true;
//        }

        //updating the enemy coordinate with respect to player speed
        for(int i=0; i<enemyCount; i++){

//            //setting the flag true when the enemy just enters the screen
//            if(enemies[i].getX()==screenX){
//                flag = true;
//            }

            enemies[i].update(player.getSpeed());

            //if collision occurs with player
            if (Rect.intersects(player.getDetectCollision(), enemies[i].getDetectCollision())) {

                //displaying boom at that location
                boom.setX(enemies[i].getX());
                boom.setY(enemies[i].getY());

                if (gameSound)
                    //playing a sound at the collision between player and the enemy
                    killEnemySound.start();

                //moving enemy outside the left edge
                enemies[i].setX(-1250);
                enemies[i].setY(-1250);

                // Kill an enemy gives 100 points
                score += 100;

            } else {

//                if(flag){
//                    //if player's x coordinate is more than the enemies's x coordinate.i.e. enemy has just passed across the player
////                    if(player.getDetectCollision().exactCenterX() >= enemies[i].getDetectCollision().exactCenterX()){
//                  if(enemies[i].getDetectCollision().exactCenterX() < 20){
//
//                        // remove a heart
//                        life[countMisses].setVisible(false);
//
//                        //increment countMisses
//                        countMisses++;
//
//                        //setting the flag false so that the else part is executed only when new enemy enters the screen
//                        flag = false;
//
//                        //if no of Misses is equal to 3, then game is over.
//                        if(countMisses== livesCount){
//                            //setting playing false to stop the game.
//                            playing = false;
//                            isGameOver = true;
//
//                            if (gameMusic)
//                            //stopping the gameOn music
//                                gameBackgroundMusic.stop();
//
//                            if (gameSound)
//                            //play the gameOver sound
//                                gameOversound.start();
//
//                        }
//                    }
//                }

            }
        }

//        friend.update(player.getSpeed());
        for(int i=0; i<friendCount; i++){

            friends[i].update(player.getSpeed());

            if(friends[i].getX()==screenX){
                flag = true;
            }

            if(Rect.intersects(player.getDetectCollision(),friends[i].getDetectCollision())){

                //displaying the boom at the collision
                boom.setX(friends[i].getX());
                boom.setY(friends[i].getY());

                friends[i].setX(-1250);
                friends[i].setY(-1250);

//                //setting playing false to stop the game
//                playing = false;
//                //setting the isGameOver true as the game is over
//                isGameOver = true;
//
//                if (gameMusic)
//                    //stopping the gameOn music
//                    gameBackgroundMusic.stop();
//
//                if (gameSound)
//                    //play the gameOver sound
//                    gameOversound.start();


                // remove a heart
                life[countMisses].setVisible(false);

                //increment countMisses
                countMisses++;

                //setting the flag false so that the else part is executed only when new enemy enters the screen
                flag = false;

                //if no of Misses is equal to 3, then game is over.
                if(countMisses== livesCount) {
                    //setting playing false to stop the game.
                    playing = false;
                    isGameOver = true;

                    if (gameMusic)
                        //stopping the gameOn music
                        gameBackgroundMusic.stop();

                    if (gameSound)
                        //play the gameOver sound
                        gameOversound.start();
                }


            }
        }
//        //checking for a collision between player and a friend
//        if(Rect.intersects(player.getDetectCollision(),friend.getDetectCollision())){
//
//            //displaying the boom at the collision
//            boom.setX(friend.getX());
//            boom.setY(friend.getY());
//            //setting playing false to stop the game
//            playing = false;
//            //setting the isGameOver true as the game is over
//            isGameOver = true;
//
//            if (gameMusic)
//                //stopping the gameOn music
//                gameBackgroundMusic.stop();
//
//            if (gameSound)
//                //play the gameOver sound
//                gameOversound.start();
//
//        }
    }

    private void draw() {
        //checking if surface is valid
        if (surfaceHolder.getSurface().isValid()) {
            //locking the canvas
            canvas = surfaceHolder.lockCanvas();


            //drawing a background color for canvas
            if (Build.VERSION.SDK_INT >= 23)
                canvas.drawColor(getResources().getColor(R.color.background , null));
            else
                canvas.drawColor(context.getResources().getColor(R.color.background));

//            Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.background);
//            canvas.drawBitmap(bitmap, 0, 0, paint);

            //setting the paint color to white to draw the stars
            paint.setColor(Color.WHITE);
            paint.setTextSize(20);
            //drawing all stars
            for (Star s : stars) {
                paint.setStrokeWidth(s.getStarWidth());
                canvas.drawPoint(s.getX(), s.getY(), paint);
            }

            //drawing the score on the game screen
            paint.setTextSize(120);
            canvas.drawText(getResources().getString(R.string.score) + ": " + score,400,150,paint);

            //Drawing the player
            canvas.drawBitmap(player.getBitmap(), player.getX(), player.getY(), paint);

            //drawing the enemies
            for (int i = 0; i < enemyCount; i++) {
                canvas.drawBitmap(enemies[i].getBitmap(), enemies[i].getX(), enemies[i].getY(), paint);
            }

            //drawing boom image
            canvas.drawBitmap(boom.getBitmap(), boom.getX(), boom.getY(), paint);

            //drawing friends image
//            canvas.drawBitmap(friend.getBitmap(), friend.getX(), friend.getY(), paint);
            for (int i = 0; i < friendCount; i++) {
                canvas.drawBitmap(friends[i].getBitmap(), friends[i].getX(), friends[i].getY(), paint);
            }

            //draw lives
            for (int i=0;i<life.length;i++)
                if (life[i].isVisible())
                    canvas.drawBitmap(life[i].getBitmap(), life[i].getX(), life[i].getY(), paint);

            if(isGameOver){
                paint.setTextSize(250); // make it adjust to screen!
                paint.setTextAlign(Paint.Align.CENTER);
                int yPos = (int) ((canvas.getHeight() / 2) - ((paint.descent() + paint.ascent()) / 2));
                int xPos = canvas.getWidth()/2;
                canvas.drawText(getResources().getString(R.string.game_over), xPos, yPos, paint);

            }

            //Unlocking the canvas
            surfaceHolder.unlockCanvasAndPost(canvas);
        }
    }

    private void control() { //This method will control the frames per seconds drawn. Here we are calling the delay method of Thread. And this is actually making our frame rate to around 60fps.
        try {
            gameThread.sleep(17);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void pause() {
        //when the game is paused
        //setting the variable to false
        playing = false;
        try {
            //stopping the thread
            gameThread.join();
        } catch (InterruptedException e) {
        }
    }

    public void resume() {
        //when the game is resumed
        //starting the thread again
        playing = true;
        gameThread = new Thread(this);
        gameThread.start();
    }

    boolean firstTouch = false;
    double time = 0;
    @Override
    public boolean onTouchEvent(MotionEvent event) {

//        return super.onTouchEvent(event);
        switch (event.getAction() & MotionEvent.ACTION_MASK) {
            case MotionEvent.ACTION_UP:
                //When the user presses on the screen
                //we will do something here

                //stopping the boosting when screen is released
                player.stopBoosting();
                break;
            case MotionEvent.ACTION_DOWN:
                //When the user releases the screen
                //do something here

                if(firstTouch && (System.currentTimeMillis() - time) <= 300) {
                    //do stuff here for double tap
//                    Toast.makeText(context, "double tap", Toast.LENGTH_SHORT).show();
//
//                    // to be continue
//                    player.setBoosting();

                    firstTouch = false;
                }else {
                    firstTouch = true;
                    time = System.currentTimeMillis();
                }
                
                //boosting the space jet when screen is pressed
                player.setBoosting();
                break;
//             case MotionEvent.ACTION_MOVE:
////                 player.setX((int)event.getX());
//                 player.setY((int)event.getY());
//                 break;
        }

        //if the game's over, tapping on game Over screen sends you to MainActivity
        if(isGameOver){
            if(event.getAction() == MotionEvent.ACTION_DOWN){
//                context.startActivity(new Intent(context,MainActivity.class));
                return false;
            }
        }

        return true;
    }


    //stop the music on exit
    public static void stopMusic(){
        gameBackgroundMusic.stop();
    }

    public int getScore() {
        return score;
    }

    public void saveHighScore(String name){

        int temp;
        //Assigning the scores to the highScore integer array
        for(int j=0;j<4;j++){
            if( highScore[j] < score ){
                temp = highScore[j];
                highScore[j] = score;
                score = temp;
            }
        }

        //storing the scores through shared Preferences
        SharedPreferences.Editor e = sharedPreferences.edit();
        for(int n=0;n<4;n++){
            e.putInt("score"+(n+1),highScore[n]);
        }
        e.commit();
    }

}