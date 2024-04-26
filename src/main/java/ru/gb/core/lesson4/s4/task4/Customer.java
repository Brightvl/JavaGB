package ru.gb.core.lesson4.s4.task4;

public class Customer {
    String name;
    int age;
    String phone;

    public Customer(String name, int age, String phone) {
        this.name = name;
        this.age = age;
        this.phone = phone;
    }

    @Override
    public String toString() {
        return "[" +
                "Имя: '" + name + '\'' +
                ", Возраст: " + age +
                ", Телефон: '" + phone + '\'' +
                ']';
    }
}
