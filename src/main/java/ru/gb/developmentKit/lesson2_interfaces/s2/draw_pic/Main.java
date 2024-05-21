package ru.gb.developmentKit.lesson2_interfaces.s2.draw_pic;






import ru.gb.developmentKit.lesson2_interfaces.s2.draw_pic.view.MainWindow;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new MainWindow();
            }
        });
    }
}