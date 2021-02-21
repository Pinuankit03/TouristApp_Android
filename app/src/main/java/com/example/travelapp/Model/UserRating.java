package com.example.travelapp.Model;

public class UserRating {

    private int userId;
    private int placeId;
    private float placeRating;

    public UserRating() {
    }


    public UserRating(int userId, int placeId, float placeRating) {
        this.userId = userId;
        this.placeId = placeId;
        this.placeRating = placeRating;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getPlaceId() {
        return placeId;
    }

    public void setPlaceId(int placeId) {
        this.placeId = placeId;
    }

    public float getPlaceRating() {
        return placeRating;
    }

    public void setPlaceRating(float placeRating) {
        this.placeRating = placeRating;
    }
}
