package com.example.dentalclinic;

import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class CreditCardPaymentStatusActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_credit_card_payment_status);

        // Retrieve data from the intent
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            // Find the TextViews in the layout
            TextView cardHolderNameTextView = findViewById(R.id.cardHolderNameTextView);
            TextView cardNumberTextView = findViewById(R.id.cardNumberTextView);
            TextView statusTextView = findViewById(R.id.statusTextView);

            if (extras.containsKey("card_holder_name") && extras.containsKey("card_number")) {
                String cardHolderName = extras.getString("card_holder_name");
                String cardNumber = extras.getString("card_number");
                String paymentStatus = extras.getString("payment_status");

                // Set the text of the TextViews
                cardHolderNameTextView.setText("Card Holder Name: " + cardHolderName);
                cardNumberTextView.setText("Credit Card Number: " + cardNumber);
                statusTextView.setText("Payment Status: " + paymentStatus);
            }
        }
    }
}
