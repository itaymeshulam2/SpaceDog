package com.example.spacedog;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class Life {

    //Bitmap to get character from image
    private Bitmap bitmap;

    //coordinates
    private int x;
    private int y;

    private boolean visible;

    public Life(Context context, int x, int y) {

        Bitmap tempBitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.heart);
        bitmap = Bitmap.createScaledBitmap(tempBitmap, 200, 200, false);

        this.x = x;
        this.y = y;
        this.visible = true;
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }
}
