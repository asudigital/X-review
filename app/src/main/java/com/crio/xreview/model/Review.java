package com.crio.xreview.model;

public class Review {
    private int reviewId;
    private int userId;
    private int companyId;
    private String reviewText;
    private int rating;

    public Review(int reviewId, int userId, int companyId, String reviewText, int rating) {
        this.reviewId = reviewId;
        this.userId = userId;
        this.companyId = companyId;
        this.reviewText = reviewText;
        this.rating = rating;
    }

    public int getReviewId() {
        return reviewId;
    }

    public int getUserId() {
        return userId;
    }

    public int getCompanyId() {
        return companyId;
    }

    public String getReviewText() {
        return reviewText;
    }

    public int getRating() {
        return rating;
    }
}

