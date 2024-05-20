package com.example.rutgerscafe;

/**
 * The abstract class MenuItem represents an item on a menu.
 * Subclasses of MenuItem must implement the price() method to calculate the price of the item.
 * @author Priyansh Parikh, Siddarth Seloth
 */
public abstract class MenuItem {

    /**
     * Calculates the price of the menu item.
     *
     * @return The price of the menu item.
     */
    public abstract double price();

}