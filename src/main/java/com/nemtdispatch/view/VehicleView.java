package com.nemtdispatch.view;

import com.nemtdispatch.entity.Vehicle;
import com.nemtdispatch.service.FleetService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.IntegerField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.data.renderer.ComponentRenderer;

@PageTitle("Vehicle Management")
@Route(value = "vehicles", layout = MainLayout.class)
public class VehicleView extends VerticalLayout {

    private final FleetService fleetService;
    private final Grid<Vehicle> grid = new Grid<>(Vehicle.class, false);

    public VehicleView(FleetService fleetService) {
        this.fleetService = fleetService;

        setSizeFull();
        setPadding(true);

        add(createHeader(), createVehicleGrid());
        refreshGrid();
    }

    private HorizontalLayout createHeader() {
        H2 title = new H2("🚐 Vehicle Management");

        Button addButton = new Button("➕ Add Vehicle", e -> openVehicleDialog(new Vehicle()));
        addButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);

        Button refreshButton = new Button("🔄 Refresh", e -> refreshGrid());

        HorizontalLayout header = new HorizontalLayout(title);
        header.add(addButton, refreshButton);
        header.setWidthFull();
        header.setJustifyContentMode(JustifyContentMode.BETWEEN);
        header.setAlignItems(Alignment.CENTER);

        return header;
    }

    private Grid<Vehicle> createVehicleGrid() {
        grid.addColumn(Vehicle::getId).setHeader("Vehicle ID").setWidth("120px");
        grid.addColumn(Vehicle::getPlateNumber).setHeader("Plate #").setWidth("100px");
        grid.addColumn(Vehicle::getMake).setHeader("Make").setWidth("100px");
        grid.addColumn(Vehicle::getModel).setHeader("Model").setWidth("100px");
        grid.addColumn(Vehicle::getCapacity).setHeader("Capacity").setWidth("80px");

        grid.addColumn(new ComponentRenderer<>(vehicle -> {
            Span badge = new Span(vehicle.isWheelchairAccessible() ? "♿ Yes" : "❌ No");
            if (vehicle.isWheelchairAccessible()) {
                badge.getStyle().set("background", "#4caf50").set("color", "white");
            } else {
                badge.getStyle().set("background", "#9e9e9e").set("color", "white");
            }
            badge.getStyle().set("padding", "4px 8px").set("border-radius", "12px").set("font-size", "0.75em");
            return badge;
        })).setHeader("Wheelchair").setWidth("100px");

        grid.addColumn(new ComponentRenderer<>(vehicle -> {
            Span badge = new Span(vehicle.getStatus().toString());

            switch (vehicle.getStatus()) {
                case AVAILABLE:
                    badge.getStyle().set("background", "#4caf50").set("color", "white");
                    break;
                case IN_USE:
                    badge.getStyle().set("background", "#ff9800").set("color", "white");
                    break;
                case MAINTENANCE:
                    badge.getStyle().set("background", "#f44336").set("color", "white");
                    break;
                case OUT_OF_SERVICE:
                    badge.getStyle().set("background", "#9e9e9e").set("color", "white");
                    break;
            }

            badge.getStyle().set("padding", "4px 8px").set("border-radius", "12px").set("font-size", "0.75em");
            return badge;
        })).setHeader("Status").setWidth("120px");

        grid.addColumn(new ComponentRenderer<>(vehicle -> {
            Button editButton = new Button("✏️", e -> openVehicleDialog(vehicle));
            editButton.addThemeVariants(ButtonVariant.LUMO_SMALL, ButtonVariant.LUMO_TERTIARY);
            return editButton;
        })).setHeader("Actions").setWidth("80px");

        grid.setSizeFull();
        return grid;
    }

    private void openVehicleDialog(Vehicle vehicle) {
        Dialog dialog = new Dialog();
        dialog.setHeaderTitle(vehicle.getId() == null ? "➕ New Vehicle" : "✏️ Edit Vehicle");
        dialog.setWidth("400px");

        FormLayout form = new FormLayout();

        TextField plateNumber = new TextField("Plate Number");
        plateNumber.setValue(vehicle.getPlateNumber() != null ? vehicle.getPlateNumber() : "");

        TextField make = new TextField("Make");
        make.setValue(vehicle.getMake() != null ? vehicle.getMake() : "");

        TextField model = new TextField("Model");
        model.setValue(vehicle.getModel() != null ? vehicle.getModel() : "");

        IntegerField capacity = new IntegerField("Passenger Capacity");
        capacity.setValue(vehicle.getCapacity());
        capacity.setMin(1);
        capacity.setMax(15);

        Checkbox wheelchairAccessible = new Checkbox("Wheelchair Accessible");
        wheelchairAccessible.setValue(vehicle.isWheelchairAccessible());

        form.add(plateNumber, make, model, capacity, wheelchairAccessible);

        Button saveButton = new Button("💾 Save", e -> {
            vehicle.setPlateNumber(plateNumber.getValue());
            vehicle.setMake(make.getValue());
            vehicle.setModel(model.getValue());
            vehicle.setCapacity(capacity.getValue());
            vehicle.setWheelchairAccessible(wheelchairAccessible.getValue());

            fleetService.addVehicle(vehicle);
            showNotification("Vehicle saved successfully!", NotificationVariant.LUMO_SUCCESS);
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
        grid.setItems(fleetService.getAllVehicles());
    }

    private void showNotification(String message, NotificationVariant variant) {
        Notification notification = Notification.show(message, 3000, Notification.Position.TOP_CENTER);
        notification.addThemeVariants(variant);
    }
}
