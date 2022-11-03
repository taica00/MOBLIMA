package main.models;

public enum CinemaClass implements SeatingLayout {
    STANDARD("Standard", 17, 34, new int[]{7, 27}, new double[]{5, 7, 10, 14.5}),
    GVMAX("GVMax", 10, 18, new int[]{4, 14}, new double[]{10, 10, 11.5, 16}),
    GOLD_CLASS("Gold Class", 4, 6, new int[]{2, 4}, new double[]{32, 32, 35, 45}),
    GOLD_CLASS_EXPRESS("Gold Class Express", 4, 8, new int[]{2, 4, 6}, new double[]{28, 28, 31, 41}),
    DELUXE_PLUS("Deluxe Plus", 5, 8, new int[]{2, 4, 6}, new double[]{10, 12, 17, 22}),
    IMAX("IMAX", 8, 21, new int[]{4, 16}, new double[]{17, 19, 20, 23}),
    LUMIERE("Lumiere", 4, 6, new int[]{2, 4}, new double[]{17, 18, 23, 28}),
    DREAMERS("Dreamers", 6, 10, new int[]{5}, new double[]{7, 8, 9, 14}),
    PREMIERE("premiere", 6, 8, new int[]{2, 4, 6}, new double[]{22, 25, 30, 35}),
    PLATINUM_MOVIE_SUITES("Platinum Movie Suites", 5, 6, new int[]{2, 4}, new double[]{16, 16, 25, 25});

    private String name;
    private int rows;
    private int columns;
    private int[] aisles;
    private double[] ticketPrices;

    CinemaClass(String name, int rows, int columns, int[] aisles, double[] ticketPrices) {
        this.name = name;
        this.rows = rows;
        this.columns = columns;
        this.aisles = aisles;
        this.ticketPrices = ticketPrices;
    }

    public void setPrice(int index, double price) {
        ticketPrices[index] = price;
    }

    @Override
    public int rows() {return rows;}

    @Override
    public int columns() {return columns;}

    @Override
    public int[] aisles() {return aisles;}

    @Override
    public double[] ticketPrices() {return ticketPrices;}

    @Override
    public String toString() {return name;}
}
