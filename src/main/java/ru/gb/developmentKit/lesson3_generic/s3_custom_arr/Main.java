package ru.gb.developmentKit.lesson3_generic.s3_custom_arr;

import java.io.DataInputStream;

public class Main {

    public static void main(String[] args) {
        Task1<String, DataInputStream, Integer> task1 = new Task1<>("Текст", new DataInputStream(System.in), 9);
        task1.print();

        CollectionEx<String> collectionEx = new CollectionEx<>();
        collectionEx.add("A");
        collectionEx.add("B");
        collectionEx.add("C");
        collectionEx.add("D");
        collectionEx.add("E");
        collectionEx.add("F");
        collectionEx.add("G");
        System.out.println(collectionEx);
    }
}
