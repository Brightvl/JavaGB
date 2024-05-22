package ru.gb.developmentKit.lesson2_interfaces.hw2_server_interface.server.UI;


import ru.gb.developmentKit.lesson2_interfaces.hw2_server_interface.View;
import ru.gb.developmentKit.lesson2_interfaces.hw2_server_interface.server.Server;
import ru.gb.developmentKit.lesson2_interfaces.hw2_server_interface.server.ServerAdminApi;

import javax.swing.*;
import java.awt.*;

/**
 * Класс сервера
 */
public class ServerGUI extends JFrame implements View {
    /**
     * Ширина окна
     */
    public static final int WIDTH = 400;
    /**
     * Высота
     */
    public static final int HEIGHT = 300;

    /**
     * Текстовая панель вывода логов
     */
    private JTextArea log;

    private final ServerAdminApi server;


    /**
     * Конструктор класса
     */
    public ServerGUI(Server server) {
        this.server = server;
        setWindowParams();
        createPanel();
        setVisible(true);
    }

    /**
     * Параметры GUI
     */
    private void setWindowParams() {
        setSize(WIDTH, HEIGHT);
        setLocationRelativeTo(null); // расположение в центре экрана
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
     * Кнопка старт
     *
     * @return компонент
     */
    private Component createBtnStart() {
        JButton btnStart = new JButton("Start");

        btnStart.addActionListener(e -> {
            if (server.isServerWorking()) {
                appendMessage("Сервер уже запущен");
            } else {
                server.startServer();
                appendMessage("Сервер запущен!");
            }
        });
        return btnStart;
    }

    /**
     * Кнопка стоп
     *
     * @return компонент
     */
    private Component createBtnStop() {
        JButton btnStop = new JButton("Stop");
        btnStop.addActionListener(e -> {
            if (!server.isServerWorking()) {
                appendMessage("Сервер уже остановлен");
            } else {
                server.disconnect();
                appendMessage("Сервер остановлен!");
            }
        });
        return btnStop;
    }

    @Override
    public void appendMessage(String message) {
        log.append(message + "\n");
    }
}

