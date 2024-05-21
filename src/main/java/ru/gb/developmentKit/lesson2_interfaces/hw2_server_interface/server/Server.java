package ru.gb.developmentKit.lesson2_interfaces.hw2_server_interface.server;

import ru.gb.developmentKit.lesson2_interfaces.hw2_server_interface.client.Client;
import ru.gb.developmentKit.lesson2_interfaces.hw2_server_interface.server.UI.ServerGUI;
import ru.gb.developmentKit.lesson2_interfaces.hw2_server_interface.View;

import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;

public class Server implements ServerAdminApi {
    /**
     * Путь к log файлу
     */
    public static final String PATH = "src/main/java/ru/gb/developmentKit/lesson1_GUI/hw1_server/data/log.txt";

    /**
     * Хранит в себе клиентов работающих с сервером
     */
    private final List<Client> CLIENT_GUI_LIST;

    /**
     * Экземпляр сервера
     */
    private View serverGUI;


    /**
     * Состояние работы севера
     */
    private boolean work;

    public Server() {
        CLIENT_GUI_LIST = new ArrayList<>();
    }


    public void showGUI() {
        serverGUI = new ServerGUI(this);
    }



    public void disconnectUser(Client client) {
        CLIENT_GUI_LIST.remove(client);
        if (client != null) {
            client.disconnectFromServer();
        }
    }

    /**
     * Отправить сообщение во все чаты
     *
     * @param text сообщение
     */
    private void answerAll(String text) {
        for (Client client : CLIENT_GUI_LIST) {
            client.appendMessage(text);
        }
    }

    /**
     * Записать в log файл
     *
     * @param text текст сообщений
     */
    private void saveInLog(String text) {
        try (FileWriter writer = new FileWriter(PATH, true)) {
            writer.write(text);
            writer.write("\n");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Прочитать лог файл сообщений
     *
     * @return строка со всеми сообщениями
     */
    private String readLog() {
        StringBuilder stringBuilder = new StringBuilder();
        try (FileReader reader = new FileReader(PATH);) {
            int c;
            while ((c = reader.read()) != -1) {
                stringBuilder.append((char) c);
            }
            stringBuilder.delete(stringBuilder.length() - 1, stringBuilder.length());
            return stringBuilder.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public String getLog() {
        return readLog();
    }

    /**
     * Создает сообщение и сохраняет в log файл
     *
     * @param text сообщение
     */
    public void message(String text) {
        if (!isServerWorking()) {
            return;
        }
        text += "";
        serverGUI.appendMessage(text);
        answerAll(text);
        saveInLog(text);
    }

    @Override
    public boolean isServerWorking() {
        return work;
    }

    @Override
    public boolean connectUser(Client client) {
        if (!work) {
            return false;
        }
        CLIENT_GUI_LIST.add(client);
        return true;
    }

    @Override
    public void startServer() {
        work = true;
        answerAll("Сервер запущен");
        saveInLog("Сервер запущен");
    }


    @Override
    public void disconnect() {
        this.work = false;
        List<Client> clientsToDisconnect = new ArrayList<>(CLIENT_GUI_LIST);
        for (Client client : clientsToDisconnect) {
            disconnectUser(client);
        }
    }
}
