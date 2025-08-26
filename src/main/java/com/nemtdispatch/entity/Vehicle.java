package com.nemtdispatch.entity;

public class Vehicle {
    private String id;
    private String plateNumber;
    private String make;
    private String model;
    private int capacity;
    private boolean wheelchairAccessible;
    private VehicleStatus status;

    public enum VehicleStatus {
        AVAILABLE, IN_USE, MAINTENANCE, OUT_OF_SERVICE
    }

    public Vehicle() {
        this.status = VehicleStatus.AVAILABLE;
    }

    public Vehicle(String plateNumber, String make, String model, int capacity, boolean wheelchairAccessible) {
        this();
        this.id = "VEH-" + System.currentTimeMillis();
        this.plateNumber = plateNumber;
        this.make = make;
        this.model = model;
        this.capacity = capacity;
        this.wheelchairAccessible = wheelchairAccessible;
    }

    // Getters and Setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getPlateNumber() { return plateNumber; }
    public void setPlateNumber(String plateNumber) { this.plateNumber = plateNumber; }

    public String getMake() { return make; }
    public void setMake(String make) { this.make = make; }

    public String getModel() { return model; }
    public void setModel(String model) { this.model = model; }

    public int getCapacity() { return capacity; }
    public void setCapacity(int capacity) { this.capacity = capacity; }

    public boolean isWheelchairAccessible() { return wheelchairAccessible; }
    public void setWheelchairAccessible(boolean wheelchairAccessible) { this.wheelchairAccessible = wheelchairAccessible; }

    public VehicleStatus getStatus() { return status; }
    public void setStatus(VehicleStatus status) { this.status = status; }

    public String getDisplayName() {
        return make + " " + model + " (" + plateNumber + ")";
    }
}
