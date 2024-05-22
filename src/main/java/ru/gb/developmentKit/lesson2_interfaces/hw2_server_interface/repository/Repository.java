package ru.gb.developmentKit.lesson2_interfaces.hw2_server_interface.repository;

import java.io.FileReader;
import java.io.FileWriter;

public class Repository implements Serialize {
    @Override
    public void save(String path, String text) {
        try (FileWriter writer = new FileWriter(path, true)) {
            writer.write(text);
            writer.write("\n");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public String read(String path) {
        StringBuilder stringBuilder = new StringBuilder();
        try (FileReader reader = new FileReader(path);) {
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
}
