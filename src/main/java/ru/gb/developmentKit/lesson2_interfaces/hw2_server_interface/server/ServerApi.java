package ru.gb.developmentKit.lesson2_interfaces.hw2_server_interface.server;

import ru.gb.developmentKit.lesson2_interfaces.hw2_server_interface.client.Client;

public interface ServerApi {
    boolean isServerWorking();


    /**
     * Попытка подключения клиента к серверу, если успешно - добавляем клиента в список
     *
     * @param client клиент
     * @return true если успешно
     */
    boolean connectUser(Client client);

    /**
     * Отключение клиента от сервера - удаляет из списка
     *
     * @param client клиент
     */
    void disconnectUser(Client client);
    /**
     * Получить лог сообщений
     *
     * @return строка
     */
    String getLog();

    void message(String text);

}
