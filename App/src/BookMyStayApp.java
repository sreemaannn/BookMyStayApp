import java.util.HashMap;
import java.util.Map;

// Room Domain Model
class Room {

    private String type;
    private double price;
    private String amenities;

    public Room(String type, double price, String amenities) {
        this.type = type;
        this.price = price;
        this.amenities = amenities;
    }

    public String getType() {
        return type;
    }

    public double getPrice() {
        return price;
    }

    public String getAmenities() {
        return amenities;
    }

    public void displayDetails() {
        System.out.println("Room Type: " + type);
        System.out.println("Price: $" + price);
        System.out.println("Amenities: " + amenities);
    }
}

// Centralized Inventory Manager
class RoomInventory {

    private Map<String, Integer> inventory;

    public RoomInventory() {
        inventory = new HashMap<>();
    }

    public void addRoomType(String roomType, int count) {
        inventory.put(roomType, count);
    }

    public int getAvailability(String roomType) {
        return inventory.getOrDefault(roomType, 0);
    }

    public Map<String, Integer> getAllAvailability() {
        return inventory;
    }
}

// Read-Only Search Service
class SearchService {

    private RoomInventory inventory;
    private Map<String, Room> rooms;

    public SearchService(RoomInventory inventory, Map<String, Room> rooms) {
        this.inventory = inventory;
        this.rooms = rooms;
    }

    public void searchAvailableRooms() {

        System.out.println("\nAvailable Rooms:\n");

        for (String roomType : rooms.keySet()) {

            int available = inventory.getAvailability(roomType);

            // Defensive check: only show available rooms
            if (available > 0) {

                Room room = rooms.get(roomType);

                room.displayDetails();
                System.out.println("Available Count: " + available);
                System.out.println("----------------------------");
            }
        }
    }
}

// Main System
public class BookMyStayApp {

    public static void main(String[] args) {

        // Initialize inventory
        RoomInventory inventory = new RoomInventory();
        inventory.addRoomType("Single", 5);
        inventory.addRoomType("Double", 0);
        inventory.addRoomType("Suite", 2);

        // Create room objects
        Map<String, Room> rooms = new HashMap<>();

        rooms.put("Single", new Room("Single", 100, "WiFi, TV, AC"));
        rooms.put("Double", new Room("Double", 150, "WiFi, TV, AC, Mini Bar"));
        rooms.put("Suite", new Room("Suite", 300, "WiFi, TV, AC, Jacuzzi"));

        // Create search service
        SearchService searchService = new SearchService(inventory, rooms);

        // Guest performs search
        searchService.searchAvailableRooms();
    }
}
