package ru.gb.core.lesson5.hw5;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Program {

    /**
     * Написать функцию, создающую резервную копию всех файлов в директории во вновь созданную папку ./backup
     */

    private static final Random random = new Random();
    private static final int CHAR_BOUND_L = 65; // ASCII-код начального символа ('A')
    private static final int CHAR_BOUND_H = 90; // ASCII-код конечного символа ('Z')
    private static final String TO_SEARCH = "GeekBrains"; // Искомое слово
    private static final String dirPath = "./src/main/java/ru/gb/core/lesson5/hw5/";

    /**
     * Точка входа в программу
     */
    public static void main(String[] args) throws IOException {
        File dataPath = new File(dirPath + "data", "/");
        createMissingDirectory(dataPath);

        {
            File backupPath = new File(dirPath + "./backup", "/");
            createMissingDirectory(backupPath);
            backupAllFile(dataPath, backupPath);
        }// HW_5 backup указанных файлов

        {
            System.out.println(generateSymbols(15));
        }// генерация символов

        {
            writeFileContents(dataPath + "sample01.txt", 10, TO_SEARCH);
            writeFileContents(dataPath + "sample02.txt", 10, TO_SEARCH);

            concatenate(
                    dataPath + "sample01.txt",
                    dataPath + "sample02.txt",
                    dataPath + "sample_out.txt");
        } // Склеивание файлов

        {
            System.out.println(searchInFile(dataPath + "sample01.txt", TO_SEARCH));
            System.out.println(searchInFile(dataPath + "sample02.txt", TO_SEARCH));
        } // Поиск по названию

        {
            //создаем пачку файлов с рандомными значениями внутри
            String[] fileName = new String[5];
            for (int i = 0; i < fileName.length; i++) {
                fileName[i] = "file_" + i + ".txt";
                writeFileContents(dataPath + fileName[i], 15, TO_SEARCH);
                System.out.printf("Файл %s создан.\n", fileName[i]);
            }

            List<String> list = searchMatch(dataPath, TO_SEARCH);
            for (String s : list) {
                System.out.printf("Файл %s содержит искомое слово '%s'\n",
                        s.replace(dataPath.getCanonicalPath(), ""), TO_SEARCH);
                // replace заменил путь на "" и оставил только название файла
            }
        } // Поиск файлов в папке, содержащих заданное слово


    }

    private static boolean createMissingDirectory(File file) {
        if (!file.exists()) {
            return file.mkdir();

        }
        return false;
    }

    //region Создание строки

    /**
     * Метод генерирует строку заданной длины, используя символы от 'A' до 'Z'.
     *
     * @param count кол-во символов
     * @return строка
     */
    static String generateSymbols(int count) {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < count; i++) {
            stringBuilder.append((char) random.nextInt(CHAR_BOUND_L, CHAR_BOUND_H + 1));
        }
        return stringBuilder.toString();
    }
    //endregion

    // region Запись в файл

    /**
     * Записать последовательность символов в файл, при этом, случайным образом дописать осознанное слово
     *
     * @param fileName     имя файла
     * @param count        количество символов
     * @param wordToInsert слово для вставки
     * @throws IOException исключение ввода вывода
     */
    static void writeFileContents(String fileName, int count, String wordToInsert) throws IOException {
        try (FileOutputStream fileOutputStream = new FileOutputStream(fileName)) {
            // .getBytes разбивает строку на массив байтов char[], необходимого для FileOutputStream
            fileOutputStream.write(generateSymbols(count).getBytes(StandardCharsets.UTF_8));
            if (random.nextInt(2) == 0) { // 50% шанса, что в файл запишется искомое слово
                fileOutputStream.write(wordToInsert.getBytes(StandardCharsets.UTF_8));
                fileOutputStream.write(generateSymbols(count).getBytes(StandardCharsets.UTF_8));
            }
        }
    }
    //endregion

    //region Склеивание файлов

    /**
     * Метод склеивает содержимое двух файлов в третий файл.
     *
     * @param fileIn1 первый файл
     * @param fileIn2 второй файл
     * @param fileOut склеенный файл
     * @throws IOException исключение ввода вывода
     */
    static void concatenate(String fileIn1, String fileIn2, String fileOut) throws IOException {
        // На запись в файл
        try (FileOutputStream fileOutputStream = new FileOutputStream(fileOut)) {
            int tempFile;
            // Читаем побитово два файла до тех пор пока .read не вернет -1(конец)
            try (FileInputStream fileInputStream = new FileInputStream(fileIn1);
                 FileInputStream fileInputStream2 = new FileInputStream(fileIn2)) {
                while ((tempFile = fileInputStream.read()) != -1) {
                    // пока условие выполняется записываем в новый файл
                    fileOutputStream.write(tempFile);
                }
                while ((tempFile = fileInputStream2.read()) != -1) {
                    fileOutputStream.write(tempFile);
                }
            }
        }
    }
    //endregion

    //region Поиск строки

    /**
     * Поиск строки внутри файла
     *
     * @param fileName   имя файла
     * @param searchWord искомое слово
     * @return true если найден
     * @throws IOException Исключение ввода вывода
     */
    static boolean searchInFile(String fileName, String searchWord) throws IOException {
        byte[] searchData = searchWord.getBytes();
        int charKey; // для хранения кода символа
        int counter = 0; // счетчик совпадения
        try (FileInputStream fileInputStream = new FileInputStream(fileName)) {
            // записываем char, пока не дойдем до конца, если достигнут конец, то read вернет -1
            while ((charKey = fileInputStream.read()) != -1) {
                if (charKey == searchData[counter]) {
                    counter++;// счетчик++ если бит искомого слова searchData совпадает с charKey
                } else {
                    counter = 0; // обнуляем если последовательность оборвана
                    if (charKey == searchData[counter])
                        counter++; // проверка вдруг после обрыва снова идет искомое слово, пример GeekGeekBrains
                }
                if (counter == searchData.length) {
                    return true; // если искомое слово равно нашему счетчику
                }
            }
            return false;
        }
    }
    //endregion

    //region Поиск по названию

    /**
     * Поиск файлов в папке, содержащих заданное слово
     *
     * @param dir              директория файлов
     * @param searchedFileName название файла
     * @return возвращает List<String> с именами файлов
     * @throws IOException исключение ввода вывода
     */
    static List<String> searchMatch(File dir, String searchedFileName) throws IOException {
        List<String> list = new ArrayList<>();
        File[] files = dir.listFiles(); // массив путей файлов
        if (files == null)
            return list;
        for (File file : files) {
            if (file.isFile()) { // проверка является ли обычным файлом
                if (searchInFile(file.getCanonicalPath(), searchedFileName)) // поиск слова в файле
                    list.add(file.getCanonicalPath()); // все ок добавляем в list
            }
        }
        return list;
    }
    // endregion

    //region HW_5

    /**
     * Копирует все файлы из директории в директорию
     *
     * @param sourcePath откуда копируем
     * @param targetPath куда
     * @throws IOException исключение ввода вывода данных
     */
    static void backupAllFile(File sourcePath, File targetPath) throws IOException {
        File[] files = sourcePath.listFiles(); // список путей файлов
        if (files != null) { // Проверка на null
            for (File file : files) {
                // создаем новый путь из пути и имени файла, имитируя копирование
                File out = new File(targetPath, file.getName()); // Внесение корректировки
                try (FileInputStream fileInputStream = new FileInputStream(file);
                     FileOutputStream fileOutputStream = new FileOutputStream(out)) {
                    byte[] buffer = new byte[1024];
                    int length; //количество фактически прочитанных байт
                    while ((length = fileInputStream.read(buffer)) > 0) {
                        // метод записывает в файл только те байты, которые были фактически прочитаны из исходного
                        // файла, чтобы избежать записи ненужных данных в конец целевого файла
                        fileOutputStream.write(buffer, 0, length);
                    }
                }
            }
        } else {
            System.out.printf("Путь %s не является директорией или директория пуста.\n", sourcePath.getAbsoluteFile());
        }
    }
    //endregion
}
