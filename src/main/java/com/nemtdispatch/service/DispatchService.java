package com.nemtdispatch.service;

import com.nemtdispatch.entity.*;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Random;

@Service
public class DispatchService {
    private final TripService tripService;
    private final FleetService fleetService;
    private final Random random = new Random();

    public DispatchService(TripService tripService, FleetService fleetService) {
        this.tripService = tripService;
        this.fleetService = fleetService;
    }

    public int dispatchTrips() {
        List<Trip> requestedTrips = tripService.getTripsByStatus(Trip.TripStatus.REQUESTED);
        List<Driver> availableDrivers = fleetService.getAvailableDrivers();
        List<Vehicle> availableVehicles = fleetService.getAvailableVehicles();

        int dispatched = 0;

        for (Trip trip : requestedTrips) {
            if (!availableDrivers.isEmpty() && !availableVehicles.isEmpty()) {
                // Simple assignment: pick random available driver and vehicle
                Driver driver = availableDrivers.get(random.nextInt(availableDrivers.size()));
                Vehicle vehicle = availableVehicles.get(random.nextInt(availableVehicles.size()));

                // Assign trip
                trip.setDriverId(driver.getId());
                trip.setDriverName(driver.getName());
                trip.setVehicleId(vehicle.getId());
                trip.setStatus(Trip.TripStatus.SCHEDULED);

                // Update availability
                driver.setAvailable(false);
                driver.setStatus(Driver.DriverStatus.BUSY);
                vehicle.setStatus(Vehicle.VehicleStatus.IN_USE);

                // Remove from available lists
                availableDrivers.remove(driver);
                availableVehicles.remove(vehicle);

                dispatched++;

                System.out.println("🚀 Dispatched trip " + trip.getId() + " to driver " + driver.getName());
            } else {
                break; // No more available resources
            }
        }

        System.out.println("📋 Dispatch complete: " + dispatched + " trips assigned");
        return dispatched;
    }
}
