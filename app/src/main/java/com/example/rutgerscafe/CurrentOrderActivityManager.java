package com.example.rutgerscafe;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import java.util.Collection;

/**
 * This class represents the activity for managing the current order in the cafe application.
 * It allows users to add items to the order, cancel items from the order, and place the order.
 * @author Priyansh Parikh, Siddarth Seloth
 */
public class CurrentOrderActivityManager extends AppCompatActivity {
    private ListView orders;
    private static ArrayAdapter<MenuItem> adapter;
    private static Order order = new Order();
    private TextView orderSubtotal, salesTax, total;
    private Button placeOrder;

    /**
     * Called when the activity is starting. Initializes the layout of the activity,
     * sets up the ListView to display the current order, and sets listeners for managing the order.
     *
     * @param savedInstanceState If the activity is being re-initialized after previously being shut down,
     *                           this Bundle contains the data it most recently supplied in onSaveInstanceState(Bundle).
     *                           Note: Otherwise, it is null.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_current_order);
        initializeFields();

        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, order.getMenuItems());
        orders.setAdapter(adapter);
        manageListView(orders);
        placeOrderButtonListener(placeOrder);
        updateOrderCosts();

    }

    /**
     * Adds menu items to the current order.
     *
     * @param orderItems The collection of menu items to add to the order.
     */
    public static void addToOrder(Collection<? extends MenuItem> orderItems)
    {
        addToOrder(orderItems.toArray(new MenuItem[0]));
    }

    /**
     * Adds to items ListView based on passed in MenuItems array
     * @param orderItems - Array of MenuItems
     */
    public static void addToOrder(MenuItem... orderItems) {
        for (MenuItem orderItem : orderItems) {
            if (orderItem != null) {
                order.addMenuItem(orderItem);

            }
        }
    }

    /**
     * Updates the display of order costs (subtotal, sales tax, total).
     */
    private void updateOrderCosts()
    {
        orderSubtotal.setText(MainActivity.formatPrice(order.getOrderSubTotal()));
        salesTax.setText(MainActivity.formatPrice(order.getSalesTax()));
        total.setText(MainActivity.formatPrice(order.getTotal()));
    }

    /**
     * Cancels an item from the current order.
     *
     * @param item The item to cancel.
     */
    public void cancelItem(MenuItem item)
    {
        if(item == null) return;
        order.removeMenuItem(item);
        adapter.remove(item);
    }

    /**
     * Sets a listener for ListView items to prompt the user for confirmation when canceling an item.
     *
     * @param orders The ListView to which the listener is attached.
     */
    private void manageListView(ListView orders)
    {
        orders.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l)
            {
                AlertDialog.Builder alert = new AlertDialog.Builder(view.getContext());
                alert.setTitle("Cancel Item");
                alert.setMessage("Confirming cancelling item");
                alert.setPositiveButton("yes", new DialogInterface.OnClickListener()
                {
                    public void onClick(DialogInterface dialog, int which)
                    {
                        cancelItem(adapter.getItem(i));
                        updateOrderCosts();
                        Toast.makeText(view.getContext(), " Item cancelled.", Toast.LENGTH_LONG).show();
                    }
                }).setNegativeButton("no", new DialogInterface.OnClickListener()
                {
                    public void onClick(DialogInterface dialog, int which)
                    {
                        Toast.makeText(view.getContext(),
                                " Item not cancelled.", Toast.LENGTH_LONG).show();
                    }
                });
                AlertDialog dialog = alert.create();
                dialog.show();
            }
        });
    }

    /**
     * Sets a listener for the place order button to prompt the user for confirmation when placing the order.
     *
     * @param placeOrder The button to which the listener is attached.
     */
    private void placeOrderButtonListener(Button placeOrder)
    {
        placeOrder.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                if(adapter.isEmpty())
                {
                    Toast.makeText(view.getContext(), " No orders to be placed.", Toast.LENGTH_LONG).show();
                    return;
                }
                AlertDialog.Builder alert = new AlertDialog.Builder(view.getContext());
                alert.setTitle("Place order");
                alert.setMessage("Confirming placing order");
                alert.setPositiveButton("yes", new DialogInterface.OnClickListener()
                {
                    public void onClick(DialogInterface dialog, int which)
                    {
                        Order existingOrder = new Order(order);
                        AllOrdersActivityManager.placeOrder(existingOrder);
                        resetOrder();
                        Toast.makeText(view.getContext(), " Order placed.", Toast.LENGTH_SHORT).show();
                    }
                }).setNegativeButton("no", new DialogInterface.OnClickListener()
                {
                    public void onClick(DialogInterface dialog, int which)
                    {
                        Toast.makeText(view.getContext(),
                                " Order not placed.", Toast.LENGTH_LONG).show();
                    }
                });
                AlertDialog dialog = alert.create();
                dialog.show();
            }
        });
    }

    /**
     * Initializes the views used in the activity.
     */
    private void initializeFields()
    {
        orderSubtotal = findViewById(R.id.orderSubTotal);
        salesTax = findViewById(R.id.salesTax);
        total = findViewById(R.id.total);
        orders = findViewById(R.id.orders);
        placeOrder = findViewById(R.id.placeOrder);
    }

    /**
     * Resets the current order.
     */
    public void resetOrder()
    {
        order = new Order();
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, order.getMenuItems()); // Create a new adapter with the updated order
        orders.setAdapter(adapter);
        updateOrderCosts();
    }


}


