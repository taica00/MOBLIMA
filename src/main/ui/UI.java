package main.ui;

/**
 * This is the superclass for all UI classes.
 * @author Tai Chen An
 * @version 1.0 
 * @since 2022-11-08 
 */

public abstract class UI {
    /**
     * @throws IllegalStateException if user attempts to create object of this utility class.
     */
    protected UI() {
        throw new IllegalStateException("Utility class");
    }
}
