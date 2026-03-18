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
        return "Reservation ID: " + reservationId +
                ", Customer: " + customerName +
                ", Room Type: " + roomType;
    }
}

// Booking History (stores confirmed reservations)
class BookingHistory {
    private List<Reservation> history = new ArrayList<>();

    // Add confirmed booking
    public void addReservation(Reservation r) {
        history.add(r); // maintains insertion order
    }

    // Retrieve all bookings
    public List<Reservation> getAllReservations() {
        return history;
    }
}

// Reporting Service
class BookingReportService {

    // Display all bookings
    public static void displayAllBookings(List<Reservation> reservations) {
        System.out.println("\n--- Booking History ---");

        for (Reservation r : reservations) {
            System.out.println(r);
        }
    }

    // Generate summary report
    public static void generateSummary(List<Reservation> reservations) {
        System.out.println("\n--- Summary Report ---");

        Map<String, Integer> roomCount = new HashMap<>();

        for (Reservation r : reservations) {
            roomCount.put(r.roomType,
                    roomCount.getOrDefault(r.roomType, 0) + 1);
        }

        for (String type : roomCount.keySet()) {
            System.out.println(type + " Rooms Booked: " + roomCount.get(type));
        }

        System.out.println("Total Reservations: " + reservations.size());
    }
}

// Main class
public class BookMyStayApp {

    public static void main(String[] args) {

        BookingHistory history = new BookingHistory();

        // Simulating confirmed bookings
        history.addReservation(new Reservation("S1", "Alice", "Single"));
        history.addReservation(new Reservation("D2", "Bob", "Double"));
        history.addReservation(new Reservation("S3", "Charlie", "Single"));
        history.addReservation(new Reservation("SU4", "David", "Suite"));

        // Admin views booking history
        List<Reservation> allBookings = history.getAllReservations();

        BookingReportService.displayAllBookings(allBookings);
        BookingReportService.generateSummary(allBookings);
    }
}