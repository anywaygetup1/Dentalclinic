package com.example.dentalclinic;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class PatientReviewActivity extends AppCompatActivity {

    private EditText patientReviewEditText;
    private Button submitReviewButton;
    private DatabaseReference databaseReference;
    private TextView reviewTextView; // TextView to display the decorated review
    private ReviewComponent doctorReview = new DoctorReview(); // Instance of the doctor's review

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_review);

        patientReviewEditText = findViewById(R.id.editTextReview);
        submitReviewButton = findViewById(R.id.buttonSubmitReview);
        reviewTextView = findViewById(R.id.reviewTextView);

        // Initialize Firebase Realtime Database reference
        databaseReference = FirebaseDatabase.getInstance().getReference().child("reviews");

        submitReviewButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String patientReviewText = patientReviewEditText.getText().toString();

                if (!patientReviewText.isEmpty()) {
                    // Decorate the patient's review with the doctor's review
                    String decoratedReview = decorateReviewWithDoctorReview(patientReviewText);
                    // Save the decorated review to Firebase
                    saveReviewToDatabase(decoratedReview);

                    // Display a success message
                    Toast.makeText(PatientReviewActivity.this, "Review submitted successfully!", Toast.LENGTH_SHORT).show();
                } else {
                    // Display an error message if the patient's review is empty
                    Toast.makeText(PatientReviewActivity.this, "Please enter your review.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private String decorateReviewWithDoctorReview(String patientReviewText) {
        // Create an instance of PatientReview and decorate the patient's review with the doctor's review
        PatientReview patientReview = new PatientReview(doctorReview, patientReviewText);
        return patientReview.getReview();
    }

    private void saveReviewToDatabase(String reviewText) {
        // Push the decorated review to Firebase Realtime Database under the "reviews" node
        databaseReference.push().setValue(reviewText);
    }
}
