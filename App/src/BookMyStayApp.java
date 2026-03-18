import java.io.*;
import java.util.*;

// Reservation class (Serializable)
class Reservation implements Serializable {
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

// Wrapper class to persist system state
class SystemState implements Serializable {
    Map<String, Integer> inventory;
    List<Reservation> bookings;

    SystemState(Map<String, Integer> inventory, List<Reservation> bookings) {
        this.inventory = inventory;
        this.bookings = bookings;
    }
}

// Persistence Service
class PersistenceService {

    private static final String FILE_NAME = "system_state.dat";

    // Save state to file
    public static void save(SystemState state) {
        try (ObjectOutputStream oos =
                     new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {

            oos.writeObject(state);
            System.out.println("System state saved successfully.");

        } catch (IOException e) {
            System.out.println("Error saving state: " + e.getMessage());
        }
    }

    // Load state from file
    public static SystemState load() {
        try (ObjectInputStream ois =
                     new ObjectInputStream(new FileInputStream(FILE_NAME))) {

            System.out.println("System state loaded successfully.");
            return (SystemState) ois.readObject();

        } catch (FileNotFoundException e) {
            System.out.println("No previous state found. Starting fresh.");
        } catch (Exception e) {
            System.out.println("Error loading state. Starting with safe defaults.");
        }

        return null;
    }
}

// Main Class
public class BookMyStayApp {

    public static void main(String[] args) {

        Map<String, Integer> inventory;
        List<Reservation> bookings;

        // Try loading previous state
        SystemState state = PersistenceService.load();

        if (state != null) {
            inventory = state.inventory;
            bookings = state.bookings;
        } else {
            // Initialize fresh state
            inventory = new HashMap<>();
            inventory.put("Single", 2);
            inventory.put("Double", 1);

            bookings = new ArrayList<>();

            bookings.add(new Reservation("S1", "Alice", "Single"));
            bookings.add(new Reservation("D2", "Bob", "Double"));
        }

        // Display current state
        System.out.println("\nCurrent Inventory: " + inventory);
        System.out.println("Current Bookings: " + bookings);

        // Simulate new booking
        bookings.add(new Reservation("S3", "Charlie", "Single"));
        inventory.put("Single", inventory.get("Single") - 1);

        System.out.println("\nAfter New Booking:");
        System.out.println("Inventory: " + inventory);
        System.out.println("Bookings: " + bookings);

        // Save state before shutdown
        PersistenceService.save(new SystemState(inventory, bookings));
    }
}