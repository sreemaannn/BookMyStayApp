import java.util.*;

// Reservation class
class Reservation {
    String reservationId;
    String customerName;
    String roomType;

    Reservation(String reservationId, String customerName, String roomType) {
        this.reservationId = reservationId;
        this.customerName = customerName;
        this.roomType = roomType;
    }

    @Override
    public String toString() {
        return reservationId + " - " + customerName + " (" + roomType + ")";
    }
}

// Cancellation Service
class CancellationService {

    // Stack for rollback (LIFO)
    private Stack<String> rollbackStack = new Stack<>();

    // Cancel booking
    public void cancelBooking(String reservationId,
                              Map<String, Reservation> bookings,
                              Map<String, Integer> inventory) {

        // Validate existence
        if (!bookings.containsKey(reservationId)) {
            System.out.println("Cancellation Failed: Reservation not found -> " + reservationId);
            return;
        }

        // Get reservation
        Reservation res = bookings.get(reservationId);

        // Push to rollback stack
        rollbackStack.push(reservationId);

        // Restore inventory
        inventory.put(res.roomType, inventory.get(res.roomType) + 1);

        // Remove booking (mark as cancelled)
        bookings.remove(reservationId);

        System.out.println("Booking Cancelled: " + reservationId);

        // Show rollback state
        System.out.println("Rollback Stack: " + rollbackStack);
    }
}

// Main Class
public class BookMyStayApp {

    public static void main(String[] args) {

        // Inventory
        Map<String, Integer> inventory = new HashMap<>();
        inventory.put("Single", 0);
        inventory.put("Double", 1);

        // Confirmed bookings
        Map<String, Reservation> bookings = new HashMap<>();

        bookings.put("S1", new Reservation("S1", "Alice", "Single"));
        bookings.put("D2", new Reservation("D2", "Bob", "Double"));

        CancellationService service = new CancellationService();

        // Cancel valid booking
        service.cancelBooking("S1", bookings, inventory);

        // Try cancelling again (invalid)
        service.cancelBooking("S1", bookings, inventory);

        // Cancel another booking
        service.cancelBooking("D2", bookings, inventory);

        // Final state
        System.out.println("\nFinal Inventory:");
        for (String type : inventory.keySet()) {
            System.out.println(type + ": " + inventory.get(type));
        }

        System.out.println("\nRemaining Bookings:");
        System.out.println(bookings);
    }
}