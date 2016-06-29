package com.example.plague.rgbcircles;

import android.graphics.Color;

public class MainCircle extends SimpleCircle{
    public static final int INT_RADIUS = 50;
    public static final int INT_SPEED = 50;
    public static final int OUR_COLOR = Color.BLUE;

    public MainCircle(int x, int y) {
        super(x, y, INT_RADIUS);
        setColor(OUR_COLOR);
    }

    public void moveMainCircleWhenTouch(int x1, int y1) {
        int dx = (x1 - x) * INT_SPEED / GameManager.getWidth();
        int dy = (y1 - y) * INT_SPEED / GameManager.getHeight();
        this.x+=dx;
        this.y+=dy;
    }

    public void initRadius() {
        radius = INT_RADIUS;
    }

    public void growRadius(EnemyCircle circle) {
        radius = (int) (Math.sqrt(Math.pow(radius,2) + Math.pow(circle.radius,2)));
    }
}
