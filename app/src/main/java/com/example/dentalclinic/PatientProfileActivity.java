package com.example.dentalclinic;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class PatientProfileActivity extends AppCompatActivity {

    // Define UI components
    private ImageView patientImageView;
    private TextView firstNameTextView;
    private TextView lastNameTextView;
    private TextView emailTextView;
    private Button makePaymentButton;
    private Button seeReportButton;
    private Button giveReviewButton; // New "Give Review" button

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_profile);

        // Initialize UI components
        patientImageView = findViewById(R.id.patientImageView);
        firstNameTextView = findViewById(R.id.textViewFirstName);
        lastNameTextView = findViewById(R.id.textViewLastName);
        emailTextView = findViewById(R.id.textViewEmail);
        makePaymentButton = findViewById(R.id.buttonMakePayment);
        seeReportButton = findViewById(R.id.buttonSeeReport);
        giveReviewButton = findViewById(R.id.buttonGiveReview); // Initialize the new "Give Review" button with the correct ID


        // Set a click listener for the "Make Payment" button
        makePaymentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // When the button is clicked, navigate to the PatientPaymentActivity
                Intent intent = new Intent(PatientProfileActivity.this, PatientPaymentActivity.class);
                startActivity(intent);
            }
        });

        // Set a click listener for the "See Report" button
        seeReportButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Handle "See Report" button click
                navigateToReportActivity();
            }
        });

        // Set a click listener for the "Give Review" button
        giveReviewButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Handle "Give Review" button click
                navigateToReviewActivity();
            }
        });
    }

    private void navigateToReportActivity() {
        // Create an intent to open the PatientReportActivity
        Intent intent = new Intent(PatientProfileActivity.this, PatientReportActivity.class);
        startActivity(intent);
    }

    private void navigateToReviewActivity() {
        // Create an intent to open the PatientReviewActivity
        Intent intent = new Intent(PatientProfileActivity.this, PatientReviewActivity.class);
        startActivity(intent);
    }
}
