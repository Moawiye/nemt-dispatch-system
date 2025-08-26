package com.nemtdispatch.service;

import com.nemtdispatch.entity.Trip;
import org.springframework.stereotype.Service;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@Service
public class TripService {
    private final Map<String, Trip> trips = new ConcurrentHashMap<>();

    public Trip createTrip(Trip trip) {
        if (trip.getId() == null) {
            trip.setId("TRIP-" + System.currentTimeMillis());
        }
        trips.put(trip.getId(), trip);
        System.out.println("✅ Trip created: " + trip.getId() + " for " + trip.getCustomerName());
        return trip;
    }

    public Trip updateTripStatus(String tripId, Trip.TripStatus status) {
        Trip trip = trips.get(tripId);
        if (trip != null) {
            trip.setStatus(status);
            System.out.println("📝 Trip " + tripId + " status updated to: " + status);
        }
        return trip;
    }

    public Trip getTripById(String tripId) {
        return trips.get(tripId);
    }

    public List<Trip> getAllTrips() {
        return new ArrayList<>(trips.values());
    }

    public List<Trip> getTripsByStatus(Trip.TripStatus status) {
        return trips.values().stream()
                .filter(trip -> trip.getStatus() == status)
                .collect(Collectors.toList());
    }

    public boolean cancelTrip(String tripId) {
        Trip trip = trips.get(tripId);
        if (trip != null && trip.getStatus() != Trip.TripStatus.COMPLETED) {
            trip.setStatus(Trip.TripStatus.CANCELLED);
            System.out.println("❌ Trip cancelled: " + tripId);
            return true;
        }
        return false;
    }

    public long getTripCountByStatus(Trip.TripStatus status) {
        return trips.values().stream()
                .filter(trip -> trip.getStatus() == status)
                .count();
    }
}
