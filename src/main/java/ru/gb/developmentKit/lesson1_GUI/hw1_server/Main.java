package ru.gb.developmentKit.lesson1_GUI.hw1_server;

public class Main {
    public static void main(String[] args) {

        Server serverWindow = new Server();
        new Client(serverWindow, 500);
        new Client(serverWindow, -500);
    }
}

