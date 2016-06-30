package com.example.plague.rgbcircles;

import android.app.Activity;
import android.graphics.Canvas;
import android.util.Log;
import android.view.animation.AccelerateInterpolator;

import java.util.ArrayList;
import java.util.logging.Logger;

public class GameManager extends Activity{
    public static final int MAX_ENEMY_CIRCLES = 10;
    private MainCircle mainCircle;
    private ArrayList<EnemyCircle> circles;
    private ArrayList<Thread> threads;
    private CanvasView canvasView;
    private static int width;

    public static int getHeight() {
        return height;
    }

    private static int height;

    GameManager(CanvasView canvasView, int w, int h) {
        this.canvasView = canvasView;
        width = w;
        height = h;
        initMainCircle();
        initEnemyCircles();
    }

    private void initEnemyCircles() {
        SimpleCircle simpleCircleArea = mainCircle.getCircleArea();
        circles = new ArrayList<EnemyCircle>();
        for (int i = 0; i < MAX_ENEMY_CIRCLES; i++) {
            EnemyCircle circle;
            do{
                circle = EnemyCircle.getRandomCircle();
            }while(circle.isIntersected(simpleCircleArea));
            circles.add(circle);
        }
        calculateAndSetCirclesColor();
        moveEnemyCircles();
    }

    private void calculateAndSetCirclesColor() {
        for (EnemyCircle  circle: circles) {
            circle.setEnemyOrFoodRelation(mainCircle);
        }
    }

    public static int getWidth() {
        return width;
    }

    private void initMainCircle() {
        mainCircle = new MainCircle(width/2,height/2);
    }

    public void onDraw(Canvas canvas){
        canvasView.drawCircle(mainCircle);
        for (EnemyCircle circle : circles) {
            canvasView.drawCircle(circle);
        }
    }

    public void onTouchEvent(int x, int y){
        mainCircle.moveMainCircleWhenTouch(x,y);
        checkCollision();
        //moveCircles();
    }

    public void checkCollision() {
        EnemyCircle circleForDell = null;
        for (EnemyCircle circle: circles) {
            if(mainCircle.isIntersected(circle)){
                if(circle.isSmallerThen(mainCircle)){
                    mainCircle.growRadius(circle);
                    circleForDell = circle;
                    calculateAndSetCirclesColor();
                    break;
                }
                else {
                    gameEnd("YOU LOSE");
                }
            }
        }
        if(circleForDell != null){
            circleForDell.setAlive(false);
            circles.remove(circleForDell);
        }
        if(circles.isEmpty()){
            gameEnd("YOU WIN");
        }
    }

    private void gameEnd(String text){
        canvasView.showText(text);
        mainCircle.initRadius();
        initEnemyCircles();
        canvasView.redraw();
        for (Thread thread : threads){
            thread.interrupt();
        }
        threads.clear();
    }

    private void moveCircles(){
        for (EnemyCircle circle : circles){
            circle.moveOneStep();
        }
    }

    private void moveEnemyCircles(){
        threads = new ArrayList<>();
        for (EnemyCircle circle : circles){
            Thread thread = new Thread(new EnemyThread(circle, canvasView, this));
            threads.add(thread);
            thread.start();
        }
    }
}