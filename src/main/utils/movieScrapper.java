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

import main.models.Cinema;
import main.models.Cineplex;
import main.models.Movie;
import main.models.Movie.MovieStatus;

public class MovieScrapper {
    static final String C = "Cathay";
    static final String G = "Golden Village";
    static final String S = "Shaw Theatres";
    static Cineplex[] cineplexes = new Cineplex[3];
    static List<Movie> movies= new ArrayList<>();
    public static void main(String[] args) {
        java.util.logging.Logger.getLogger("com.gargoylesoftware").setLevel(java.util.logging.Level.OFF);
        cineplexes[0] = new Cineplex(C, new Cinema[]{new Cinema(C, "AMK Hub"), new Cinema(C, "Causeway Point"), new Cinema(C, "Jem")});
        cineplexes[1] = new Cineplex(G, new Cinema[]{new Cinema(G, "Funan"), new Cinema(G, "Grand"), new Cinema(G, "VivoCity")});
        cineplexes[2] = new Cineplex(S, new Cinema[]{new Cinema(S, "JCube"), new Cinema(S, "Jewel"), new Cinema(S, "Nex")});
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
            final HtmlDivision allMovies = (HtmlDivision)page.getFirstByXPath("//div[@class=" + className + "]");
            final List<HtmlDivision> moviesList = allMovies.getByXPath(".//div[@class='mov-lg']");
            for (HtmlDivision movieDiv : moviesList) {
                final HtmlAnchor anchor = (HtmlAnchor)movieDiv.getFirstByXPath(".//a");
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
                    List<HtmlDivision> showTimes = showTimesList.getByXPath(".//div[@class='btn btn-info']");
                    for (HtmlDivision showTime : showTimes) {
                        String time = showTime.asNormalizedText();
                        if (time.endsWith(")")) 
                            time = time.substring(0, 7);
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
            case C: cineplexIndex = 0; break;
            case G: cineplexIndex = 1; break;
            case S: cineplexIndex = 2; break;
            default: return; 
        }
        String[] cinemaAndType = location[1].split(", ");
        String cinemaName= "NULL";
        String cinemaType;
        if (cinemaAndType.length == 3) {
            cinemaType = cinemaAndType[0];
            if (cinemaType.startsWith("Gemini")) {
                cinemaType = "GEMINI";
                cinemaName = "Funan";
            }
            else if (cinemaType.startsWith("Gold")) {
                cinemaType = "GOLDCLASSEXPRESS";
                cinemaName = "Funan"; 
            }
            else if (cinemaType.equals("Grand")) {
                cinemaType = "STANDARD";
                cinemaName = "Grand";
            }
            else if (cinemaType.startsWith("Deluxe")) {
                cinemaType = "DELUXEPLUS";
                cinemaName = "Funan";
            }
        }
        else if (cinemaAndType[0].startsWith("Gold")) {
            cinemaType = "GOLDCLASS";
            cinemaName = cinemaAndType[0].substring(11);
        }
        else if (cinemaAndType[0].startsWith("Gemini")) {
            cinemaType = "GEMINI";
            cinemaName = "Funan";
        }
        else if (cinemaAndType[0].startsWith("GVmax")) {
            cinemaType = "GVMAX";
            cinemaName = "VivoCity";
        }
        else {
            cinemaType = "STANDARD";
            cinemaName = cinemaAndType[0];
        }
        Cinema cinema = cineplexes[cineplexIndex].getCinema(cinemaName);
        
        if (cinema != null) {
            System.out.println(cinema.getCineplex()+" "+cinema.getLocation()+" "+movie.getTitle()+" "+date+" "+time+" "+cinemaType);
            cinema.addSession(movie, date, time, cinemaType);
        }
    }

    private static void serialize() {
        try {
            FileOutputStream fileOut = new FileOutputStream("src/main/data/movies.ser");
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(movies);
            out.close();
            fileOut.close();
            System.out.println("Serialized data is saved in src/main/data/movies.ser");

            fileOut = new FileOutputStream("src/main/data/cineplexes.ser");
            out = new ObjectOutputStream(fileOut);
            out.writeObject(cineplexes);
            out.close();
            fileOut.close();
            System.out.println("Serialized data is saved in src/main/data/cineplexes.ser");
        } catch (IOException i) {
            i.printStackTrace();
        } 
    }

    
}
