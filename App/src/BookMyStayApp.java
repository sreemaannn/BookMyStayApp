import java.util.*;

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
}

// Centralized Inventory (State Holder)
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
}

// Reservation Request (Guest Intent)
class Reservation {

    private String guestName;
    private String roomType;

    public Reservation(String guestName, String roomType) {
        this.guestName = guestName;
        this.roomType = roomType;
    }

    public String getGuestName() {
        return guestName;
    }

    public String getRoomType() {
        return roomType;
    }

    public void displayRequest() {
        System.out.println("Guest: " + guestName + " requested " + roomType + " room");
    }
}

// Booking Request Queue (FIFO)
class BookingRequestQueue {

    private Queue<Reservation> requestQueue;

    public BookingRequestQueue() {
        requestQueue = new LinkedList<>();
    }

    // Add booking request
    public void addRequest(Reservation reservation) {
        requestQueue.offer(reservation);
        System.out.println("Request added to queue for " + reservation.getGuestName());
    }

    // View queue contents
    public void displayQueue() {

        System.out.println("\nCurrent Booking Request Queue:");

        if (requestQueue.isEmpty()) {
            System.out.println("No pending requests.");
            return;
        }

        for (Reservation r : requestQueue) {
            r.displayRequest();
        }
    }

    // Get next request (not processing yet)
    public Reservation peekNextRequest() {
        return requestQueue.peek();
    }
}

// Main System Simulation
public class BookMyStayApp {

    public static void main(String[] args) {

        // Initialize inventory (state holder)
        RoomInventory inventory = new RoomInventory();
        inventory.addRoomType("Single", 5);
        inventory.addRoomType("Double", 3);
        inventory.addRoomType("Suite", 2);

        // Room definitions
        Map<String, Room> rooms = new HashMap<>();
        rooms.put("Single", new Room("Single", 100, "WiFi, TV"));
        rooms.put("Double", new Room("Double", 150, "WiFi, TV, Mini Bar"));
        rooms.put("Suite", new Room("Suite", 300, "WiFi, TV, Jacuzzi"));

        // Booking request queue
        BookingRequestQueue bookingQueue = new BookingRequestQueue();

        // Guests submit booking requests
        Reservation r1 = new Reservation("Alice", "Single");
        Reservation r2 = new Reservation("Bob", "Suite");
        Reservation r3 = new Reservation("Charlie", "Double");
        Reservation r4 = new Reservation("David", "Single");

        bookingQueue.addRequest(r1);
        bookingQueue.addRequest(r2);
        bookingQueue.addRequest(r3);
        bookingQueue.addRequest(r4);

        // Display queue (FIFO order preserved)
        bookingQueue.displayQueue();

        // Show next request to be processed
        Reservation next = bookingQueue.peekNextRequest();
        if (next != null) {
            System.out.println("\nNext request to process:");
            next.displayRequest();
        }

        // IMPORTANT: No inventory changes occur here
        System.out.println("\nInventory remains unchanged at request intake stage.");
    }
}
