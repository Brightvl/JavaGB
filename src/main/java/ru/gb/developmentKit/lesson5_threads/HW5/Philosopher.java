package ru.gb.developmentKit.lesson5_threads.HW5;

import ru.gb.core.lesson2_ticTacToe.Hw2_ticTacToe.ui.Colorant;

import java.util.Random;
import java.util.concurrent.CountDownLatch;

public class Philosopher extends Thread {
    private final String name;
    private final int leftFork;
    private final int rightFork;
    private final int amountFoodIntake;
    private int countEat;

    private final Random random;
    private final CountDownLatch cdl;
    private final Colorant colorant;

    private final Table table;

    public Philosopher(String name, Table table, int leftFork, int rightFork, CountDownLatch cdl, int amountOfFoodIntake) {
        this.name = name;
        this.table = table;
        this.leftFork = leftFork;
        this.rightFork = rightFork;
        this.cdl = cdl;
        this.amountFoodIntake = amountOfFoodIntake;

        countEat = 0;
        random = new Random();
        colorant = new Colorant();
    }

    @Override
    public void run() {
        while (countEat < amountFoodIntake) {
            try {
                thinking();
                eating();
            } catch (InterruptedException e) {
                e.fillInStackTrace();
            }
        }

        System.out.println(name + colorant.stringColorAndStyle(35, 51, (" наелся")));
        cdl.countDown();
    }

    /**
     * Логика ужина
     *
     * @throws InterruptedException исключение на поток
     */
    private void eating() throws InterruptedException {
        if (table.tryGetForks(leftFork, rightFork)) {
            System.out.println(
                    name + " " +
                            colorant.stringColorAndStyle(32, 51, " начал кушать ") +
                            " в левой руке " + colorant.stringColorAndStyle((30 + (leftFork % 10)), 1, leftFork + 1)
                            + ", в правой " + colorant.stringColorAndStyle((30 + (rightFork % 10)), 1, rightFork + 1));

            sleep(random.nextLong(3000, 6000));
            table.putForks(leftFork, rightFork);

            System.out.println(name + " " +
                    colorant.stringColorAndStyle(31, 51, " задумался ") +
                    " положил вилку " + colorant.stringColorAndStyle((30 + (leftFork % 10)), 1, leftFork + 1)
                    + " и " + colorant.stringColorAndStyle((30 + (rightFork % 10)), 1, rightFork + 1));
            countEat++;
        }

    }

    private void thinking() throws InterruptedException {
        sleep(random.nextLong(100, 2000));
    }
}