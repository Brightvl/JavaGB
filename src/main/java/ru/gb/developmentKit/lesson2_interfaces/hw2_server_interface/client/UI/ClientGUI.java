package ru.gb.developmentKit.lesson2_interfaces.hw2_server_interface.client.UI;

import ru.gb.developmentKit.lesson2_interfaces.hw2_server_interface.client.Client;
import ru.gb.developmentKit.lesson2_interfaces.hw2_server_interface.client.ClientApi;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

/**
 * Класс клиента
 */
public class ClientGUI extends JFrame implements ClientView {
    /**
     * Ширина окна
     */
    public static final int WIDTH = 400;
    /**
     * Высота окна
     */
    public static final int HEIGHT = 400;

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

    private final ClientApi CLIENT;

    public ClientGUI(Client client, int x, int y) {
        this.CLIENT = client;

        setWindowParams(x,y);
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
                    getMessage();
                }
            }
        });
        btnSend = new JButton("Send");
        btnSend.addActionListener(e -> getMessage());
        panel.add(tfMessage);
        panel.add(btnSend, BorderLayout.EAST);
        return panel;
    }

    /**
     * Выводит сообщение из log
     */
    public void getMessage() {
        CLIENT.getMessage(tfMessage.getText());
        tfMessage.setText("");
    }

    public void connectToServer() {
        CLIENT.connectToServer(tfLogin.getText());
        if (CLIENT.isConnected()) {
            loginPanel.setVisible(false);
        }
    }

    public void disconnectFromServer() {
        CLIENT.disconnectFromServer();
        disconnectClient();
    }

    @Override
    public void appendMessage(String message) {
        log.append(message + "\n");
    }

    @Override
    public int getDefaultCloseOperation() {
        disconnectFromServer();
        return super.getDefaultCloseOperation();
    }


    @Override
    public void disconnectClient() {
        if (!CLIENT.isConnected()) {
            loginPanel.setVisible(true);
        }
    }
}

