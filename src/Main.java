import java.util.*;

public class Main {
    private static Scanner scanner = new Scanner(System.in);
    private static List<User> users;
    private static List<Product> products;
    private static User currentUser;

    public static void main(String[] args) {
        users = DataHandler.loadData();
        products = DataStore.getAllProducts();

        while (true) {
            if (currentUser == null) {
                showWelcomeMenu();
            } else {
                showUserMenu();
            }
        }
    }

    private static void showWelcomeMenu() {
        System.out.println("\n=== Welcome to the Shopping Cart ===");
        System.out.println("1. Sign Up");
        System.out.println("2. Log In");
        System.out.println("3. Exit");
        System.out.print("Choose an option: ");
        String choice = scanner.nextLine();

        switch (choice) {
            case "1" -> signUp();
            case "2" -> logIn();
            case "3" -> {
                DataHandler.saveData(users);
                System.out.println("Thank you for visiting! Goodbye.");
                System.exit(0);
            }
            default -> System.out.println("Invalid option.");
        }
    }

    private static void signUp() {
        System.out.print("Enter username: ");
        String username = scanner.nextLine().trim();
        if (findUserByUsername(username) != null) {
            System.out.println("Username already exists.");
            return;
        }
        System.out.print("Enter password: ");
        String password = scanner.nextLine().trim();

        User newUser = new User(username, password);
        users.add(newUser);
        DataHandler.saveData(users);
        System.out.println("Sign-up successful! You can now log in.");
    }

    private static void logIn() {
        System.out.print("Username: ");
        String username = scanner.nextLine().trim();
        System.out.print("Password: ");
        String password = scanner.nextLine().trim();

        User user = findUserByUsername(username);
        if (user == null || !user.checkPassword(password)) {
            System.out.println("Invalid username or password.");
            return;
        }
        currentUser = user;
        System.out.println("Logged in successfully! Welcome, " + currentUser.getUsername() + ".");
    }

    private static User findUserByUsername(String username) {
        for (User user : users) {
            if (user.getUsername().equalsIgnoreCase(username)) return user;
        }
        return null;
    }

    private static void showUserMenu() {
        System.out.println("\n=== Main Menu ===");
        System.out.println("1. View Products");
        System.out.println("2. Search Products");
        System.out.println("3. View Cart");
        System.out.println("4. Checkout");
        System.out.println("5. Order History");
        System.out.println("6. Logout");
        System.out.print("Choose an option: ");
        String choice = scanner.nextLine();

        switch (choice) {
            case "1" -> browseByCategory();
            case "2" -> searchProducts();
            case "3" -> viewCart();
            case "4" -> checkout();
            case "5" -> viewOrderHistory();
            case "6" -> {
                currentUser = null;
                System.out.println("Logged out successfully.");
            }
            default -> System.out.println("Invalid option.");
        }
    }

    private static void browseByCategory() {
        Set<String> categories = new TreeSet<>();
        for (Product p : products) categories.add(p.getCategory());

        System.out.println("\nCategories:");
        int i = 1;
        List<String> categoryList = new ArrayList<>(categories);
        for (String c : categoryList) {
            System.out.println(i++ + ". " + c);
        }
        System.out.print("Select a category or 0 to go back: ");
        String input = scanner.nextLine();
        int choice;
        try {
            choice = Integer.parseInt(input);
        } catch (NumberFormatException e) {
            System.out.println("Invalid input.");
            return;
        }
        if (choice == 0) return;
        if (choice < 1 || choice > categoryList.size()) {
            System.out.println("Invalid category choice.");
            return;
        }

        String selectedCategory = categoryList.get(choice - 1);
        System.out.println("\nProducts in " + selectedCategory + ":");
        List<Product> categoryProducts = new ArrayList<>();
        for (Product p : products) {
            if (p.getCategory().equalsIgnoreCase(selectedCategory)) {
                categoryProducts.add(p);
                System.out.println(p);
            }
        }
        addToCartPrompt(categoryProducts);
    }

    private static void searchProducts() {
        System.out.print("Enter search keyword: ");
        String keyword = scanner.nextLine().toLowerCase();
        List<Product> found = new ArrayList<>();
        for (Product p : products) {
            if (p.getName().toLowerCase().contains(keyword) || p.getDescription().toLowerCase().contains(keyword)) {
                found.add(p);
            }
        }
        if (found.isEmpty()) {
            System.out.println("No products found.");
            return;
        }
        System.out.println("\nSearch results:");
        for (Product p : found) System.out.println(p);
        addToCartPrompt(found);
    }

