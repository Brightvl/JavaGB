package ru.gb.developmentKit.lesson2_interfaces.hw2_server_interface.client;

import ru.gb.developmentKit.lesson2_interfaces.hw2_server_interface.client.UI.ClientGUI;
import ru.gb.developmentKit.lesson2_interfaces.hw2_server_interface.client.UI.ClientView;
import ru.gb.developmentKit.lesson2_interfaces.hw2_server_interface.server.Server;
import ru.gb.developmentKit.lesson2_interfaces.hw2_server_interface.server.ServerApi;

public class Client implements ClientApi {

    /**
     * Объект сервер
     */
    private final ServerApi SERVER;
    /**
     * Объект view
     */
    private final ClientView CLIENT_VIEW;
    /**
     * Состояние подключения
     */
    private boolean connected;
    /**
     * Имя клиента
     */
    private String login;




    public Client(Server server) {
        this.SERVER = server;
        this.CLIENT_VIEW = new ClientGUI(this, 500, 500);
    }


    @Override
    public boolean isConnected() {
        return connected;
    }

    @Override
    public void connectToServer(String login) {
        if (SERVER.connectUser(this)) {
            CLIENT_VIEW.appendMessage("Вы успешно подключились!\n");
            connected = true;
            this.login = login;
            String log = SERVER.getLog();
            if (log != null) {
                CLIENT_VIEW.appendMessage(log);
            }
        } else {
            CLIENT_VIEW.appendMessage("Подключение не удалось");
        }
    }

    @Override
    public void disconnectFromServer() {
        if (connected) {

            connected = false;
            SERVER.disconnectUser(this);
            CLIENT_VIEW.disconnectClient();
            CLIENT_VIEW.appendMessage("Вы были отключены от сервера!");
        }
    }

    @Override
    public void getMessage(String message) {
        if (connected) {
            String text = message;
            if (!text.isEmpty()) {
                SERVER.message(login + ": " + text);

            }
        } else {
            appendMessage("Нет подключения к серверу");
        }
    }

    public void appendMessage(String message) {
        CLIENT_VIEW.appendMessage(message);

    }
}
