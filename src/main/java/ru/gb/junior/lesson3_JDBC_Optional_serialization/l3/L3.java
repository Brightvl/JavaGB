package ru.gb.junior.lesson3_JDBC_Optional_serialization.l3;

import java.io.FileOutputStream;
import java.io.ObjectOutputStream;

public class L3 {
    public static void main(String[] args) throws Exception {
        String str = "HI Bitches";
        FileOutputStream fos = new FileOutputStream("ser");
        ObjectOutputStream oos = new ObjectOutputStream(fos);
        oos.writeObject(str);
        oos.close();
    }
}
