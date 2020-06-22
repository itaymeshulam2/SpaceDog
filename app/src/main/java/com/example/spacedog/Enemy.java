package com.example.spacedog;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;

import java.util.Random;

public class Enemy {

    //bitmap for the enemy
    //we have already pasted the bitmap in the drawable folder
    private Bitmap bitmap;

    //x and y coordinates
    private int x;
    private int y;

    //enemy speed
    private int speed = 1;

    //min and max coordinates to keep the enemy inside the screen
    private int maxX;
    private int minX;
    private int maxY;
    private int minY;

    //creating a rect object
    private Rect detectCollision;

    public Enemy(Context context, int screenX, int screenY) {

        Random random = new Random();
        int image = random.nextInt(5)+1;
        Bitmap tempBitmap;
        if (image == 1)
            tempBitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.dog_1);
        else if (image == 2)
            tempBitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.dog_2);
        else if (image == 3)
            tempBitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.dog_3);
        else if (image == 4)
            tempBitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.dog_4);
        else
            tempBitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.dog_5);

        //getting bitmap from drawable resource
//        bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.enemy);

        bitmap = Bitmap.createScaledBitmap(tempBitmap, 250, 250, false);

        //initializing min and max coordinates
        maxX = screenX;
        maxY = screenY;
        minX = 0;
        minY = 0;

        //generating a random coordinate to add enemy

        speed = random.nextInt(6) + 10;
        x = screenX;
//        y = random.nextInt(maxY) - bitmap.getHeight();
        y = random.nextInt(maxY - bitmap.getHeight());

        //initializing rect object
        detectCollision = new Rect(x, y, bitmap.getWidth(), bitmap.getHeight());
    }

    public void update(int playerSpeed) {
        //decreasing x coordinate so that enemy will move right to left
        x -= playerSpeed;
        x -= speed;
        //if the enemy reaches the left edge
        if (x < minX - bitmap.getWidth()) {
            //adding the enemy again to the right edge
            Random random = new Random();
            speed = random.nextInt(10) + 10;
            x = maxX;
//            y = generator.nextInt(maxY) - bitmap.getHeight();
            y = random.nextInt(maxY- bitmap.getHeight());
        }

        //Adding the top, left, bottom and right to the rect object
        detectCollision.left = x;
        detectCollision.top = y;
        detectCollision.right = x + bitmap.getWidth();
        detectCollision.bottom = y + bitmap.getHeight();
    }

    //adding a setter to x coordinate so that we can change it after collision
    public void setX(int x){
        this.x = x;
    }
    public void setY(int y){
        this.y = y;
    }

    //one more getter for getting the rect object
    public Rect getDetectCollision() {
        return detectCollision;
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getSpeed() {
        return speed;
    }

}
