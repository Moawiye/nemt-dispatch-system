package com.nemtdispatch.view;

import com.nemtdispatch.entity.Driver;
import com.nemtdispatch.service.FleetService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.data.renderer.ComponentRenderer;

@PageTitle("Driver Management")
@Route(value = "drivers", layout = MainLayout.class)
public class DriverView extends VerticalLayout {

    private final FleetService fleetService;
    private final Grid<Driver> grid = new Grid<>(Driver.class, false);

    public DriverView(FleetService fleetService) {
        this.fleetService = fleetService;

        setSizeFull();
        setPadding(true);

        add(createHeader(), createDriverGrid());
        refreshGrid();
    }

    private HorizontalLayout createHeader() {
        H2 title = new H2("👥 Driver Management");

        Button addButton = new Button("➕ Add Driver", e -> openDriverDialog(new Driver()));
        addButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);

        Button refreshButton = new Button("🔄 Refresh", e -> refreshGrid());

        HorizontalLayout header = new HorizontalLayout(title);
        header.add(addButton, refreshButton);
        header.setWidthFull();
        header.setJustifyContentMode(JustifyContentMode.BETWEEN);
        header.setAlignItems(Alignment.CENTER);

        return header;
    }

    private Grid<Driver> createDriverGrid() {
        grid.addColumn(Driver::getId).setHeader("Driver ID").setWidth("120px");
        grid.addColumn(Driver::getName).setHeader("Name").setWidth("150px");
        grid.addColumn(Driver::getPhoneNumber).setHeader("Phone").setWidth("120px");
        grid.addColumn(Driver::getLicenseNumber).setHeader("License #").setWidth("120px");

        grid.addColumn(new ComponentRenderer<>(driver -> {
            Span badge = new Span(driver.getStatus().toString());
            badge.getElement().getThemeList().add("badge");

            switch (driver.getStatus()) {
                case AVAILABLE:
                    badge.getStyle().set("background", "#4caf50").set("color", "white");
                    break;
                case BUSY:
                    badge.getStyle().set("background", "#ff9800").set("color", "white");
                    break;
                case OFF_DUTY:
                    badge.getStyle().set("background", "#9e9e9e").set("color", "white");
                    break;
            }

            badge.getStyle().set("padding", "4px 8px").set("border-radius", "12px").set("font-size", "0.75em");
            return badge;
        })).setHeader("Status").setWidth("100px");

        grid.addColumn(Driver::getVehicleId).setHeader("Vehicle").setWidth("100px");

        grid.addColumn(new ComponentRenderer<>(driver -> {
            Button editButton = new Button("✏️", e -> openDriverDialog(driver));
            editButton.addThemeVariants(ButtonVariant.LUMO_SMALL, ButtonVariant.LUMO_TERTIARY);
            return editButton;
        })).setHeader("Actions").setWidth("80px");

        grid.setSizeFull();
        return grid;
    }

    private void openDriverDialog(Driver driver) {
        Dialog dialog = new Dialog();
        dialog.setHeaderTitle(driver.getId() == null ? "➕ New Driver" : "✏️ Edit Driver");
        dialog.setWidth("400px");

        FormLayout form = new FormLayout();

        TextField name = new TextField("Full Name");
        name.setValue(driver.getName() != null ? driver.getName() : "");

        TextField phoneNumber = new TextField("Phone Number");
        phoneNumber.setValue(driver.getPhoneNumber() != null ? driver.getPhoneNumber() : "");

        TextField licenseNumber = new TextField("License Number");
        licenseNumber.setValue(driver.getLicenseNumber() != null ? driver.getLicenseNumber() : "");

        form.add(name, phoneNumber, licenseNumber);

        Button saveButton = new Button("💾 Save", e -> {
            driver.setName(name.getValue());
            driver.setPhoneNumber(phoneNumber.getValue());
            driver.setLicenseNumber(licenseNumber.getValue());

            fleetService.addDriver(driver);
            showNotification("Driver saved successfully!", NotificationVariant.LUMO_SUCCESS);
            refreshGrid();
            dialog.close();
        });
        saveButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);

        Button cancelButton = new Button("❌ Cancel", e -> dialog.close());

        dialog.add(form);
        dialog.getFooter().add(cancelButton, saveButton);
        dialog.open();
    }

    private void refreshGrid() {
        grid.setItems(fleetService.getAllDrivers());
    }

    private void showNotification(String message, NotificationVariant variant) {
        Notification notification = Notification.show(message, 3000, Notification.Position.TOP_CENTER);
        notification.addThemeVariants(variant);
    }
}
