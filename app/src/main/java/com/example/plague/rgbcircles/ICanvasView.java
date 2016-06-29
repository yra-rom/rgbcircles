package com.example.plague.rgbcircles;

public interface ICanvasView {
    void drawCircle(SimpleCircle mainCircle);

    void redraw();

    void showText(String text);
}
