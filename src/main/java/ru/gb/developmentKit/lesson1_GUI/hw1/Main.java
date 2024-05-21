package ru.gb.developmentKit.lesson1_GUI.hw1;

public class Main {
    public static void main(String[] args) {

        Server serverWindow = new Server();
        new ClientGUI(serverWindow, 500);
        new ClientGUI(serverWindow, -500);
    }
}

