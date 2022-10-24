package main.models;

enum MovieStatus {
    COMING_SOON("Coming Soon"), 
    PREVIEW("Preview"), 
    NOW_SHOWING("Now Showing"), 
    END_OF_SHOWING("End of Showing");

    private String string;

    MovieStatus(String name){string = name;}

    @Override
    public String toString() {
        return string;
    }
}
