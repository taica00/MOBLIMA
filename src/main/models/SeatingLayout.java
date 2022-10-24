package main.models;

public interface SeatingLayout {
    public int rows();
    public int columns(); // includes aisles
    public int[] aisles(); // each value represents the column index that is an aisle
}
