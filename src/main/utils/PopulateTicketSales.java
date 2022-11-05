package main.utils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import main.models.Movie;

public class PopulateTicketSales extends Populator {
    public static void main(String[] args) {
        Map<Movie, Integer> ticketSales = new HashMap<>();
        List<Movie> movies = loadData("movies.ser");
        for (Movie movie : movies)
            ticketSales.put(movie, 0);
        serialize(ticketSales, "ticketsales.ser");
    }
}
