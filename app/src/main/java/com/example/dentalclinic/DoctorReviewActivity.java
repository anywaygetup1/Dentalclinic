package com.example.dentalclinic;

import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class DoctorReviewActivity extends AppCompatActivity {

    private TextView reviewTextView;
    private DatabaseReference databaseReference;

    // Create an instance of the DoctorReview class
    private ReviewComponent doctorReview = new DoctorReview();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_review);

        // Initialize your UI component
        reviewTextView = findViewById(R.id.reviewTextView);

        // Initialize Firebase Realtime Database reference
        databaseReference = FirebaseDatabase.getInstance().getReference().child("reviews");

        // Display existing reviews from the database and decorate them with doctor's reviews
        displayDoctorReviews();
    }

    private void displayDoctorReviews() {
        // Listen for changes in the "reviews" node of the database
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    StringBuilder reviewsBuilder = new StringBuilder();

                    for (DataSnapshot reviewSnapshot : dataSnapshot.getChildren()) {
                        String review = reviewSnapshot.getValue(String.class);
                        reviewsBuilder.append(review).append("\n");
                    }

                    // Decorate the reviews with doctor's reviews
                    String decoratedReviews = decorateDoctorReviews(reviewsBuilder.toString());
                    reviewTextView.setText(decoratedReviews);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Handle any errors, if necessary
            }
        });
    }

    private String decorateDoctorReviews(String reviews) {
        // Create an instance of DoctorReview and decorate the existing reviews with doctor's reviews
        return doctorReview.getReview() + "\n" + reviews;
    }
}
