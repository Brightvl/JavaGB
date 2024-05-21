package ru.gb.developmentKit.lesson2_interfaces.hw2_server_interface.server;

public interface ServerAdminApi extends ServerApi {
    void startServer();

    void disconnect();
}
