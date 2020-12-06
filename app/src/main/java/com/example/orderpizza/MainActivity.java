package com.example.orderpizza;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    int cupsOfCoffee = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void increment(View view) {
        if(cupsOfCoffee == 100){
            Toast.makeText(this, "You cannot have more than 100 cups of coffee", Toast.LENGTH_SHORT).show();
            return;
        }
        cupsOfCoffee = cupsOfCoffee + 1;
        displayQuantity(cupsOfCoffee);
    }

    public void decrement(View view) {
        if(cupsOfCoffee == 1){
            Toast.makeText(this, "You cannot have less than 1 cup of coffee", Toast.LENGTH_SHORT).show();
            return;
        }
        cupsOfCoffee = cupsOfCoffee - 1;
        displayQuantity(cupsOfCoffee);
    }

    public void submitOrder(View view) {
        EditText nameField = (EditText) findViewById(R.id.nameOfUser);
        String usersName = nameField.getText().toString();

        // Figure out if the user wants whipped cream topping
        CheckBox whippedCreamCheckBox = (CheckBox) findViewById(R.id.whipped_cream_checkbox);
        boolean hasWhippedCream = whippedCreamCheckBox.isChecked();

        // Figure out if the user wants chocolate topping
        CheckBox chocolateCheckBox = (CheckBox) findViewById(R.id.chocolate_checkbox);
        boolean hasChocolate = chocolateCheckBox.isChecked();

        // Calculate the price
        int price = calculatePrice(hasWhippedCream, hasChocolate);

        // Display the order summary on the screen
        String orderMessage = createOrderSummary(usersName, price, hasWhippedCream, hasChocolate);

        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:"));
        intent.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.email_subject) + usersName);
        intent.putExtra(Intent.EXTRA_EMAIL, "order@starbucks.co.in");
        intent.putExtra(Intent.EXTRA_TEXT, orderMessage);
        if(intent.resolveActivity(getPackageManager()) != null){
            startActivity(intent);
        }
//        displayMessage(orderMessage);
    }

    private int calculatePrice(boolean addWhippedCream, boolean addChocolate) {
        int basePrice = 5;

        if(addWhippedCream){
            basePrice = basePrice + 1;
        }

        if(addChocolate){
            basePrice = basePrice + 2;
        }
        return cupsOfCoffee * basePrice;
    }

    private String createOrderSummary(String name, int price, boolean addWhippedCream, boolean addChocolate) {
        String priceMessage = getString(R.string.order_summary_name, name);
        priceMessage += "\n" + getString(R.string.order_summary_addWhippedCream) + addWhippedCream;
        priceMessage += "\n" + getString(R.string.order_summary_addChocolate) + addChocolate;
        priceMessage += "\n" + getString(R.string.order_summary_quantity) + cupsOfCoffee;
        priceMessage += "\n Total : " + price;
        priceMessage += "\n" + getString(R.string.thank_you);
        return priceMessage;
    }

    /**
     * This method displays the given quantity value on the screen.
     */
    private void displayQuantity(int numberOfCoffees) {
        TextView quantityTextView = (TextView) findViewById(
                R.id.cupsOfCoffee);
        quantityTextView.setText("" + numberOfCoffees);
    }

    //    private void displayMessage(String message) {
//        TextView orderSummaryTextView = (TextView) findViewById(R.id.order_summary_text_view);
//        orderSummaryTextView.setText(message);
//    }
}