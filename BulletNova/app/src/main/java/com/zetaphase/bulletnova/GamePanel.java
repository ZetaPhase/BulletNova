package com.zetaphase.bulletnova;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;


public class GamePanel extends SurfaceView implements SurfaceHolder.Callback
{
    public static final int WIDTH = 2260;
    public static final int HEIGHT = 960;
    public static final int MOVESPEED = -5;
    private MainThread thread;
    private Background bg;
    private Player player;

    public GamePanel(Context context)
    {
        super(context);


        //add the callback to the surfaceholder to intercept events
        getHolder().addCallback(this);

        thread = new MainThread(getHolder(), this);

        //make gamePanel focusable so it can handle events
        setFocusable(true);
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height){}

    @Override
    public void surfaceDestroyed(SurfaceHolder holder){
        boolean retry = true;
        while(retry)
        {
            try{thread.setRunning(false);
                thread.join();

            }catch(InterruptedException e){e.printStackTrace();}
            retry = false;
        }
        //thread.interrupt();

    }

    @Override
    public void surfaceCreated(SurfaceHolder holder){

        bg = new Background(BitmapFactory.decodeResource(getResources(), R.drawable.background));
        player = new Player(BitmapFactory.decodeResource(getResources(), R.drawable.tetroid), 200, 200, 3);
        //we can safely start the game loop
        System.out.println(thread.getState());
        thread = new MainThread(getHolder(), this);
        thread.setRunning(true);
        try{
            thread.start();
        }catch (java.lang.IllegalThreadStateException e){e.printStackTrace();}

    }
    @Override
    public boolean onTouchEvent(MotionEvent event)
    {
        if(event.getAction()==MotionEvent.ACTION_DOWN){
            if(!player.getPlaying()){
                player.setPlaying(true);
            }
            player.setTouching(true);
            int TouchX = (int) event.getX();
            int TouchY = (int) event.getY();
            player.setTouchX(TouchX);
            player.setTouchY(TouchY);
            player.setPrevioustouchY(TouchY);
            //System.out.println("("+TouchX+", "+TouchY+")");
            return true;
        }
        if(event.getAction()==MotionEvent.ACTION_MOVE){
            int TouchX = (int) event.getX();
            int TouchY = (int) event.getY();
            player.setTouchX(TouchX);
            player.setTouchY(TouchY);
            //System.out.println("("+TouchX+", "+TouchY+")");
        }
        if(event.getAction()==MotionEvent.ACTION_UP){
            //System.out.println("up");
            player.setTouching(false);
            return true;
        }
        return super.onTouchEvent(event);
    }

    public void update()
    {
        if(player.getPlaying()){
            bg.update();
            player.update();
        }

    }
    @Override
    public void draw(Canvas canvas)
    {
        final float scaleFactorX = getWidth()/(WIDTH * 1.f);
        final float scaleFactorY = getHeight()/(HEIGHT * 1.f);
        if(canvas!=null) {
            final int savedState = canvas.save();
            canvas.scale(scaleFactorX, scaleFactorY);
            bg.draw(canvas);
            player.draw(canvas);
            canvas.restoreToCount(savedState);
        }
    }

}