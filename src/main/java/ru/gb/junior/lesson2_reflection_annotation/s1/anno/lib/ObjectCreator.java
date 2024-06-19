package ru.gb.junior.lesson2_reflection_annotation.s1.anno.lib;

import java.lang.reflect.Constructor;

/**
 * Класс для создания объектов и обработки аннотаций
 */
public class ObjectCreator {

    public static <T> T createObj(Class<T> tClass) {
        try {
            // Получаем конструктор класса
            Constructor<T> constructor = tClass.getDeclaredConstructor();

            // Создаем экземпляр объекта
            T obj = constructor.newInstance();

            // Обрабатываем аннотации объекта
            RandomAnnotationProcessor.processAnnotation(obj);
            return obj;
        } catch (Exception e) {
            System.err.println("ниче не получилось: " + e.getMessage());
            return null; // throw new IllegalStateException
        }
    }

    /**
     * Доработынный класс в ДЗ добавленные обработки для Date Instance LocalDate
     * @param tClass
     * @return
     * @param <T>
     */
    public static <T> T createObjHW(Class<T> tClass) {
        try {

            Constructor<T> constructor = tClass.getDeclaredConstructor();

            T obj = constructor.newInstance();

            RandomAnnotationProcessor.processAnnotationHW(obj);
            return obj;
        } catch (Exception e) {
            System.err.println("ниче не получилось: " + e.getMessage());
            return null; // throw new IllegalStateException
        }
    }

}
