package main.models;

public class Review implements java.io.Serializable{
    private static final long serialVersionUID = 4L;
    private String reviewerName;
    private String reviewContent;
    private int rating;

    public Review(String reviewerName, String reviewContent, int rating) {
        if (rating < 1 || rating > 5)
            throw new IllegalArgumentException("Rating must be between 1 to 5 (inclusive)");
        if (reviewerName == null || reviewerName.isBlank())
            throw new IllegalArgumentException("reviewerName cannot be null or blank");
        this.reviewerName = reviewerName;
        this.reviewContent = reviewContent;
        this.rating = rating;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Reviewer rating: " + rating + "/5\n");
        sb.append(reviewContent);
        sb.append("\nReviewer: " + reviewerName + "\n");
        return sb.toString();
    }

    public int getRating() {
        return rating;
    }

}
