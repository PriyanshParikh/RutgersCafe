package com.example.rutgerscafe;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * This class represents a singleton for managing orders in the cafe application.
 * It ensures that only one instance of the order management system exists throughout the application.
 * One instance of a map of all orders that can be accessed through all the orering classes
 * @author Priyansh Parikh, Siddarth Seloth
 */
public class OrderSingleton {
    public static OrderSingleton instance;
    private static Map<Integer, Order> orders;

    /**
     * Private constructor to prevent instantiation from outside the class.
     */
    private OrderSingleton(){
        orders = new HashMap<>();
    }


    /**
     * Retrieves the singleton instance of the OrderSingleton class.
     *
     * @return The singleton instance.
     */
    public static synchronized OrderSingleton getInstance() {
        if (instance == null) {
            instance = new OrderSingleton();
        }
        return instance;
    }

    /**
     * Adds an order to the order management system.
     *
     * @param order The order to be added.
     */
    public void addOrder(Order order) {
        orders.put(order.getOrderNumber(), order);
    }

    /**
     * Removes an order from the order management system.
     *
     * @param order The order to be removed.
     */
    public void removeOrder(Order order) {
        orders.remove(order.getOrderNumber());
    }

    /**
     * Retrieves all orders from the order management system.
     *
     * @return A collection of orders.
     */
    public Collection<Order> getOrders()
    {
        return orders.values();
    }


}
