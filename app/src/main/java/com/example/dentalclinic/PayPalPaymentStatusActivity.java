package com.example.dentalclinic;

import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class PayPalPaymentStatusActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay_pal_payment_status);

        // Retrieve data from the intent
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            // Find the TextViews in the layout
            TextView emailTextView = findViewById(R.id.payPalEmailTextView);
            TextView statusTextView = findViewById(R.id.paymentStatusTextView);

            String email = extras.getString("email_used");
            String paymentStatus = extras.getString("payment_status");

            // Set the text of the TextViews
            emailTextView.setText("PayPal Email: " + email);
            statusTextView.setText("Payment Status: " + paymentStatus);
        }
    }
}
