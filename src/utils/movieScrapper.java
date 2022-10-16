import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlAnchor;
import com.gargoylesoftware.htmlunit.html.HtmlDivision;
import com.gargoylesoftware.htmlunit.html.HtmlOption;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlSelect;

import models.Cinema;
import models.Cineplex;
import models.Movie;
import models.Session;
import models.Movie.MovieStatus;

public class movieScrapper {
    static Cineplex[] cineplexes = new Cineplex[3];
    static List<Movie> movies= new ArrayList<>();
    public static void main(String[] args) {
        java.util.logging.Logger.getLogger("com.gargoylesoftware").setLevel(java.util.logging.Level.OFF);
        cineplexes[0] = new Cineplex("Cathay", new Cinema[]{new Cinema("AMK Hub"), new Cinema("Causeway Point"), new Cinema("Jem")});
        cineplexes[1] = new Cineplex("Golden Village", new Cinema[]{new Cinema("Funan"), new Cinema("Grand"), new Cinema("VivoCity")});
        cineplexes[2] = new Cineplex("Shaw", new Cinema[]{new Cinema("JCube"), new Cinema("Jewel"), new Cinema("Nex")});
        loadMovies("nowshowing.aspx", 20);
        loadMovies("comingsoon.aspx", 10);
        movies.sort((x, y)->x.getTitle().compareTo(y.getTitle()));
        serialize();
    }

    private static void loadMovies(String domain, int count) {
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
            for (HtmlDivision movieDiv : moviesList) {
                final HtmlAnchor anchor = (HtmlAnchor)movieDiv.getFirstByXPath("a");
                final HtmlPage moviePage = client.getPage("https://www.cinemaonline.sg" + anchor.getHrefAttribute());
                HtmlDivision movieDetails = (HtmlDivision)moviePage.getFirstByXPath("//div[@class='con-lg']");
                String[] movieDetailsArr = movieDetails.asNormalizedText().split("\n");
                String title = movieDetailsArr[0];
                String sypnosis = movieDetailsArr[1];
                String rating = movieDetailsArr[5].split(": ")[1].trim();
                String cast = movieDetailsArr[10].substring(6);
                String director = movieDetailsArr[11].substring(10);
                movies.add(new Movie(title, rating, movieStatus, sypnosis, director, cast));
                if (movieStatus == MovieStatus.NOWSHOWING)
                    loadShowTimes(movieDetails, movies.get(movies.size()-1));
                if (--count == 0) 
                    return;
            }
            client.close();
        } catch(Exception e) {
            e.printStackTrace();
        } 
    }

    private static void loadShowTimes(HtmlDivision movieDetails, Movie movie) {
        try {
            final WebClient client = new WebClient();
            client.getOptions().setCssEnabled(false);
            client.getOptions().setJavaScriptEnabled(true);
            client.getOptions().setThrowExceptionOnScriptError(false);  
            List<HtmlAnchor> anchors = movieDetails.getByXPath("//a");
            int index = 26;
            while (!anchors.get(index).asNormalizedText().equals("[Showtimes]"))
                index++;
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
                String date = ((HtmlOption)showTimesBox.getFirstByXPath("//option")).asNormalizedText().split(",")[0];
                List<HtmlDivision> showTimesLists = showTimesBox.getByXPath("//div[@id='ShowtimesList']");
                for (HtmlDivision showTimesList : showTimesLists) { //iterate through locations
                    String[] location = showTimesList.asNormalizedText().split("\n")[0].split(" - "); // {cineplex, location}
                    List<HtmlDivision> showTimes = showTimesList.getByXPath("//div[@class='btn btn-info']");
                    for (HtmlDivision showTime : showTimes) {
                        String time = showTime.asNormalizedText();
                        addSessionToCineplex(movie, date, location, time);
                    }
                }
            }
            client.close();
            
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    private static void addSessionToCineplex(Movie movie, String date, String[] location, String time) {
        int cineplexIndex;
        switch(location[0]) {
            case "Cathay": cineplexIndex = 0; break;
            case "GV": cineplexIndex = 1; break;
            case "Shaw Theatres": cineplexIndex = 2; break;
            default: return; 
        }
        String[] cinemaAndType = location[1].split(", ");
        String cinemaName;
        String cinemaType;
        if (cinemaAndType.length == 3) {
            cinemaType = cinemaAndType[0].replace(" ","").toUpperCase();
            if (cinemaType.endsWith("TWOTOVIEW"))
                cinemaType = "GEMINI";
            else if (!cinemaType.equals("GVMAX") || !cinemaType.equals("DELUXEPLUS") || !cinemaType.equals("GEMINI"))
                return;
            cinemaName = cinemaAndType[1];
        }
        else if (cinemaAndType[0].startsWith("Gold")) {
            cinemaType = "GOLDCLASS";
            cinemaName = cinemaAndType[0].substring(11);
        }
        else {
            cinemaType = "STANDARD";
            cinemaName = cinemaAndType[0];
        }
        Cinema cinema = cineplexes[cineplexIndex].getCinema(cinemaName);
        if (cinema != null)
            cinema.addSession(movie, date, time, cinemaType);
    }

    private static void serialize() {
        try {
            FileOutputStream fileOut = new FileOutputStream("src/data/movies.ser");
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(movies);
            out.close();
            fileOut.close();
            System.out.println("Serialized data is saved in src/data/movies.ser");

            fileOut = new FileOutputStream("src/data/cineplexesshowtimes.ser");
            out = new ObjectOutputStream(fileOut);
            out.writeObject(cineplexes);
            out.close();
            fileOut.close();
            System.out.println("Serialized data is saved in src/data/cineplexesshowtimes.ser");
        } catch (IOException i) {
            i.printStackTrace();
        } 
    }

    
}
