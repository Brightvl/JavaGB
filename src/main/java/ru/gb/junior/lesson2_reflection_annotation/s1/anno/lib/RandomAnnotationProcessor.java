package ru.gb.junior.lesson2_reflection_annotation.s1.anno.lib;

import java.lang.reflect.Field;

/**
 * Класс для реализации аннотации @Random
 */
public class RandomAnnotationProcessor {

  public static void processAnnotation(Object obj) {
    // найти все поля класса, над которыми стоит аннотация @Random
    // вставить туда рандомное число в диапазоне [0, 100)

    // Сначала надо достать аннотацию
    java.util.Random random = new java.util.Random();
    // далее берем класс
    Class<?> objClass = obj.getClass();

    for (Field field : objClass.getDeclaredFields()) {
      // obj instanceOf Person тоже самое что
      // AnnotationsMain.Person.class.isInstance(obj) тоже самое что
      // obj.getClass().isAssignableFrom(AnnotationsMain.Person.class)

      // isAnnotationPresent если аннотация есть и .isAssignableFrom тип поля int
      if (field.isAnnotationPresent(Random.class) && field.getType().isAssignableFrom(int.class)) {
        Random annotation = field.getAnnotation(Random.class); // берем аннотацию
        int min = annotation.min();
        int max = annotation.max();

        try {
          field.setAccessible(true); // чтобы можно было изменять final поля
          field.set(obj, random.nextInt(min, max));
        } catch (IllegalAccessException e) {
          System.err.println("Не удалось вставить значение в поле: " + e.getMessage());
        }
      }
    }

  }

}
