package ru.gb.junior.lesson1_stream.l1;

public class Lambda {
    public static void main(String[] args) {

        // Обычная реализация интерфейса
        {
            PlainInterface anInterface = new PlainInterface() {
                @Override
                public String action(int x, int y) {
                    return String.valueOf(x + y);
                }
            };
            System.out.println(anInterface.action(5, 5));
        }

        // Лямбда выражение
        { // По сути передали аргументы (x, y) -> и провели с ними операцию String.valueOf(x + y)
            // все как в методе интерфейса String action(int x, int y);
            PlainInterface anInterface = (x, y) -> String.valueOf(x + y);
            System.out.println(anInterface.action(5, 5));
        }


        {

            // это тоже что и
            PlainInterfaceInt interfaceInt = (x, y) -> Integer.compare(x, y);
            // это!
            interfaceInt = Integer::compare;
            System.out.println(interfaceInt.action(5, 15));

            // Как это работает?
            // Если аргументы и тип, возвращаемого значения
            // методов которые мы используем в одном методе схожи с другим методом
            // int action(int x, int y);
            // public static int compare(int x, int y)
            // interfaceInt = (x, y) -> Integer.compare(x, y);
            // по сути нет смысла писать полностью а можно просто сократить до
            // interfaceInt = Integer::compare;
            // мы как бы присвоили реализацию интерфейсу другому из другого интерфейса
        }


    }
}


// Интерфейс считается функциональным если в нем один метод Если есть аннотация @FunctionalInterface то в интерфейс не
// получится добавить лишнего и код не сломается
/**
 * Возвращает строку
 */
@FunctionalInterface
interface PlainInterface {
    String action(int x, int y);
}

/**
 * Возвращает число
 */
@FunctionalInterface
interface PlainInterfaceInt {
    int action(int x, int y);
}

