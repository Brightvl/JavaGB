package ru.gb.core.lesson2_ticTacToe.Hw2_ticTacToe.ui;

public class Colorant {



    /**
     * Чтобы раскрасить консоль
     *
     * @param color номер цвета ANSI Escape
     * @param s     строка текста
     * @return
     */
    public String stringColor(int color, String s) {
        StringBuilder stringBuilder = new StringBuilder("\u001B[" + color + "m" + s + "\u001B[0m");
        return stringBuilder.toString();

    }
    /**
     * Чтобы раскрасить и стилизовать текст в консоли
     *
     * @param color номер цвета ANSI Escape
     * @param style номер стиля ANSI Escape
     * @param s     строка текста
     * @return      стилизованная строка
     */
    public String stringColorAndStyle(int color, int style, String s) {
        StringBuilder stringBuilder = new StringBuilder("\u001B[" + style + ";" + color + "m" + s + "\u001B[0m");
        return stringBuilder.toString();
    }
    /**
     * Чтобы раскрасить и стилизовать текст в консоли
     *
     * @param color номер цвета ANSI Escape
     * @param style номер стиля ANSI Escape
     * @param s     строка текста
     * @return      стилизованная строка
     */
    public String stringColorAndStyle(int color, int style, int s) {
        StringBuilder stringBuilder = new StringBuilder("\u001B[" + style + ";" + color + "m" + s + "\u001B[0m");
        return stringBuilder.toString();
    }

    /**
     * Чтобы раскрасить консоль
     *
     * @param color номер цвета ANSI Escape
     * @param s     char
     * @return цветной текст
     */
    public String stringColor(int color, char s) {
        StringBuilder stringBuilder = new StringBuilder("\u001B[" + color + "m" + s + "\u001B[0m");
        return stringBuilder.toString();

    }

    /**
     * Чтобы раскрасить консоль
     *
     * @param color номер цвета ANSI Escape
     * @param s     char
     * @return цветной текст
     */
    public String stringColor(int color, int s) {
        StringBuilder stringBuilder = new StringBuilder("\u001B[" + color + "m" + s + "\u001B[0m");
        return stringBuilder.toString();

    }

}
