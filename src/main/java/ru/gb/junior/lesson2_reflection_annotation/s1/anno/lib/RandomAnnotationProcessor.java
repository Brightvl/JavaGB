package ru.gb.junior.lesson2_reflection_annotation.s1.anno.lib;

import ru.gb.junior.lesson2_reflection_annotation.hw.RandomDate;

import java.lang.reflect.Field;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
/*
   найти все поля класса, над которыми стоит аннотация @Random
   вставить туда рандомное число в диапазоне [0, 100)
 */

/**
 * Класс для обработки аннотации @Random
 */
public class RandomAnnotationProcessor {

    /**
     * Пример разобран на семинаре. Случайно сгенерированное число int
     *
     * @param obj
     */
    public static void processAnnotation(Object obj) {
        // Создаем экземпляр генератора случайных чисел.
        // Путь к классу Random (java.util.Random), чтобы избежать путаницы с нашей аннотацией @Random.
        java.util.Random random = new java.util.Random();
        // Получаем класс объекта
        Class<?> objClass = obj.getClass();

        // Проходим по всем полям класса
        for (Field field : objClass.getDeclaredFields()) {
            // obj instanceOf Person тоже самое что
            // AnnotationsMain.Person.class.isInstance(obj) тоже самое что
            // obj.getClass().isAssignableFrom(AnnotationsMain.Person.class)

            // .isAnnotationPresent есть ли на поле аннотация @Random и .isAssignableFrom является ли поле типом int
            if (field.isAnnotationPresent(Random.class) && field.getType().isAssignableFrom(int.class)) {
                Random annotation = field.getAnnotation(Random.class); // Получаем аннотацию
                int min = annotation.min();
                int max = annotation.max();

                try {
                    field.setAccessible(true); // чтобы можно было изменять final поля
                    field.set(obj, random.nextInt(min, max)); // Устанавливаем случайное значение в поле
                } catch (IllegalAccessException e) {
                    System.err.println("Не удалось вставить значение в поле: " + e.getMessage());
                }
            }
        }
    }


    /**
     * ДЗ добавить к @Random генерацию Date Instance LocalDate LocalDateTime
     *
     * @param obj
     */
    public static void processAnnotationHW(Object obj) {
        Class<?> objClass = obj.getClass();

        for (Field field : objClass.getDeclaredFields()) {
            // Обработка аннотации @Random для полей типа int
            if (field.isAnnotationPresent(Random.class)) {
                processRandomAnnotation(obj, field);
            }
            // Обработка аннотации @Random для полей типа date
            if (field.isAnnotationPresent(RandomDate.class)) {
                processRandomDateAnnotation(obj, field);
            }
        }
    }

    private static void processRandomAnnotation(Object obj, Field field) {
        java.util.Random random = new java.util.Random();
        Random annotation = field.getAnnotation(Random.class);
        int min = annotation.min();
        int max = annotation.max();
        try {
            field.setAccessible(true);
            field.set(obj, random.nextInt(max - min) + min);
        } catch (IllegalAccessException e) {
            System.err.println("Не удалось вставить значение в поле: " + e.getMessage());
        }
    }

    private static void processRandomDateAnnotation(Object obj, Field field) {
        java.util.Random random = new java.util.Random();
        RandomDate annotation = field.getAnnotation(RandomDate.class);
        long min = annotation.min();
        long max = annotation.max();

        // Часовой пояс. Используется для преобразования между различными временными зонами.
        ZoneId zoneId = ZoneId.of(annotation.zone());

        if (min > max) {
            throw new IllegalArgumentException("min должно быть меньше max");
        }
        try {
            field.setAccessible(true);
            long randomTime = min + (long) (random.nextDouble() * (max - min));
            Instant randomInstant = Instant.ofEpochMilli(randomTime); // проще всего работать через этот класс

                // Обработка аннотации @RandomDate для полей типа Date хранит время как количество миллисекунд,
                // прошедших с 1 января 1970 года (Unix) временя, интерпретированного по текущей временной зоне JVM
                // не поддергивает явного выставления временной зоны
                // Поэтому вывод примерно такой Wed Jan 01 03:00:00 MSK 2025 а не 00:00:00
            if (field.getType().isAssignableFrom(Date.class)) {
                field.set(obj, Date.from(randomInstant));
                // для полей типа Instant представляет момент времени с точностью
                // до наносекунды, не поддерживает часовые пояса, дает только время UTC
            } else if (field.getType().isAssignableFrom(Instant.class)) {
                field.set(obj, randomInstant);
                // Для полей типа LocalDate содержит дату (год, месяц, день).
                // Так же можно управлять часовой зоной
            } else if (field.getType().isAssignableFrom(LocalDate.class)) {
                field.set(obj, randomInstant.atZone(zoneId).toLocalDate());
                // Обработка аннотации @RandomDate для полей типа LocalDateTime представляет локальную дату и время
                // без часового пояса.
            } else if (field.getType().isAssignableFrom(LocalDateTime.class)) {
                field.set(obj, randomInstant.atZone(zoneId).toLocalDateTime());
            }
        } catch (IllegalAccessException e) {
            System.err.println("Не удалось вставить значение в поле: " + e.getMessage());
        }

    }
}


