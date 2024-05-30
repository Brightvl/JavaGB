package ru.gb.developmentKit.lesson3_generic.l3_fruitBox;

import java.util.ArrayList;
import java.util.List;

// extend ограничение сверху <T extends Fruit>, т.е. Текущий класс T и его наследники
public class Box<T extends Fruit> {
    private final List<T> container = new ArrayList<>();

    void add(T fruit) {
        container.add(fruit);
    }

    void print() {
        System.out.println(getWeight());
    }

    float getWeight() {
        return (container.isEmpty())
                ? 0
                : container.get(0).getWeight() * container.size();
    }

    // Box<Apple> appleBox = new Box<>();
    // Box<Orange> orangeBox = new Box<>();
    // С Box<?>: appleBox.compare(orangeBox); - Это допустимо, потому что Box<?> принимает любой тип Box.
    // С Box<T>: appleBox.compare(orangeBox); - Это вызовет ошибку компиляции, потому что T должен быть одним и тем же типом.
    boolean compare(Box<?> with) {
        return this.getWeight() - with.getWeight() < 0.0001;
    }

    // super Ограничение снизу - Box<? super T>, Текущий класс и его родители
    // можно:
    // orangeBox.transferTo(otherOrangeBox);
    // orangeBox.transferTo(fruitBox);
    void transferTo(Box<? super T> dest) {
        dest.container.addAll(container);
        container.clear();
    }

}
