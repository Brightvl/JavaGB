package ru.gb.developmentKit.lesson2_interfaces.hw2_server_interface.server;

import ru.gb.developmentKit.lesson2_interfaces.hw2_server_interface.client.ClientGUI;
import ru.gb.developmentKit.lesson2_interfaces.hw2_server_interface.server.UI.ServerGUI;
import ru.gb.developmentKit.lesson2_interfaces.hw2_server_interface.server.UI.View;

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
    private final List<ClientGUI> CLIENT_GUI_LIST;

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


    /**
     * Попытка подключения клиента к серверу, если успешно - добавляем клиента в список
     *
     * @param clientGUI клиент
     * @return true если успешно
     */
    public boolean connectUser(ClientGUI clientGUI) {
        if (!work) {
            return false;
        }
        CLIENT_GUI_LIST.add(clientGUI);
        return true;
    }





    /**
     * Отключение клиента от сервера - удаляет из списка
     *
     * @param clientGUI клиент
     */
    public void disconnectUser(ClientGUI clientGUI) {
        CLIENT_GUI_LIST.remove(clientGUI);
        if (clientGUI != null) {
            clientGUI.disconnectFromServer();
        }
    }

    /**
     * Отправить сообщение во все чаты
     *
     * @param text сообщение
     */
    private void answerAll(String text) {
        for (ClientGUI clientGUI : CLIENT_GUI_LIST) {
            clientGUI.answer(text);
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

    /**
     * Получить лог сообщений
     *
     * @return строка
     */
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
    public void startServer() {
        work = true;
        answerAll("Сервер запущен");
        saveInLog("Сервер запущен");
    }


    @Override
    public void disconnect() {
        this.work = false;
        List<ClientGUI> clientsToDisconnect = new ArrayList<>(CLIENT_GUI_LIST);
        for (ClientGUI clientGUI : clientsToDisconnect) {
            disconnectUser(clientGUI);
        }
    }
}
