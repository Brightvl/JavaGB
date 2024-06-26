package ru.gb.core.lesson3.hw3.empoyee;

/**
 * Работник с фиксированой оплатой
 */
public class Worker extends Employee {


    /**
     * Фабричный метод для создания объекта
     *
     * @param name
     * @return
     */
    public static Worker create(String name, double monthlySalary) {
        return new Worker(name, monthlySalary);
    }

    public Worker(String name, double monthlySalary) {
        super(name, monthlySalary);
    }

    @Override
    public Double calculationOfAverageMonthlySalary() {
        return super.getMonthlySalary();
    }
}
