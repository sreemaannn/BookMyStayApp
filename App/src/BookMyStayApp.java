import java.util.*;

// Custom Exception for invalid booking
class InvalidBookingException extends Exception {
    public InvalidBookingException(String message) {
        super(message);
    }
}

// Booking Request class
class BookingRequest {
    String customerName;
    String roomType;

    BookingRequest(String customerName, String roomType) {
        this.customerName = customerName;
        this.roomType = roomType;
    }
}

// Validator Class
class BookingValidator {

    // Validate booking request
    public static void validate(BookingRequest request, Map<String, Integer> inventory)
            throws InvalidBookingException {

        // Check null or empty input
        if (request.customerName == null || request.customerName.isEmpty()) {
            throw new InvalidBookingException("Customer name cannot be empty.");
        }

        // Validate room type
        if (!inventory.containsKey(request.roomType)) {
            throw new InvalidBookingException("Invalid room type: " + request.roomType);
        }

        // Check availability
        if (inventory.get(request.roomType) <= 0) {
            throw new InvalidBookingException("No rooms available for type: " + request.roomType);
        }
    }
}

// Main Class
public class BookMyStayApp {

    private static Map<String, Integer> inventory = new HashMap<>();

    public static void main(String[] args) {

        // Initialize inventory
        inventory.put("Single", 1);
        inventory.put("Double", 1);

        // Test booking requests
        List<BookingRequest> requests = new ArrayList<>();

        requests.add(new BookingRequest("Alice", "Single"));   // valid
        requests.add(new BookingRequest("", "Double"));         // invalid name
        requests.add(new BookingRequest("Bob", "Suite"));       // invalid type
        requests.add(new BookingRequest("Charlie", "Single"));  // no availability

        // Process requests safely
        for (BookingRequest req : requests) {
            try {
                processBooking(req);
            } catch (InvalidBookingException e) {
                System.out.println("Booking Failed: " + e.getMessage());
            }
        }
    }

    // Process booking with validation
    private static void processBooking(BookingRequest request)
            throws InvalidBookingException {

        // Fail-fast validation
        BookingValidator.validate(request, inventory);

        // If validation passes → proceed
        inventory.put(request.roomType, inventory.get(request.roomType) - 1);

        System.out.println("Booking Confirmed for " + request.customerName +
                " (" + request.roomType + ")");
    }
}