package com.nemtdispatch.service;

import com.nemtdispatch.entity.Driver;
import com.nemtdispatch.entity.Vehicle;
import org.springframework.stereotype.Service;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@Service
public class FleetService {
    private final Map<String, Driver> drivers = new ConcurrentHashMap<>();
    private final Map<String, Vehicle> vehicles = new ConcurrentHashMap<>();

    public Driver addDriver(Driver driver) {
        if (driver.getId() == null) {
            driver.setId("DRV-" + System.currentTimeMillis());
        }
        drivers.put(driver.getId(), driver);
        System.out.println("👨‍💼 Driver added: " + driver.getName());
        return driver;
    }

    public Vehicle addVehicle(Vehicle vehicle) {
        if (vehicle.getId() == null) {
            vehicle.setId("VEH-" + System.currentTimeMillis());
        }
        vehicles.put(vehicle.getId(), vehicle);
        System.out.println("🚐 Vehicle added: " + vehicle.getDisplayName());
        return vehicle;
    }

    public Driver getDriver(String driverId) {
        return drivers.get(driverId);
    }

    public Vehicle getVehicle(String vehicleId) {
        return vehicles.get(vehicleId);
    }

    public List<Driver> getAllDrivers() {
        return new ArrayList<>(drivers.values());
    }

    public List<Vehicle> getAllVehicles() {
        return new ArrayList<>(vehicles.values());
    }

    public List<Driver> getAvailableDrivers() {
        return drivers.values().stream()
                .filter(Driver::isAvailable)
                .collect(Collectors.toList());
    }

    public List<Vehicle> getAvailableVehicles() {
        return vehicles.values().stream()
                .filter(vehicle -> vehicle.getStatus() == Vehicle.VehicleStatus.AVAILABLE)
                .collect(Collectors.toList());
    }
}
