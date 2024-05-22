package ru.gb.developmentKit.lesson2_interfaces.hw2_server_interface.repository;

public interface Serialize {
    /**
     * Сохранить строку в файл
     * @param path путь
     * @param text текст
     */
    void save(String path, String text);

    /**
     * Чтение текста из файла
     * @param path путь
     * @return текст
     */
    String read(String path);

}
