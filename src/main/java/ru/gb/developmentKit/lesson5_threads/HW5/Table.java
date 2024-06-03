package ru.gb.developmentKit.lesson5_threads.HW5;

import ru.gb.core.lesson2_ticTacToe.Hw2_ticTacToe.ui.Colorant;

import java.util.concurrent.CountDownLatch;

public class Table extends Thread {

    private final int PHILOSOPHER_COUNT;

    private final Fork[] forks;
    private final Philosopher[] philosophers;
    private final CountDownLatch cdl;
    private int amountFoodIntake;
    private Colorant colorant;


    public Table(int philosopher_count, int amountFoodIntake) {
        PHILOSOPHER_COUNT = philosopher_count;
        this.amountFoodIntake = amountFoodIntake;
        this.colorant = new Colorant();

        forks = new Fork[philosopher_count];
        philosophers = new Philosopher[philosopher_count];
        cdl = new CountDownLatch(philosopher_count);
        setTheTable();
    }


    @Override
    public void run() {
        System.out.println(colorant.stringColorAndStyle(30,42," Ужин начался "));
        try {
            startDinner();
            cdl.await();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        System.out.println(colorant.stringColorAndStyle(30,41,"Ужин закончен"));
    }

    /**
     * Метод создает вилки и философов
     */
    private void setTheTable() {
        for (int i = 0; i < PHILOSOPHER_COUNT; i++) {
            philosophers[i] = new Philosopher(
                    colorant.stringColorAndStyle((30 + (i % 10)), 4, "Philosopher №" + i),
                    this,
                    i,
                    (i + 1) % PHILOSOPHER_COUNT, // вилок на 1 больше
                    cdl,
                    amountFoodIntake);
        }
        for (int i = 0; i < PHILOSOPHER_COUNT; i++) {
            forks[i] = new Fork();
        }
    }

    /**
     * Начинает ужин для каждого философа
     */
    private void startDinner() {
        for (Philosopher philosopher : philosophers) {
            philosopher.start();

        }
    }

    /**
     * Попытка взять вилку
     *
     * @param leftFork  левая вилка
     * @param rightFork правая вилка
     * @return значение занятости
     */
    public synchronized boolean tryGetForks(int leftFork, int rightFork) {
        if (!forks[leftFork].isUsing() && !forks[rightFork].isUsing()) {
            forks[leftFork].setUsing(true);
            forks[rightFork].setUsing(true);
            return true;
        }
        return false;
    }

    /**
     * Освободить вилки
     *
     * @param leftFork  левая вилка
     * @param rightFork правая вилка
     */
    public void putForks(int leftFork, int rightFork) {
        forks[leftFork].setUsing(false);
        forks[rightFork].setUsing(false);
    }


}
