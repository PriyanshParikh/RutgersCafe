package com.example.rutgerscafe;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;

/**
 * This class represents the activity for ordering donuts in the cafe application.
 * Allows users to select various types of donuts, specify quantities, and add them to the order.
 * @author Priyansh Parikh, Siddarth Seloth
 */
public class OrderingDonuts extends AppCompatActivity {

    private RecyclerView recyclerView;
    private TextView subtotalTextView;
    private DonutRecyclerViewAdapter adapter;
    private Button addToOrder;
    private static ArrayList<Donut> donuts = new ArrayList<>();

    /**
     * Called when the activity is starting. Initializes the layout of the activity,
     * sets up the ListView to display all orders, and sets listeners for canceling orders.
     *
     * @param savedInstanceState If the activity is being re-initialized after previously being shut down,
     *                           this Bundle contains the data it most recently supplied in onSaveInstanceState(Bundle).
     *                           Note: Otherwise, it is null.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ordering_donuts);

        recyclerView = findViewById(R.id.donutRecView);
        subtotalTextView = findViewById(R.id.subTotal);
        addToOrder = findViewById(R.id.addToOrder);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        donuts.add(new Donut("boston cream", 1.79));
        donuts.add(new Donut("glazed", 1.79));
        donuts.add(new Donut("jelly", 1.79));
        donuts.add(new Donut("apple crumble", 1.79));
        donuts.add(new Donut("cinnamon", 1.79));
        donuts.add(new Donut("maple frosted", 1.79));
        donuts.add(new Donut("strawberry frosted", 1.89));
        donuts.add(new Donut("vanilla frosted", 1.89));
        donuts.add(new Donut("chocolate frosted", 1.89));
        donuts.add(new Donut("jelly holes", 0.39));
        donuts.add(new Donut("glazed holes", 0.39));
        donuts.add(new Donut("powdered holes", 0.39));

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new DonutRecyclerViewAdapter(donuts, subtotalTextView);
        recyclerView.setAdapter(adapter);

        addToOrder = findViewById(R.id.addToOrder);
        addToOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleAddToOrder();
            }
        });
    }

    /**
     * Handles the button click event to add the donuts to the current order.
     *
     */
    private void handleAddToOrder() {
        boolean anyDonutSelected = false;
        for (Donut donut : donuts) {
            if (donut.getQuantity() > 0) {
                anyDonutSelected = true;
                break;
            }
        }
        if (!anyDonutSelected) {
            Toast.makeText(OrderingDonuts.this, "No donut selected.", Toast.LENGTH_SHORT).show();
            return;
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(OrderingDonuts.this);
        builder.setTitle("Confirm Order");
        builder.setMessage("Are you sure you want to add these items to your order?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                List<Donut> selectedDonuts = new ArrayList<>();
                for (Donut donut : donuts) {
                    if (donut.getQuantity() > 0) {
                        selectedDonuts.add(new Donut(donut));
                    }
                }
                CurrentOrderActivityManager.addToOrder(selectedDonuts);

                for (Donut donut : donuts) {
                    donut.setQuantity(0);
                }
                adapter.notifyDataSetChanged();
                subtotalTextView.setText("Subtotal: $0.00");
                Toast.makeText(OrderingDonuts.this, "Donuts added to order.", Toast.LENGTH_SHORT).show();
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        builder.show();
    }

}