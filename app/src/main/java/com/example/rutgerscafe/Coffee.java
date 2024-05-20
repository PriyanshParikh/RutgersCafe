package com.example.rutgerscafe;

import java.util.HashSet;
import java.util.Set;

/**
 * This class handles all the functions of a coffee object and allows for its creation
 * @author Priyansh Parikh, Siddarth Seloth
 */
public class Coffee extends MenuItem{
    private String cupSize;
    private int numberOfAddIns;
    private int quantity;
    private static final double BASE_PRICE = 1.99;
    private Set<String> addIns;

    /**
     * Constructs a Coffee object with the specified cup size, number of additional ingredients, and quantity.
     *
     * @param cupSize         The size of the coffee cup (e.g., short, tall, grande, venti)
     * @param numberOfAddIns  The number of additional ingredients
     * @param quantity        The quantity of coffee ordered
     */
    public Coffee(String cupSize, int numberOfAddIns, int quantity) {
        this.cupSize = cupSize;
        this.quantity = quantity;
        this.numberOfAddIns = numberOfAddIns;
        this.addIns = new HashSet<>();
    }

    /**
     * Constructs a Coffee object copy of another coffee object
     * @param other --> coffe to be copied
     */
    public Coffee(Coffee other){
        this.cupSize = other.cupSize;
        this.quantity = other.quantity;
        this.numberOfAddIns = other.getNumberOfAddIns();
        setAddIns(other.getAddIns());
    }



    /**
     * Calculates the price of the coffee based on its size, additional ingredients, and quantity.
     *
     * @return The total price of the coffee
     */
    @Override
    public double price() {
        double sizePrice = calculateCupSizePrice(cupSize);
        double addInPrice = 0.30 * addIns.size();
        return (BASE_PRICE + sizePrice + addInPrice) * quantity;
    }


    /**
     * Calculates the price based on the cup size.
     *
     * @param cupSize The size of the coffee cup
     * @return The price based on the cup size
     */
    private double calculateCupSizePrice(String cupSize) {
        switch (this.cupSize.toLowerCase()) {
            case "short":
                return 0.0;
            case "tall":
                return 0.50;
            case "grande":
                return 1.00;
            case "venti":
                return 1.50;
            default:
                return 0.0;
        }
    }

    /**
     * Adds an additional ingredient to the coffee by adding to the Set of AddIns.
     *
     * @param addIn The additional ingredient to add
     */
    public void addAddIn(String addIn){
        this.numberOfAddIns++;
        this.addIns.add(addIn);
    }

    /**
     * Removes an additional ingredient from the coffee.
     *
     * @param addIn The additional ingredient to remove
     */
    public void removeAddIn(String addIn){
        this.addIns.remove(addIn);
        this.numberOfAddIns--;
    }


    /**
     * Generates a string representation of the coffee item.
     *
     * @return A string representation of the coffee item
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Coffee ").append("(").append(this.quantity).append(")").append(" ").append(cupSize);
        if (addIns.size() > 0) {
            sb.append(" [");
            for (String addIn : addIns) {
                sb.append(addIn).append(", ");
            }
            sb.delete(sb.length() - 2, sb.length());
            sb.append("]");

        }

        return sb.toString();
    }

    /**
     * Gets the set of additional ingredients.
     *
     * @return The set of additional ingredients
     */
    public Set<String> getAddIns() {
        return this.addIns;
    }

    /**
     * Sets the set of additional ingredients.
     *
     * @param addIns The set of additional ingredients to set
     */
    public void setAddIns(Set<String > addIns){

        this.addIns = addIns;
    }

    /**
     * Sets the cup size of the coffee.
     *
     * @param cupSize The cup size to set
     */
    public void setCupSize(String cupSize) {
        this.cupSize = cupSize;
    }

    /**
     * Gets the number of additional ingredients.
     *
     * @return The number of additional ingredients
     */
    public int getNumberOfAddIns() {
        return numberOfAddIns;
    }

    /**
     * Sets the quantity of coffee ordered.
     *
     * @param quantity The quantity to set
     */
    public void setQuantity(int quantity){
        this.quantity = quantity;
    }
}
