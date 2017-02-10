package com.zetaphase.bulletnova;

import android.graphics.Bitmap;
import android.graphics.Canvas;


public class Player extends GameObject{
    private Bitmap spritesheet;
    private int score;
    private double dya;
    private boolean up;
    private boolean playing;
    private Animation animation = new Animation();
    private long startTime;
    private int touchX;
    private int touchY;
    private int previoustouchX;
    private int previoustouchY;

    public Player(Bitmap res, int w, int h, int numFrames) {

        x = 100;
        y = 100;
        dy = 0;
        score = 0;
        height = h;
        width = w;

        Bitmap[] image = new Bitmap[numFrames];
        spritesheet = res;

        for (int i = 0; i < image.length; i++)
        {
            image[i] = Bitmap.createBitmap(spritesheet, i*width, 0, width, height);
        }

        animation.setFrames(image);
        animation.setDelay(10);
        startTime = System.nanoTime();

    }

    public void setUp(boolean b){up = b;}

    public void update()
    {
        long elapsed = (System.nanoTime()-startTime)/1000000;
        if(elapsed>100)
        {
            score++;
            startTime = System.nanoTime();
        }
        animation.update();

        //System.out.println("Dya" + dya);
        /*
        IMPLEMENTATION #1
        - Player Y position does not change according to TouchX
        - Changes according to difference in TouchX and PreviousTouchX
         */
        if(up){
            dy = touchY - previoustouchY;
        }else{
            dy = 0;
        }
        /*
        IMPLEMENTATION #2
        - Player Y accelerates to current TouchY
        if(up) {
            if(Math.abs(touchY - y)>8){
                dy = (int)(dya=(touchY - y));
            }else{
                dy = (int)(dya=0);
            }
        }else{
            dy = (int)(dya=0);
        }

        IMPLEMENTATION #3
        - When finger touching screen, upward acceleration
        - When finger not touching screen, downward acceleration
        if(up){
            dy = (int)(dya-=1.1);

        }
        else{
            dy = (int)(dya+=1.1);
        }
        */

        if(dy>14)dy = 14;
        if(dy<-14)dy = -14;
        if(y<0)y=0;
        if(y>930)y=930;

        y += dy*2;
        dy = 0;

        previoustouchY = touchY;
    }

    public void draw(Canvas canvas)
    {
        canvas.drawBitmap(animation.getImage(),x,y-100,null);
    }
    public int getScore(){return score;}
    public boolean getPlaying(){return playing;}
    public void setPlaying(boolean b){playing = b;}
    public void resetDYA(){dya = 0;}
    public void resetScore(){score = 0;}
    public void setTouchX(int touchX){
        this.touchX = touchX;
    }
    public void setTouchY(int touchY){
        this.touchY = touchY;
    }
}