package main.models;

/**
 * Represents a review of a movie.
 * Each movie can have many reviews.
 * @author Tai Chen An
 * @version 1.0 
 * @since 2022-11-06 
 */

public class Review implements java.io.Serializable{
    private static final long serialVersionUID = 4L;

    /**
     * The name of the reviewer who wrote this review.
     */
    private String reviewerName;

    /**
     * The content of this review.
     */
    private String reviewContent;

    /**
     * The rating of the movie on a scale of 1-5.
     */
    private int rating;

    /**
     * Creates a new Review with the given fields.
     * A review may have only the rating of the movie and no text content.
     * @param reviewerName name of reviewer
     * @param reviewContent content of review
     * @param rating reviewer rating for movie
     */
    public Review(String reviewerName, String reviewContent, int rating) {
        if (rating < 1 || rating > 5)
            throw new IllegalArgumentException("Rating must be between 1 to 5 (inclusive)");
        if (reviewerName == null || reviewerName.isBlank())
            throw new IllegalArgumentException("reviewerName cannot be null or blank");
        if (reviewContent == null)
            throw new IllegalArgumentException("reviewContent cannot be null");
        this.reviewerName = reviewerName;
        this.reviewContent = reviewContent;
        this.rating = rating;
    }

    /** 
     * @return contents of this review.
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Reviewer rating: " + rating + "/5\n");
        sb.append(reviewContent);
        sb.append("\nReviewer: " + reviewerName + "\n");
        return sb.toString();
    }

    /** 
     * @return rating of movie on a scale of 1-5.
     */
    public int getRating() {
        return rating;
    }

}
