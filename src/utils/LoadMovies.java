import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlAnchor;
import com.gargoylesoftware.htmlunit.html.HtmlDivision;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

import models.Movie;
import models.Movie.MovieStatus;

public class LoadMovies {

    public static void main(String[] args) {
        List<Movie> movies= new ArrayList<>();
        getMovies("nowshowing.aspx", movies);
        getMovies("comingsoon.aspx", movies);
        movies.sort((x, y)->x.getTitle().compareTo(y.getTitle()));
        serialize(movies);
    }

    public static void getMovies(String domain, List<Movie> movies) {
        try {
            final WebClient client = new WebClient();
            client.getOptions().setCssEnabled(false);
            client.getOptions().setJavaScriptEnabled(false);
            String className;
            Movie.MovieStatus movieStatus;
            if(domain.equals("nowshowing.aspx")) {
                className = "'NowShowingMov'";
                movieStatus = MovieStatus.NOWSHOWING;
            }
            else {
                className = "'ComingSoonMov'";
                movieStatus = MovieStatus.COMINGSOON;
            }
            final HtmlPage page = client.getPage("https://www.cinemaonline.sg/movies/" + domain);
            final HtmlDivision div = (HtmlDivision)page.getFirstByXPath("//div[@class=" + className + "]");
            final List<HtmlDivision> moviesList = div.getByXPath("//li/div/div[@class='mov-lg']");
            int count = 0;
            for (HtmlDivision movie : moviesList) {
                final HtmlAnchor anchor = (HtmlAnchor)movie.getFirstByXPath("a");
                final HtmlPage moviePage = client.getPage("https://www.cinemaonline.sg" + anchor.getHrefAttribute());
                final HtmlDivision movieDetails = (HtmlDivision)moviePage.getFirstByXPath("//div[@class='con-lg']");
                String[] movieDetailsArr = movieDetails.asNormalizedText().split("\n");
                String title = movieDetailsArr[0];
                String sypnosis = movieDetailsArr[1];
                String rating = movieDetailsArr[5].split(": ")[1].trim();
                String cast = movieDetailsArr[10].substring(6);
                String director = movieDetailsArr[11].substring(10);
                movies.add(new Movie(title, rating, movieStatus, sypnosis, director, cast));
                if (++count == 15)
                    return;
            }
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    public static void serialize(List<Movie> movies) {
        try {
            FileOutputStream fileOut = new FileOutputStream("src/data/movies.ser");
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(movies);
            out.close();
            fileOut.close();
            System.out.println("Serialized data is saved in src/data/movies.ser");
        } catch (IOException i) {
            i.printStackTrace();
        }
    }

    
}
