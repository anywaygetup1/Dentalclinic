package com.example.dentalclinic;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

public class LabWorkerProfileActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lab_worker_profile);

        // Find the "Manage Report" button
        Button manageReportButton = findViewById(R.id.buttonManageReport);

        // Set a click listener for the button
        manageReportButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Handle the button click, e.g., navigate to a "Manage Report" activity
                Intent intent = new Intent(LabWorkerProfileActivity.this, ManageReportActivity.class);
                startActivity(intent);
            }
        });

        // Other code for the LabWorkerProfileActivity
    }
}
