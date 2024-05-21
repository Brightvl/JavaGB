package ru.gb.developmentKit.lesson2_interfaces.s2.common;



import java.awt.*;

public interface CanvasRepaintListener {
    void onDrawFrame(MainCanvas canvas, Graphics g, float deltaTime);
}
