package ru.gb.core.lesson3.s3.sample1;

import java.util.ArrayList;

public class Program {

    public static void main(String[] args) {

        ArrayList<Product> products = new ArrayList<>();
        products.add(new ConcreteProduct());
        products.add(new ConcreteProduct2());
        products.add(new ConcreteProduct3());

        processProducts(products);
    }

    static void processProducts(ArrayList<Product> list){
        for (Product product : list){
            System.out.println(product.getName());
        }
    }

}
