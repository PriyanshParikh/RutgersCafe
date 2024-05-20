package com.example.rutgerscafe;

import android.annotation.SuppressLint;

import androidx.annotation.NonNull;

/**
 * The Donut class represents a donut item in the menu.
 * It extends MenuItem class.
 * @author Priyansh Parikh, Siddarth Seloth
 */
public class Donut extends MenuItem {
    private String flavor;
    private double basePrice;
    private int quantity;

    /**
     * Constructs a Donut object with the specified type, flavor, and quantity.
     *
     * @param flavor   The flavor of the donut
     * @param basePrice The base price of donut ordered
     */
    public Donut(String flavor, double basePrice) {
        this.flavor = flavor;
        this.basePrice = basePrice;
        this.quantity = 0;
    }

    /**
     * Constructs a Donut object copy of an other donut object.
     *
     * @param other donut to be copied
     */
    public Donut(Donut other)
    {
        this.flavor = other.flavor;
        this.quantity = other.quantity;
        this.basePrice = other.basePrice;
    }

    /**
     * Gets the flavor of the donut.
     *
     * @return The flavor of the donut
     */
    public String getFlavor() {
        return flavor;
    }

    /**
     * Gets the base price of the donut.
     *
     * @return The base price of the donut
     */
    public double getBasePrice() {
        return basePrice;
    }

    /**
     * Gets the qty of the donut.
     *
     * @return The quantity of the donut type
     */
    public int getQuantity() {
        return quantity;
    }

    /**
     * sets the qty of the donut.
     *
     * @param  quantity --> quantity of donut to be set to
     */
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    /**
     * Calculates the price of the donut based on its type and quantity.
     *
     * @return The total price of the donut
     */
    @Override
    public double price() {
        return basePrice * quantity;
    }



    /**
     * Generates a string representation of the donut item.
     *
     * @return A string representation of the donut item
     */
    @NonNull
    @Override
    @SuppressLint("DefaultLocale")
    public String toString() {
        return String.format("%s (%d)", flavor, quantity);
    }
}