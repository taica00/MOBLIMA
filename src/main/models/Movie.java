package main.models;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Represents a movie.
 * A movie can be screened across all cinemas.
 * @author Tai Chen An
 * @version 1.0 
 * @since 2022-11-06 
 */

public class Movie implements java.io.Serializable {
    private static final long serialVersionUID = 3L;
    /**
     * The title of this movie.
     */
    private String title;

    /**
     * The censorship rating of this movie.
     */
    private Rating rating;

    /**
     * The showing status of this movie.
     */
    private MovieStatus showingStatus;

    /**
     * The sypnosis of this movie.
     */
    private String sypnopsis;

    /**
     * The director of this movie.
     */
    private String director;

    /**
     * The casts of this movie.
     */
    private String casts;

    /**
     * List of {@link Review} for this movie.
     */
    private List<Review> reviews;

    public enum Rating {G, PG, PG13, NC16, M18, R21, NA}

    /**
     * Creates a movie with the given attributes.
     * None of the fields should be blank.
     * @param title
     * @param rating
     * @param showingStatus
     * @param sypnopsis
     * @param director
     * @param casts
     */
    public Movie(String title, String rating, MovieStatus showingStatus, String sypnopsis, String director, String casts) {
        if (title == null || sypnopsis == null || director == null || casts == null)
            throw new IllegalArgumentException("Fields cannot be null");
        if (title.isBlank() || sypnopsis.isBlank() || director.isBlank() || casts.isEmpty())
            throw new IllegalArgumentException("Fields cannot be blank");
        this.title = title;
        this.rating = Rating.valueOf(rating);
        this.showingStatus = showingStatus;
        this.sypnopsis = sypnopsis;
        this.director = director;
        this.casts = casts;
        reviews = new ArrayList<>();
    }

    /** 
     * @return all information of this movie.
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Title: " + title + "\n");
        sb.append("Rating: " + rating + "\n");
        sb.append("Showing status: " + showingStatus + "\n");
        sb.append("Director: " + director + "\n");
        sb.append("Cast: " + casts + "\n");
        sb.append("Sypnopsis: " + sypnopsis + "\n");
        sb.append("Reviewer Rating: ");
        if (reviews.size() <= 1)
            sb.append("NA\n");
        else
            sb.append(getReviewerRating() + "\n");
        return sb.toString();
    }
    
    /**
     * Adds a {@link Review} to the list of reviews for this movie.
     * @param review
     */
    public void addReview(Review review) {
        reviews.add(review);
    }
    
    /** 
     * The reviewer rating is calculated from the average of all of this movie's reviews.
     * @return rating out of 5
     */
    public double getReviewerRating() {
        if (reviews.isEmpty())
            return 0;
        double sum = 0;
        for (Review review : reviews)
            sum += review.getRating();
        sum /= reviews.size();
        return (double) Math.round(sum * 10) / 10;
    }

    /** 
     * @param obj
     * @return boolean
     */
    @Override
    public boolean equals(Object obj) {
        if (obj == this)
            return true;
        if (!(obj instanceof Movie))
            return false;
        Movie other = (Movie)obj;
        return title.equals(other.title);
    }

    /** 
     * @return int
     */
    @Override
    public int hashCode() {
        int prime = 31;
        return prime + Objects.hashCode(title);
    }

    /** 
     * @param title title of this movie.
     */
    public void setTitle(String title) {
        if (title == null || title.isBlank())
            throw new IllegalArgumentException("title cannot be null or blank.");
        this.title = title;
    }

    /** 
     * @param rating censorship rating of this movie.
     */
    public void setRating(String rating) {
        this.rating = Rating.valueOf(rating);
    }

    /** 
     * @param showingStatus showing status of this movie.
     */
    public void setShowingStatus(String showingStatus) {
        this.showingStatus = MovieStatus.valueOf(showingStatus);
    }

    /** 
     * @param sypnopsis sypnopsis for this movie.
     */
    public void setSypnopsis(String sypnopsis) {
        if (sypnopsis == null || sypnopsis.isBlank())
            throw new IllegalArgumentException("sypnopsis cannot be null or blank.");
        this.sypnopsis = sypnopsis;
    }

    /** 
     * @param director director for this movie.
     */
    public void setDirector(String director) {
        if (director == null || director.isBlank())
            throw new IllegalArgumentException("director cannot be null or blank.");
        this.director = director;
    }
    
    /** 
     * @param casts casts for this movie.
     */
    public void setCasts(String casts) {
        if (casts == null || casts.isBlank())
            throw new IllegalArgumentException("casts cannot be null or blank.");
        this.casts = casts;
    }

    /** 
     * @return title of this movie.
     */
    public String getTitle() {
        return title;
    }

    /** 
     * @return censorship rating of this movie;
     */
    public Rating getRating() {
        return rating;
    }

    /** 
     * @return showing status of this movie.
     */
    public MovieStatus getShowingStatus() {
        return showingStatus;
    }
    
    /** 
     * @return sypnosis of this movie.
     */
    public String getSypnopsis() {
        return sypnopsis;
    }

    /** 
     * @return director of this movie.
     */
    public String getDirector() {
        return director;
    }

    /** 
     * @return casts of this movie.
     */
    public String getCasts() {
        return casts;
    }

    /** 
     * @return list of the reviews for this movie.
     */
    public List<Review> getReviews() {
        return reviews;
    }
}