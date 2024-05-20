package com.example.rutgerscafe;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;


/**
 * This class represents the activity for ordering coffee in the cafe application.
 * Allows users to select the size, quantity, and add-ins for their coffee order.
 * Users can confirm and add their coffee order to the current order.
 * @author Priyansh Parikh, Siddarth Seloth
 */
public class OrderingCoffee extends AppCompatActivity {

    private Spinner size, qty;

    private CheckBox sweetCream, mocha, frenchVanilla, irishCream, caramel;

    private Button addCoffeeToOrder;
    private TextView coffee_subTotalEditText;

    private Coffee coffee;
    private CheckBox[] addIns;
    private final String[] sizes = {"Short", "Tall", "Grande", "Venti"};
    private final List<Integer> quantities = Arrays.asList(1, 2, 3, 4, 5);

    /**
     * Called when the activity is starting. This is where most initialization should go:
     * calling setContentView(int) to inflate the activity's UI, using findViewById(int)
     * to interact with widgets in the UI, and other setup operations.
     *
     * @param savedInstanceState If the activity is being re-initialized after previously
     *                           being shut down then this Bundle contains the data it most
     *                           recently gave.
     *
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ordering_coffee);
        initializeFields();
        addIns = new CheckBox[]{mocha,frenchVanilla,irishCream,sweetCream,caramel};
        size.setAdapter(new ArrayAdapter<>(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, sizes));
        qty.setAdapter(new ArrayAdapter<>(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, quantities));
        coffee = new Coffee((String) size.getSelectedItem(), 0,Integer.parseInt(String.valueOf(qty.getSelectedItem())));
        spinnerListeners(size,  qty);
        addCoffeeButtonHandler(addCoffeeToOrder);
    }

    /**
     * Initializes all views and fields in the activity.
     */
    public void initializeFields() {
        size = findViewById(R.id.breadType);
        qty = findViewById(R.id.protien);
        coffee_subTotalEditText = findViewById(R.id.sandwich_subTotalEditText);
        addCoffeeToOrder = findViewById(R.id.addSandwichToOrder);
        sweetCream = findViewById(R.id.cheese);
        mocha = findViewById(R.id.onion);
        frenchVanilla = findViewById(R.id.frenchVanilla);
        irishCream = findViewById(R.id.tomato);
        caramel = findViewById(R.id.lettuce);
        coffee_subTotalEditText.setText(String.format(Locale.US, "$%.2f", 1.99));

    }

    /**
     * Listener method for checkboxes to add or remove add-ins from the coffee order.
     *
     * @param view The checkbox view that triggered the listener.
     */
    @SuppressLint("DefaultLocale")
    public void checkBoxListener(View view)
    {
        for (CheckBox addIn : this.addIns)
        {
            if(((CheckBox) view).isChecked())
            {
                coffee.addAddIn((String) ((CheckBox) view).getText());
            }
            else
            {
                coffee.removeAddIn((String) ((CheckBox) view).getText());
            }
        }
        coffee_subTotalEditText.setText(String.format(Locale.US, "$%.2f", coffee.price()));
    }

    /**
     * Sets the selected size of the coffee order.
     */
    void selectSize()
    {
        if (size == null) return;
        coffee.setCupSize((String) size.getSelectedItem());
        coffee_subTotalEditText.setText(String.format(Locale.US, "$%.2f", coffee.price()));
    }

    /**
     * Sets the selected quantity of the coffee order.
     */
    void selectQuantity()
    {
        if (qty == null) return;
        coffee.setQuantity((Integer) qty.getSelectedItem());
        coffee_subTotalEditText.setText(String.format(Locale.US, "$%.2f", coffee.price()));
    }

    /**
     * Adds listeners to the size and quantity spinners.
     *
     * @param size The size spinner.
     * @param qty  The quantity spinner.
     */
    private void spinnerListeners(Spinner size, Spinner qty) {
        size.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                selectSize();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

        qty.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                selectQuantity();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
    }

    /**
     * Resets the coffee order form to its default state.
     */
    public void reset()
    {
        qty.setSelection(0);
        size.setSelection(0);
        coffee = new Coffee((String) size.getSelectedItem(), 0,Integer.parseInt(String.valueOf(qty.getSelectedItem())));
        coffee_subTotalEditText.setText(String.format(Locale.US, "$%.2f", coffee.price()));
        sweetCream.setChecked(false);
        caramel.setChecked(false);
        mocha.setChecked(false);
        frenchVanilla.setChecked(false);
        irishCream.setChecked(false);
    }

    /**
     * Handles the button click event to add the coffee order to the current order.
     *
     * @param addCoffeeToOrder The button to add coffee to the order.
     */
    private void addCoffeeButtonHandler(Button addCoffeeToOrder)
    {
        addCoffeeToOrder.setOnClickListener(new View.OnClickListener()
        {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View view)
            {
                AlertDialog.Builder alert = new AlertDialog.Builder(view.getContext());
                alert.setTitle("Add to order");
                alert.setMessage("Confirming adding order");
                alert.setPositiveButton("yes", new DialogInterface.OnClickListener()
                {
                    public void onClick(DialogInterface dialog, int which)
                    {
                        CurrentOrderActivityManager.addToOrder(new Coffee(coffee));
                        reset();
                        Toast.makeText(view.getContext(), " Order added.", Toast.LENGTH_SHORT).show();
                    }
                }).setNegativeButton("no", new DialogInterface.OnClickListener()
                {
                    public void onClick(DialogInterface dialog, int which)
                    {
                        Toast.makeText(view.getContext(),
                                " Order not added.", Toast.LENGTH_LONG).show();
                    }
                });
                AlertDialog dialog = alert.create();
                dialog.show();
            }
        });
    }

}