package ru.gb.developmentKit.lesson2_interfaces.hw2_server_interface.client.UI;

import ru.gb.developmentKit.lesson2_interfaces.hw2_server_interface.View;

public interface ClientView extends View {

    /**
     * Соединение с клиентом
     */
    void disconnectClient();

}
