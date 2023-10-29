package com.example.dentalclinic;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class PayPalPaymentActivity extends AppCompatActivity implements ManagePaymentStrategy {

    private String patientPassword = "123456"; // Replace with the actual patient's password

    private EditText payPalEmailEditText;
    private EditText passwordEditText;

    private ManagePaymentStrategy paymentStrategy = this; // Use the current activity as the strategy

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay_pal_payment);

        payPalEmailEditText = findViewById(R.id.payPalEmailEditText);
        passwordEditText = findViewById(R.id.passwordEditText);

        Button completePaymentButton = findViewById(R.id.buttonCompletePayment);

        completePaymentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Delegate payment processing to the strategy
                paymentStrategy.processPayment();
            }
        });
    }

    private void showPaymentSuccessMessage(String payPalEmail) {
        // Display a toast message
        Toast.makeText(this, "Payment successful! Payment Status: Paid", Toast.LENGTH_SHORT).show();

        // Create an intent to start the PayPalPaymentStatusActivity
        Intent intent = new Intent(PayPalPaymentActivity.this, PayPalPaymentStatusActivity.class);
        intent.putExtra("email_used", payPalEmail); // Pass the PayPal email used for payment
        intent.putExtra("payment_status", "Paid"); // You can pass the actual payment status
        startActivity(intent);
    }

    private void showPaymentFailedMessage() {
        Toast.makeText(this, "Payment failed. Incorrect password.", Toast.LENGTH_SHORT).show();
    }

    // Implement the processPayment method from the ManagePaymentStrategy interface
    @Override
    public void processPayment() {
        String payPalEmail = payPalEmailEditText.getText().toString();
        String enteredPassword = passwordEditText.getText().toString();

        if (enteredPassword.equals(patientPassword)) {
            // Password is correct, payment successful
            showPaymentSuccessMessage(payPalEmail);
        } else {
            // Incorrect password, payment failed
            showPaymentFailedMessage();
        }
    }
}
