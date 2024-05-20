package com.example.rutgerscafe;
import androidx.annotation.NonNull;

import java.util.HashSet;
import java.util.Set;

/**
 * The Sandwich class represents a sandwich item in the menu of the RU Cafe.
 * It contains information about the protein, bread, and additional add-ons of the sandwich.
 * @author Priyansh Parikh, Siddarth Seloth
 */
public class Sandwich extends MenuItem {
    private String protein;
    private String bread;
    private Set<String> addOns;

    /**
     * Constructs a Sandwich object with the specified protein and bread.
     *
     * @param protein The type of protein in the sandwich (e.g., beef, chicken, fish).
     * @param bread   The type of bread used for the sandwich.
     */
    public Sandwich(String protein, String bread) {
        this.bread = bread;
        this.protein = protein;
        this.addOns = new HashSet<>();
    }

    /**
     * Constructs a sandwich object copy of another sandwich object.
     */
    public Sandwich(Sandwich other){
        this.bread = other.bread;
        this.protein = other.protein;
        setAddOns(other.getAddOns());

    }

    /**
     * Calculates the price of the sandwich based on its protein, bread, and additional add-ons.
     *
     * @return The total price of the sandwich.
     */
    @Override
    public double price() {
        double basePrice = calculateBasePrice();
        double addOnsPrice = calculateAddOnsPrice();
        return basePrice + addOnsPrice;
    }

    /**
     * Calculates the base price of the sandwich based on its protein.
     *
     * @return The base price of the sandwich.
     */
    private double calculateBasePrice() {
        switch (protein.toLowerCase()) {
            case "beef":
                return 10.99;
            case "chicken":
                return 8.99;
            case "fish":
                return 9.99;
            default:
                return 0.0;
        }
    }

    /**
     * Calculates the price of additional add-ons in the sandwich.
     *
     * @return The price of additional add-ons in the sandwich.
     */
    private double calculateAddOnsPrice() {
        double addOnsPrice = 0.0;
        for (String addOn : addOns) {
            if (addOn.equalsIgnoreCase("cheese")) {
                addOnsPrice += 1.0;
            } else {
                addOnsPrice += 0.30;
            }
        }
        return addOnsPrice;
    }

    /**
     * Adds an additional add-on to the sandwich.
     *
     * @param addIn The additional add-on to be added to the sandwich.
     */
    public void addAddOn(String addIn){
        this.addOns.add(addIn);
    }

    /**
     * Removes an additional add-on from the sandwich.
     *
     * @param addIn The additional add-on to be removed from the sandwich.
     */
    public void removeAddOn(String addIn){
        this.addOns.remove(addIn);
    }



    /**
     * Returns a string representation of the sandwich.
     *
     * @return A string containing information about the sandwich, including its protein, bread, and add-ons.
     */
    @NonNull
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Sandwich ").append("(").append(getBread()).append(",").append(" ").append(this.protein).append(")");
        if (addOns.size() > 0) {
            sb.append(" [");
            for (String addIn : addOns) {
                sb.append(addIn).append(", ");
            }
            sb.delete(sb.length() - 2, sb.length());
            sb.append("]");

        }
        return sb.toString();
    }

    /**
     * Gets the type of bread used for the sandwich.
     *
     * @return The type of bread used for the sandwich.
     */
    public String getBread(){
        return this.bread;
    }


    /**
     * Gets the additional add-ons included in the sandwich.
     *
     * @return A set containing the additional add-ons included in the sandwich.
     */
    public Set<String> getAddOns() {
        return this.addOns;
    }

    /**
     * Sets the additional add-ons included in the sandwich.
     *
     * @param addOns The additional add-ons to be included in the sandwich.
     * @return The set of additional add-ons included in the sandwich.
     */

    public Set<String> setAddOns(Set<String > addOns){
        return this.addOns = addOns;
    }

    /**
     * Sets the type of bread used for the sandwich.
     *
     * @param bread The type of bread to be used for the sandwich.
     */
    public void setBread(String bread) {
        this.bread = bread;
    }

    /**
     * Sets the type of protein in the sandwich.
     *
     * @param protein The type of protein to be included in the sandwich.
     */
    public void setProtein(String protein) {
        this.protein = protein;
    }
}
