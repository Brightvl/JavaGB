package ru.gb.developmentKit.lesson5_threads.HW5;

public class Main {
    public static void main(String[] args) {
        Table table = new Table(5,5);
        table.start();
    }
}
