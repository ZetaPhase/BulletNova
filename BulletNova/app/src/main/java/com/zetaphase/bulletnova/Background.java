package com.zetaphase.bulletnova;

import android.graphics.Bitmap;
import android.graphics.Canvas;

/**
 * Created by Dave Ho on 2/6/2017.
 */

public class Background {

    private Bitmap image;
    private int x, y, dy;

    public Background(Bitmap res){
        image = res;
    }

    public void update(){
        y += dy;
        if(y<-GamePanel.HEIGHT){
            y=0;
        }
    }

    public void draw(Canvas canvas){
        canvas.drawBitmap(image, x, y, null);
        if(x<0){
            canvas.drawBitmap(image, x, y+GamePanel.HEIGHT, null);
        }
    }
    public void setVector(int dy){
        this.dy = dy;
    }
}
