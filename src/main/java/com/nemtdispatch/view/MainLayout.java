package com.nemtdispatch.view;

import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.Scroller;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.sidenav.SideNav;
import com.vaadin.flow.component.sidenav.SideNavItem;
import com.vaadin.flow.router.RouterLink;
import com.vaadin.flow.theme.lumo.LumoUtility;

public class MainLayout extends AppLayout {

    public MainLayout() {
        // Set the primary style name for custom CSS targeting
        setPrimarySection(Section.NAVBAR);
        
        // Create modern header
        createHeader();
        
        // Create modern sidebar
        createSidebar();
        
        // Apply custom styling
        applyCustomStyling();
    }

    private void createHeader() {
        // Create the main header container
        Div headerContainer = new Div();
        headerContainer.addClassName("nav-header");
        headerContainer.getStyle()
                .set("display", "flex")
                .set("align-items", "center")
                .set("justify-content", "space-between")
                .set("width", "100%");

        // Left side: Toggle button and logo
        Div leftSection = new Div();
        leftSection.getStyle()
                .set("display", "flex")
                .set("align-items", "center")
                .set("gap", "var(--space-6)");

        // Modern toggle button
        DrawerToggle toggle = new DrawerToggle();
        toggle.addClassName("btn-ghost");
        toggle.getStyle()
                .set("background", "transparent")
                .set("border", "none")
                .set("color", "var(--text-primary)")
                .set("font-size", "1.25rem");

        // Logo with gradient text
        H1 logo = new H1("🚐 NEMT Dispatch");
        logo.addClassName("nav-logo");
        logo.getStyle()
                .set("margin", "0")
                .set("font-size", "1.5rem")
                .set("font-weight", "700");

        leftSection.add(toggle, logo);

        // Right side: User profile and notifications
        Div rightSection = new Div();
        rightSection.getStyle()
                .set("display", "flex")
                .set("align-items", "center")
                .set("gap", "var(--space-4)");

        // Notification bell
        Icon notificationIcon = VaadinIcon.BELL.create();
        notificationIcon.getStyle()
                .set("color", "var(--text-secondary)")
                .set("cursor", "pointer")
                .set("font-size", "1.25rem");

        // User avatar
        Div userAvatar = new Div();
        userAvatar.getStyle()
                .set("width", "40px")
                .set("height", "40px")
                .set("border-radius", "50%")
                .set("background", "var(--primary-gradient)")
                .set("display", "flex")
                .set("align-items", "center")
                .set("justify-content", "center")
                .set("color", "white")
                .set("font-weight", "600")
                .set("cursor", "pointer");
        userAvatar.setText("JD"); // Initials

        rightSection.add(notificationIcon, userAvatar);

        headerContainer.add(leftSection, rightSection);
        addToNavbar(headerContainer);
    }

    private void createSidebar() {
        // Create modern sidebar
        SideNav nav = new SideNav();
        nav.setWidth("280px");
        nav.getStyle()
                .set("background", "var(--bg-glass)")
                .set("backdrop-filter", "blur(20px)")
                .set("border-right", "1px solid var(--border-primary)");

        // Add navigation items with modern styling
        nav.addItem(createNavItem("📊 Dashboard", DashboardView.class, VaadinIcon.DASHBOARD.create()));
        nav.addItem(createNavItem("🎫 Trips", TripView.class, VaadinIcon.TAXI.create()));
        nav.addItem(createNavItem("👥 Drivers", DriverView.class, VaadinIcon.USER.create()));
        nav.addItem(createNavItem("🚐 Vehicles", VehicleView.class, VaadinIcon.TRUCK.create()));
        nav.addItem(createNavItem("🚀 Dispatch", DispatchView.class, VaadinIcon.PLAY.create()));

        // Wrap in scroller for better UX
        Scroller scroller = new Scroller(nav);
        scroller.setClassName(LumoUtility.Padding.SMALL);
        scroller.getStyle()
                .set("background", "transparent");

        addToDrawer(scroller);
    }

    private SideNavItem createNavItem(String label, Class<? extends com.vaadin.flow.component.Component> navigationTarget, Icon icon) {
        SideNavItem item = new SideNavItem(label, navigationTarget, icon);
        
        // Apply custom styling to the item
        item.getElement().getStyle()
                .set("margin", "var(--space-1) var(--space-2)")
                .set("border-radius", "12px")
                .set("transition", "all var(--transition-normal)");

        // Style the icon
        icon.getStyle()
                .set("color", "var(--text-secondary)")
                .set("font-size", "1.125rem");

        return item;
    }

    private void applyCustomStyling() {
        // Apply custom styles to the main layout
        getElement().getStyle()
                .set("background", "var(--bg-primary)");

        // Style the drawer - use the proper Vaadin Flow API
        getElement().getStyle()
                .set("--vaadin-app-layout-drawer-background", "var(--bg-glass)")
                .set("--vaadin-app-layout-drawer-border", "1px solid var(--border-primary)");

        // Style the navbar - use the proper Vaadin Flow API
        getElement().getStyle()
                .set("--vaadin-app-layout-navbar-background", "var(--bg-glass)")
                .set("--vaadin-app-layout-navbar-border", "1px solid var(--border-primary)")
                .set("--vaadin-app-layout-navbar-shadow", "var(--shadow-lg)");
    }
}
