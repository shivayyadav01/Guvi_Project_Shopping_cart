import java.io.Serializable;

public class Review implements Serializable {
    private int rating;
    private String comment;

    public Review(int rating, String comment) {
        this.rating = rating;
        this.comment = comment;
    }

    public int getRating() { return rating; }
    public String getComment() { return comment; }

    @Override
    public String toString() {
        return "Rating: " + rating + "/5 | Comment: " + comment;
    }
}
