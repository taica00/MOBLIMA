package main.utils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlAnchor;
import com.gargoylesoftware.htmlunit.html.HtmlDivision;
import com.gargoylesoftware.htmlunit.html.HtmlOption;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlSelect;

import main.models.Cinema;
import main.models.Cineplex;
import main.models.Movie;
import main.models.MovieStatus;

public class MoviesShowtimesScrapper extends Populator {
    static final String C = "Cathay";
    static final String G = "Golden Village";
    static final String S = "Shaw Theatres";
    static List<Cineplex> cineplexes = new ArrayList<>();
    static List<Movie> movies= new ArrayList<>();
    
    /** 
     * @param args
     */
    public static void main(String[] args) {
        java.util.logging.Logger.getLogger("com.gargoylesoftware").setLevel(java.util.logging.Level.OFF);
        cineplexes.add(new Cineplex(C, new Cinema[]{new Cinema(C, "AMK Hub"), new Cinema(C, "Causeway Point"), new Cinema(C, "Jem")}));
        cineplexes.add(new Cineplex(G, new Cinema[]{new Cinema(G, "Funan"), new Cinema(G, "Grand"), new Cinema(G, "VivoCity")}));
        cineplexes.add(new Cineplex(S, new Cinema[]{new Cinema(S, "JCube"), new Cinema(S, "Jewel"), new Cinema(S, "Nex")}));
        loadMovies("nowshowing.aspx", 25);
        loadMovies("comingsoon.aspx", 5);
        movies.sort((x, y)->x.getTitle().compareTo(y.getTitle()));
        serialize(cineplexes, "cineplexes.ser");
        serialize(movies, "movies.ser");
    }

    
    /** 
     * @param domain
     * @param count
     */
    private static void loadMovies(String domain, int count) {
        try {
            final WebClient client = new WebClient();
            client.getOptions().setCssEnabled(false);
            client.getOptions().setJavaScriptEnabled(false);
            String className;
            MovieStatus movieStatus;
            if(domain.equals("nowshowing.aspx")) {
                className = "'NowShowingMov'";
                movieStatus = MovieStatus.NOW_SHOWING;
            }
            else {
                className = "'ComingSoonMov'";
                movieStatus = MovieStatus.COMING_SOON;
            }
            final HtmlPage page = client.getPage("https://www.cinemaonline.sg/movies/" + domain);
            final HtmlDivision allMovies = (HtmlDivision)page.getFirstByXPath("//div[@class=" + className + "]");
            final List<HtmlDivision> moviesList = allMovies.getByXPath("//div[@class='mov-lg']");
            for (HtmlDivision movieDiv : moviesList) {
                final HtmlAnchor anchor = (HtmlAnchor)movieDiv.getFirstByXPath(".//a");
                final HtmlPage moviePage = client.getPage("https://www.cinemaonline.sg" + anchor.getHrefAttribute());
                HtmlDivision movieDetails = (HtmlDivision)moviePage.getFirstByXPath("//div[@class='con-lg']");
                String[] movieDetailsArr = movieDetails.asNormalizedText().split("\n");
                DateTimeFormatter dtf = DateTimeFormatter.ofPattern("d MMM yyyy");
                LocalDate date = LocalDate.parse(movieDetailsArr[6].split(": ")[1], dtf);
                if (movieStatus.equals(MovieStatus.NOW_SHOWING) && date.isAfter(LocalDate.now()))
                    continue;
                String title = formatMovieTitle(movieDetailsArr[0]);
                String sypnosis = movieDetailsArr[1];
                String rating = movieDetailsArr[5].split(": ")[1].trim();
                String cast = movieDetailsArr[10].substring(6);
                String director = movieDetailsArr[11].substring(10);
                movies.add(new Movie(title, rating, movieStatus, sypnosis, director, cast));
                if (movieStatus == MovieStatus.NOW_SHOWING)
                    loadShowTimes(movieDetails, movies.get(movies.size()-1));
                if (--count == 0) 
                    return;
            }
            client.close();
        } catch(Exception e) {
            e.printStackTrace();
        } 
    }

    
    /** 
     * @param movieTitle
     * @return String
     */
    private static String formatMovieTitle(String movieTitle) {
        if (movieTitle.endsWith(")"))
            return movieTitle.substring(0, movieTitle.indexOf("(")-1);
        return movieTitle;
    }

    
    /** 
     * @param movieDetails
     * @param movie
     */
    private static void loadShowTimes(HtmlDivision movieDetails, Movie movie) {
        try {
            final WebClient client = new WebClient();
            client.getOptions().setCssEnabled(false);
            client.getOptions().setJavaScriptEnabled(true);
            client.getOptions().setThrowExceptionOnScriptError(false);  
            List<HtmlAnchor> anchors = movieDetails.getByXPath(".//a");
            int index = anchors.size();
            while (!anchors.get(--index).asNormalizedText().equals("[Showtimes]"));
            HtmlPage showTimePage = client.getPage("https://www.cinemaonline.sg" + anchors.get(index).getHrefAttribute());
            HtmlSelect dateSelect = (HtmlSelect)showTimePage.getElementById("ctl00_cphContent_ctl00_ddlShowdate");
            if (dateSelect == null) {
                client.close();
                return;
            }
            for (HtmlOption option : dateSelect.getOptions()) {  //iterate through days
                option.setSelected(true);
                client.waitForBackgroundJavaScript(1 * 1000);
                showTimePage = (HtmlPage)showTimePage.getEnclosingWindow().getEnclosedPage();
                HtmlDivision showTimesBox = showTimePage.getFirstByXPath("//div[@class='ShowtimesBox']");
                if (showTimesBox == null) 
                    continue;
                String date = option.asNormalizedText().split(", ")[0];
                List<HtmlDivision> showTimesLists = showTimesBox.getByXPath(".//div[@id='ShowtimesList']");
                for (HtmlDivision showTimesList : showTimesLists) { //iterate through locations
                    String[] location = showTimesList.asNormalizedText().split("\n")[0].split(" - "); // {cineplex, location}
                    List<HtmlDivision> cinemaClasses = showTimesList.getByXPath("./div");
                    for (HtmlDivision cinemaClass : cinemaClasses) { //iterate through cinemaClasses
                        String title = cinemaClass.asNormalizedText().split("\n")[0].trim();
                        if (title.contains("Eng Sub"))
                            continue;
                        String[] split = title.split(" ");
                        String cClass = split[split.length-1].toUpperCase();
                        if (cClass.endsWith(")"))
                            cClass = "STANDARD";
                        else if (cClass.endsWith("SUITES"))
                            cClass = "PLATINUM_MOVIE_SUITES";
                        List<HtmlDivision> showTimes = cinemaClass.getByXPath(".//div[@class='btn btn-info']");
                        for (HtmlDivision showTime : showTimes) {
                            String time = showTime.asNormalizedText();
                            if (time.endsWith(")")) 
                                continue;
                            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d MMMM yyyyhh:mma");
                            LocalDateTime dateTime = LocalDateTime.parse(date+time, formatter);
                            if (location[0].equals("GV"))
                                addSessionToGV(movie, dateTime, location[1]);
                            else
                                addSessionToCineplex(movie, dateTime, location, cClass);
                        }
                    } 
                }
            }
            client.close();
            
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
    
    /** 
     * @param movie
     * @param dateTime
     * @param location
     * @param cinemaClass
     */
    private static void addSessionToCineplex(Movie movie, LocalDateTime dateTime, String[] location, String cinemaClass) { 
        Cineplex cineplex = null;
        for (Cineplex c : cineplexes)
            if (c.getName().equals(location[0]))
                cineplex = c;
        if (cineplex == null)
            return;
        String cinemaName = location[1].split(", ")[0];
        Cinema cinema = cineplex.getCinema(cinemaName);
        if (cinema == null)
            return;
        System.out.println(cinema + " " + movie.getTitle() + " " + dateTime + " " + cinemaClass);
        cinema.addSession(movie, dateTime, cinemaClass, false);
    }

    
    /** 
     * @param movie
     * @param dateTime
     * @param location
     */
    private static void addSessionToGV(Movie movie, LocalDateTime dateTime, String location) {
        String[] cinemaAndType = location.split(", ");
        String cinemaName= "NULL";
        String cinemaType;
        if (cinemaAndType.length == 3) {
            cinemaType = cinemaAndType[0];
            if (cinemaType.startsWith("Gold")) {
                cinemaType = "GOLD_CLASS_EXPRESS";
                cinemaName = "Funan"; 
            }
            else if (cinemaType.equals("Grand")) {
                cinemaType = "STANDARD";
                cinemaName = "Grand";
            }
            else if (cinemaType.startsWith("Deluxe")) {
                cinemaType = "DELUXE_PLUS";
                cinemaName = "Funan";
            }
        }
        else if (cinemaAndType[0].startsWith("Gold")) {
            cinemaType = "GOLD_CLASS";
            cinemaName = cinemaAndType[0].substring(11);
        }
        else if (cinemaAndType[0].startsWith("Gemini")) 
            return;
        else if (cinemaAndType[0].startsWith("GVmax")) {
            cinemaType = "GVMAX";
            cinemaName = "VivoCity";
        }
        else {
            cinemaType = "STANDARD";
            cinemaName = cinemaAndType[0];
        }
        Cinema cinema = cineplexes.get(1).getCinema(cinemaName);
        if (cinema == null) 
            return;
        System.out.println(cinema + " " + movie.getTitle() + " " + dateTime + " " + cinemaType);
        cinema.addSession(movie, dateTime, cinemaType, false);
        
    }
}
