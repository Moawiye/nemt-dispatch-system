package com.nemtdispatch.view;

import com.nemtdispatch.entity.Trip;
import com.nemtdispatch.service.TripService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.datetimepicker.DateTimePicker;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@PageTitle("Trip Management")
@Route(value = "trips", layout = MainLayout.class)
public class TripView extends VerticalLayout {

    private final TripService tripService;
    private final Grid<Trip> grid = new Grid<>(Trip.class, false);

    public TripView(TripService tripService) {
        this.tripService = tripService;

        setSizeFull();
        setPadding(false);
        setSpacing(false);
        setClassName("trip-view");

        // Apply custom styling
        getElement().getStyle()
                .set("background", "var(--bg-primary)")
                .set("min-height", "100vh");

        add(createHeroHeader(), createTripGrid());
        refreshGrid();
    }

    private Div createHeroHeader() {
        Div hero = new Div();
        hero.addClassName("hero-section");
        hero.getStyle()
                .set("background", "var(--secondary-gradient)")
                .set("padding", "var(--space-12) var(--space-8)")
                .set("text-align", "center")
                .set("position", "relative")
                .set("overflow", "hidden")
                .set("margin-bottom", "var(--space-8)");

        // Add subtle background pattern
        hero.getStyle().set("background-image", 
                "radial-gradient(circle at 20% 80%, rgba(139, 92, 246, 0.1) 0%, transparent 50%), " +
                "radial-gradient(circle at 80% 20%, rgba(99, 102, 241, 0.1) 0%, transparent 50%)");

        H1 heroTitle = new H1("Trip Management");
        heroTitle.addClassName("gradient-text");
        heroTitle.getStyle()
                .set("font-size", "2.5rem")
                .set("font-weight", "700")
                .set("margin", "0 0 var(--space-4) 0")
                .set("letter-spacing", "-0.025em");

        Paragraph heroSubtitle = new Paragraph("Manage transportation requests and track trip status");
        heroSubtitle.getStyle()
                .set("font-size", "1.125rem")
                .set("color", "var(--text-secondary)")
                .set("margin", "0 0 var(--space-6) 0");

        // Action buttons
        HorizontalLayout actionButtons = new HorizontalLayout();
        actionButtons.setSpacing(true);
        actionButtons.setJustifyContentMode(JustifyContentMode.CENTER);

        Button newTripButton = new Button("🎫 New Trip", e -> openTripDialog(new Trip()));
        newTripButton.addClassName("btn btn-primary");
        newTripButton.getStyle()
                .set("font-size", "1rem")
                .set("padding", "var(--space-3) var(--space-6)");

        Button refreshButton = new Button("🔄 Refresh", e -> refreshGrid());
        refreshButton.addClassName("btn btn-secondary");
        refreshButton.getStyle()
                .set("font-size", "1rem")
                .set("padding", "var(--space-3) var(--space-6)");

        actionButtons.add(newTripButton, refreshButton);
        hero.add(heroTitle, heroSubtitle, actionButtons);
        return hero;
    }

    private Div createTripGrid() {
        Div container = new Div();
        container.addClassName("container");
        container.getStyle()
                .set("margin-bottom", "var(--space-16)");

        // Grid header
        Div gridHeader = new Div();
        gridHeader.getStyle()
                .set("display", "flex")
                .set("justify-content", "space-between")
                .set("align-items", "center")
                .set("margin-bottom", "var(--space-6)");

        H3 gridTitle = new H3("Active Trips");
        gridTitle.getStyle()
                .set("font-size", "1.5rem")
                .set("font-weight", "600")
                .set("color", "var(--text-primary)")
                .set("margin", "0");

        Span tripCount = new Span(tripService.getAllTrips().size() + " trips");
        tripCount.getStyle()
                .set("color", "var(--text-secondary)")
                .set("font-size", "0.875rem");

        gridHeader.add(gridTitle, tripCount);

        // Grid container with glass effect
        Div gridContainer = new Div();
        gridContainer.addClassName("glass-card");
        gridContainer.getStyle()
                .set("padding", "var(--space-6)")
                .set("overflow", "hidden");

        // Configure the grid
        configureGrid();
        grid.setSizeFull();
        gridContainer.add(grid);

        container.add(gridHeader, gridContainer);
        return container;
    }

