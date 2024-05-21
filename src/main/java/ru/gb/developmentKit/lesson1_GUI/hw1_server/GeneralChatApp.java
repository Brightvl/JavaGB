package ru.gb.developmentKit.lesson1_GUI.hw1_server;

import ru.gb.developmentKit.lesson1_GUI.hw1_server.client.Client;
import ru.gb.developmentKit.lesson1_GUI.hw1_server.server.Server;

public class GeneralChatApp {
    public static void main(String[] args) {

        Server serverWindow = new Server();
        new Client(serverWindow, 500);
        new Client(serverWindow, -500);
    }
}

