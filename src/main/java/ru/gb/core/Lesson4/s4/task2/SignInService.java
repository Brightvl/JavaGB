package ru.gb.core.Lesson4.s4.task2;

public class SignInService {

    /**
     * Метод проверки логина и пароля
     * @param authentifier
     * @return
     * @throws WrongLoginException
     * @throws WrongPasswordException
     */
    public static boolean checkCredentials(Authentifier authentifier) throws WrongLoginException, WrongPasswordException {
        boolean conf = authentifier.getPassword().equals(authentifier.getConfirmPassword());
        int loginLength = authentifier.getLogin().length();
        int passwordLength = authentifier.getPassword().length();
        if (loginLength >= 20){
            throw new WrongLoginException(loginLength);
        }
        else if (passwordLength < 20 || !conf)
            throw new WrongPasswordException(passwordLength, conf);
        else{
            return true;
        }
    }

}
