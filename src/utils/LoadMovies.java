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

public class LoadMovies {

    public static void main(String[] args) {
        try {
            final WebClient client = new WebClient();
            client.getOptions().setCssEnabled(false);
            client.getOptions().setJavaScriptEnabled(false);
            final HtmlPage page = client.getPage("https://www.cinemaonline.sg/movies/nowshowing.aspx");
            final HtmlDivision div = (HtmlDivision)page.getFirstByXPath("//div[@class='NowShowingMov']");
            final List<HtmlDivision> moviesList = div.getByXPath("//li/div/div[@class='mov-lg']");
            List<Movie> movies = new ArrayList<>();
            for (HtmlDivision movie : moviesList) {
                final HtmlAnchor anchor = (HtmlAnchor)movie.getFirstByXPath("a");
                final HtmlPage moviePage = client.getPage("https://www.cinemaonline.sg" + anchor.getHrefAttribute());
                final HtmlDivision movieDetails = (HtmlDivision)moviePage.getFirstByXPath("//div[@class='con-lg']");
                String[] movieDetailsArr = movieDetails.asNormalizedText().split("\n");
                String title = movieDetailsArr[0];
                String sypnosis = movieDetailsArr[1];
                String rating = movieDetailsArr[5].split(": ")[1].trim();
                String[] cast = movieDetailsArr[10].split(": ")[1].split(", ");
                String director = movieDetailsArr[11].split(": ")[1];
                movies.add(new Movie(title, rating, Movie.MovieStatus.NOWSHOWING, sypnosis, director, cast));
            }
            serialize(movies);
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
