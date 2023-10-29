package com.example.dentalclinic;

public class PatientReview implements ReviewComponent {
    private final ReviewComponent decoratedReview;
    private final String patientReview;

    public PatientReview(ReviewComponent decoratedReview, String patientReview) {
        this.decoratedReview = decoratedReview;
        this.patientReview = patientReview;
    }

    @Override
    public String getReview() {
        return decoratedReview.getReview() + "\n" + "Patient's Review: " + patientReview;
    }
}

