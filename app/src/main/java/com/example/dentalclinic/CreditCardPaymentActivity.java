package com.example.dentalclinic;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class CreditCardPaymentActivity extends AppCompatActivity implements ManagePaymentStrategy {

    private String patientPassword = "123456"; // Replace with the actual patient's password

    private EditText cardNumberEditText;
    private EditText cardHolderNameEditText;
    private EditText expirationDateEditText;
    private EditText cvvEditText;
    private EditText passwordEditText;

    private ManagePaymentStrategy paymentStrategy = this; // Use the current activity as the strategy

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_credit_card_payment);

        cardNumberEditText = findViewById(R.id.cardNumberEditText);
        cardHolderNameEditText = findViewById(R.id.cardHolderNameEditText);
        expirationDateEditText = findViewById(R.id.expirationDateEditText);
        cvvEditText = findViewById(R.id.cvvEditText);
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

    private void showPaymentSuccessMessage(String cardHolderName, String cardNumber) {
        Toast.makeText(this, "Payment successful! Payment Status: Paid", Toast.LENGTH_SHORT).show();

        // Create an intent to start the CreditCardPaymentStatusActivity
        Intent intent = new Intent(CreditCardPaymentActivity.this, CreditCardPaymentStatusActivity.class);
        intent.putExtra("card_holder_name", cardHolderName);
        intent.putExtra("card_number", cardNumber);
        intent.putExtra("payment_status", "Paid"); // You can pass the actual payment status
        startActivity(intent);
    }

    private void showPaymentFailedMessage() {
        Toast.makeText(this, "Payment failed. Incorrect password.", Toast.LENGTH_SHORT).show();
    }

    // Implement the processPayment method from the ManagePaymentStrategy interface
    @Override
    public void processPayment() {
        String cardNumber = cardNumberEditText.getText().toString();
        String cardHolderName = cardHolderNameEditText.getText().toString();
        String expirationDate = expirationDateEditText.getText().toString();
        String cvv = cvvEditText.getText().toString();
        String enteredPassword = passwordEditText.getText().toString();

        if (enteredPassword.equals(patientPassword)) {
            // Password is correct, payment successful
            showPaymentSuccessMessage(cardHolderName, cardNumber);
        } else {
            // Incorrect password, payment failed
            showPaymentFailedMessage();
        }
    }
}
