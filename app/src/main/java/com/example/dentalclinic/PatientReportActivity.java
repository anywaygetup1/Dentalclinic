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

public class PatientReportActivity extends AppCompatActivity implements ManageReportObserver {

    private TextView reportBoxTextView;

    // Firebase
    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_report);

        // Initialize Firebase Authentication
        mAuth = FirebaseAuth.getInstance();

        // Initialize Firebase Realtime Database
        mDatabase = FirebaseDatabase.getInstance().getReference();

        // Initialize UI components
        reportBoxTextView = findViewById(R.id.textViewReportBox);

        // Retrieve the currently logged-in user
        FirebaseUser currentUser = mAuth.getCurrentUser();

        if (currentUser != null) {
            // No need to fetch the user ID here since we're fetching reports directly
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

                        reportBoxTextView.setText(reportContent.toString());
                    } else {
                        Log.d("PatientReportActivity", "No report data found.");
                        reportBoxTextView.setText("No reports available.");
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    Log.e("PatientReportActivity", "Database Error: " + databaseError.getMessage());
                    reportBoxTextView.setText("Error loading reports.");
                }
            });
        } else {
            Log.d("PatientReportActivity", "No user is currently logged in.");
            reportBoxTextView.setText("No user logged in.");
        }
    }

    @Override
    public void updateReport(String report) {
        // Handle the report update in the PatientReportActivity
        reportBoxTextView.setText(report);
    }
}
