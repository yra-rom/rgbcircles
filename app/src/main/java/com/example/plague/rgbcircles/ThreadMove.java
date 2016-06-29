package com.example.plague.rgbcircles;
import android.util.Log;

import java.util.ArrayList;

public class ThreadMove implements Runnable{
    protected EnemyCircle circle;
    protected CanvasView canvas;
    protected GameManager gameManager;
    ArrayList<EnemyCircle> circles;
    private final int uniqNumber;

    public ThreadMove(GameManager gameManager,
                      EnemyCircle circle,
                      CanvasView canvas,
                      Integer i, ArrayList<EnemyCircle> circles){
        this.circle =  circle;
        this.canvas = canvas;
        this.gameManager = gameManager;
        this.circles = circles;
        uniqNumber = i;

    }

    private boolean updateIsCirclesEmpty(ArrayList<EnemyCircle> circles) {
        return circles.isEmpty();
    }

    @Override
    public void run(){
        makeCurrentThreadLogs();
        while(!updateIsCirclesEmpty(circles)){
            circle.moveOneStep();
            gameManager.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    canvas.redraw();
                    gameManager.checkCollision();
                }
            });
            Thread.yield();
            try {
                Thread.sleep(25);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
    @Override

    protected void finalize(){
        makeKillLogs();
    }
    protected void makeKillLogs() {
        Log.d("MyLogs", new StringBuilder("Killed Thread :$ ").append(uniqNumber).toString());
    }
    protected void makeCurrentThreadLogs(){
        Log.d("MyLogs", new StringBuilder().append("Current Thread $:").append(uniqNumber).toString());
    }
}
