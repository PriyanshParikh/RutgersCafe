package com.example.rutgerscafe;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;


/**
 * This class represents the activity for managing all orders in the cafe application.
 * It displays a list of all orders and allows users to cancel orders.
 * @author Priyansh Parikh, Siddarth Seloth
 */
public class AllOrdersActivityManager extends AppCompatActivity {
    private ListView allOrders;
    private static ArrayAdapter<Order> allOrdersAdapter;

    private static OrderSingleton orders = OrderSingleton.getInstance();

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
        setContentView(R.layout.activity_all_orders);
        allOrders = findViewById(R.id.allOrders);

        allOrdersAdapter = new ArrayAdapter<Order>(this, android.R.layout.simple_list_item_1, new ArrayList(orders.getOrders()));
        allOrders.setAdapter(allOrdersAdapter);
        storeOrdersListViewListener(allOrders);
    }

    /**
     * Method to place an order. Adds the provided order to the list of orders.
     *
     * @param order The order to be placed.
     */
    public static void placeOrder(Order order)
    {
        orders.addOrder(new Order(order));

    }

    /**
     * Method to cancel an order. Removes the provided order from the list of orders
     * and updates the ListView adapter.
     *
     * @param order The order to be cancelled.
     */
    public void cancelOrder(Order order)
    {
        orders.removeOrder(order);
        allOrdersAdapter.remove(order);
    }

    /**
     * Sets a listener for ListView items to prompt the user for confirmation when clicking on an order.
     * If confirmed, cancels the order.
     *
     * @param storeOrders The ListView to which the listener is attached.
     */
    private void storeOrdersListViewListener(ListView storeOrders)
    {
        storeOrders.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l)
            {
                AlertDialog.Builder alert = new AlertDialog.Builder(view.getContext());
                alert.setTitle("Cancel Order");
                alert.setMessage("Confirming cancelling order");
                alert.setPositiveButton("yes", new DialogInterface.OnClickListener()
                {
                    public void onClick(DialogInterface dialog, int which)
                    {
                        cancelOrder(allOrdersAdapter.getItem(i));
                        Toast.makeText(view.getContext(), " Order cancelled.", Toast.LENGTH_LONG).show();
                    }
                }).setNegativeButton("no", new DialogInterface.OnClickListener()
                {
                    public void onClick(DialogInterface dialog, int which)
                    {
                        Toast.makeText(view.getContext(),
                                " Order not cancelled.", Toast.LENGTH_LONG).show();
                    }
                });
                AlertDialog dialog = alert.create();
                dialog.show();
            }
        });
    }
}