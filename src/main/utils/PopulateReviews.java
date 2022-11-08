package main.utils;

import java.util.List;

import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlAnchor;
import com.gargoylesoftware.htmlunit.html.HtmlDivision;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlSpan;

import main.models.Movie;
import main.models.MovieStatus;
import main.models.Review;

public class PopulateReviews extends Populator {
    
    public static void main(String[] args) {
        java.util.logging.Logger.getLogger("com.gargoylesoftware").setLevel(java.util.logging.Level.OFF);
        List<Movie> movies = loadData("movies.ser");
        for (Movie movie : movies) {
            if (movie.getShowingStatus().equals(MovieStatus.COMING_SOON))
                continue;
            try {
                String movieTitle = formatMovieTitle(movie.getTitle());
                final WebClient client = new WebClient();
                client.getOptions().setCssEnabled(false);
                client.getOptions().setJavaScriptEnabled(false);
                String formattedTitle = formatMovieTitle(movieTitle);
                String searchUrl = "https://www.imdb.com/find?q=" + formattedTitle + "&ref_=nv_sr_sm";
                HtmlPage searchPage = client.getPage(searchUrl);
                HtmlAnchor a = searchPage.getFirstByXPath("//*[contains(@href, '/title/tt')]");
                String moviePageUrl = "https://www.imdb.com" + a.getHrefAttribute();
                String reviewPageUrl = getReviewPageUrl(moviePageUrl);
                HtmlPage reviewsPage = client.getPage(reviewPageUrl);
                List<HtmlDivision> reviewsList = reviewsPage.getByXPath("//div[@class='lister-item mode-detail imdb-user-review  collapsable']");
                if (!reviewsList.isEmpty())
                    addReview(movie, reviewsList, 10);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        serialize(movies, "movies.ser");
    }

    private static void addReview(Movie movie, List<HtmlDivision> reviewsList, int count) {
        for (HtmlDivision div : reviewsList) {
            HtmlSpan ratingSpan = div.getFirstByXPath(".//span[@class='rating-other-user-rating']");
            if (ratingSpan == null)
                continue;
            String ratingString = ratingSpan.asNormalizedText().split("/")[0];
            HtmlSpan nameSpan = div.getFirstByXPath(".//span[@class='display-name-link']");
            int rating = (int)Math.ceil((double)Integer.parseInt(ratingString) / 2);
            HtmlDivision reviewContent = div.getFirstByXPath(".//div[@class='text show-more__control']");
            movie.addReview(new Review(nameSpan.asNormalizedText(), reviewContent.asNormalizedText(), rating));
            if (--count == 0)
                return;
        }
    }

    private static String formatMovieTitle(String movieTitle) {
        movieTitle = movieTitle.replace(" ", "+");
        movieTitle = movieTitle.replace(":", "%3A");
        return movieTitle;
    }

    private static String getReviewPageUrl(String moviePageUrl) {
        return moviePageUrl.substring(0, moviePageUrl.lastIndexOf("?")) + "reviews";
    }
}