    private void configureGrid() {
        // Remove default styling
        grid.getElement().getStyle().set("border", "none");
        grid.getElement().getStyle().set("background", "transparent");

        // Configure columns with modern styling
        grid.addColumn(Trip::getId).setHeader("Trip ID").setWidth("120px");
        grid.addColumn(Trip::getCustomerName).setHeader("Customer").setWidth("150px");
        grid.addColumn(Trip::getCustomerPhone).setHeader("Phone").setWidth("120px");
        grid.addColumn(Trip::getPickupAddress).setHeader("Pickup").setFlexGrow(1);
        grid.addColumn(Trip::getDropoffAddress).setHeader("Dropoff").setFlexGrow(1);
        
        // Formatted date column
        grid.addColumn(trip -> {
            if (trip.getScheduledTime() != null) {
                return trip.getScheduledTime().format(DateTimeFormatter.ofPattern("MMM dd, HH:mm"));
            }
            return "Not scheduled";
        }).setHeader("Scheduled").setWidth("140px");

        // Status column with modern badges
        grid.addColumn(new ComponentRenderer<>(trip -> {
            Div badge = new Div();
            badge.getStyle()
                    .set("padding", "var(--space-1) var(--space-3)")
                    .set("border-radius", "20px")
                    .set("font-size", "0.75rem")
                    .set("font-weight", "600")
                    .set("text-align", "center")
                    .set("min-width", "80px");

            String status = trip.getStatus().toString();
            badge.setText(status);

            // Modern status colors
            switch (trip.getStatus()) {
                case COMPLETED:
                    badge.getStyle()
                            .set("background", "rgba(16, 185, 129, 0.2)")
                            .set("color", "#10b981")
                            .set("border", "1px solid rgba(16, 185, 129, 0.3)");
                    break;
                case SCHEDULED:
                    badge.getStyle()
                            .set("background", "rgba(59, 130, 246, 0.2)")
                            .set("color", "#3b82f6")
                            .set("border", "1px solid rgba(59, 130, 246, 0.3)");
                    break;
                case REQUESTED:
                    badge.getStyle()
                            .set("background", "rgba(245, 158, 11, 0.2)")
                            .set("color", "#f59e0b")
                            .set("border", "1px solid rgba(245, 158, 11, 0.3)");
                    break;
                case CANCELLED:
                    badge.getStyle()
                            .set("background", "rgba(239, 68, 68, 0.2)")
                            .set("color", "#ef4444")
                            .set("border", "1px solid rgba(239, 68, 68, 0.3)");
                    break;
                default:
                    badge.getStyle()
                            .set("background", "rgba(156, 163, 175, 0.2)")
                            .set("color", "#9ca3af")
                            .set("border", "1px solid rgba(156, 163, 175, 0.3)");
            }

            return badge;
        })).setHeader("Status").setWidth("120px");

        grid.addColumn(Trip::getDriverName).setHeader("Driver").setWidth("120px");
        
        // Amount column with currency formatting
        grid.addColumn(trip -> {
            double amount = trip.getBillableAmount();
            if (amount > 0) {
                return "$" + String.format("%.2f", amount);
            }
            return "$0.00";
        }).setHeader("Amount").setWidth("100px");

        // Actions column with modern buttons
        grid.addColumn(new ComponentRenderer<>(trip -> {
            HorizontalLayout actions = new HorizontalLayout();
            actions.setSpacing(true);

            Button editButton = new Button("✏️", e -> openTripDialog(trip));
            editButton.addClassName("btn btn-ghost");
            editButton.getStyle()
                    .set("padding", "var(--space-2)")
                    .set("min-width", "auto")
                    .set("font-size", "0.875rem");

            Button cancelButton = new Button("❌", e -> {
                if (tripService.cancelTrip(trip.getId())) {
                    showNotification("Trip cancelled successfully", NotificationVariant.LUMO_SUCCESS);
                    refreshGrid();
                } else {
                    showNotification("Cannot cancel this trip", NotificationVariant.LUMO_ERROR);
                }
            });
            cancelButton.addClassName("btn btn-ghost");
            cancelButton.getStyle()
                    .set("padding", "var(--space-2)")
                    .set("min-width", "auto")
                    .set("font-size", "0.875rem")
                    .set("color", "#ef4444");

            actions.add(editButton, cancelButton);
            return actions;
        })).setHeader("Actions").setWidth("120px");
    }

