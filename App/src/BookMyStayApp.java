import java.util.*;

// Booking Request
class BookingRequest {
    String customerName;
    String roomType;

    BookingRequest(String customerName, String roomType) {
        this.customerName = customerName;
        this.roomType = roomType;
    }
}

// Shared Booking System (Critical Section inside)
class BookingSystem {

    private Queue<BookingRequest> queue = new LinkedList<>();
    private Map<String, Integer> inventory = new HashMap<>();
    private Set<String> allocatedRooms = new HashSet<>();

    private int roomCounter = 1;

    public BookingSystem() {
        inventory.put("Single", 2);
        inventory.put("Double", 1);
    }

    // Add request (synchronized)
    public synchronized void addRequest(BookingRequest request) {
        queue.add(request);
        System.out.println("Request Added: " + request.customerName);
    }

    // Process booking (critical section)
    public synchronized void processBooking() {

        if (queue.isEmpty()) return;

        BookingRequest request = queue.poll();

        System.out.println(Thread.currentThread().getName() +
                " processing " + request.customerName);

        // Check availability
        if (inventory.getOrDefault(request.roomType, 0) > 0) {

            String roomId = request.roomType.charAt(0) + "" + roomCounter++;

            // Prevent duplicate allocation
            if (!allocatedRooms.contains(roomId)) {

                allocatedRooms.add(roomId);

                // Update inventory safely
                inventory.put(request.roomType,
                        inventory.get(request.roomType) - 1);

                System.out.println("Booking Confirmed: " +
                        request.customerName + " -> " + roomId);
            }

        } else {
            System.out.println("Booking Failed (No Rooms): " +
                    request.customerName);
        }
    }

    // Display final state
    public void displayState() {
        System.out.println("\nFinal Inventory: " + inventory);
        System.out.println("Allocated Rooms: " + allocatedRooms);
    }
}

// Worker Thread
class BookingProcessor extends Thread {

    private BookingSystem system;

    BookingProcessor(BookingSystem system, String name) {
        super(name);
        this.system = system;
    }

    @Override
    public void run() {
        // Each thread tries multiple times
        for (int i = 0; i < 3; i++) {
            system.processBooking();
        }
    }
}

// Main Class
public class BookMyStayApp {

    public static void main(String[] args) {

        BookingSystem system = new BookingSystem();

        // Simulate multiple guest requests
        system.addRequest(new BookingRequest("Alice", "Single"));
        system.addRequest(new BookingRequest("Bob", "Single"));
        system.addRequest(new BookingRequest("Charlie", "Single"));
        system.addRequest(new BookingRequest("David", "Double"));
        system.addRequest(new BookingRequest("Eve", "Double"));

        // Multiple threads (concurrent processing)
        Thread t1 = new BookingProcessor(system, "Thread-1");
        Thread t2 = new BookingProcessor(system, "Thread-2");
        Thread t3 = new BookingProcessor(system, "Thread-3");

        t1.start();
        t2.start();
        t3.start();

        // Wait for threads to finish
        try {
            t1.join();
            t2.join();
            t3.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Final system state
        system.displayState();
    }
}