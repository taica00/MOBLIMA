package main.controllers;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * This class is the super class for all controller classes.
 * Includes methods to deserialise/serialise data for all subclasses.
 * @author Tai Chen An
 * @version 1.0 
 * @since 2022-11-08 
 */

public abstract class Controller {
    /**
     * @throws IllegalStateException if user attempts to create an object of this utility class.
     */
    protected Controller() {
        throw new IllegalStateException("Utility class");
    }

    /**
     * Deserialises the object from the given filepath. 
     * @param filePath path to load serialised object from.
     * @return object loaded from data file.
     */
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
    
    /**
     * Serialises the given object at the given filepath. 
     * @param element object to be serialised.
     * @param filePath path to store serialised object.
     */
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
