package com.example.rutgerscafe;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import androidx.appcompat.app.AppCompatActivity;

/**
 * This class connects all the activities together from the main page and makes sure that
 * all the image buttons and regular buttons open the other other activities when clicked
 * @author Priyansh Parikh, Siddarth Seloth
 */
public class MainActivity extends AppCompatActivity {

    private ImageButton orderDonuts, orderCoffee, orderSandwich;
    private Button currentOrder, allOrders;

    /**
     * Links all button of GUI to go the respective activities when button clicked
     * @param savedInstanceState - Bundle representing the saved instance of creation
     */
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();


        orderDonuts.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view) {
                Intent donut = new Intent(MainActivity.this, OrderingDonuts.class);
                startActivity(donut);
            }
        });

        orderCoffee.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view) {
                Intent coffee = new Intent(MainActivity.this, OrderingCoffee.class);
                startActivity(coffee);
            }
        });

        orderSandwich.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view) {
                Intent sandwich = new Intent(MainActivity.this, OrderingSandwich.class);
                startActivity(sandwich);
            }
        });



        currentOrder.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view) {
                Intent currentOrder = new Intent(MainActivity.this, CurrentOrderActivityManager.class);
                startActivity(currentOrder);
            }
        });

        allOrders.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent allOrders = new Intent(MainActivity.this, AllOrdersActivityManager.class);
                startActivity(allOrders);
            }
        });


    }

    /**
     * Gives the formatted cost of a menu item
     * @param value - double representing cost
     * @return - String representing the formatted cost
     */
    @SuppressLint("DefaultLocale")
    public static String formatPrice(double value)
    {
        return String.format("%1$.2f", value);
    }

    /**
     * It initializes all the GUI components linking the code to the GUI
     */
    private void initViews()
    {
        orderDonuts = findViewById(R.id.orderDonut);
        orderCoffee = findViewById(R.id.orderCoffee);
        orderSandwich = findViewById(R.id.orderSandwich);
        currentOrder = findViewById(R.id.currentOrder);
        allOrders = findViewById(R.id.allOrders);

    }
}