    private static void addToCartPrompt(List<Product> list) {
        System.out.print("\nEnter product ID to add to cart or 0 to go back: ");
        String input = scanner.nextLine();
        int id;
        try {
            id = Integer.parseInt(input);
        } catch (NumberFormatException e) {
            System.out.println("Invalid input.");
            return;
        }
        if (id == 0) return;
        Product selected = null;
        for (Product p : list) {
            if (p.getId() == id) {
                selected = p;
                break;
            }
        }
        if (selected == null) {
            System.out.println("Product ID not found.");
            return;
        }
        System.out.print("Enter quantity (1-10): ");
        int qty;
        try {
            qty = Integer.parseInt(scanner.nextLine());
            if (qty < 1 || qty > 10) throw new NumberFormatException();
        } catch (NumberFormatException e) {
            System.out.println("Invalid quantity.");
            return;
        }
        currentUser.getCart().addItem(selected, qty);
        System.out.println("Added " + qty + " x " + selected.getName() + " to cart.");
    }

    private static void viewCart() {
        Cart cart = currentUser.getCart();
        if (cart.isEmpty()) {
            System.out.println("Your cart is empty.");
            return;
        }
        System.out.println("\nYour Cart:");
        for (Map.Entry<Product, Integer> entry : cart.getItems().entrySet()) {
            System.out.println(entry.getKey().getName() + " x " + entry.getValue() +
                    " = ₹" + (entry.getKey().getPrice() * entry.getValue()));
        }
        System.out.println("Subtotal: ₹" + cart.getSubtotal());
        System.out.println("Discount: ₹" + cart.getDiscount());
        System.out.println("Total: ₹" + cart.getTotal());

        System.out.println("\nOptions:");
        System.out.println("1. Update quantity");
        System.out.println("2. Remove item");
        System.out.println("3. Clear cart");
        System.out.println("4. Go back");
        System.out.print("Choose option: ");
        String choice = scanner.nextLine();

        switch (choice) {
            case "1" -> updateCartQuantity();
            case "2" -> removeCartItem();
            case "3" -> {
                cart.clearCart();
                System.out.println("Cart cleared.");
            }
            case "4" -> { /* back */ }
            default -> System.out.println("Invalid option.");
        }
    }

    private static void updateCartQuantity() {
        System.out.print("Enter product name to update quantity: ");
        String pname = scanner.nextLine();
        Product prod = null;
        for (Product p : currentUser.getCart().getItems().keySet()) {
            if (p.getName().equalsIgnoreCase(pname)) {
                prod = p;
                break;
            }
        }
        if (prod == null) {
            System.out.println("Product not found in cart.");
            return;
        }
        System.out.print("Enter new quantity (1-10): ");
        int qty;
        try {
            qty = Integer.parseInt(scanner.nextLine());
            if (qty < 1 || qty > 10) throw new NumberFormatException();
        } catch (NumberFormatException e) {
            System.out.println("Invalid quantity.");
            return;
        }
        currentUser.getCart().updateQuantity(prod, qty);
        System.out.println("Quantity updated.");
    }

    private static void removeCartItem() {
        System.out.print("Enter product name to remove: ");
        String pname = scanner.nextLine();
        Product prod = null;
        for (Product p : currentUser.getCart().getItems().keySet()) {
            if (p.getName().equalsIgnoreCase(pname)) {
                prod = p;
                break;
            }
        }
        if (prod == null) {
            System.out.println("Product not found in cart.");
            return;
        }
        currentUser.getCart().removeItem(prod);
        System.out.println("Item removed from cart.");
    }

    private static void checkout() {
        Cart cart = currentUser.getCart();
        if (cart.isEmpty()) {
            System.out.println("Cart is empty. Add items before checkout.");
            return;
        }
        System.out.println("\nOrder Summary:");
        for (Map.Entry<Product, Integer> entry : cart.getItems().entrySet()) {
            System.out.println(entry.getKey().getName() + " x " + entry.getValue() +
                    " = ₹" + (entry.getKey().getPrice() * entry.getValue()));
        }
        System.out.println("Subtotal: ₹" + cart.getSubtotal());
        System.out.println("Discount: ₹" + cart.getDiscount());
        System.out.println("Total: ₹" + cart.getTotal());

        String address = currentUser.getSavedAddress();
        System.out.println("Saved delivery address: " + (address == null ? "None" : address));
        System.out.print("Use saved address? (y/n): ");
        String useSaved = scanner.nextLine().trim().toLowerCase();
        if (!useSaved.equals("y")) {
            System.out.print("Enter new delivery address: ");
            address = scanner.nextLine().trim();
            System.out.print("Save this address for future? (y/n): ");
            String saveAddr = scanner.nextLine().trim().toLowerCase();
            if (saveAddr.equals("y")) currentUser.setSavedAddress(address);
        }
        if (address == null || address.isBlank()) {
            System.out.println("Address is required to place order.");
            return;
        }

        System.out.print("Confirm purchase? (y/n): ");
        String confirm = scanner.nextLine().trim().toLowerCase();
        if (!confirm.equals("y")) {
            System.out.println("Order cancelled.");
            return;
        }

        Order order = new Order(new HashMap<>(cart.getItems()), address, cart.getTotal());
        currentUser.addOrder(order);
        cart.clearCart();
        DataHandler.saveData(users);
        System.out.println("Order placed successfully!");
        System.out.println(order);
    }

    private static void viewOrderHistory() {
        System.out.println("\nYour Orders:");
        currentUser.printOrders();
    }
}
