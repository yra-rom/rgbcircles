package com.example.plague.rgbcircles;

import android.app.Activity;

public class EnemyThread extends Activity implements Runnable {
    EnemyCircle circle;
    CanvasView canvas;
    GameManager manager;

    public EnemyThread(EnemyCircle circle, CanvasView canvas, GameManager manager) {
        this.circle = circle;
        this.canvas = canvas;
        this.manager = manager;
    }

    @Override
    public void run() {
        while(true) {
            circle.moveOneStep();
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    canvas.redraw();
                    manager.checkCollision();
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
}
