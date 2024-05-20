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
import java.util.Locale;

/**
 * This class represents the activity for ordering sandwiches in the cafe application.
 * Allows users to select the bread, protein, and add-ins for their sandwich order.
 * Users can confirm and add their sandwich order to the current order.
 * @author Priyansh Parikh, Siddarth Seloth
 */
public class OrderingSandwich extends AppCompatActivity {
    private Spinner breadType, protein;
    private CheckBox lettuce, tomato, onion, cheese;
    private Button addSandwichToOrder;
    private TextView sandwich_subTotalEditText;

    private Sandwich sandwich;
    private CheckBox[] addIns;
    private final String[] breads = {"Bagel", "Wheat Bread", "Sour Dough"};
    private final String[] proteins = {"beef", "Chicken", "Fish"};

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
        setContentView(R.layout.activity_ordering_sandwich);
        initializeFields();
        addIns = new CheckBox[]{lettuce, tomato, onion, cheese};
        breadType.setAdapter(new ArrayAdapter<>(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, breads));
        protein.setAdapter(new ArrayAdapter<>(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, proteins));
        sandwich = new Sandwich((String) protein.getSelectedItem(), (String) breadType.getSelectedItem());
        spinnerListeners(breadType,  protein);
        addSandwichToOrder(addSandwichToOrder);
    }

    /**
     * Initializes all views and fields in the activity.
     */
    public void initializeFields() {
        breadType = findViewById(R.id.breadType);
        protein = findViewById(R.id.protien);
        sandwich_subTotalEditText = findViewById(R.id.sandwich_subTotalEditText);
        addSandwichToOrder = findViewById(R.id.addSandwichToOrder);
        cheese = findViewById(R.id.cheese);
        onion = findViewById(R.id.onion);
        tomato = findViewById(R.id.tomato);
        lettuce = findViewById(R.id.lettuce);
        sandwich_subTotalEditText.setText(String.format(Locale.US, "$%.2f", 10.99));

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
                sandwich.addAddOn((String) ((CheckBox) view).getText());
            }
            else
            {
                sandwich.removeAddOn((String) ((CheckBox) view).getText());
            }
        }
        sandwich_subTotalEditText.setText(String.format(Locale.US, "$%.2f", sandwich.price()));
    }

    /**
     * Sets the selected bread of the sandwich order.
     */
    void selectBread()
    {
        if (breadType == null) return;
        sandwich.setBread((String) breadType.getSelectedItem());
        sandwich_subTotalEditText.setText(String.format(Locale.US, "$%.2f", sandwich.price()));
    }

    /**
     * Sets the selected protein of the sandwich order.
     */
    void selectProtein()
    {
        if (protein == null) return;
        sandwich.setProtein((String) protein.getSelectedItem());
        sandwich_subTotalEditText.setText(String.format(Locale.US, "$%.2f", sandwich.price()));
    }


    /**
     * Adds listeners to the size and quantity spinners.
     *
     * @param breadType The breadType spinner.
     * @param protein  The protein spinner.
     */
    private void spinnerListeners(Spinner breadType, Spinner protein) {
        breadType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                selectBread();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

        protein.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                selectProtein();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
    }

    /**
     * Resets the sandwich order form to its default state.
     */
    public void reset()
    {
        breadType.setSelection(0);
        protein.setSelection(0);
        sandwich = new Sandwich((String) protein.getSelectedItem(), (String) breadType.getSelectedItem());
        sandwich_subTotalEditText.setText(String.format(Locale.US, "$%.2f", sandwich.price()));
        lettuce.setChecked(false);
        tomato.setChecked(false);
        onion.setChecked(false);
        cheese.setChecked(false);
    }

    /**
     * Handles the button click event to add the sandwich order to the current order.
     *
     * @param addSandwichToOrder The button to add sandwich to the order.
     */
    private void addSandwichToOrder(Button addSandwichToOrder)
    {
        addSandwichToOrder.setOnClickListener(new View.OnClickListener()
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
                        CurrentOrderActivityManager.addToOrder(new Sandwich(sandwich));
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