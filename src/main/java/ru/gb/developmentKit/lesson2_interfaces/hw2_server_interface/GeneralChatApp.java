package ru.gb.developmentKit.lesson2_interfaces.hw2_server_interface;



import ru.gb.developmentKit.lesson2_interfaces.hw2_server_interface.client.Client;
import ru.gb.developmentKit.lesson2_interfaces.hw2_server_interface.server.Server;

public class GeneralChatApp {
    public static void main(String[] args) {
        Server server = new Server();
        server.showGUI();

        new Client(server);
        new Client(server);
    }
}

