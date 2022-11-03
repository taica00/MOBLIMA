package main.utils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import main.models.Movie;
import main.models.Review;

public class PopulateReviews extends Populator {
    public static void main(String[] args) {
        Map<Movie, List<Review>> reviews = new HashMap<>();
        serialize(reviews, "reviews.ser");
    }
}
