package ru.gb.developmentKit.lesson2_interfaces.hw2_server_interface.client;

public interface ClientApi {
    boolean isConnected();

    /**
     * Подключение к серверу
     */
    void connectToServer(String login);

    /**
     * Отключает клиента от сервера
     */
    void disconnectFromServer();

    /**
     * Отправляет сообщение на сервер
     * @param message сообщение
     */
    void getMessage(String message);
}
