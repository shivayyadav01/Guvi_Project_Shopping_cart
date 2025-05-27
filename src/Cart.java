import java.io.Serializable;
import java.util.*;

public class Cart implements Serializable {
    private Map<Product, Integer> items = new HashMap<>();

    public void addItem(Product product, int quantity) {
        items.put(product, items.getOrDefault(product, 0) + quantity);
    }

    public void removeItem(Product product) {
        items.remove(product);
    }

    public void updateQuantity(Product product, int quantity) {
        if (items.containsKey(product)) items.put(product, quantity);
    }

    public void clearCart() {
        items.clear();
    }

    public Map<Product, Integer> getItems() {
        return items;
    }

    public int getSubtotal() {
        int total = 0;
        for (Map.Entry<Product, Integer> entry : items.entrySet()) {
            total += entry.getKey().getPrice() * entry.getValue();
        }
        return total;
    }

    public int getDiscount() {
        int subtotal = getSubtotal();
        return subtotal >= 5000 ? (int) (subtotal * 0.10) : 0;
    }

    public int getTotal() {
        return getSubtotal() - getDiscount();
    }

    public boolean isEmpty() {
        return items.isEmpty();
    }
}
