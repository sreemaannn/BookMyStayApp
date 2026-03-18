import java.util.*;

class BookingRequest {
    String customerName;
    String roomType;

    BookingRequest(String customerName, String roomType) {
        this.customerName = customerName;
        this.roomType = roomType;
    }
}

public class BookMyStayApp{

    // FIFO Queue for booking requests
    private static Queue<BookingRequest> requestQueue = new LinkedList<>();

    // Inventory: room type -> available count
    private static Map<String, Integer> inventory = new HashMap<>();

    // Track allocated room IDs (global uniqueness)
    private static Set<String> allocatedRoomIds = new HashSet<>();

    // Map room type -> assigned room IDs
    private static Map<String, Set<String>> roomAllocations = new HashMap<>();

    // Room ID generator counter
    private static int roomCounter = 1;

    public static void main(String[] args) {

        // Initialize inventory
        inventory.put("Single", 2);
        inventory.put("Double", 2);
        inventory.put("Suite", 1);

        // Initialize allocation map
        roomAllocations.put("Single", new HashSet<>());
        roomAllocations.put("Double", new HashSet<>());
        roomAllocations.put("Suite", new HashSet<>());

        // Add booking requests (FIFO)
        requestQueue.add(new BookingRequest("Alice", "Single"));
        requestQueue.add(new BookingRequest("Bob", "Double"));
        requestQueue.add(new BookingRequest("Charlie", "Single"));
        requestQueue.add(new BookingRequest("David", "Suite"));
        requestQueue.add(new BookingRequest("Eve", "Suite")); // should fail

        // Process bookings
        processBookings();

        // Display final allocations
        displayAllocations();
    }

    // Process queue
    private static void processBookings() {
        while (!requestQueue.isEmpty()) {

            BookingRequest request = requestQueue.poll(); // FIFO
            System.out.println("\nProcessing request for: " + request.customerName);

            allocateRoom(request);
        }
    }

    // Core allocation logic
    private static void allocateRoom(BookingRequest request) {
        String type = request.roomType;

        // Check availability
        if (inventory.get(type) > 0) {

            // Generate unique room ID
            String roomId = generateRoomId(type);

            // Ensure uniqueness
            if (!allocatedRoomIds.contains(roomId)) {

                // Atomic operation: assign + update inventory
                allocatedRoomIds.add(roomId);
                roomAllocations.get(type).add(roomId);

                inventory.put(type, inventory.get(type) - 1);

                System.out.println("Booking Confirmed!");
                System.out.println("Customer: " + request.customerName);
                System.out.println("Room Type: " + type);
                System.out.println("Room ID: " + roomId);
            }

        } else {
            System.out.println("Booking Failed for " + request.customerName + " (No rooms available)");
        }
    }

    // Unique Room ID generator
    private static String generateRoomId(String type) {
        return type.substring(0, 1).toUpperCase() + roomCounter++;
    }

    // Display results
    private static void displayAllocations() {
        System.out.println("\nFinal Room Allocations:");

        for (String type : roomAllocations.keySet()) {
            System.out.println(type + " Rooms: " + roomAllocations.get(type));
        }

        System.out.println("\nRemaining Inventory:");
        for (String type : inventory.keySet()) {
            System.out.println(type + ": " + inventory.get(type));
        }
    }
}