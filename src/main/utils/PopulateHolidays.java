package main.utils;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

public class PopulateHolidays extends Populator {
    public static void main(String[] args) {
        Set<LocalDate> holidays = new HashSet<>();
        holidays.add(LocalDate.of(2022, 12, 26));
        holidays.add(LocalDate.of(2023, 1, 2));
        holidays.add(LocalDate.of(2023, 1, 23));
        holidays.add(LocalDate.of(2023, 1, 24));
        serialize(holidays, "holidays.ser");   
    }

    
}
