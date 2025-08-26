package com.nemtdispatch.view;

import com.nemtdispatch.entity.Trip;
import com.nemtdispatch.service.DispatchService;
import com.nemtdispatch.service.TripService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@PageTitle("Dispatch Center")
@Route(value = "dispatch", layout = MainLayout.class)
public class DispatchView extends VerticalLayout {

    private final DispatchService dispatchService;
    private final TripService tripService;

    public DispatchView(DispatchService dispatchService, TripService tripService) {
        this.dispatchService = dispatchService;
        this.tripService = tripService;

        setSizeFull();
        setPadding(true);

        add(createHeader(), createDispatchPanel());
    }

    private VerticalLayout createHeader() {
        VerticalLayout header = new VerticalLayout();
        header.setSpacing(false);
        header.setPadding(false);

        H2 title = new H2("🚀 Dispatch Center");
        title.getStyle().set("margin-bottom", "10px");

        Paragraph description = new Paragraph("Optimize and assign transportation requests to available drivers and vehicles.");
        description.getStyle().set("color", "#666").set("margin-top", "0");

        header.add(title, description);
        return header;
    }

    private VerticalLayout createDispatchPanel() {
        VerticalLayout panel = new VerticalLayout();
        panel.setSpacing(true);

        // Status Overview
        VerticalLayout statusSection = createStatusSection();

        // Dispatch Actions
        VerticalLayout actionsSection = createActionsSection();

        panel.add(statusSection, actionsSection);
        return panel;
    }

    private VerticalLayout createStatusSection() {
        VerticalLayout section = new VerticalLayout();
        section.setSpacing(true);

        H3 sectionTitle = new H3("📊 Current Status");

        HorizontalLayout statusCards = new HorizontalLayout();
        statusCards.setSpacing(true);
        statusCards.setWidthFull();

        // Create status cards
        statusCards.add(
                createStatusCard("🎫", "Requested Trips",
                        String.valueOf(tripService.getTripCountByStatus(Trip.TripStatus.REQUESTED)),
                        "#ff9800"),
                createStatusCard("📋", "Scheduled Trips",
                        String.valueOf(tripService.getTripCountByStatus(Trip.TripStatus.SCHEDULED)),
                        "#2196f3"),
                createStatusCard("🚀", "In Progress",
                        String.valueOf(tripService.getTripCountByStatus(Trip.TripStatus.EN_ROUTE)),
                        "#9c27b0"),
                createStatusCard("✅", "Completed Today",
                        String.valueOf(tripService.getTripCountByStatus(Trip.TripStatus.COMPLETED)),
                        "#4caf50")
        );

        section.add(sectionTitle, statusCards);
        return section;
    }

    private VerticalLayout createStatusCard(String icon, String title, String value, String color) {
        VerticalLayout card = new VerticalLayout();
        card.getStyle()
                .set("background", "white")
                .set("border", "2px solid " + color)
                .set("border-radius", "12px")
                .set("padding", "20px")
                .set("text-align", "center")
                .set("box-shadow", "0 2px 8px rgba(0,0,0,0.1)")
                .set("min-width", "150px");
        card.setSpacing(false);
        card.setPadding(false);

        // Icon
        H2 iconH2 = new H2(icon);
        iconH2.getStyle().set("margin", "0").set("font-size", "2.5em");

        // Value
        H2 valueH2 = new H2(value);
        valueH2.getStyle().set("margin", "10px 0 5px 0").set("color", color).set("font-size", "2em");

        // Title
        Paragraph titleP = new Paragraph(title);
        titleP.getStyle().set("margin", "0").set("color", "#666").set("font-size", "0.9em");

        card.add(iconH2, valueH2, titleP);
        return card;
    }

    private VerticalLayout createActionsSection() {
        VerticalLayout section = new VerticalLayout();
        section.setSpacing(true);

        H3 sectionTitle = new H3("⚡ Dispatch Actions");

        HorizontalLayout buttonLayout = new HorizontalLayout();
        buttonLayout.setSpacing(true);

        Button runDispatchButton = new Button("🚀 Run Optimization", e -> runDispatch());
        runDispatchButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY, ButtonVariant.LUMO_LARGE);
        runDispatchButton.getStyle().set("background", "#1976d2");

        Button refreshStatusButton = new Button("🔄 Refresh Status", e -> {
            getUI().ifPresent(ui -> ui.getPage().reload());
        });
        refreshStatusButton.addThemeVariants(ButtonVariant.LUMO_LARGE);

        Button viewTripsButton = new Button("📋 View All Trips", e -> {
            getUI().ifPresent(ui -> ui.navigate("trips"));
        });
        viewTripsButton.addThemeVariants(ButtonVariant.LUMO_LARGE);

        buttonLayout.add(runDispatchButton, refreshStatusButton, viewTripsButton);

        // Instructions
        VerticalLayout instructions = new VerticalLayout();
        instructions.setSpacing(false);
        instructions.getStyle().set("background", "#f5f5f5").set("padding", "20px").set("border-radius", "8px").set("margin-top", "20px");

        H3 instructionsTitle = new H3("📖 How to Use Dispatch");
        instructionsTitle.getStyle().set("margin-top", "0");

        Paragraph step1 = new Paragraph("1. 🎫 Create trips using the Trip Management section");
        Paragraph step2 = new Paragraph("2. 👥 Ensure you have available drivers in the system");
        Paragraph step3 = new Paragraph("3. 🚐 Make sure vehicles are available and ready");
        Paragraph step4 = new Paragraph("4. 🚀 Click 'Run Optimization' to automatically assign trips");

        instructions.add(instructionsTitle, step1, step2, step3, step4);

        section.add(sectionTitle, buttonLayout, instructions);
        return section;
    }

    private void runDispatch() {
        try {
            int dispatchedTrips = dispatchService.dispatchTrips();

            if (dispatchedTrips > 0) {
                showNotification("🚀 Success! " + dispatchedTrips + " trips dispatched successfully!",
                        NotificationVariant.LUMO_SUCCESS);
            } else {
                showNotification("ℹ️ No trips to dispatch. Either no requested trips or no available resources.",
                        NotificationVariant.LUMO_PRIMARY);
            }

            // Refresh the page to show updated status
            getUI().ifPresent(ui -> ui.getPage().reload());

        } catch (Exception e) {
            showNotification("❌ Error during dispatch: " + e.getMessage(),
                    NotificationVariant.LUMO_ERROR);
        }
    }

    private void showNotification(String message, NotificationVariant variant) {
        Notification notification = Notification.show(message, 5000, Notification.Position.TOP_CENTER);
        notification.addThemeVariants(variant);
    }
}
