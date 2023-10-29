package com.example.dentalclinic;

import android.os.Bundle;
import android.widget.TextView;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class DoctorReportActivity extends AppCompatActivity implements ManageReportObserver {

    private TextView reportTextView;

    // Firebase
    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_report);

        // Initialize Firebase Authentication
        mAuth = FirebaseAuth.getInstance();

        // Initialize Firebase Realtime Database
        mDatabase = FirebaseDatabase.getInstance().getReference();

        // Initialize UI components
        reportTextView = findViewById(R.id.reportTextView);

        // Retrieve the currently logged-in user
        FirebaseUser currentUser = mAuth.getCurrentUser();

        if (currentUser != null) {
            String userId = currentUser.getUid();

            // Create a reference to the "reports" node in the Realtime Database
            DatabaseReference reportsRef = mDatabase.child("reports");

            reportsRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()) {
                        StringBuilder reportContent = new StringBuilder();
                        for (DataSnapshot reportSnapshot : dataSnapshot.getChildren()) {
                            String report = reportSnapshot.getValue(String.class);
                            reportContent.append(report).append("\n");
                        }

                        // Display the report content in the reportTextView
                        reportTextView.setText(reportContent.toString());
                    } else {
                        Log.d("DoctorReportActivity", "No report data found.");
                        reportTextView.setText("No reports available.");
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    Log.e("DoctorReportActivity", "Database Error: " + databaseError.getMessage());
                    reportTextView.setText("Error loading reports.");
                }
            });
        } else {
            Log.d("DoctorReportActivity", "No user is currently logged in.");
            reportTextView.setText("No user logged in.");
        }
    }

    @Override
    public void updateReport(String report) {
        // Handle the report update in the DoctorReportActivity
        reportTextView.setText(report);
    }
}
