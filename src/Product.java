import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Product implements Serializable {
    private int id;
    private String name;
    private String category;
    private int price;
    private String description;
    private List<Review> reviews = new ArrayList<>();

    public Product(int id, String name, String category, int price, String description) {
        this.id = id;
        this.name = name;
        this.category = category;
        this.price = price;
        this.description = description;
    }

    public int getId() { return id; }
    public String getName() { return name; }
    public String getCategory() { return category; }
    public int getPrice() { return price; }
    public String getDescription() { return description; }
    public List<Review> getReviews() { return reviews; }

    public void addReview(Review review) {
        reviews.add(review);
    }

    public double getAverageRating() {
        if (reviews.isEmpty()) return 0;
        double sum = 0;
        for (Review r : reviews) sum += r.getRating();
        return sum / reviews.size();
    }

    @Override
    public String toString() {
        return "ID: " + id + " | Name: " + name + " | Price: ₹" + price +
                " | Description: " + description + " | Category: " + category;
    }
}
