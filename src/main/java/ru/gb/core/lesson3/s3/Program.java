package ru.gb.core.lesson3.s3;

public class Program {

    public static void main(String[] args) {

        try
        {
            Human human2 = Human.create("User #1", 28, 100, 10);

        }
        catch (Exception e){
            e.printStackTrace();
        }

    }

}
