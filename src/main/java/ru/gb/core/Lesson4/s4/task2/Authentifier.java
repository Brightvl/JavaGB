package ru.gb.core.Lesson4.s4.task2;

public class Authentifier {

    public Authentifier(String login, String password, String confirmPassword) {
        this.login = login;
        this.password = password;
        this.confirmPassword = confirmPassword;
    }

    /**
     * Имя пользователя
     */
    private final String login;

    /**
     * Пароль
     */
    private final String password;

    /**
     * Подтверждение пароля
     */
    private final String confirmPassword;

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

}
