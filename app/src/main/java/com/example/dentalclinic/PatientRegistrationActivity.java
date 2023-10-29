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
import com.google.firebase.auth.AuthResult; // Import AuthResult
import com.google.firebase.auth.FirebaseAuth; // Import FirebaseAuth
import com.google.firebase.auth.FirebaseUser; // Import FirebaseUser
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class PatientRegistrationActivity extends AppCompatActivity {

    private EditText emailEditText;
    private EditText passwordEditText;
    private Button registerButton;
    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_registration);

        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();

        emailEditText = findViewById(R.id.editTextEmail);
        passwordEditText = findViewById(R.id.editTextPassword);
        registerButton = findViewById(R.id.buttonRegister);

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = emailEditText.getText().toString();
                String password = passwordEditText.getText().toString();

                if (isValidInput(email, password)) {
                    registerPatient(email, password);
                } else {
                    showToast("Invalid credentials");
                }
            }
        });
    }

    private boolean isValidInput(String email, String password) {
        return !email.isEmpty() && android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches() &&
                !password.isEmpty() && password.length() >= 6;
    }

    private void registerPatient(String email, String password) {
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Registration successful
                            showToast("Registration successful");

                            FirebaseUser user = mAuth.getCurrentUser();
                            if (user != null) {
                                // Set user type to "patient" in the Realtime Database
                                mDatabase.child("users").child(user.getUid()).child("user_type").setValue("patient");

                                // Redirect to the patient's profile or login page
                                Intent intent = new Intent(PatientRegistrationActivity.this, PatientProfileActivity.class);
                                startActivity(intent);
                            }
                        } else {
                            // Registration failed
                            showToast("Registration failed: " + task.getException().getMessage());
                        }
                    }
                });
    }

    private void showToast(String message) {
        Toast.makeText(PatientRegistrationActivity.this, message, Toast.LENGTH_SHORT).show();
    }
}
