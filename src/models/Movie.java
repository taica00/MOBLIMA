package models;

import java.util.ArrayList;
import java.util.List;

public class Movie implements java.io.Serializable {
    private String title;
    private Rating rating;
    private MovieStatus showingStatus;
    private String sypnopsis;
    private String director;
    private String[] cast;
    private double reviewerRating;
    private List<Review> reviews;

    public enum Rating {G, PG, PG13, NC16, M18, R21}
    public enum MovieStatus {COMINGSOON, PREVIEW, NOWSHOWING, ENDOFSHOWING}

    public Movie(String title, String rating, MovieStatus showingStatus, String sypnopsis, String director, String[] cast) {
        this.title = title;
        this.rating = Rating.valueOf(rating);
        this.showingStatus = showingStatus;
        this.sypnopsis = sypnopsis;
        this.director = director;
        this.cast = cast;
    }

    

    

    
}