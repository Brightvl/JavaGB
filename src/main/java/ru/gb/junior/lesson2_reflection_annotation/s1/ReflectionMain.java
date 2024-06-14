package ru.gb.junior.lesson2_reflection_annotation.s1;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;

public class ReflectionMain {

    public static void main(String[] args) throws Exception {
        // Reflection (рефлексия) -> Способность языка в Runtime(выполнения) узнавать структуру самого себя

        /*    Создали объекты по 2-м конструкторам     */
        Person unnamed = new Person();
        Person person = new Person("personName");
        System.out.println(unnamed);    // out: Person{name='unnamed', age=0}
        System.out.println(person);     // out: Person{name='personName', age=0}

        /* Class<>
        Сначала нужно получить объект Class, который будет описывать класс Person.
        В переменной personClass и aClass теперь лежит вся информация из чего состоит класс (в стеке)
         */
        Class<? extends Person> aClass = unnamed.getClass();    // 1 способ: через уже созданный объект класса
        Class<Person> personClass = Person.class;               // 2 способ: Через имя класса

        // Constructor<>
        {
        /*
        Создаем объект класс Person.
        Получаем конкретный конструктор через передаваемый тип .getConstructor()
        Если бы мы хотели использовать конструктор с String то вызывали бы так .getConstructor(String.class)
        Для такого класса с параметрами было бы так: .newInstance("аргумент")
         */
            Constructor<Person> constructor = personClass.getConstructor(); // Получили конструктор без параметров
            Person viaReflectPerson = constructor.newInstance();            // Создали объект
            System.out.println(viaReflectPerson);                           // out: Person{name='unnamed', age=0}

            {
            /*
            Перебор всех конструкторов.
            Declared - чтобы выводились только конструкторы самого класса без его родительских например класса Object
             */
                Constructor<?>[] declaredConstructors = personClass.getDeclaredConstructors();
                for (Constructor<?> declaredConstructor : declaredConstructors) {
                    for (Class<?> parameterType : declaredConstructor.getParameterTypes()) {
                        System.out.println(parameterType);
                    }
                    System.out.println("---");
            /*
            output:

            ---
            class java.lang.String
            ---
            У нас два конструктора, но выводится один, поскольку один без параметров в консоли пустое значение
             */
                }
            }
        }

        // Method<>
        {
        /*
        Работа с методами
        */
            System.out.println(unnamed.getName());   // Без рефлексии

            // Достаем метод getName через рефлексию invoke(вызвать)
            Method getName = Person.class.getMethod("getName");
            System.out.println(getName.invoke(unnamed));    // out: unnamed      Person unnamed = new Person();
            System.out.println(getName.invoke(person));     // out: personName   Person person = new Person("personName");

            // Достаем метод setAge через рефлексию
            Method setAge = Person.class.getMethod("setAge", int.class);
            setAge.invoke(unnamed, 10);       // unnamed.setAge(10);
            System.out.println(unnamed);            // out: Person{name='unnamed', age=10}

        /*
        Статические методы
         */
            // без рефлексии
            System.out.println("Counter = " + Person.getCounter()); // out: Counter = 3

            // С рефлексией
            Method getCounter = Person.class.getMethod("getCounter");
            System.out.println("Counter (via reflect) = " + getCounter.invoke(null)); //out: Counter (via reflect) = 3
        /*
        Поскольку мы достаем параметр из класса, а не из объекта
        в объявление можно передать что угодно, но обычно null
            getCounter.invoke(null)

        (*** задача)
            Person nullPerson = null; // Нет объекта
            System.out.println(nullPerson.getCounter());
        Тоже отработает потому что метод статический можно просто вызвать так:
            System.out.println(Person.getCounter());
         */
        }

        // Field<>
        {
            Field name = Person.class.getDeclaredField("name");    // get
            System.out.println("Name field = " + name.get(unnamed));    // out: unnamed
        /*
           Закомментировано потому что name это final
           менять не получится, но idea не ругается а исключение при запуске будет
           менять final пола все таки можно, но немного иначе
         */
            // name.set(unnamed, "new name");                              // set
            // System.out.println("Name field = " + name.get(unnamed));    // out: new name
        }

        // Declared
        {
        /*
        Различия Declared и обычных методов том что declared достает только задекларированные параметры класса
        не учитывая наследование

        Мы создали класс ExtPerson extends Person
        У класса Person есть метод getName, а у ExtPerson его по сути нет, несмотря на наследование
            ExtPerson.class.getDeclaredMethod("getName");
        программа упадет так как в нем по сути не будет такого метода, этот метод лежит в Person
        Есть два решения, использовать метод
            ExtPerson.class.getMethod("getName"); чтобы достать из Person
        либо переопределить метод getName() (задекларировать) в ExtPerson, тогда
        ExtPerson.class.getDeclaredMethod("getName"); сработает без проблем
        */
            Method declaredConstructor = ExtPerson.class.getDeclaredMethod("getName");
        }

        // Superclass
        {
            /*
                Тут мы по сути с помощью наследованного класса достали класс родитель вывод будет одинаковый
             */
            Class<Person> personClass1 = Person.class;
            Class<? super ExtPerson> superclass = ExtPerson.class.getSuperclass();

            System.out.println(personClass1);   // class ru.gb.junior.lesson2_reflection.s1.ReflectionMain$Person
            System.out.println(superclass);     // class ru.gb.junior.lesson2_reflection.s1.ReflectionMain$Person
        }

        // Interfaces
        {
            Class<?>[] interfaces = ArrayList.class.getInterfaces();
            Arrays.stream(interfaces).
                    forEach(System.out::println);
        }
    }

    /*
    Класс для объяснения Decelerated выше
     */
    private static class ExtPerson extends Person {

        @Override
        public String getName() {
            return super.getName();
        }
    }

    /*
    Создали 2 конструктора
     */
    private static class Person {

        private static long counter = 0L;

        private final String name;
        private int age;

        public Person() {
            this("unnamed");
        }

        public Person(String name) {
            this.name = name;
            counter++;
        }

        public static long getCounter() {
            return counter;
        }

        public String getName() {
            return name;
        }

        public int getAge() {
            return age;
        }

        public void setAge(int age) {
            this.age = age;
        }

        @Override
        public String toString() {
            return "Person{" +
                    "name='" + name + '\'' +
                    ", age=" + age +
                    '}';
        }
    }

}
