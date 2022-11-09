package main.utils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;

import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlAnchor;
import com.gargoylesoftware.htmlunit.html.HtmlDivision;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlParagraph;
import com.gargoylesoftware.htmlunit.html.HtmlSection;

import main.controllers.MovieController;
import main.models.Cinema;
import main.models.CinemaClass;
import main.models.Cineplex;
import main.models.Movie;
import main.models.Session;

public class SessionsScrapper extends Populator{
    private static List<Cineplex> cineplexes;
    public static void main(String[] args) {
        java.util.logging.Logger.getLogger("com.gargoylesoftware").setLevel(java.util.logging.Level.OFF);
        MovieController.loadMovies();
        cineplexes = deserialise("cineplexes.ser");
        String[] cineplexIds = new String[]{"564", "587", "744"};
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("MM/d/yy");
        for (String cineplexId : cineplexIds) {
            for (int i = 0; i < 7; i++) {
                LocalDate date = LocalDate.now().plusDays(i);
                loadSessions(date.format(dtf), cineplexId);
            }
        }
        serialize(cineplexes, "cineplexes.ser"); 
    }

    private static void loadSessions(String date, String cineplexId) {
        try {
            final WebClient client = new WebClient();
            client.getOptions().setCssEnabled(false);
            client.getOptions().setJavaScriptEnabled(false);
            final HtmlPage page = client.getPage("https://www.cinemaonline.sg/showtimes/showtimes_result.aspx?dt="+date+"&cinid="+cineplexId+"&movid=0&region=&areaid=0");
            HtmlSection showTimesSec = page.getFirstByXPath("//section[@id='ShowtimesSec']");
            List<HtmlDivision> movieShowTimes = showTimesSec.getByXPath(".//div");
            for (HtmlDivision movieDiv : movieShowTimes) {
                if (movieDiv.asNormalizedText().length() < 51)
                    continue;
                HtmlAnchor movieTitleAnchor = movieDiv.getFirstByXPath(".//a");
                String movieTitle = movieTitleAnchor.asNormalizedText();
                if (movieTitle.endsWith(")"))
                    continue;
                Movie movie = MovieController.searchMovie(movieTitle);
                List<HtmlAnchor> showTimes = movieDiv.getByXPath(".//a[@class='shawticketing']");
                for (HtmlAnchor showTimeAnchor : showTimes) {
                    if (showTimeAnchor.asNormalizedText().trim().endsWith(")"))
                        continue;
                    String seatingCode = formatLink(showTimeAnchor.getHrefAttribute());
                    final HtmlPage seatingPage = client.getPage("https://shaw.sg/seat-selection/" + seatingCode);
                    HtmlParagraph theatre = seatingPage.getFirstByXPath("//p[contains(text(), 'Shaw Theatres')]");
                    addSessionToCinema(cineplexId, movie, date, formatString(showTimeAnchor.asNormalizedText().trim()), theatre.asNormalizedText().substring(14));
                }
            }
            client.close();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    private static void addSessionToCinema(String cineplexId, Movie movie, String date, String time, String cinemaTheatre) {
        int cineplexIndex;
        switch (Integer.parseInt(cineplexId)) {
            case 564: cineplexIndex = 0; break;
            case 587: cineplexIndex = 1; break;
            case 744: cineplexIndex = 2; break;
            default: cineplexIndex = -1;
        }
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("MM/d/yyhh:mma", Locale.US);
        LocalDateTime dateTime = LocalDateTime.parse(date+time, dtf);
        CinemaClass cinemaClass;
        String[] split = cinemaTheatre.split(" ");
        if (split[1].equals("Hall"))
            cinemaClass = CinemaClass.STANDARD;
        else
            cinemaClass = CinemaClass.valueOf(split[1].toUpperCase());
        int cinemaNumber = 1;
        Character lastChar = cinemaTheatre.charAt(cinemaTheatre.length()-1);
        if (Character.isDigit(lastChar)) 
            cinemaNumber = Character.getNumericValue(lastChar);
        Cinema cinema = cineplexes.get(cineplexIndex).getCinema(cinemaClass, cinemaNumber);
        if (cinema == null)
            System.out.println(cinemaTheatre);
        System.out.println(dateTime + " " + movie.getTitle() + " " + cinema);
        cinema.addSession(new Session(cinema, movie, dateTime, false));
    }

    private static String formatLink(String link) {
        return link.substring(link.lastIndexOf("%")+3);
    }  
}
