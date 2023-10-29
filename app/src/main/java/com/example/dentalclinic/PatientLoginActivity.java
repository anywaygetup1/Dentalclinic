package com.example.dentalclinic;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class PatientLoginActivity extends AppCompatActivity {

    // Define UI components
    private EditText emailEditText;
    private EditText passwordEditText;
    private Button loginButton;

    // Firebase Authentication
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_login);

        // Initialize Firebase Authentication
        mAuth = FirebaseAuth.getInstance();

        // Initialize UI components
        emailEditText = findViewById(R.id.editTextEmail);
        passwordEditText = findViewById(R.id.editTextPassword);
        loginButton = findViewById(R.id.buttonLogin);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Get user inputs
                String email = emailEditText.getText().toString();
                String password = passwordEditText.getText().toString();

                if (isValidInput(email, password)) {
                    // Sign in with Firebase Authentication
                    mAuth.signInWithEmailAndPassword(email, password)
                            .addOnCompleteListener(PatientLoginActivity.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        // Login successful, navigate to the patient profile or another activity
                                        Toast.makeText(PatientLoginActivity.this, "Login successful", Toast.LENGTH_SHORT).show();

                                        // Add code to navigate to the PatientProfileActivity here
                                        Intent intent = new Intent(PatientLoginActivity.this, PatientProfileActivity.class);
                                        startActivity(intent);
                                    } else {
                                        // Login failed, handle the error.
                                        Toast.makeText(PatientLoginActivity.this, "Login failed: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                }
            }
        });
    }

    // Validate user input (email and password)
    private boolean isValidInput(String email, String password) {
        boolean isValid = true;

        if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            isValid = false;
            emailEditText.setError("Please enter a valid email address.");
        }

        if (password.isEmpty() || password.length() < 6) {
            isValid = false;
            passwordEditText.setError("Invalid password");
        }

        return isValid;
    }
}
