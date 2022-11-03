package main.utils;

import java.util.HashMap;
import java.util.Map;

import main.models.Movie;

public class PopulateTicketSales extends Populator {
    public static void main(String[] args) {
        Map<Movie, Integer> ticketSales = new HashMap<>();
        serialize(ticketSales, "tickersales.ser");
    }
}