    private void openTripDialog(Trip trip) {
        Dialog dialog = new Dialog();
        dialog.setHeaderTitle(trip.getId() == null ? "🎫 New Trip" : "✏️ Edit Trip");
        dialog.setWidth("700px");

        // Style the dialog
        dialog.getElement().getStyle()
                .set("background", "var(--bg-glass)")
                .set("backdrop-filter", "blur(20px)")
                .set("border", "1px solid var(--border-primary)")
                .set("border-radius", "16px");

        FormLayout form = new FormLayout();
        form.setResponsiveSteps(new FormLayout.ResponsiveStep("0", 1));

        // Create styled form fields
        TextField customerName = createStyledTextField("Customer Name", 
                trip.getCustomerName() != null ? trip.getCustomerName() : "");
        TextField customerPhone = createStyledTextField("Phone Number", 
                trip.getCustomerPhone() != null ? trip.getCustomerPhone() : "");
        TextField pickupAddress = createStyledTextField("Pickup Address", 
                trip.getPickupAddress() != null ? trip.getPickupAddress() : "");
        TextField dropoffAddress = createStyledTextField("Dropoff Address", 
                trip.getDropoffAddress() != null ? trip.getDropoffAddress() : "");

        DateTimePicker scheduledTime = new DateTimePicker("Scheduled Time");
        scheduledTime.setValue(trip.getScheduledTime() != null ? trip.getScheduledTime() : LocalDateTime.now().plusHours(1));
        scheduledTime.getStyle()
                .set("background", "var(--bg-glass)")
                .set("border", "1px solid var(--border-primary)")
                .set("border-radius", "12px")
                .set("color", "var(--text-primary)");

        ComboBox<String> payerType = new ComboBox<>("Payer Type");
        payerType.setItems("MEDICAID", "PRIVATE", "INSURANCE", "BROKER");
        payerType.setValue(trip.getPayerType() != null ? trip.getPayerType() : "MEDICAID");
        payerType.getStyle()
                .set("background", "var(--bg-glass)")
                .set("border", "1px solid var(--border-primary)")
                .set("border-radius", "12px")
                .set("color", "var(--text-primary)");

        NumberField billableAmount = new NumberField("Billable Amount ($)");
        billableAmount.setValue(trip.getBillableAmount() > 0 ? trip.getBillableAmount() : null);
        billableAmount.setMin(0);
        billableAmount.getStyle()
                .set("background", "var(--bg-glass)")
                .set("border", "1px solid var(--border-primary)")
                .set("border-radius", "12px")
                .set("color", "var(--text-primary)");

        form.add(customerName, customerPhone, pickupAddress, dropoffAddress, scheduledTime, payerType, billableAmount);

        // Footer buttons
        HorizontalLayout footer = new HorizontalLayout();
        footer.setSpacing(true);
        footer.setJustifyContentMode(JustifyContentMode.END);

        Button saveButton = new Button("💾 Save", e -> {
            trip.setCustomerName(customerName.getValue());
            trip.setCustomerPhone(customerPhone.getValue());
            trip.setPickupAddress(pickupAddress.getValue());
            trip.setDropoffAddress(dropoffAddress.getValue());
            trip.setScheduledTime(scheduledTime.getValue());
            trip.setPayerType(payerType.getValue());
            trip.setBillableAmount(billableAmount.getValue());

            if (trip.getCustomerId() == null) {
                trip.setCustomerId("CUST-" + System.currentTimeMillis());
            }

            tripService.createTrip(trip);
            showNotification("Trip saved successfully!", NotificationVariant.LUMO_SUCCESS);
            refreshGrid();
            dialog.close();
        });
        saveButton.addClassName("btn btn-primary");

        Button cancelButton = new Button("❌ Cancel", e -> dialog.close());
        cancelButton.addClassName("btn btn-secondary");

        footer.add(cancelButton, saveButton);

        dialog.add(form);
        dialog.getFooter().add(footer);
        dialog.open();
    }

    private TextField createStyledTextField(String label, String value) {
        TextField field = new TextField(label);
        field.setValue(value);
        field.addClassName("glass-input");
        return field;
    }

    private void refreshGrid() {
        grid.setItems(tripService.getAllTrips());
    }

    private void showNotification(String message, NotificationVariant variant) {
        Notification notification = Notification.show(message, 3000, Notification.Position.TOP_CENTER);
        notification.addThemeVariants(variant);
    }
}
