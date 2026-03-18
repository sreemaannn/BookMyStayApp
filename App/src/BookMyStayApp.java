import java.util.*;

// Represents an Add-On Service
class Service {
    String serviceName;
    double cost;

    Service(String serviceName, double cost) {
        this.serviceName = serviceName;
        this.cost = cost;
    }

    @Override
    public String toString() {
        return serviceName + " (₹" + cost + ")";
    }
}

public class BookMyStayApp {

    // Map: Reservation ID -> List of Services
    private static Map<String, List<Service>> reservationServices = new HashMap<>();

    public static void main(String[] args) {

        // Example reservation IDs (from previous use case)
        String res1 = "S1";
        String res2 = "D2";

        // Add services
        addService(res1, new Service("Breakfast", 500));
        addService(res1, new Service("Spa", 1500));

        addService(res2, new Service("Airport Pickup", 800));

        // Display services
        displayServices(res1);
        displayServices(res2);

        // Show total cost
        System.out.println("\nTotal Add-On Cost for " + res1 + ": ₹" + calculateTotalCost(res1));
        System.out.println("Total Add-On Cost for " + res2 + ": ₹" + calculateTotalCost(res2));
    }

    // Add service to reservation
    private static void addService(String reservationId, Service service) {

        reservationServices.putIfAbsent(reservationId, new ArrayList<>());
        reservationServices.get(reservationId).add(service);

        System.out.println("Added " + service.serviceName + " to Reservation " + reservationId);
    }

    // Display services for a reservation
    private static void displayServices(String reservationId) {

        System.out.println("\nServices for Reservation " + reservationId + ":");

        List<Service> services = reservationServices.get(reservationId);

        if (services == null || services.isEmpty()) {
            System.out.println("No services selected.");
            return;
        }

        for (Service s : services) {
            System.out.println("- " + s);
        }
    }

    // Calculate total cost
    private static double calculateTotalCost(String reservationId) {

        double total = 0;

        List<Service> services = reservationServices.get(reservationId);

        if (services != null) {
            for (Service s : services) {
                total += s.cost;
            }
        }

        return total;
    }
}