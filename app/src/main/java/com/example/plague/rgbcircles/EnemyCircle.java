package com.example.plague.rgbcircles;

import android.graphics.Color;

import java.util.Random;

public class EnemyCircle extends SimpleCircle {

    public static final int FROM_RADIUS = 10;
    public static final int TO_RADIUS = 110;
    public static final int ENEMY_COLOR = Color.RED;
    public static final int FOOD_COLOR = Color.rgb(0, 200, 0);
    public static final int RANDOM_SPEED = 10;
    private int dx, dy;

    public EnemyCircle(int x, int y, int radius,int dx, int dy) {
        super(x, y, radius);
        this.dx = dx;
        this.dy = dy;
    }

    public static EnemyCircle getRandomCircle() {
        Random random = new Random();
        int x = random.nextInt(GameManager.getWidth());
        int y = random.nextInt(GameManager.getHeight());
        int dx = 1 + random.nextInt(RANDOM_SPEED);
        int dy = 1 + random.nextInt(RANDOM_SPEED);
        int radius = FROM_RADIUS + random.nextInt(TO_RADIUS - FROM_RADIUS);
        EnemyCircle enemyCircle = new EnemyCircle(x,y,radius, dx, dy);
        enemyCircle.setColor(ENEMY_COLOR);
        return enemyCircle;
    }

    public void setEnemyOrFoodRelation(MainCircle mainCirle){
        if(isSmallerThen(mainCirle)){
            setColor(FOOD_COLOR);
        }else{
            setColor(ENEMY_COLOR);
        }
    }

    public boolean isSmallerThen(SimpleCircle cirle){
        return radius < cirle.radius;
    }

    public void moveOneStep() {
        x+=dx;
        y+=dy;
        checkBorder();
    }

    private void checkBorder() {
        if(x > GameManager.getWidth() || x < 0){
            dx = -dx;
        }
        if(y > GameManager.getHeight() || y < 0){
            dy = -dy;
        }
    }
}
