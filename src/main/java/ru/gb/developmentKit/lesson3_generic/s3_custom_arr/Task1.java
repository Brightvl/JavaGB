package ru.gb.developmentKit.lesson3_generic.s3_custom_arr;

import java.io.DataInput;
import java.io.InputStream;

public class Task1<T extends Comparable<String>, V extends InputStream & DataInput, K extends Number> {

    T t;
    V v;
    K k;

    Task1(T t, V v, K k) {
        this.t = t;
        this.v = v;
        this.k = k;
    }

    public T getT() {
        return t;
    }

    public V getV() {
        return v;
    }

    public K getK() {
        return k;
    }

    public void print() {
        System.out.printf("T: %s, V: %s, K: %s\n",
                t.getClass().getName(), v.getClass().getName(), k.getClass().getName());
    }
}


