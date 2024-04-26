package ru.gb.core.lesson4.s4.task4;

public class Order {

    /**
     * Покупатель
     */
    Customer customer;

    /**
     * Товар
     */
    Item item;

    /**
     * Кол-во товаров
     */
    int count;

    public Order(Customer customer, Item item, int count) {
        this.customer = customer;
        this.item = item;
        this.count = count;
    }

    @Override
    public String toString() {
        return "Заказ{\n" +
                "\tПокупатель: " + customer +
                "\n\tТовар: " + item +
                "\n\tКол-во: " + count +
                "\n}";
    }
}