package main.models;

public enum CinemaClass implements SeatingLayout {
    STANDARD("Standard") {
        @Override
        public int rows() {return 17;}
        @Override
        public int columns() {return 34;}
        @Override
        public int[] aisles() {return new int[]{7, 27};}
        @Override
        public double[] ticketPrices() {return new double[]{5, 7, 10, 14.5};}    
    },
    GVMAX("GVMax") {
        @Override
        public int rows() {return 10;}
        @Override
        public int columns() {return 18;}
        @Override
        public int[] aisles() {return new int[]{4, 14};}
        @Override
        public double[] ticketPrices() {return new double[]{10, 10, 11.5, 16};}    
    },
    GOLD_CLASS("Gold Class") {
        @Override
        public int rows() {return 4;}
        @Override
        public int columns() {return 6;}
        @Override
        public int[] aisles() {return new int[]{2, 4};}
        @Override
        public double[] ticketPrices() {return new double[]{32, 32, 35, 45};}    
    },
    GOLD_CLASS_EXPRESS("Gold Class Express") {
        @Override
        public int rows() {return 4;}
        @Override
        public int columns() {return 8;}
        @Override
        public int[] aisles() {return new int[]{2, 4, 6};}
        @Override
        public double[] ticketPrices() {return new double[]{28, 28, 31, 41};}    
    },
    DELUXE_PLUS("Deluxe Plus") {
        @Override
        public int rows() {return 5;}
        @Override
        public int columns() {return 8;}
        @Override
        public int[] aisles() {return new int[]{2, 4, 6};}
        @Override
        public double[] ticketPrices() {return new double[]{10, 12, 17, 22};}    
    },
    IMAX("IMAX") {
        @Override
        public int rows() {return 8;}
        @Override
        public int columns() {return 21;}
        @Override
        public int[] aisles() {return new int[]{4, 16};}
        @Override
        public double[] ticketPrices() {return new double[]{17, 19, 20, 23};}    
    },
    LUMIERE("Lumiere") {
        @Override
        public int rows() {return 4;}
        @Override
        public int columns() {return 6;}
        @Override
        public int[] aisles() {return new int[]{2, 4};}
        @Override
        public double[] ticketPrices() {return new double[]{17, 18, 23, 28};}    
    },
    DREAMERS("Dreamers") {
        @Override
        public int rows() {return 6;}
        @Override
        public int columns() {return 10;}
        @Override
        public int[] aisles() {return new int[]{5};}
        @Override
        public double[] ticketPrices() {return new double[]{7, 8, 9, 14};}    
    },
    PREMIERE("premiere") {
        @Override
        public int rows() {return 6;}
        @Override
        public int columns() {return 8;}
        @Override
        public int[] aisles() {return new int[]{2, 4, 6};}
        @Override
        public double[] ticketPrices() {return new double[]{22, 25, 30, 35};}    
    },
    PLATINUM_MOVIE_SUITES("Platinum Movie Suites") {
        @Override
        public int rows() {return 5;}
        @Override
        public int columns() {return 6;}
        @Override
        public int[] aisles() {return new int[]{2, 4};}
        @Override
        public double[] ticketPrices() {return new double[]{16, 16, 25, 25};}    
    };

    private String string;

    CinemaClass(String name){string = name;}

    @Override
    public String toString() {
        return string;
    }
}
