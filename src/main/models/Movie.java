package main.models;

import java.util.ArrayList;
import java.util.List;

public class Movie implements java.io.Serializable {
    private static final long serialVersionUID = 3L;
    private String title;
    private Rating rating;
    private MovieStatus showingStatus;
    private String sypnopsis;
    private String director;
    private String casts;
    private double reviewerRating;
    private List<Review> reviews;
    private int ticketSales;

    public enum Rating {G, PG, PG13, NC16, M18, R21, NA}
    public enum MovieStatus {COMINGSOON, PREVIEW, NOWSHOWING, ENDOFSHOWING}

    public Movie(String title, String rating, MovieStatus showingStatus, String sypnopsis, String director, String casts) {
        this.title = title;
        this.rating = Rating.valueOf(rating);
        this.showingStatus = showingStatus;
        this.sypnopsis = sypnopsis;
        this.director = director;
        this.casts = casts;
        reviewerRating = 0;
        reviews = new ArrayList<>();
        ticketSales = 0;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Title: " + title + "\n");
        sb.append("Rating: " + rating + "\n");
        sb.append("Showing status: " + showingStatus + "\n");
        sb.append("Director: " + director + "\n");
        sb.append("Cast: " + casts + "\n");
        sb.append("Sypnopsis: " + sypnopsis + "\n");
        return sb.toString();
    }

    public void addReview(Review review) {
        reviews.add(review);
        updateReviewerRating();
    }

    private void updateReviewerRating() {
        double sum = 0;
        for (Review review : reviews)
            sum += review.getRating();
        sum /= reviews.size();
        //TODO round to 1 d.p
        reviewerRating = sum;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setRating(Rating rating) {
        this.rating = rating;
    }

    public void setShowingStatus(MovieStatus showingStatus) {
        this.showingStatus = showingStatus;
    }

    public void setSypnopsis(String sypnopsis) {
        this.sypnopsis = sypnopsis;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public void setCasts(String casts) {
        this.casts = casts;
    }

    public String getTitle() {
        return title;
    }

    public Rating getRating() {
        return rating;
    }

    public MovieStatus getShowingStatus() {
        return showingStatus;
    }

    public String getSypnopsis() {
        return sypnopsis;
    }

    public String getDirector() {
        return director;
    }

    public String getCasts() {
        return casts;
    }

    public double getReviewerRating() {
        return reviewerRating;
    }

    public List<Review> getReviews() {
        return reviews;
    }

    
    
}