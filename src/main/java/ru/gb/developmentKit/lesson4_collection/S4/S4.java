package ru.gb.developmentKit.lesson4_collection.S4;

import java.util.*;
import java.util.stream.Collectors;

public class S4 {
    public static List<String> generateList() {
        List<String> names = new ArrayList<>();
        names.add("Василий");
        names.add("Мария");
        names.add("Екатерина");
        names.add("Александр");
        names.add("Михаил");
        names.add("Иван");
        return names;
    }
}

class Task1 {

    /*
    В рамках выполнения задачи необходимо:
    1. Создайте коллекцию мужских и женских имен с помощью интерфейса List
    2. Отсортируйте коллекцию в алфавитном порядке
    3. Отсортируйте коллекцию по количеству букв в слове
    4. Разверните коллекцию
     */
    public static void main(String[] args) {
        List<String> names = S4.generateList();
        System.out.println(names);
        System.out.println(sortByAlphabet(names));
        System.out.println(sortByLengthLine(names));
        System.out.println(reverseList(names));
    }

    /**
     * Отсортируйте коллекцию в алфавитном порядке
     *
     * @param names имена
     * @return list
     */
    private static List<String> sortByAlphabet(List<String> names) {
//        List<String> result = new ArrayList<>(names);
//        result.sort(null);
//        return result;
        return names.stream().sorted().toList();
    }

    /**
     * Отсортируйте коллекцию по количеству букв в слове
     *
     * @param names имена
     * @return list
     */
    private static List<String> sortByLengthLine(List<String> names) {
//        List<String> result = new ArrayList<>(names);
//        result.sort((o1, o2) -> o1.length() - o2.length());
//        return result;
        return names.stream().sorted((o1, o2) -> o1.length() - o2.length()).toList();
    }

    /**
     * Разверните коллекцию
     *
     * @param names имена
     * @return list
     */
    private static List<String> reverseList(List<String> names) {
        //stream поток байт всегда нужно возвращать в нужное из стрима .toList()
        return names.stream().sorted(Comparator.reverseOrder()).toList();
    }
}


/**
 * В рамках выполнения задачи необходимо: Создайте коллекцию мужских и женских имен с помощью интерфейса List - добавьте
 * повторяющиеся значения. Получите уникальный список Set на основании List. Определите наименьший элемент (алфавитный
 * порядок) Определите наибольший элемент (по количеству букв в слове, но в обратном порядке) Удалите все элементы
 * содержащие букву ‘A’
 */
class Task2 {

    public static void main(String[] args) {
        List<String> names = S4.generateList();
        System.out.println(names);

        Set<String> uniqNames = getUniq(names);
        System.out.println(uniqNames);

        System.out.println(getMin(uniqNames));
        System.out.println(getMax(uniqNames));
        System.out.println(uniqNames);
        System.out.println(deleteSomeLoop(uniqNames));
        // System.out.println(deleteSomeFilter(uniqNames));
        // deleteSome(uniqNames);
        // System.out.println(uniqNames);
    }

    public static Set<String> getUniq(List<String> list) {
        return new HashSet<>(list);
    }

    public static String getMin(Set<String> names) {
        return names.stream().max(Comparator.naturalOrder()).orElse(null);
    }

    public static String getMax(Set<String> names) {
        return names.stream().min(Comparator.comparing(String::length)).orElse(null);
    }

    public static void deleteSome(Set<String> names) {
        names.removeIf(x -> x.contains("А") || x.contains("а"));
    }

    public static Set<String> deleteSomeFilter(Set<String> names) {
        return names.stream().filter(x -> !x.contains("А") && !x.contains("а")).collect(Collectors.toSet());
    }

    public static Set<String> deleteSomeLoop(Set<String> names) {
        Set<String> result = new HashSet<>();
        for (String s : names) {
            if (!s.contains("А") && !s.contains("а")) result.add(s);
        }
        return result;
    }
}

/**
 * В рамках выполнения задачи необходимо: Создайте телефонный справочник с помощью Map - телефон это ключ, а имя
 * значение. Найдите человека с самым маленьким номером телефона. Найдите номер телефона человека чье имя самое большое
 * в алфавитном порядке
 */
class Task3 {
    public static void main(String[] args) {
        HashMap<String, String> contacts = new HashMap<>();
        contacts.put("893754654656", "Ilon mask");
        contacts.put("893754546644", "Tom Hanks");
        contacts.put("893721254544", "Alizee");
        contacts.put("892715555555", "Arnold Schwartz");
        contacts.put("292715555555", "No Schwartz");

        System.out.println(getSmallNumber(contacts));
        System.out.println(getSmallNumberLoop(contacts));
        System.out.println(getBiggestNameLoop(contacts));
        System.out.println(getBiggestName(contacts));
    }

    public static String getSmallNumber(HashMap<String, String> contacts) {
        return contacts
                .entrySet()
                .stream()
                .min(Comparator.comparingLong(o -> Long.parseLong(o.getKey())))
                .get()
                .getValue();
    }

    public static String getSmallNumberLoop(HashMap<String, String> contacts) {
        String smallestNum = "";
        for (var number : contacts.keySet()) {
            if (smallestNum.isEmpty()) {
                smallestNum = number;
            }
            if (Long.parseLong(smallestNum) > Long.parseLong(number)) smallestNum = number;
        }
        return contacts.get(smallestNum);

    }

    public static String getBiggestNameLoop(HashMap<String, String> contacts) {
        String biggestName = "";
        String resultName = "";
        for (var entry : contacts.entrySet()) {
            if (biggestName.isEmpty()) {
                biggestName = entry.getValue();
                resultName = entry.getKey();
            }
            if (biggestName.compareTo(entry.getValue()) < 0) {
                biggestName = entry.getValue();
                resultName = entry.getKey();
            }
        }
        return resultName;
    }

    public static String getBiggestName(HashMap<String, String> contacts) {
        return contacts.entrySet().stream().max((o1, o2) -> o1.getValue().compareTo(o2.getValue())).get().getKey();
    }
}
