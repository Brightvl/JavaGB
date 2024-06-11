package ru.gb.junior.lesson1_lambda_stream.l1;


import java.util.*;
import java.util.stream.Collectors;

public class StreamObject {
    /**
     * В методе main инициализируется список personIDS и генерируется случайная зарплата.
     *
     * @param args
     */
    public static void main(String[] args) {
        List<PersonID> personIDS = Arrays.asList(
                new PersonID("Иванов И.И.", new Date(1992, Calendar.FEBRUARY, 7), genSalary()),
                new PersonID("Петров П.В.", new Date(1987, Calendar.APRIL, 27), genSalary()),
                new PersonID("Селиванов В.А.", new Date(1995, Calendar.AUGUST, 15), genSalary()),
                new PersonID("Кладовцева Я.И.", new Date(1996, Calendar.JUNE, 28), genSalary()),
                new PersonID("Стильнов В.М.", new Date(1981, Calendar.SEPTEMBER, 18), genSalary()),
                new PersonID("Иванова С.В.", new Date(1991, Calendar.FEBRUARY, 17), genSalary()),
                new PersonID("Одоевцева М.В.", new Date(2001, Calendar.JANUARY, 6), genSalary()),
                new PersonID("Кузеванов А.И.", new Date(2003, Calendar.JUNE, 14), genSalary()),
                new PersonID("Донцев Ю.Ф.", new Date(1991, Calendar.MAY, 22), genSalary()),
                new PersonID("Кривцова А.И.", new Date(1976, Calendar.DECEMBER, 4), genSalary()),
                new PersonID("Бронникова И.И.", new Date(1999, Calendar.OCTOBER, 19), genSalary()),
                new PersonID("Остафьев И.А.", new Date(1995, Calendar.FEBRUARY, 24), genSalary())
        );


        /*
         * В этом примере я добавил только фильтр. Я выбираю из потока только тех, кто
         * родился после 1 января 1995 года. Из исходного списка в 12 человек были выбраны
         * только 6. Можно усложнить задачу, добавив еще один фильтр
         */
        List<String> tmpList = personIDS.stream()
                .filter(n -> n.getDATE()
                        .compareTo(new Date(1995, Calendar.JANUARY, 1)) > 0)
                .map(PersonID::getDOB).collect(Collectors.toList());
        System.out.println(tmpList);


        /*
        К фильтру и сортировке добавился еще один метод - map. Теперь мы собираем
        имена сотрудников и сортируем их по уровню зарплаты. Результат - список
        сотрудников с именами, родившимися после 1995 года, отсортированными по
        зарплате.
         */
        List<String> tmpList1 = personIDS.stream()
                .filter(n -> n.getDATE().compareTo(new Date(1995, Calendar.JANUARY, 1)) > 0)
                .sorted((a, b) -> (int) (a.getSalary() - b.getSalary()))
                .map(PersonID::getFIO).collect(Collectors.toList());

        /*
            На этот раз я внес небольшие изменения. В методе map я добавил метод limit(5),
            который ограничивает список первыми пятью элементами. Мы берем список
            объектов, отбираем только те, которые соответствуют определенному условию,
            затем сортируем полученный список по одному из полей объекта и, наконец,
            создаем новый список с описаниями объектов и также ограничиваем его первыми
            пятью элементами.
         */
        List<String> tmpList3 = personIDS.stream()
                .filter(n -> n.getDATE().compareTo(new Date(1995, Calendar.JANUARY, 1)) > 0)
                .sorted((a, b) -> (int) (a.getSalary() - b.getSalary()))
                .map(n -> n.getFIO() + " (" + n.getDOB() + ") " + n.getSalary())
                .limit(5)
                .collect(Collectors.toList());
        tmpList.forEach(n -> System.out.println(n));

    }

    private static int genSalary() {
        return new Random().nextInt(63758) + 16242;
    }
}

enum Months {
    Января, Февраля, Марта, Апреля, Мая, Июня, Июля, Августа,
    Сентября, Октября, Ноября, Декабря
}

/**
 * Класс, описывающий сотрудника с закрытыми полями: id, ФИО, датой рождения, зарплатой и геттерами.
 */
class PersonID {
    private final int ID;
    private final String FIO;
    private final Date DOB;
    private float salary;

    PersonID(String fio, Date dob, float salary) {
        FIO = fio;
        DOB = dob;
        ID = new Random().nextInt();
        this.salary = salary;
    }

    public int getID() {
        return ID;
    }

    public String getFIO() {
        return FIO;
    }

    public String getDOB() {
        return "" + DOB.getDate() + " " +
                Months.values()[DOB.getMonth()] + " " + DOB.getYear();
    }

    public float getSalary() {
        return salary;
    }

    public void setSalary(float salary) {
        this.salary = salary;
    }

    public Comparable<Date> getDATE() {
        return DOB;
    }
}


