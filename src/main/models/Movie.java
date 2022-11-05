package main.models;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Movie implements java.io.Serializable {
    private static final long serialVersionUID = 3L;
    private String title;
    private Rating rating;
    private MovieStatus showingStatus;
    private String sypnopsis;
    private String director;
    private String casts;
    private List<Review> reviews;

    public enum Rating {G, PG, PG13, NC16, M18, R21, NA}

    public Movie(String title, String rating, MovieStatus showingStatus, String sypnopsis, String director, String casts) {
        this.title = title;
        this.rating = Rating.valueOf(rating);
        this.showingStatus = showingStatus;
        this.sypnopsis = sypnopsis;
        this.director = director;
        this.casts = casts;
        reviews = new ArrayList<>();
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
    }

    public double getReviewerRating() {
        if (reviews.isEmpty())
            return 0;
        double sum = 0;
        for (Review review : reviews)
            sum += review.getRating();
        sum /= reviews.size();
        return (double) Math.round(sum * 10) / 10;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this)
            return true;
        if (!(obj instanceof Movie))
            return false;
        Movie other = (Movie)obj;
        return title.equals(other.title);
    }

    @Override
    public int hashCode() {
        int prime = 31;
        return prime + Objects.hashCode(title);
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setRating(String rating) {
        this.rating = Rating.valueOf(rating);
    }

    public void setShowingStatus(String showingStatus) {
        this.showingStatus = MovieStatus.valueOf(showingStatus);
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

    public List<Review> getReviews() {
        return reviews;
    }
}