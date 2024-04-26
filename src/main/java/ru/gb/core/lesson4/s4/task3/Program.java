package ru.gb.core.lesson4.s4.task3;


import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 Задача 3
 ========
 Запишите в файл следующие строки:
 <p>
 Анна=4
 Елена=5
 Марина=6
 Владимир=?
 Константин=?
 Иван=4
 <p>
 Реализуйте метод, который считывает данные из файла и сохраняет в двумерный массив (либо HashMap, если
 студенты с ним знакомы). В отдельном методе нужно будет пройти по структуре данных, если сохранено
 значение ?, заменить его на соответствующее число. Если на каком-то месте встречается символ, отличный от
 числа или ?, бросить подходящее исключение.
 */

public class Program {

    static Random random = new Random();

    public static void main(String[] args) {
        prepareFile();

        try {
            Map<String, Integer> map = loadFile();
            assert map != null;
            for (Map.Entry<String, Integer> item : map.entrySet()) {
                System.out.printf("%s - %d\n", item.getKey(), item.getValue());
            }
        }
        catch (LoadFileProcessException e){
            System.out.println(e.getMessage());
            System.out.println("Строка: " + e.getLine());
            System.out.println("Ожидалось кол-во символов или знак \"?\", получили: " + e.getData());
        }

    }

    static Map<String, Integer> loadFile() throws LoadFileProcessException {
        String line;
        int counter = 0;
        Map<String, Integer> map = new HashMap<>();

        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader("names.txt"));
            while ((line = bufferedReader.readLine()) != null){
                counter++;
                String[] parts = line.split("=");
                try {
                    map.put(parts[0], parts[1].length() == 1 && parts[1].charAt(0) == '?' ? parts[0].length() : Integer.parseInt(parts[1]));
                }
                catch (NumberFormatException n){
                    throw new LoadFileProcessException("Исключение при обработке данных", counter, parts[1]);
                }
            }
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        return map;
    }

    static void prepareFile(){
        try (FileWriter writer = new FileWriter("names.txt", false)){
            writer.write("Анна=4\n");
            writer.write("Елена=5\n");
            if (random.nextInt(2) == 0){
               // throw new Exception("Internal Exception!");
            }
            writer.write("Марина=6\n");
            writer.write("Владимир=?\n");
            writer.write("Константин=?\n");
            writer.write("Иван=4\n");
        }
        catch (IOException e){
            e.printStackTrace();
        }
        catch (Exception e){
            e.printStackTrace();
        }
        /*finally {
            try {
                writer.close();
            }
            catch (IOException e){
                //...
            }
        }*/
    }

}
