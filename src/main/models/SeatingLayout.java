package main.models;

public interface SeatingLayout {
    public int rows();
    public int columns(); // does not include aisles
    public int[] aisles(); // each value represents the column that is an aisle
    public double[] ticketPrices(); // prices for {senior citizen, student, mon-thur, fri-sun/ph}
}
