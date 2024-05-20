package com.example.rutgerscafe;

import android.annotation.SuppressLint;
import androidx.annotation.NonNull;
import java.util.ArrayList;
import java.util.List;

/**
 * The Order class represents an order containing a list of menu items.
 * It provides methods to add and remove menu items, calculate subtotal, sales tax, and total,
 * and retrieve information about the order.
 * @author Priyansh Parikh, Siddarth Seloth
 */
public class Order {
    private static int nextOrderNumber = 1;
    private int orderNumber;
    private List<MenuItem> menuItems;
    public static final float taxRate = 6.625f/100;

    /**
     * Constructs a new Order object with a unique order number and an empty list of menu items.
     */
    public Order() {
        this.orderNumber = nextOrderNumber++;
        this.menuItems = new ArrayList<>();
    }

    /**
     * Constructs an order object copy of another order object
     * @param  order --> order to be copied
     */
    public Order(Order order){
        this.orderNumber = order.orderNumber;
        this.menuItems = order.getMenuItems();
    }
    /**
     * Adds a menu item to the order.
     *
     * @param menuItem The menu item to add to the order.
     */
    public void addMenuItem(MenuItem menuItem) {
        menuItems.add(menuItem);
    }

    /**
     * Removes a menu item from the order.
     *
     * @param menuItem The menu item to remove from the order.
     */
    public void removeMenuItem(MenuItem menuItem) {
        menuItems.remove(menuItem);
    }

    /**
     * Gets the order number.
     *
     * @return The order number.
     */
    public int getOrderNumber() {
        return orderNumber;
    }

    /**
     * Gets the list of menu items in the order.
     *
     * @return A list of menu items.
     */
    public List<MenuItem> getMenuItems() {
        return menuItems;
    }

    /**
     * Calculates the subtotal cost of all the menu items in the order.
     *
     * @return The subtotal cost.
     */
    public double getOrderSubTotal() {
        return menuItems.stream().mapToDouble(MenuItem::price).sum();
    }

    /**
     * Calculates the sales tax amount on the subtotal.
     *
     * @return The sales tax amount.
     */
    public double getSalesTax() {
        return getOrderSubTotal() * taxRate;
    }

    /**
     * Calculates the total cost of the order after applying sales tax.
     *
     * @return The total cost of the order.
     */
    public double getTotal() {
        return getOrderSubTotal() * (1 + taxRate);
    }

    /**
     * Returns a string representation of the order, including subtotal, sales tax, total, and list of items.
     *
     * @return A string representation of the order.
     */
    @NonNull
    @Override
    @SuppressLint("DefaultLocale")
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Order #").append(getOrderNumber());
        sb.append("\n Price=").append(String.format("%1$.2f", getOrderSubTotal()));
        sb.append(";\n Tax=").append(String.format("%1$.2f", getSalesTax()));
        sb.append(";\n Total=").append(String.format("%1$.2f", getTotal()));
        sb.append(";\n Items=").append(menuItems);
        return sb.toString();
    }
}