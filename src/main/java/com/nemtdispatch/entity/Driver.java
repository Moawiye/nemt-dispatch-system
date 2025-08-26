package com.nemtdispatch.entity;

import java.time.LocalDateTime;

public class Driver {
    private String id;
    private String name;
    private String phoneNumber;
    private String licenseNumber;
    private boolean available;
    private String vehicleId;
    private DriverStatus status;

    public enum DriverStatus {
        AVAILABLE, BUSY, OFF_DUTY
    }

    public Driver() {
        this.available = true;
        this.status = DriverStatus.AVAILABLE;
    }

    public Driver(String name, String phoneNumber, String licenseNumber) {
        this();
        this.id = "DRV-" + System.currentTimeMillis();
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.licenseNumber = licenseNumber;
    }

    // Getters and Setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getPhoneNumber() { return phoneNumber; }
    public void setPhoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber; }

    public String getLicenseNumber() { return licenseNumber; }
    public void setLicenseNumber(String licenseNumber) { this.licenseNumber = licenseNumber; }

    public boolean isAvailable() { return available; }
    public void setAvailable(boolean available) { this.available = available; }

    public String getVehicleId() { return vehicleId; }
    public void setVehicleId(String vehicleId) { this.vehicleId = vehicleId; }

    public DriverStatus getStatus() { return status; }
    public void setStatus(DriverStatus status) { this.status = status; }
}
