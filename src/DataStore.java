import java.util.*;

public class DataStore {
    public static List<Product> getAllProducts() {
        List<Product> products = new ArrayList<>();

        products.add(new Product(1, "Laptop", "Electronics", 75000, "High-performance gaming laptop"));
        products.add(new Product(2, "Smartphone", "Electronics", 30000, "Latest 5G smartphone"));
        products.add(new Product(3, "Gaming PC", "Electronics", 120000, "Powerful gaming desktop"));
        products.add(new Product(4, "Bluetooth Speaker", "Electronics", 5000, "Portable with deep bass"));
        products.add(new Product(5, "Ocean Breeze", "Perfumes", 3500, "Fresh aquatic fragrance"));
        products.add(new Product(6, "Rose Elegance", "Perfumes", 4000, "Elegant floral rose scent"));
        products.add(new Product(7, "Mystic Oud", "Perfumes", 5200, "Bold oriental oud essence"));
        products.add(new Product(8, "Java Programming", "Books", 800, "Complete Java guide"));
        products.add(new Product(9, "Fiction Novel", "Books", 450, "Gripping bestselling story"));
        products.add(new Product(10, "Data Structures", "Books", 600, "DSA book for beginners"));

        return products;
    }
}
