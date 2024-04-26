package ru.gb.core.Lesson4.s4.task2;

/**
 Задача 2
 ========
 Класс «Проверка логина и пароля».
 1. Создать статический метод который принимает на вход три параметра: login,
 password и confirmPassword.
 2. Длина login должна быть меньше 20 символов. Если login не соответствует
 этому требованию, необходимо выбросить WrongLoginException.
 3. Длина password должна быть не меньше 20 символов. Также password и confirmPassword
 должны быть равны. Если password не соответствует этим требованиям, необходимо выбросить WrongPasswordException.
 4. WrongPasswordException и WrongLoginException – пользовательские классы исключения с двумя конструкторами –- один по умолчанию,
 второй принимает параметры исключения (неверные данные) и возвращает пользователю в
 виде «ожидалось/фактически».
 5. В основном классе программы необходимо по-разному обработать исключения.
 6. Метод возвращает true, если значения верны или false в противном случае.
 */

public class Program {

    public static void main(String[] args) {
        // Реквизиты для входа в систему
        Authentifier[] credentials = {
                new Authentifier("ivan", "1i2v3a4n5i6v7a8n91011", "1i2v3a4n5i6v7a8n91011"), //correct
                new Authentifier("1i2v3a4n5i6v7a8n91011", "", ""), //wrong login length
                new Authentifier("ivan", "1i2v3a4n5i6v7a8n91011", "1i2v3a4n5"), //confirm mismatch
                new Authentifier("ivan", "1i2v3a4n5", "1i2v3a4n5"),//wrong password length
                new Authentifier("ivan", "1i2v3a4n5", "1i") //wrong password length and confirm mismatch
        };

        // Проверка реквизитов
        for (Authentifier credential : credentials) {
            try {
                System.out.println(SignInService.checkCredentials(credential));
            } catch (WrongLoginException e) {
                e.printStackTrace();
            } catch (WrongPasswordException e) {
                System.out.println(e.getMessage());
            }
        }
    }

}
