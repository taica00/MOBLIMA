package main.controllers;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public abstract class Controller {
    protected Controller() {
        throw new IllegalStateException("Utility class");
    }

    protected static <T> T loadData(String filePath) {
        try {
            FileInputStream fileIn = new FileInputStream(filePath);
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
    
    protected static <T> void saveData(T element, String filePath) {
        try {
            FileOutputStream fileOut = new FileOutputStream(filePath);
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(element);
            out.close();
            fileOut.close();
        } catch (IOException i) {
            i.printStackTrace();
        } 
    }
}
