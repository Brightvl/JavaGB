package ru.gb.developmentKit.lesson2_interfaces.hw2_server_interface;

public interface View {
    /**
     * Добавить в GUI сообщение
     *
     * @param message сообщение
     */
    void appendMessage(String message);

    void setText(String text);
}
