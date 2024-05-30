package ru.gb.developmentKit.lesson3_generic.l3_fruitBox;

public class Main {
    public static void main(String[] args) {

        Box<Fruit> fruitBox = new Box<>();
        fruitBox.add(new Apple());
        fruitBox.add(new Orange());

        Box<Orange> orangeBox = new Box<>();
        orangeBox.add(new Orange()); //1.5
        orangeBox.add(new Orange()); //1.5

        Box<Orange> orangeBox2 = new Box<>(); // orangeBox = 3 orange, orangeBox2 = 0
        orangeBox.transferTo(orangeBox2); // orangeBox = 2 orange, orangeBox2 = 1

        Box<Apple> appleBox = new Box<>();
        appleBox.add(new Apple()); //1.0
        appleBox.add(new Apple()); //1.0
        appleBox.add(new Apple()); //1.0

        System.out.println (appleBox.compare(orangeBox)); // 3==3? true


    }
}
