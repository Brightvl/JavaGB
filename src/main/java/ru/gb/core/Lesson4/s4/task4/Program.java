package ru.gb.core.Lesson4.s4.task4;


import ru.gb.core.Lesson4.s4.task4.exceptions.AmountException;
import ru.gb.core.Lesson4.s4.task4.exceptions.CustomerException;
import ru.gb.core.Lesson4.s4.task4.exceptions.ProductException;

/**
 Задача 4
 ========
 Класс «Эмуляция интернет-магазина».
 1.  Написать классы покупатель (ФИО, возраст, телефон), товар (название, цена) и
 заказ (объект покупатель, объект товар, целочисленное количество).
 2.  Создать массив покупателей (инициализировать 2 элемента), массив товаров
 (инициализировать 5 элементов) и массив заказов (пустой на 5 элементов).
 3.  Создать статический метод «совершить покупку» со строковыми параметрами,
 соответствующими полям объекта заказа. Метод должен вернуть объект заказа.
 4.  Если в метод передан несуществующий покупатель – метод должен выбросить
 исключение CustomerException, если передан несуществующий товар, метод
 должен выбросить исключение ProductException,
 если было передано отрицательное или слишком больше значение количества
 (например, 100), метод должен выбросить исключение AmountException.
 5.  Вызвать метод совершения покупки несколько раз таким образом, чтобы заполнить массив
 покупок возвращаемыми значениями. Обработать исключения следующим образом (в заданном порядке):
 – если был передан неверный товар – вывести в консоль сообщение об ошибке, не совершать данную покупку;
 – если было передано неверное количество – купить товар в количестве 1;
 – если был передан неверный пользователь – завершить работу приложения
 с исключением.
 6.  Вывести в консоль итоговое количество совершённых покупок после выполнения основного
 кода приложения
 */

public class Program {

    public static void main(String[] args) {
        Object[][] info = {
                {Shop.getCustomers()[0], Shop.getItems()[0], 2}, //good
                {Shop.getCustomers()[1], Shop.getItems()[1], -1}, //bad amount -1
                {Shop.getCustomers()[0], Shop.getItems()[2], 150}, //bad amount >100
                {Shop.getCustomers()[1], new Item("Цветок", 10), 1}, //no item
                {new Customer("Федор", 40, "+3-444-555-66-77"), Shop.getItems()[3], 1}, //no customer
        };
        int capacity = 0;
        int i = 0;
        while (capacity != Shop.getOrders().length - 1 || i != info.length) {

            try {

                Shop.getOrders()[capacity] = Shop.buy((Customer) info[i][0], (Item) info[i][1], (int) info[i][2]);
                System.out.println("Заказ: #" + (capacity + 1001) + " успешно обработан.");
                System.out.println("Информация по заказу:\n" + Shop.getOrders()[capacity]);
                capacity++;
            } catch (ProductException e) {
                e.printStackTrace();
            } catch (AmountException e) {
                e.printStackTrace();
            } catch (CustomerException e) {
                e.printStackTrace();
            }
            ++i;
        }

    }

}
