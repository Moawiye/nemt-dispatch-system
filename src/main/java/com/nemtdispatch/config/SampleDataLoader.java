package com.nemtdispatch.config;

import com.nemtdispatch.entity.*;
import com.nemtdispatch.service.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import java.time.LocalDateTime;

@Component
public class SampleDataLoader implements CommandLineRunner {

    private final TripService tripService;
    private final FleetService fleetService;

    public SampleDataLoader(TripService tripService, FleetService fleetService) {
        this.tripService = tripService;
        this.fleetService = fleetService;
    }

    @Override
    public void run(String... args) throws Exception {
        System.out.println("🔄 Loading sample data...");

        // Add sample drivers
        Driver driver1 = new Driver("John Smith", "555-0101", "DL123456789");
        Driver driver2 = new Driver("Sarah Johnson", "555-0102", "DL987654321");
        Driver driver3 = new Driver("Mike Wilson", "555-0103", "DL456789123");

        fleetService.addDriver(driver1);
        fleetService.addDriver(driver2);
        fleetService.addDriver(driver3);

        // Add sample vehicles
        Vehicle vehicle1 = new Vehicle("ABC-123", "Ford", "Transit Connect", 4, true);
        Vehicle vehicle2 = new Vehicle("XYZ-789", "Chevrolet", "Express", 6, false);
        Vehicle vehicle3 = new Vehicle("DEF-456", "Ford", "Transit", 8, true);

        fleetService.addVehicle(vehicle1);
        fleetService.addVehicle(vehicle2);
        fleetService.addVehicle(vehicle3);

        // Add sample trips
        Trip trip1 = new Trip("CUST001", "Mary Wilson", "555-1001",
                "123 Main St, Raleigh, NC", "Duke University Hospital",
                LocalDateTime.now().plusHours(2), 25.50, "MEDICAID");

        Trip trip2 = new Trip("CUST002", "Robert Brown", "555-1002",
                "789 Oak Ave, Cary, NC", "WakeMed Medical Center",
                LocalDateTime.now().plusHours(3), 18.75, "PRIVATE");

        Trip trip3 = new Trip("CUST003", "Alice Davis", "555-1003",
                "456 Pine St, Durham, NC", "UNC Medical Center",
                LocalDateTime.now().plusHours(1), 32.00, "INSURANCE");

        Trip trip4 = new Trip("CUST004", "James Miller", "555-1004",
                "321 Elm St, Chapel Hill, NC", "Rex Hospital",
                LocalDateTime.now().plusHours(4), 28.25, "MEDICAID");

        tripService.createTrip(trip1);
        tripService.createTrip(trip2);
        tripService.createTrip(trip3);
        tripService.createTrip(trip4);

        System.out.println("✅ Sample data loaded successfully!");
        System.out.println("🌐 Open your browser to: http://localhost:8080");
        System.out.println("📊 Dashboard will show all the sample data");
    }
}
