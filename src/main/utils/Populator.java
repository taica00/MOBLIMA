package main.utils;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public abstract class Populator {
    protected Populator () {
        throw new IllegalStateException("Utility class");
    }

    protected static <T> T loadData(String fileName) {
        try {
            FileInputStream fileIn = new FileInputStream("src/main/data/" + fileName);
            ObjectInputStream in = new ObjectInputStream(fileIn);
            T element = (T)in.readObject();
            in.close();
            fileIn.close();
            return element;
        } catch (IOException i) {
            i.printStackTrace();
        } catch (ClassNotFoundException c) {
            System.out.println("Class not found");
            c.printStackTrace();
        }
        return null;
    }

    protected static <T> void serialize(T obj, String fileName) {
        try {
            FileOutputStream fileOut = new FileOutputStream("src/main/data/" + fileName);
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(obj);
            out.close();
            fileOut.close();
            System.out.println("Serialized data is saved in src/main/data/" + fileName);
        } catch (IOException i) {
            i.printStackTrace();
        } 
    }
}