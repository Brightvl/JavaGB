package ru.gb.developmentKit.lesson2_interfaces.hw2_server_interface.client;


import ru.gb.developmentKit.lesson2_interfaces.hw2_server_interface.server.Server;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

/**
 * Класс клиента
 */
public class Client extends JFrame {
    /**
     * Ширина окна
     */
    public static final int WIDTH = 400;
    /**
     * Высота окна
     */
    public static final int HEIGHT = 400;

    /**
     * Объект сервер
     */
    private final Server server;
    /**
     * Состояние подключения
     */
    private boolean connected;
    /**
     * Имя клиента
     */
    private String name;

    /**
     * Текстовая область
     */
    private JTextArea log;
    /**
     * Однострочное поле
     */
    private JTextField tfIPAddress, tfPort, tfLogin, tfMessage;
    /**
     * Поле ввода пароля
     */
    private JPasswordField pfPassword;
    /**
     * Кнопки
     */
    private JButton btnLogin, btnSend;
    /**
     * Окно авторизации
     */
    private JPanel loginPanel;

    public Client(Server server, int x) {
        this.server = server;

        setWindowParams(server.getX() + x, server.getY());
        createPanel();
        setVisible(true);
    }

    /**
     * Параметры GUI
     */
    private void setWindowParams(int x, int y) {
        setSize(WIDTH, HEIGHT);
        setResizable(false);
        setTitle("GBGram");
        setLocation(x, y);
    }

    /**
     * Создать GUI клиента
     */
    private void createPanel() {
        add(createLoginPanel(), BorderLayout.NORTH);
        add(createLog());
        add(createInputTextPanel(), BorderLayout.SOUTH);
    }

    /**
     * Ответ
     *
     * @param text Текст
     */
    public void answer(String text) {
        appendLog(text);
    }

    /**
     * Подключение к серверу
     */
    private void connectToServer() {
        if (server.connectUser(this)) {
            appendLog("Вы успешно подключились!\n");
            loginPanel.setVisible(false);
            connected = true;
            name = tfLogin.getText();
            String log = server.getLog();
            if (log != null) {
                appendLog(log);
            }
        } else {
            appendLog("Подключение не удалось");
        }
    }

    /**
     * Отключает клиента от сервера
     */
    public void disconnectFromServer() {
        if (connected) {
            loginPanel.setVisible(true);
            connected = false;
            server.disconnectUser(this);
            appendLog("Вы были отключены от сервера!");
        }
    }

    /**
     * Выводит сообщение из log
     */
    public void message() {
        if (connected) {
            String text = tfMessage.getText();
            if (!text.isEmpty()) {
                server.message(name + ": " + text);
                tfMessage.setText("");
            }
        } else {
            appendLog("Нет подключения к серверу");
        }

    }

    /**
     * Выводит сообщение log в окно чата
     *
     * @param text сообщение
     */
    private void appendLog(String text) {
        log.append(text + "\n");
    }


    /**
     * Создает панель авторизации
     *
     * @return панель
     */
    private Component createLoginPanel() {
        loginPanel = new JPanel(new GridLayout(2, 3));

        loginPanel.add(tfIPAddress = new JTextField("127.0.0.1"));
        loginPanel.add(tfPort = new JTextField("8189"));
        loginPanel.add(new JPanel()); // пустое окно

        loginPanel.add(tfLogin = new JTextField("login"));
        loginPanel.add(pfPassword = new JPasswordField("12345"));
        loginPanel.add(btnLogin = new JButton("Confirm"));
        btnLogin.addActionListener(e -> connectToServer());

        return loginPanel;
    }

    /**
     * Создает панель log
     *
     * @return компонент
     */
    private Component createLog() {
        log = new JTextArea();
        log.setEditable(false);
        return new JScrollPane(log);
    }

    /**
     * Создает панель для ввода сообщений
     *
     * @return панель
     */
    private Component createInputTextPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        tfMessage = new JTextField();
        tfMessage.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                if (e.getKeyChar() == '\n') {
                    message();
                }
            }
        });
        btnSend = new JButton("Send");
        btnSend.addActionListener(e -> message());
        panel.add(tfMessage);
        panel.add(btnSend, BorderLayout.EAST);
        return panel;
    }


    @Override
    public int getDefaultCloseOperation() {
        disconnectFromServer();
        return super.getDefaultCloseOperation();
    }
}

