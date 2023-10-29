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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class DoctorRegistrationActivity extends AppCompatActivity {

    // Define UI components
    private EditText firstNameEditText;
    private EditText lastNameEditText;
    private EditText emailEditText;
    private EditText passwordEditText;
    private Button registerButton;

    // Firebase Authentication
    private FirebaseAuth mAuth;

    // Firebase Realtime Database
    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_registration);

        // Initialize Firebase Authentication
        mAuth = FirebaseAuth.getInstance();

        // Initialize Firebase Realtime Database
        mDatabase = FirebaseDatabase.getInstance().getReference();

        // Initialize UI components
        firstNameEditText = findViewById(R.id.editTextFirstName);
        lastNameEditText = findViewById(R.id.editTextLastName);
        emailEditText = findViewById(R.id.editTextEmail);
        passwordEditText = findViewById(R.id.editTextPassword);
        registerButton = findViewById(R.id.buttonRegister);

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Get user inputs
                String firstName = firstNameEditText.getText().toString();
                String lastName = lastNameEditText.getText().toString();
                String email = emailEditText.getText().toString();
                String password = passwordEditText.getText().toString();

                if (isValidInput(firstName, lastName, email, password)) {
                    // Register the user with Firebase Authentication
                    mAuth.createUserWithEmailAndPassword(email, password)
                            .addOnCompleteListener(DoctorRegistrationActivity.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        // Registration successful
                                        Toast.makeText(DoctorRegistrationActivity.this, "Registration successful", Toast.LENGTH_SHORT).show();

                                        // Save user data to Realtime Database
                                        saveUserDataToDatabase(firstName, lastName, email, "doctor"); // Set user type to "doctor"

                                        // Navigate to the doctor's login page
                                        Intent loginIntent = new Intent(DoctorRegistrationActivity.this, DoctorLoginActivity.class);
                                        startActivity(loginIntent);
                                    } else {
                                        // Registration failed, handle the error.
                                        Toast.makeText(DoctorRegistrationActivity.this, "Registration failed: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                }
            }
        });
    }

    private boolean isValidInput(String firstName, String lastName, String email, String password) {
        // Implement your validation logic here
        // For example, check if fields are not empty and validate email format.
        // You can also check password complexity.

        boolean isValid = true;

        if (firstName.isEmpty()) {
            isValid = false;
            firstNameEditText.setError("Please enter your first name.");
        }

        if (lastName.isEmpty()) {
            isValid = false;
            lastNameEditText.setError("Please enter your last name.");
        }

        if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            isValid = false;
            emailEditText.setError("Please enter a valid email address.");
        }

        if (password.isEmpty() || password.length() < 6) {
            isValid = false;
            passwordEditText.setError("Password must be at least 6 characters.");
        }

        return isValid;
    }

    private void saveUserDataToDatabase(String firstName, String lastName, String email, String userType) {
        // Get the user's unique ID from Firebase Authentication
        String userId = mAuth.getCurrentUser().getUid();

        // Save the user data to Realtime Database under the "users" node with the user's unique ID
        mDatabase.child("users").child(userId).child("first_name").setValue(firstName);
        mDatabase.child("users").child(userId).child("last_name").setValue(lastName);
        mDatabase.child("users").child(userId).child("email").setValue(email);
        mDatabase.child("users").child(userId).child("user_type").setValue(userType);
    }
}
