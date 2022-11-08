package main.utils;

import java.util.ArrayList;
import java.util.List;

import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlAnchor;
import com.gargoylesoftware.htmlunit.html.HtmlDivision;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

import main.models.Movie;
import main.models.MovieStatus;

public class MoviesScrapper extends Populator {
    static final String C = "Cathay";
    static final String G = "Golden Village";
    static final String S = "Shaw Theatres";
    static List<Movie> movies= new ArrayList<>();
    
    public static void main(String[] args) {
        java.util.logging.Logger.getLogger("com.gargoylesoftware").setLevel(java.util.logging.Level.OFF);
        loadMovies("nowshowing.aspx", 45);
        loadMovies("comingsoon.aspx", 5);
        movies.sort((x, y)->x.getTitle().compareTo(y.getTitle()));
        serialize(movies, "movies.ser");
    }

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
                String title = formatString(movieDetailsArr[0]);
                String sypnosis = movieDetailsArr[1];
                String rating = movieDetailsArr[5].split(": ")[1].trim();
                String cast = movieDetailsArr[10].substring(6);
                String director = movieDetailsArr[11].substring(10);
                Movie movie = new Movie(title, rating, movieStatus, sypnosis, director, cast);
                System.out.println(movie.getTitle());
                movies.add(movie);
                if (--count == 0) 
                    return;
            }
            client.close();
        } catch(Exception e) {
            e.printStackTrace();
        } 
    }
}