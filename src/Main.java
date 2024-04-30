import java.time.LocalDateTime;
import java.util.*;

class Dish {
    private String title;
    private double price;
    private int preparationTime;
    private String image;

    public Dish(String title, double price, int preparationTime, String image) {
        this.title = title;
        this.price = price;
        this.preparationTime = preparationTime;
        this.image = image;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getPreparationTime() {
        return preparationTime;
    }

    public void setPreparationTime(int preparationTime) {
        this.preparationTime = preparationTime;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}

class Order {
    private Dish dish;
    private int quantity;
    private LocalDateTime orderedTime;
    private LocalDateTime fulfilmentTime;
    private boolean paid;

    public Order(Dish dish, int quantity, LocalDateTime orderedTime) {
        this.dish = dish;
        this.quantity = quantity;
        this.orderedTime = orderedTime;
        this.fulfilmentTime = null;
        this.paid = false;
    }

    public Dish getDish() {
        return dish;
    }

    public void setDish(Dish dish) {
        this.dish = dish;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public LocalDateTime getOrderedTime() {
        return orderedTime;
    }

    public void setOrderedTime(LocalDateTime orderedTime) {
        this.orderedTime = orderedTime;
    }

    public LocalDateTime getFulfilmentTime() {
        return fulfilmentTime;
    }

    public void setFulfilmentTime(LocalDateTime fulfilmentTime) {
        this.fulfilmentTime = fulfilmentTime;
    }

    public boolean isPaid() {
        return paid;
    }

    public void setPaid(boolean paid) {
        this.paid = paid;
    }
}

class RestaurantManager {
    private List<Order> orders;

    public RestaurantManager() {
        this.orders = new ArrayList<>();
    }

    public int getIncompleteOrdersCount() {
        int count = 0;
        for (Order order : orders) {
            if (order.getFulfilmentTime() == null) {
                count++;
            }
        }
        return count;
    }

    public List<Order> getOrdersSortedByOrderedTime() {
        List<Order> sortedOrders = new ArrayList<>(orders);
        sortedOrders.sort(Comparator.comparing(Order::getOrderedTime));
        return sortedOrders;
    }

    public double getAverageOrderProcessingTime() {
        int totalProcessingTime = 0;
        int completedOrdersCount = 0;
        for (Order order : orders) {
            if (order.getFulfilmentTime() != null) {
                totalProcessingTime += order.getFulfilmentTime().getMinute() - order.getOrderedTime().getMinute();
                completedOrdersCount++;
            }
        }
        return completedOrdersCount > 0 ? (double) totalProcessingTime / completedOrdersCount : 0;
    }

    public List<Order> getOrders() {
        return orders;
    }
}

public class Main {
    public static void loadFromDisk() {
        System.out.println("Načítám stav evidence z disku...");
    }

    public static void saveToDisk() {
        System.out.println("Ukládám změny na disk...");
    }

    public static void main(String[] args) {
        loadFromDisk();

        Dish dish1 = new Dish("Kuřecí řízek obalovaný", 120, 20, "kuřecí-rizek");
        Dish dish2 = new Dish("Hranolky", 50, 10, "hranolky");
        Dish dish3 = new Dish("Pstruh na víně", 180, 25, "pstruh");
        Dish dish4 = new Dish("Kofola", 30, 5, "kofola");

        LocalDateTime orderedTime1 = LocalDateTime.of(2023, 12, 4, 12, 0);
        Order order1 = new Order(dish1, 2, orderedTime1);
        Order order2 = new Order(dish2, 1, orderedTime1);
        Order order3 = new Order(dish4, 2, orderedTime1);
        RestaurantManager manager = new RestaurantManager();
        manager.getOrders().add(order1);
        manager.getOrders().add(order2);
        manager.getOrders().add(order3);

        double totalCost = 0;
        for (Order order : manager.getOrders()) {
            totalCost += order.getDish().getPrice() * order.getQuantity();
        }
        System.out.println("Celková cena konzumace pro stůl číslo 15: " + totalCost + " Kč");

        System.out.println("Počet nedokončených objednávek: " + manager.getIncompleteOrdersCount());
        System.out.println("Seřazené objednávky podle času zadání:");
        for (Order order : manager.getOrdersSortedByOrderedTime()) {
            System.out.println(order.getDish().getTitle() + " (" + order.getQuantity() + "x) " + order.getOrderedTime());
        }

        saveToDisk();
    }
}
