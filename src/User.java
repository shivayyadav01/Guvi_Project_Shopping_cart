import java.io.Serializable;
import java.util.*;

public class User implements Serializable {
    private String username;
    private String password;
    private List<Order> orders = new ArrayList<>();
    private Cart cart = new Cart();
    private String savedAddress;

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() { return username; }
    public boolean checkPassword(String input) { return password.equals(input); }
    public Cart getCart() { return cart; }
    public void addOrder(Order order) { orders.add(order); }
    public void setSavedAddress(String addr) { savedAddress = addr; }
    public String getSavedAddress() { return savedAddress; }

    public void printOrders() {
        if (orders.isEmpty()) System.out.println("No past orders.");
        else orders.forEach(System.out::println);
    }
}
