package main.models;

public enum CinemaClass implements SeatingLayout {
    STANDARD("Standard") {
        @Override
        public int rows() {return 17;}
        @Override
        public int columns() {return 36;}
        @Override
        public int[] aisles() {return new int[]{7, 28};}    
    },
    GVMAX("GVMax") {
        @Override
        public int rows() {return 10;}
        @Override
        public int columns() {return 20;}
        @Override
        public int[] aisles() {return new int[]{4, 15};}
    },
    GOLD_CLASS("Gold Class") {
        @Override
        public int rows() {return 4;}
        @Override
        public int columns() {return 11;}
        @Override
        public int[] aisles() {return new int[]{2, 5, 8};}
    },
    GOLD_CLASS_EXPRESS("Gold Class Express") {
        @Override
        public int rows() {return 4;}
        @Override
        public int columns() {return 11;}
        @Override
        public int[] aisles() {return new int[]{2, 5, 8};}
    },
    DELUXE_PLUS("Deluxe Plus") {
        @Override
        public int rows() {return 5;}
        @Override
        public int columns() {return 11;}
        @Override
        public int[] aisles() {return new int[]{2, 5, 8};}
    },
    IMAX("IMAX") {
        @Override
        public int rows() {return 8;}
        @Override
        public int columns() {return 23;}
        @Override
        public int[] aisles() {return new int[]{4, 18};}
    },
    LUMIERE("Lumiere") {
        @Override
        public int rows() {return 5;}
        @Override
        public int columns() {return 9;}
        @Override
        public int[] aisles() {return new int[]{2, 5};}
    },
    DREAMERS("Dreamers") {
        @Override
        public int rows() {return 11;}
        @Override
        public int columns() {return 6;}
        @Override
        public int[] aisles() {return new int[]{5};}
    },
    PREMIERE("premiere") {
        @Override
        public int rows() {return 6;}
        @Override
        public int columns() {return 11;}
        @Override
        public int[] aisles() {return new int[]{2, 5, 8};}
    },
    PLATINUM("Platinum Movie Suites") {
        @Override
        public int rows() {return 5;}
        @Override
        public int columns() {return 8;}
        @Override
        public int[] aisles() {return new int[]{2, 5};}
    };

    private String string;

    CinemaClass(String name){string = name;}

    @Override
    public String toString() {
        return string;
    }
}
