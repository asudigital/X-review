package com.crio.xreview.model;

public class ReviewReply {
    private int reviewId;
    private int ownerId;
    private String replyText;

    public ReviewReply(int reviewId, int ownerId, String replyText) {
        this.reviewId = reviewId;
        this.ownerId = ownerId;
        this.replyText = replyText;
    }

    public int getReviewId() {
        return reviewId;
    }

    public int getOwnerId() {
        return ownerId;
    }

    public String getReplyText() {
        return replyText;
    }
}
