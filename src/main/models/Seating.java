package main.models;

public interface Seating extends java.io.Serializable {
    public void displaySeating();
    public boolean bookSeat(char row, int col);
    public void unBookSeat(char row, int col);
    public int getNumCols();
    public int getNumRows();
}
