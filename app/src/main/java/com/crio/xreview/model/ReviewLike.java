package com.crio.xreview.model;

public class ReviewLike {
    private int reviewId;
    private int userId;

    public ReviewLike(int reviewId, int userId) {
        this.reviewId = reviewId;
        this.userId = userId;
    }

    public int getReviewId() {
        return reviewId;
    }

    public int getUserId() {
        return userId;
    }
}

