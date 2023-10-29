package com.example.dentalclinic;

import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Button;
import android.view.View;
import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class DoctorProfileActivity extends AppCompatActivity {

    private ImageView doctorImageView;
    private TextView firstNameTextView;
    private TextView lastNameTextView;
    private TextView emailTextView;
    private Button seeReportButton;
    private Button seeReviewButton; // New "See Review" button

    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_profile);

        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();

        doctorImageView = findViewById(R.id.doctorImageView);
        firstNameTextView = findViewById(R.id.textViewFirstName);
        lastNameTextView = findViewById(R.id.textViewLastName);
        emailTextView = findViewById(R.id.textViewEmail);
        seeReportButton = findViewById(R.id.buttonSeeReport);
        seeReviewButton = findViewById(R.id.buttonSeeReview); // Initialize the new "See Review" button

        seeReportButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Navigate to the DoctorReportActivity when the "See Report" button is clicked
                Intent intent = new Intent(DoctorProfileActivity.this, DoctorReportActivity.class);
                startActivity(intent);
            }
        });

        seeReviewButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Navigate to the DoctorReviewActivity when the "See Review" button is clicked
                Intent intent = new Intent(DoctorProfileActivity.this, DoctorReviewActivity.class);
                startActivity(intent);
            }
        });

        FirebaseUser currentUser = mAuth.getCurrentUser();

        if (currentUser != null) {
            String userId = currentUser.getUid();

            DatabaseReference userRef = mDatabase.child("users").child(userId);

            userRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()) {
                        String firstName = dataSnapshot.child("first_name").getValue(String.class);
                        String lastName = dataSnapshot.child("last_name").getValue(String.class);
                        String email = dataSnapshot.child("email").getValue(String.class);

                        // TODO: Load and display the doctor's image from a source

                        firstNameTextView.setText("First Name: " + firstName);
                        lastNameTextView.setText("Last Name: " + lastName);
                        emailTextView.setText("Email: " + email);

                        Log.d("DoctorProfileActivity", "First Name=" + firstName + ", Last Name=" + lastName);
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    Log.e("DoctorProfileActivity", "Database Error: " + databaseError.getMessage());
                }
            });
        }
    }
}
