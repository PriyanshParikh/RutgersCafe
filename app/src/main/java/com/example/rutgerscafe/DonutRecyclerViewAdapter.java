package com.example.rutgerscafe;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * This class is a RecyclerView adapter for displaying a list of donuts with their flavors, prices,
 * and quantities in the cafe application.
 * @author Priyansh Parikh, Siddarth Seloth
 */
public class DonutRecyclerViewAdapter extends RecyclerView.Adapter<DonutRecyclerViewAdapter.ViewHolder> {
    private List<Donut> donuts;
    private TextView subtotalTextView;

    /**
     * Constructor for the DonutRecyclerViewAdapter.
     *
     * @param donuts            List of donuts to display.
     * @param subtotalTextView  TextView to display the subtotal.
     */
    public DonutRecyclerViewAdapter(List<Donut> donuts, TextView subtotalTextView) {
        this.donuts = donuts;
        this.subtotalTextView = subtotalTextView;
    }


    /**
     * Called when RecyclerView needs a new viewHolder of the given type to represent an item.
     *
     * @param parent   The ViewGroup into which the new View will be added after it is bound to an adapter position.
     * @param viewType The view type of the new View.
     * @return A new ViewHolder that holds a View of the given view type.
     */
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_donut_row, parent, false);
        return new ViewHolder(itemView);
    }

    /**
     * Called by RecyclerView to display the data at the specified position. This method updates the contents
     * of the itemView to reflect the item at the given position.
     *
     * @param holder   The ViewHolder which should be updated to represent the contents of the item at the given position
     * @param position The position of the item within the adapter's data set.
     */
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Donut donut = donuts.get(position);
        holder.bind(donut);

        int imageResource = getImageResourceForDonut(donut);
        holder.donut_Item.setImageResource(imageResource);
    }

    /**
     * Gets the total amount of donuts selected
     *
     * @return # of donuts to be ordered
     */
    @Override
    public int getItemCount() {
        return donuts.size();
    }

    /**
     * ViewHolder class for holding the views for each donut item in the RecyclerView.
     */
    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView d_flavor;
        private TextView d_price;
        private Spinner quantitySpinner;
        private ImageView donut_Item;

        /**
         * Constructor for the ViewHolder class.
         *
         * @param itemView The root view of the donut row layout for each item in the RecyclerView.
         */
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            d_flavor = itemView.findViewById(R.id.d_flavor);
            d_price = itemView.findViewById(R.id.d_price);
            quantitySpinner = itemView.findViewById(R.id.quantitySpinner);
            donut_Item = itemView.findViewById(R.id.donut_Item);
        }

        /**
         * Binds the data of a donut to the views in the ViewHolder.
         *
         * @param donut The donut object to bind.
         */
        public void bind(Donut donut) {
            d_flavor.setText(donut.getFlavor());
            d_price.setText(String.format(Locale.US, "$%.2f", donut.getBasePrice()));
            setupSpinner(quantitySpinner, donut);
        }

        /**
         * Sets up the quantity spinner for selecting the quantity of the donut.
         *
         * @param spinner The Spinner view.
         * @param donut   The donut object.
         */
        private void setupSpinner(Spinner spinner, Donut donut) {
            ArrayAdapter<Integer> quantityAdapter = new ArrayAdapter<>(spinner.getContext(),
                    android.R.layout.simple_spinner_item, getQuantityOptions());
            quantityAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinner.setAdapter(quantityAdapter);
            spinner.setSelection(donut.getQuantity());
            spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    int newQuantity = (int) parent.getItemAtPosition(position);
                    donut.setQuantity(newQuantity);
                    updateSubtotal();
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {
                }
            });
        }

        /**
         * Generates a list of quantity options for the spinner.
         *
         * @return List of quantity options.
         */
        private List<Integer> getQuantityOptions() {
            List<Integer> quantityOptions = new ArrayList<>();
            for (int i = 0; i <= 6; i++) {
                quantityOptions.add(i);
            }
            return quantityOptions;
        }

        /**
         * Updates the subtotal text view based on the current order.
         */
        private void updateSubtotal() {
            double subtotal = calculateSubtotal();
            subtotalTextView.setText(String.format(Locale.US, "Subtotal: $%.2f", subtotal));
        }


        /**
         * Calculates the subtotal of the current order.
         *
         * @return The subtotal.
         */
        private double calculateSubtotal() {
            double subtotal = 0.0;
            for (Donut donut : donuts) {
                subtotal += donut.price();
            }
            return subtotal;
        }
    }

    /**
     * Retrieves the drawable resource ID for the donut image based on its flavor.
     *
     * @param donut The donut object.
     * @return The drawable resource ID.
     */
    private int getImageResourceForDonut(Donut donut) {
        String donutFlavor = donut.getFlavor().toLowerCase();

        switch (donutFlavor) {
            case "boston cream":
                return R.drawable.boston_cream;
            case "glazed":
                return R.drawable.glazed;
            case "jelly":
                return R.drawable.jelly_donut;
            case "apple crumble":
                return R.drawable.apple_crumble;
            case "cinnamon":
                return R.drawable.cinnamon_donut;
            case "maple frosted":
                return R.drawable.maple_frosted;
            case "strawberry frosted":
                return R.drawable.strawberry_frosted;
            case "vanilla frosted":
                return R.drawable.vanilla_frosted;
            case "chocolate frosted":
                return R.drawable.chocolate_frosted;
            case "jelly holes":
                return R.drawable.jelly_holes;
            case "glazed holes":
                return R.drawable.glazed_holes;
            case "powdered holes":
                return R.drawable.powdered_holes;
            default:
            return 0;
        }
    }




}

