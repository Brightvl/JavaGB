package ru.gb.junior.lesson2_reflection.l1;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;

public class Main {
    public static void main(String[] args) throws ClassNotFoundException, InvocationTargetException, InstantiationException, IllegalAccessException {// throws! Class.forName() может не найти класс

        // ПРОБЛЕМА
        // Car car = new Car(); // ошибка, нет конструктора по умолчанию, объект не создать
        // System.out.println(car);

        // ПОИСК
        Class<?> car = Class.forName("ru.gb.junior.lesson2_reflection.l1.Car"); // ищем клас Car

        // КОНСТРУКТОРЫ
        Constructor<?>[] constructors = car.getConstructors();// [] - потому что конструкторов может быть несколько, поэтому массив
        System.out.println(Arrays.toString(constructors)); // [public ru.gb.junior.lesson2_reflection.l1.Car(java.lang.String)]

        // ЭКЗЕМПЛЯР
        Object gaz = constructors[0].newInstance("ГАЗ"); // создаем экземпляр класса car используя конструктор
        System.out.println(gaz); // Car{name='ГАЗ'}

        // ПОЛЯ
        Field[] fields = gaz.getClass().getFields(); // получаем массив всех полей класса
        int tmp = fields[fields.length - 1].getInt(gaz); // последнее поле в классе
        fields[fields.length - 1].setInt(gaz, tmp * 2); // устанавливаем новое значение у объекта gaz
        System.out.println(gaz);
        /*
        Car{maxSpeed=100, name='ГАЗ'}
        Car{maxSpeed=200, name='ГАЗ'}
         */

        // МЕТОДЫ
        // Method[] methods = gaz.getClass().getMethods();      // выведет вообще все методы включая объекта object
        Method[] methods = gaz.getClass().getDeclaredMethods(); // поэтому делаем так, чтобы только методы gaz
        /*
        public int ru.gb.junior.lesson2_reflection.l1.Car.getMaxSpeed()
        public void ru.gb.junior.lesson2_reflection.l1.Car.setMaxSpeed(int)
        public java.lang.String ru.gb.junior.lesson2_reflection.l1.Car.toString()
         */
        for (Method method : methods) {
            System.out.println(method);
        }
    }
}

class Car {
    public String name;
    private String price;
    public String engType;
    public String engPower;
    public int maxSpeed;

    public Car(String name) {
        this.name = name;
        this.price = "1000000";
        this.engType = "V8";
        this.engPower = "123";
        this.maxSpeed = 100;

    }

    @Override
    public String toString() {
        return "Car{" +
                "maxSpeed=" + maxSpeed +
                ", name='" + name + '\'' +
                '}';
    }

    public int getMaxSpeed() {
        return maxSpeed;
    }

    public void setMaxSpeed(int maxSpeed) {
        this.maxSpeed = maxSpeed;
    }
}