package com.example.travelapp.Model;

import java.io.Serializable;
import java.util.ArrayList;

public class TouristListData implements Serializable {

    private int id;
    private String placeName;
    private String description;
    private String website;
    private String phoneNo;
    private String address;
    private ArrayList<String> pricing;
    private ArrayList<String> photo;
    private ArrayList<String> hours;
    private int ratingForPlace;
    private int status;


    public TouristListData(int id, String placeName, String description, String website, String phoneNo,
                           String address, ArrayList<String> pricing, ArrayList<String> photo, ArrayList<String> hours, int status) {
        this.id = id;
        this.placeName = placeName;
        this.description = description;
        this.website = website;
        this.phoneNo = phoneNo;
        this.address = address;
        this.pricing = pricing;
        this.photo = photo;
        this.status = status;
        this.hours = hours;
    }


    public TouristListData() {
    }

    public TouristListData(int id, String placeName, String address, ArrayList<String> photo, String phoneNo) {
        this.id = id;
        this.placeName = placeName;
        this.address = address;
        this.photo = photo;
        this.phoneNo = phoneNo;
    }

    public int getRatingForPlace() {
        return ratingForPlace;
    }

    public void setRatingForPlace(int ratingForPlace) {
        this.ratingForPlace = ratingForPlace;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPlaceName() {
        return placeName;
    }

    public void setPlaceName(String placeName) {
        this.placeName = placeName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public ArrayList<String> getPricing() {
        return pricing;
    }

    public void setPricing(ArrayList<String> pricing) {
        this.pricing = pricing;
    }

    public ArrayList<String> getPhoto() {
        return photo;
    }

    public void setPhoto(ArrayList<String> photo) {
        this.photo = photo;
    }

    public ArrayList<String> getHours() {
        return hours;
    }

    public void setHours(ArrayList<String> hours) {
        this.hours = hours;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
