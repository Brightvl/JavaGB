package ru.gb.core.lesson4.s4.task4;

import java.util.Scanner;

public class Sample {
    public static void main(String[] args) {
        int x;
        try(Scanner scanner = new Scanner(System.in)) {

            System.out.print("Введите целое число => ");
            x = Integer.parseInt(scanner.nextLine());

            System.out.println();
            System.out.println("Число: " + x);
        } catch (NumberFormatException e){
            e.printStackTrace();
        }
        //System.out.println("Число: " + x);
    }
}
