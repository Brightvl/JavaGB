package ru.gb.developmentKit.lesson1_GUI.hw1;

import javax.swing.*;
import java.awt.*;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;

/**
 * Класс сервера
 */
public class Server extends JFrame {
    /**
     * Ширина окна
     */
    public static final int WIDTH = 400;
    /**
     * Высота
     */
    public static final int HEIGHT = 300;
    /**
     * Путь к log файлу
     */
    public static final String PATH = "src/main/java/ru/gb/developmentKit/lesson1_GUI/hw1/log.txt";

    /**
     * Хранит в себе клиентов работающих с сервером
     */
    private List<ClientGUI> clientGUIList;


    /**
     * Текстовая панель вывода логов
     */
    private JTextArea log;
    /**
     * Состояние работы севера
     */
    private boolean work;

    /**
     * Конструктор класса
     */
    Server() {
        clientGUIList = new ArrayList<>();

        setWindowParams();
        createPanel();
        setVisible(true);
    }

    /**
     * Параметры GUI
     */
    private void setWindowParams() {
        setSize(WIDTH, HEIGHT);
        setLocationRelativeTo(null); // расположение окна
        setResizable(false);

        setTitle("Server panel");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    /**
     * Создать GUI сервера
     */
    private void createPanel() {
        log = new JTextArea();
        add(log);
        add(createButtons(), BorderLayout.SOUTH);
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
        clientGUIList.add(clientGUI);
        return true;
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
     * Отключение клиента от сервера - удаляет из списка
     *
     * @param clientGUI клиент
     */
    public void disconnectUser(ClientGUI clientGUI) {
        clientGUIList.remove(clientGUI);
        if (clientGUI != null) {
            clientGUI.disconnectFromServer();
        }
    }

    /**
     * Создает сообщение и сохраняет в log файл
     *
     * @param text сообщение
     */
    public void message(String text) {
        if (!work) {
            return;
        }
        text += "";
        appendLog(text);
        answerAll(text);
        saveInLog(text);
    }

    /**
     * Отправить сообщение во все чаты
     *
     * @param text сообщение
     */
    private void answerAll(String text) {
        for (ClientGUI clientGUI : clientGUIList) {
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
     * Добавить в GUI log сообщение
     *
     * @param text сообщение
     */
    private void appendLog(String text) {
        log.append(text + "\n");
    }


    /**
     * Создает компонент с кнопками
     *
     * @return Компонент
     */
    private Component createButtons() {
        JPanel panel = new JPanel(new GridLayout(1, 2));

        panel.add(createBtnStart());
        panel.add(createBtnStop());
        return panel;
    }

    /**
     * Кнопка стоп
     *
     * @return компонент
     */
    private Component createBtnStop() {
        JButton btnStop = new JButton("Stop");
        btnStop.addActionListener(e -> {
            if (!work) {
                appendLog("Сервер уже был остановлен");
            } else {
                work = false;
                List<ClientGUI> clientsToDisconnect = new ArrayList<>(clientGUIList);
                for (ClientGUI clientGUI : clientsToDisconnect) {
                    disconnectUser(clientGUI);
                }
                appendLog("Сервер остановлен!");
            }
        });
        return btnStop;
    }


    /**
     * Кнопка старт
     *
     * @return компонент
     */
    private Component createBtnStart() {
        JButton btnStart = new JButton("Start");

        btnStart.addActionListener(e -> {
            if (work) {
                appendLog("Сервер уже был запущен");
            } else {
                work = true;
                appendLog("Сервер запущен!");
            }
        });
        return btnStart;
    }
}

