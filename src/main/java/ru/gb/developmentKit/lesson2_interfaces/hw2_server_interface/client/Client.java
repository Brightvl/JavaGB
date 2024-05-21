package ru.gb.developmentKit.lesson2_interfaces.hw2_server_interface.client;

import ru.gb.developmentKit.lesson2_interfaces.hw2_server_interface.client.UI.ClientGUI;
import ru.gb.developmentKit.lesson2_interfaces.hw2_server_interface.client.UI.ClientView;
import ru.gb.developmentKit.lesson2_interfaces.hw2_server_interface.server.Server;
import ru.gb.developmentKit.lesson2_interfaces.hw2_server_interface.server.ServerApi;

public class Client implements ClientApi {

    /**
     * Объект сервер
     */
    private final ServerApi server;
    /**
     * Состояние подключения
     */
    private boolean connected;
    /**
     * Имя клиента
     */
    private String login;

    private ClientView clientView;


    public Client(Server server) {
        this.server = server;
        this.clientView = new ClientGUI(this, 50);
    }


    @Override
    public boolean isConnected() {
        return connected;
    }

    @Override
    public void connectToServer(String login) {
        if (server.connectUser(this)) {
            clientView.appendMessage("Вы успешно подключились!\n");
            connected = true;
            this.login = login;
            String log = server.getLog();
            if (log != null) {
                clientView.appendMessage(log);
            }
        } else {
            clientView.appendMessage("Подключение не удалось");
        }
    }

    @Override
    public void disconnectFromServer() {
        if (connected) {

            connected = false;
            server.disconnectUser(this);
            clientView.disconnectClient();
            clientView.appendMessage("Вы были отключены от сервера!");
        }
    }

    @Override
    public void getMessage(String message) {
        if (connected) {
            String text = message;
            if (!text.isEmpty()) {
                server.message(login + ": " + text);
                clientView.setText("");
            }
        } else {
            appendMessage("Нет подключения к серверу");
        }
    }

    public void appendMessage(String message) {
        clientView.appendMessage(message);

    }
}
