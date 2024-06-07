package ru.gb.junior.lesson1_stream.l1;

import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Stream API «поток» нужен для удобной работы с коллекциями.
 */
public class Stream {


    public static void main(String[] args) {
        List<String> myList = Arrays.asList("Привет", "мир", "!", "Я", "родился", "!");

        // Методы Stream API делятся на три типа: генераторы, фильтры и коллекционеры


        // Фильтр:
        // .filter(s -> s.length()>4) - отфильтруй все что больше 4х
        // .forEach(System.out::println) - пробегись по коллекции и выведи все в консоль
        myList.stream()
                .filter(s -> s.length() > 4)
                .forEach(System.out::println);

        // Конвейерный метод skip, который позволяет пропустить заданное количество первых элементов потока
        myList.stream()
                .skip(myList.size() / 2)
                .forEach(n -> System.out.print(n + " "));

        // Конвейерный метод limit. Это skip наоборот! Ограничивает обработку указанным
        // количеством первых элементов
        myList.stream()
                .limit(myList.size() / 2)
                .filter(n -> n.length() > 4)
                .filter(n -> n.toLowerCase().contains("а"))
                .forEach(n -> System.out.print(n + " "));

        // Distinct, Убирает дубликаты
        List<String> list = Arrays.asList("а", "б", "а", "в", "а", "г", "а", "д");
        list.stream()
                .distinct()
                .forEach(n -> System.out.print(n));

        // sorted - сортировка
        myList.stream().sorted().forEach(System.out::println);
        // или так через компаратор (который мы можем сами создавать)
        myList.stream()
                .sorted((s, t1) -> t1.length() - s.length())
                .forEach(System.out::println);
        // или многострочный компаратор
        myList.stream()
                .sorted((s, t1) -> {
                    int tmp = t1.length() - s.length();
                    if (tmp < 0) return 1;
                    else if (tmp > 0) return -1;
                    return 0;
                })
                .forEach(System.out::println);

        //.map - работает с каждым элементом
        // метод проходит по всем элементам и возвращает в поток данные изменённые по логике
        // его лямбда выражения. Он, в соответствии с концепцией non-interfering не изменяет
        //исходные данные, только данные потока
        // выводим длину всех строк в консоль
        myList.stream().map(n -> n.length()).forEach(System.out::println);

        // .findFirst - первое попавшееся в потоке слово удовлетворяющее условию
        System.out.println(myList.stream().filter(s -> s.length() > 4).findFirst());

        // .limit() - ограниченное количество
        myList.stream()
                .filter(s -> s.length() > 4)
                .limit(1)
                .forEach(n -> System.out.print(n));

        // .findAny() возвращает один результат из потока, но не обязательно первый в списке,
        // а первый обработанный.
        System.out.println(myList.stream().filter(s -> s.length() > 4).findAny());

        // Терминальные методы findAny и findFirst возвращают результатом экземпляр
        // класса Optional, поэтому и в консоли мы видим "Optional[Привет]"
        // В основном, мы хотим увидеть массивы или списки.
        // .collect - С его помощью мы можем представить данные в виде нужных нам структур данных
        List<String> tmpList = myList.stream()
                .sorted((s, t1) -> {
                    int tmp = t1.length() - s.length();
                    if (tmp<0) return 1;
                    else if (tmp>0) return -1;
                    return 0;
                })
                .collect(Collectors.toList());




        // Для лямбда-выражений существуют два ограничения.
        // Во-первых, они должны быть невмешивающимися (non-interfering), т. е. не менять исходных данных.
        // Во-вторых, они не должны * запоминать состояние (stateless), т.е. не зависеть от порядка выполнения,
        // от внешних переменных и от всего внешнего пространства в целом!

        // Пример:
        // Arrays.asList(1, 2, 3).stream();
        // Метод asList теперь сам умеет создавать потоки данных!
        // Stream.of(3, 2, 1);

    }

}
