package ru.gb.junior.lesson1_lambda_stream.hw;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import ru.gb.junior.lesson1_lambda_stream.s1.StreamDemo;

import java.time.LocalDate;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

public class Homework {

    private static <T> T getRandom(List<? extends T> items) {
        int randomIndex = ThreadLocalRandom.current().nextInt(0, items.size());
        return items.get(randomIndex);
    }

    public static void main(String[] args) {
        List<String> name = List.of(
                "Nikolai", "Aleksandr", "Stanislav",
                "Yaroslav", "Igor", "Viktor", "Maxim");
        List<String> departName = List.of(
                "Отдел Исследований и Разработок",
                "Отдел Маркетинга и Продаж",
                "Отдел Финансов",
                "Отдел Кадров",
                "Отдел Обслуживания Клиентов"
        );

        List<Department> departments = departName.stream()
                .map(depart -> {
                    Department department = new Department();
                    department.setName(depart);
                    return department;
                }).toList();

        List<Person> personList = new ArrayList<>();

        for (int i = 0; i < 20; i++) {
            Person person = new Person();
            person.setName(getRandom(name));
            person.setAge(ThreadLocalRandom.current().nextInt(20, 40));
            person.setSalary(ThreadLocalRandom.current().nextInt(40_000, 100_000));
            person.setDepart(getRandom(departments));
            personList.add(person);
        }

        personList.forEach(System.out::println);
        System.out.println();
        System.out.println(findMostYoungestPerson(personList));

        System.out.println("\ngroupByDepartment");
        System.out.println(findMostExpensiveDepartment(personList));

        System.out.println("\ngroupByDepartment");
        groupByDepartment(personList).forEach((department, persons) -> {
            System.out.println(department.getName());
            persons.forEach(person -> System.out.println("  Сотрудник: " + person.getName()));
        });

        System.out.println("\ngroupByDepartmentName");
        groupByDepartmentName(personList).forEach((department, persons) -> {
            System.out.println(department);
            persons.forEach(person -> System.out.println("  Сотрудник: " + person.getName()));
        });

        System.out.println("\ngetDepartmentOldestPerson");
        getDepartmentOldestPerson(personList).forEach((department, persons) -> {
            System.out.println(department + " " + persons.getName() + " " + persons.getAge());
        });

        System.out.println("\ncheapPersonsInDepartment");
        cheapPersonsInDepartment(personList)
                .forEach(p -> System.out.println(
                        p.getDepart().getName()
                        + " " + p.getName()
                        + " " + p.getSalary()));
    }

    @Setter
    @Getter
    @ToString
    private static class Department {
        private String name;
    }

    @Setter
    @Getter
    @ToString
    private static class Person {
        private String name;
        private int age;
        private double salary;
        private Department depart;
    }

    /**
     * Найти самого молодого сотрудника
     */
    // TODO комменты для будущего себя оставляю надеюсь ничего страшного)
    static Optional<Person> findMostYoungestPerson(List<Person> people) {
        // Сначала написал так (o1, o2) -> o1.age - o2.age
        // предложил переписать. Не особо понял метод под капотом... но работает одинаково
        return people.stream()
                .min(Comparator.comparingInt(Person::getAge));
    }

    /**
     * Найти департамент, в котором работает сотрудник с самой большой зарплатой
     */
    static Optional<Department> findMostExpensiveDepartment(List<Person> people) {
        // Короче map здесь, для того чтобы получить доступ к полям объекта optional
        // как я понял optional для некого дебага нужен, мало ли не те параметры на вход придут например
        // пустой список и тд
        return people.stream()
                .max(Comparator.comparingDouble(Person::getSalary))
                .map(person -> {
                    System.out.println("Person with max salary: " + person.getSalary());
                    return person.getDepart();
                });
    }

    /**
     * Сгруппировать сотрудников по департаментам
     */
    static Map<Department, List<Person>> groupByDepartment(List<Person> people) {
        return people.stream()
                .collect(Collectors.groupingBy(Person::getDepart));
    }


    /**
     * Сгруппировать сотрудников по названиям департаментов
     */
    static Map<String, List<Person>> groupByDepartmentName(List<Person> people) {
        return people.stream()
                .collect(Collectors.groupingBy(p -> p.getDepart().getName()));
    }

    /**
     * В каждом департаменте найти самого старшего сотрудника
     */
    static Map<String, Person> getDepartmentOldestPerson(List<Person> people) {
        return people.stream()
                .collect(Collectors.toMap(
                        d -> d.getDepart().getName(),   // ключ - название отдела
                        p -> p,                         // значение - человек
                        // doc! mergeFunction – функция слияния, используемая для разрешения
                        // конфликтов между значениями, связанными с одним и тем же ключом
                        // сравниваем по возрастам
                        (p1, p2) -> p1.getAge() > p2.getAge() ? p1 : p2
                ));
    }


    /**
     * *Найти сотрудников с минимальными зарплатами в своем отделе (прим. Можно реализовать в два запроса)
     */
    static List<Person> cheapPersonsInDepartment(List<Person> people) {
        return people.stream()
                .collect(Collectors.groupingBy(
                        Person::getDepart,  // Ключ - отдел
                        // Для каждого отдела мы выбираем человека с минимальной зарплатой
                        Collectors.minBy(Comparator.comparingDouble(Person::getSalary))
                ))

                // Теперь нужно конвертировать в List, но проблема в том что values у нас optional
                .values().stream() // Получаем значения из Map и делаем из него новый поток
                .filter(Optional::isPresent)// Фильтруем, то есть убираем все, где значение Optional может быть isEmpty
                .map(Optional::get)// берем содержимое optional если оно существует, а оно существует мы отфильтровали
                .collect(Collectors.toList()); // Собираем их в список
    }

}
