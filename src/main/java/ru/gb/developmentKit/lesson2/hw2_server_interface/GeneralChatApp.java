package ru.gb.developmentKit.lesson2.hw2_server_interface;


import ru.gb.developmentKit.lesson2.hw2_server_interface.client.Client;
import ru.gb.developmentKit.lesson2.hw2_server_interface.server.Server;

public class GeneralChatApp {
    public static void main(String[] args) {

        Server serverWindow = new Server();
        new Client(serverWindow, 500);
        new Client(serverWindow, -500);
    }
}

