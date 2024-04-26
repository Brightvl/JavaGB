package ru.gb.core.Lesson4.s4.task4;

public class Item {
    String name;
    double price;


    public Item(String name, double price) {
        this.name = name;
        this.price = price;
    }

    @Override
    public String toString() {
        return "[" +
                "Наименование: '" + name + '\'' +
                ", Цена: " + price +
                ']';
    }
}