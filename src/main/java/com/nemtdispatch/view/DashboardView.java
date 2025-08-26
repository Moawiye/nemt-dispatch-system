package com.nemtdispatch.view;

import com.nemtdispatch.entity.Trip;
import com.nemtdispatch.service.TripService;
import com.nemtdispatch.service.FleetService;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.icon.Icon;

@PageTitle("Dashboard")
@Route(value = "", layout = MainLayout.class)
public class DashboardView extends VerticalLayout {

    private final TripService tripService;
    private final FleetService fleetService;

    public DashboardView(TripService tripService, FleetService fleetService) {
        this.tripService = tripService;
        this.fleetService = fleetService;

        setSizeFull();
        setPadding(false);
        setSpacing(false);
        setClassName("dashboard-view");

        // Apply custom styling
        getElement().getStyle()
                .set("background", "var(--bg-primary)")
                .set("min-height", "100vh");

        add(createHeroSection(), createStatsGrid(), createQuickActions(), createRecentActivity());
    }

    private Div createHeroSection() {
        Div hero = new Div();
        hero.addClassName("hero-section");
        hero.getStyle()
                .set("background", "var(--secondary-gradient)")
                .set("padding", "var(--space-16) var(--space-8)")
                .set("text-align", "center")
                .set("position", "relative")
                .set("overflow", "hidden");

        // Add subtle background pattern
        hero.getStyle().set("background-image", 
                "radial-gradient(circle at 20% 80%, rgba(139, 92, 246, 0.1) 0%, transparent 50%), " +
                "radial-gradient(circle at 80% 20%, rgba(99, 102, 241, 0.1) 0%, transparent 50%)");

        H1 heroTitle = new H1("Welcome to NEMT Dispatch");
        heroTitle.addClassName("gradient-text");
        heroTitle.getStyle()
                .set("font-size", "3rem")
                .set("font-weight", "700")
                .set("margin", "0 0 var(--space-4) 0")
                .set("letter-spacing", "-0.025em");

        Paragraph heroSubtitle = new Paragraph("Professional medical transportation management at your fingertips");
        heroSubtitle.getStyle()
                .set("font-size", "1.25rem")
                .set("color", "var(--text-secondary)")
                .set("margin", "0")
                .set("max-width", "600px")
                .set("margin-left", "auto")
                .set("margin-right", "auto");

        hero.add(heroTitle, heroSubtitle);
        return hero;
    }

    private Div createStatsGrid() {
        Div statsContainer = new Div();
        statsContainer.addClassName("container");
        statsContainer.getStyle()
                .set("margin-top", "-var(--space-16)")
                .set("position", "relative")
                .set("z-index", "10");

        Div statsGrid = new Div();
        statsGrid.addClassName("grid grid-cols-5");
        statsGrid.getStyle()
                .set("gap", "var(--space-6)");

        // Create modern stat cards
        statsGrid.add(
                createStatCard("🎫", "Total Trips", String.valueOf(tripService.getAllTrips().size()), "trips"),
                createStatCard("✅", "Completed", String.valueOf(tripService.getTripCountByStatus(Trip.TripStatus.COMPLETED)), "completed"),
                createStatCard("🕒", "Pending", String.valueOf(tripService.getTripCountByStatus(Trip.TripStatus.REQUESTED)), "pending"),
                createStatCard("👥", "Active Drivers", String.valueOf(fleetService.getAllDrivers().size()), "drivers"),
                createStatCard("🚐", "Available Vehicles", String.valueOf(fleetService.getAllVehicles().size()), "vehicles")
        );

        statsContainer.add(statsGrid);
        return statsContainer;
    }

    private Div createStatCard(String icon, String label, String value, String type) {
        Div card = new Div();
        card.addClassName("stat-card");
        card.addClassName("fade-in");

        // Icon container
        Div iconContainer = new Div();
        iconContainer.addClassName("icon");
        iconContainer.setText(icon);

        // Value
        H3 valueElement = new H3(value);
        valueElement.addClassName("value");

        // Label
        Span labelElement = new Span(label);
        labelElement.addClassName("label");

        // Type indicator
        Span typeIndicator = new Span(type);
        typeIndicator.getStyle()
                .set("font-size", "0.75rem")
                .set("color", "var(--accent-blue)")
                .set("background", "rgba(59, 130, 246, 0.1)")
                .set("padding", "var(--space-1) var(--space-2)")
                .set("border-radius", "6px")
                .set("text-transform", "uppercase")
                .set("letter-spacing", "0.05em");

        card.add(iconContainer, valueElement, labelElement, typeIndicator);
        return card;
    }

