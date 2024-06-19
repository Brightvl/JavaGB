package ru.gb.junior.lesson2_reflection_annotation.s1.anno;


import ru.gb.junior.lesson2_reflection_annotation.s1.anno.lib.ObjectCreator;
import ru.gb.junior.lesson2_reflection_annotation.s1.anno.lib.Random;

public class AnnotationsMain {

  public static void main(String[] args) {

    /*
    Аналогия препода:
    Аннотации это стикеры.
    Фабричный метод это охранник который на всех сотрудников повесил стикеры (как на поля аннотации).
    А логика в классе AnnotationProcessor это по сути КПП которое решает что согласно этому стикеру (пропуск)
    нужно делать с сотрудником
     */

    // Создаем объект Person с использованием фабрики ObjectCreator
    Person rndPerson = ObjectCreator.createObj(Person.class);
    // Выводим значения полей age1 и age2
    System.out.println("age1 = " + rndPerson.age1);
    System.out.println("age2 = " + rndPerson.age2);

    // Примеры использования метода isAssignableFrom для проверки наследования классов
    Person p = new Person();
    Person ep = new ExtPerson();

    System.out.println(p.getClass().isAssignableFrom(Person.class)); // true
    System.out.println(p.getClass().isAssignableFrom(ExtPerson.class)); // true

    System.out.println(ep.getClass().isAssignableFrom(Person.class)); // false
    System.out.println(ep.getClass().isAssignableFrom(ExtPerson.class)); // true

  }

  public static class ExtPerson extends Person {

  }

  public static class Person {

    @Random // Аннотированное поле, значение будет в диапазоне [0, 100)
    private int age1;

    @Random(min = 50, max = 51) // Аннотированное поле, значение будет 50
    private int age2;

    @Random
    private String age3;

  }

}
