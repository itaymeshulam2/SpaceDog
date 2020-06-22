package com.example.spacedog;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;

import java.util.Random;

public class Friend {

    private Bitmap bitmap;
    private int x;
    private int y;
    private int speed = 1;

    private int maxX;
    private int minX;

    private int maxY;
    private int minY;

    //creating a rect object for a friendly ship
    private Rect detectCollision;

    public Friend(Context context, int screenX, int screenY) {
        bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.friend);
        maxX = screenX;
        maxY = screenY;
        minX = 0;
        minY = 0;
        Random random = new Random();
        speed = random.nextInt(6) + 10;
        x = screenX;
        y = random.nextInt(maxY - bitmap.getHeight());

        //initializing rect object
        detectCollision = new Rect(x, y, bitmap.getWidth(), bitmap.getHeight());
    }

    public void update(int playerSpeed) {
        x -= playerSpeed;
        x -= speed;
        if (x < minX - bitmap.getWidth()) {
            Random random = new Random();
            speed = random.nextInt(10) + 10;
            x = maxX;
            y = random.nextInt(maxY - bitmap.getHeight());
        }

        //Adding the top, left, bottom and right to the rect object
//        detectCollision.left = x;
//        detectCollision.top = y;
//        detectCollision.right = x + bitmap.getWidth();
//        detectCollision.bottom = y + bitmap.getHeight();

        detectCollision.left = x +50;
        detectCollision.top = y +50;
        detectCollision.right = x + bitmap.getWidth() -50;
        detectCollision.bottom = y + bitmap.getHeight() -50;
    }


    //one more getter for getting the rect object
    public Rect getDetectCollision() {
        return detectCollision;
    }

    //getters
    public Bitmap getBitmap() {
        return bitmap;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

}