    private Div createQuickActions() {
        Div actionsContainer = new Div();
        actionsContainer.addClassName("container");
        actionsContainer.getStyle()
                .set("margin-top", "var(--space-16)");

        H2 actionsTitle = new H2("Quick Actions");
        actionsTitle.addClassName("gradient-text");
        actionsTitle.getStyle()
                .set("font-size", "2rem")
                .set("font-weight", "700")
                .set("margin", "0 0 var(--space-8) 0")
                .set("text-align", "center");

        Div actionsGrid = new Div();
        actionsGrid.addClassName("grid grid-cols-2");
        actionsGrid.getStyle()
                .set("gap", "var(--space-6)")
                .set("max-width", "800px")
                .set("margin", "0 auto");

        actionsGrid.add(
                createActionCard("🎫", "New Trip Request", "Create a new transportation request", "Create Trip", "primary"),
                createActionCard("👥", "Add Driver", "Register a new driver in the system", "Add Driver", "secondary"),
                createActionCard("🚐", "Add Vehicle", "Register a new vehicle", "Add Vehicle", "secondary"),
                createActionCard("🚀", "Run Dispatch", "Optimize and assign trips automatically", "Run Dispatch", "primary")
        );

        actionsContainer.add(actionsTitle, actionsGrid);
        return actionsContainer;
    }

    private Div createActionCard(String icon, String title, String description, String buttonText, String buttonType) {
        Div card = new Div();
        card.addClassName("glass-card");
        card.addClassName("action-card");
        card.getStyle()
                .set("padding", "var(--space-8)")
                .set("text-align", "center")
                .set("display", "flex")
                .set("flex-direction", "column")
                .set("align-items", "center")
                .set("gap", "var(--space-4)");

        // Icon
        Div iconDiv = new Div();
        iconDiv.setText(icon);
        iconDiv.getStyle()
                .set("font-size", "3rem")
                .set("margin-bottom", "var(--space-4)");

        // Title
        H3 titleElement = new H3(title);
        titleElement.getStyle()
                .set("font-size", "1.25rem")
                .set("font-weight", "600")
                .set("margin", "0")
                .set("color", "var(--text-primary)");

        // Description
        Paragraph descElement = new Paragraph(description);
        descElement.getStyle()
                .set("color", "var(--text-secondary)")
                .set("margin", "0")
                .set("line-height", "1.6");

        // Button
        Div button = new Div();
        button.addClassName("btn");
        button.addClassName("btn-" + buttonType);
        button.setText(buttonText);
        button.getStyle()
                .set("margin-top", "auto")
                .set("min-width", "140px");

        card.add(iconDiv, titleElement, descElement, button);
        return card;
    }

    private Div createRecentActivity() {
        Div activityContainer = new Div();
        activityContainer.addClassName("container");
        activityContainer.getStyle()
                .set("margin-top", "var(--space-16)")
                .set("margin-bottom", "var(--space-16)");

        H2 activityTitle = new H2("Recent Activity");
        activityTitle.addClassName("gradient-text");
        activityTitle.getStyle()
                .set("font-size", "2rem")
                .set("font-weight", "700")
                .set("margin", "0 0 var(--space-8) 0")
                .set("text-align", "center");

        Div activityCard = new Div();
        activityCard.addClassName("glass-card");
        activityCard.getStyle()
                .set("padding", "var(--space-8)");

        // Sample activity items
        Div activityItem1 = createActivityItem("🎫", "New trip request created", "TRIP-1756174120775 for James Miller", "2 minutes ago", "success");
        Div activityItem2 = createActivityItem("✅", "Trip completed", "TRIP-1756174000000 for Sarah Johnson", "15 minutes ago", "completed");
        Div activityItem3 = createActivityItem("👥", "New driver registered", "Michael Chen added to the fleet", "1 hour ago", "info");

        activityCard.add(activityItem1, activityItem2, activityItem3);
        activityContainer.add(activityTitle, activityCard);
        return activityContainer;
    }

    private Div createActivityItem(String icon, String action, String details, String time, String status) {
        Div item = new Div();
        item.getStyle()
                .set("display", "flex")
                .set("align-items", "center")
                .set("gap", "var(--space-4)")
                .set("padding", "var(--space-4)")
                .set("border-radius", "12px")
                .set("margin-bottom", "var(--space-3)")
                .set("transition", "all var(--transition-normal)");

        // Status-based background
        if ("success".equals(status)) {
            item.getStyle().set("background", "rgba(16, 185, 129, 0.1)");
        } else if ("completed".equals(status)) {
            item.getStyle().set("background", "rgba(59, 130, 246, 0.1)");
        } else {
            item.getStyle().set("background", "rgba(139, 92, 246, 0.1)");
        }

        // Icon
        Div iconDiv = new Div();
        iconDiv.setText(icon);
        iconDiv.getStyle()
                .set("font-size", "1.5rem")
                .set("flex-shrink", "0");

        // Content
        Div content = new Div();
        content.getStyle()
                .set("flex", "1");

        Div actionDiv = new Div();
        actionDiv.setText(action);
        actionDiv.getStyle()
                .set("font-weight", "600")
                .set("color", "var(--text-primary)")
                .set("margin-bottom", "var(--space-1)");

        Div detailsDiv = new Div();
        detailsDiv.setText(details);
        detailsDiv.getStyle()
                .set("color", "var(--text-secondary)")
                .set("font-size", "0.875rem");

        content.add(actionDiv, detailsDiv);

        // Time
        Span timeSpan = new Span(time);
        timeSpan.getStyle()
                .set("color", "var(--text-muted)")
                .set("font-size", "0.75rem")
                .set("flex-shrink", "0");

        item.add(iconDiv, content, timeSpan);
        return item;
    }
}
