import java.io.Serializable;
import java.util.Map;

public class Order implements Serializable {
    private Map<Product, Integer> items;
    private String address;
    private int total;

    public Order(Map<Product, Integer> items, String address, int total) {
        this.items = items;
        this.address = address;
        this.total = total;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("Order Summary:\n");
        for (Map.Entry<Product, Integer> entry : items.entrySet()) {
            sb.append(entry.getKey().getName())
                    .append(" x ")
                    .append(entry.getValue())
                    .append(" = ₹")
                    .append(entry.getKey().getPrice() * entry.getValue())
                    .append("\n");
        }
        sb.append("Delivery Address: ").append(address).append("\n");
        sb.append("Total Paid: ₹").append(total).append("\n");
        return sb.toString();
    }
}
