package main.utils;

import java.util.ArrayList;
import java.util.List;

import main.models.MovieGoer;

public class PopulateMovieGoers extends Populator {
    
    public static void main(String[] args) {
        List<MovieGoer> movieGoers = new ArrayList<>();
        movieGoers.add(new MovieGoer("Mike Coxlong", "98765432", "mikecoxlong@gmail.com", "7480932833"));
        movieGoers.add(new MovieGoer("Joe", "98765431", "joe@gmail.com", "7483934833"));
        movieGoers.add(new MovieGoer("Elon Musk", "98765433", "elonmusk@gmail.com", "7482132833"));
        serialize(movieGoers, "moviegoers.ser");
    }
}
