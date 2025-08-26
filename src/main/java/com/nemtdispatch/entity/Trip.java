package com.nemtdispatch.entity;

import java.time.LocalDateTime;

public class Trip {
    private String id;
    private String customerId;
    private String customerName;
    private String customerPhone;
    private String pickupAddress;
    private String dropoffAddress;
    private LocalDateTime scheduledTime;
    private TripStatus status;
    private String driverId;
    private String driverName;
    private String vehicleId;
    private double billableAmount;
    private String payerType;

    public enum TripStatus {
        REQUESTED, SCHEDULED, EN_ROUTE, PICKED_UP, COMPLETED, CANCELLED, NO_SHOW
    }

    // Default constructor
    public Trip() {
        this.status = TripStatus.REQUESTED;
        this.scheduledTime = LocalDateTime.now();
    }

    // Constructor with parameters
    public Trip(String customerId, String customerName, String customerPhone,
                String pickupAddress, String dropoffAddress,
                LocalDateTime scheduledTime, double billableAmount, String payerType) {
        this();
        this.customerId = customerId;
        this.customerName = customerName;
        this.customerPhone = customerPhone;
        this.pickupAddress = pickupAddress;
        this.dropoffAddress = dropoffAddress;
        this.scheduledTime = scheduledTime;
        this.billableAmount = billableAmount;
        this.payerType = payerType;
        this.id = "TRIP-" + System.currentTimeMillis();
    }

    // Getters and Setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getCustomerId() { return customerId; }
    public void setCustomerId(String customerId) { this.customerId = customerId; }

    public String getCustomerName() { return customerName; }
    public void setCustomerName(String customerName) { this.customerName = customerName; }

    public String getCustomerPhone() { return customerPhone; }
    public void setCustomerPhone(String customerPhone) { this.customerPhone = customerPhone; }

    public String getPickupAddress() { return pickupAddress; }
    public void setPickupAddress(String pickupAddress) { this.pickupAddress = pickupAddress; }

    public String getDropoffAddress() { return dropoffAddress; }
    public void setDropoffAddress(String dropoffAddress) { this.dropoffAddress = dropoffAddress; }

    public LocalDateTime getScheduledTime() { return scheduledTime; }
    public void setScheduledTime(LocalDateTime scheduledTime) { this.scheduledTime = scheduledTime; }

    public TripStatus getStatus() { return status; }
    public void setStatus(TripStatus status) { this.status = status; }

    public String getDriverId() { return driverId; }
    public void setDriverId(String driverId) { this.driverId = driverId; }

    public String getDriverName() { return driverName; }
    public void setDriverName(String driverName) { this.driverName = driverName; }

    public String getVehicleId() { return vehicleId; }
    public void setVehicleId(String vehicleId) { this.vehicleId = vehicleId; }

    public double getBillableAmount() { return billableAmount; }
    public void setBillableAmount(double billableAmount) { this.billableAmount = billableAmount; }

    public String getPayerType() { return payerType; }
    public void setPayerType(String payerType) { this.payerType = payerType; }
}
