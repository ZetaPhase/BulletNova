package com.zetaphase.bulletnova;

import android.graphics.Bitmap;

/**
 * Created by Dave Ho on 2/7/2017.
 */

public class Player extends GameObject{
    private Bitmap spritesheet;
    private int score;
    private double dya;
    private boolean up;
    private boolean playing;
    //private Animation animation = new Animation();
    private long startTime;

    public Player(Bitmap res, int w, int h, int numFrames){
        x = 100;
        y = GamePanel.HEIGHT/2;
        dy = 0;
        score = 0;
        height = h;
    }
}
