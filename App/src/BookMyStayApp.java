import java.util.HashMap;
import java.util.Map;

 class RoomInventory {

    // Centralized inventory storage
    private Map<String, Integer> inventory;

    // Constructor - initializes the inventory
    public RoomInventory() {
        inventory = new HashMap<>();
    }

    // Register a room type with available count
    public void addRoomType(String roomType, int count) {
        inventory.put(roomType, count);
    }

    // Retrieve availability of a room type
    public int getAvailability(String roomType) {
        return inventory.getOrDefault(roomType, 0);
    }

    // Update availability (increase or decrease)
    public void updateAvailability(String roomType, int change) {
        int current = inventory.getOrDefault(roomType, 0);
        int updated = current + change;

        if (updated < 0) {
            System.out.println("Cannot reduce below zero for " + roomType);
            return;
        }

        inventory.put(roomType, updated);
    }

    // Display the current inventory
    public void displayInventory() {
        System.out.println("Current Room Inventory:");

        for (Map.Entry<String, Integer> entry : inventory.entrySet()) {
            System.out.println(entry.getKey() + " : " + entry.getValue());
        }
    }
}
public class BookMyStayApp {

    public static void main(String[] args) {

        // Step 1: Initialize inventory system
        RoomInventory inventory = new RoomInventory();

        // Step 2: Register room types
        inventory.addRoomType("Single", 10);
        inventory.addRoomType("Double", 7);
        inventory.addRoomType("Suite", 3);

        // Step 3: Display current inventory
        inventory.displayInventory();

        // Step 4: Retrieve availability
        System.out.println("\nSingle rooms available: " + inventory.getAvailability("Single"));

        // Step 5: Update availability (simulate booking)
        inventory.updateAvailability("Single", -2);

        // Step 6: Display updated inventory
        System.out.println("\nAfter Booking:");
        inventory.displayInventory();
    }
}
