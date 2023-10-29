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
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {

    private EditText emailEditText;
    private EditText passwordEditText;
    private Button loginButton;
    private Button patientRegistrationButton;
    private Button doctorRegistrationButton;
    private Button labWorkerRegistrationButton; // Add the Lab Worker registration button
    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        patientRegistrationButton = findViewById(R.id.buttonPatientRegistration);
        doctorRegistrationButton = findViewById(R.id.buttonDoctorRegistration);
        labWorkerRegistrationButton = findViewById(R.id.buttonLabWorkerRegistration); // Initialize the Lab Worker registration button
        loginButton = findViewById(R.id.buttonLogin);

        emailEditText = findViewById(R.id.editTextEmail);
        passwordEditText = findViewById(R.id.editTextPassword);

        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();

        patientRegistrationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startRegistrationActivity(PatientRegistrationActivity.class);
            }
        });

        doctorRegistrationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startRegistrationActivity(DoctorRegistrationActivity.class);
            }
        });

        labWorkerRegistrationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startRegistrationActivity(LabWorkerRegistrationActivity.class); // Start Lab Worker registration
            }
        });

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = emailEditText.getText().toString();
                String password = passwordEditText.getText().toString();

                if (isValidInput(email, password)) {
                    loginUser(email, password);
                } else {
                    showToast("Invalid credentials");
                }
            }
        });
    }

    private void startRegistrationActivity(Class<?> activityClass) {
        Intent intent = new Intent(MainActivity.this, activityClass);
        startActivity(intent);
    }

    private boolean isValidInput(String email, String password) {
        return !email.isEmpty() && android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches() &&
                !password.isEmpty() && password.length() >= 6;
    }

    private void loginUser(String email, String password) {
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            FirebaseUser user = mAuth.getCurrentUser();
                            if (user != null) {
                                checkUserTypeAndNavigate(user.getUid());
                            } else {
                                showToast("User is null");
                            }
                        } else {
                            showToast("Login failed");
                        }
                    }
                });
    }

    private void checkUserTypeAndNavigate(String userId) {
        DatabaseReference userRef = mDatabase.child("users").child(userId).child("user_type");
        userRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String userType = dataSnapshot.getValue(String.class);
                if ("doctor".equals(userType)) {
                    startProfileActivity(DoctorProfileActivity.class);
                } else if ("patient".equals(userType)) {
                    startProfileActivity(PatientProfileActivity.class);
                } else if ("lab_worker".equals(userType)) { // Add Lab Worker profile activity
                    startProfileActivity(LabWorkerProfileActivity.class);
                } else {
                    showToast("Unknown user type");
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                showToast("Error getting user type: " + error.getMessage());
            }
        });
    }

    private void startProfileActivity(Class<?> activityClass) {
        Intent intent = new Intent(MainActivity.this, activityClass);
        startActivity(intent);
    }

    private void showToast(String message) {
        Toast.makeText(MainActivity.this, message, Toast.LENGTH_SHORT).show();
    }
}
