package com.example.doctorsearchapp.models;

import android.os.Parcelable;
import android.util.Log;
import android.widget.RatingBar;

import com.example.doctorsearchapp.R;
import com.parse.FindCallback;
import com.parse.ParseClassName;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import org.parceler.Parcel;

import java.util.ArrayList;
import java.util.List;

@ParseClassName("Doctor")
@Parcel(analyze = Doctor.class)
public class Doctor extends ParseObject implements Parcelable {

    public static final String KEY_DOCTOR_NAME = "doctorName";
    public static final String KEY_LOCATION = "location";
    public static final String KEY_REVIEW_LIST = "reviewList";
    public static final String KEY_RATING = "overallRating";

    // Empty constructor for Parceler library
    public Doctor() { }

    public String getDoctorName() {
        return getString(KEY_DOCTOR_NAME);
    }

    public void setDoctorName(String doctorName) {
        put(KEY_DOCTOR_NAME, doctorName);
    }

    public String getLocation() {
        return getString(KEY_LOCATION);
    }

    public ArrayList<String> getReviews()
    {
        return (ArrayList<String>) get(KEY_REVIEW_LIST);
    }

    public void saveReview(String reviewId)
    {
        ArrayList<String> reviews = getReviews();
        reviews.add(reviewId);
        put(KEY_REVIEW_LIST, reviews);
        saveInBackground();
    }

    public void setLocation(String location) {
        put(KEY_LOCATION, location);
    }

    public Float getRating()
    {
        return (Float) get(KEY_RATING);
    }

    public void saveRating(Float rating)
    {
        put(KEY_RATING, rating);
        saveInBackground();
    }

    public void setRating(RatingBar rbOverallRating)
    {
        ArrayList<String> reviews = getReviews();
        ParseQuery<Reviews> query = ParseQuery.getQuery(Reviews.class);
        Float originalRating = getRating();

        query.whereContainedIn("objectId", reviews);

        query.findInBackground(new FindCallback<Reviews>() {
            @Override
            public void done(List<Reviews> reviews, ParseException e) {

                Float maxRating = reviews.size() * 5.0f;
                int rating = 0;

                if (e != null)
                {
                    Log.i("DoctorAdpater", "something went wrong");
                }

                for (Reviews review : reviews)
                {
                    rating += review.getRating();
                }

                Float finalRating = (rating / maxRating) * 5.0f;

                if(originalRating != finalRating)
                {
                    saveRating(finalRating);
                }
                rbOverallRating.setRating(finalRating);
            }
        });
    }

//    public int doctorId;
//    public String doctorName;
//    public float overallRating;
//    public String location;
//    public ArrayList<Integer> reviewList;

//    public Doctor() {};
//
//    public Doctor(int doctorId, String doctorName, int overallRating, String location, ArrayList<Integer> reviewList) {
//        this.doctorId = doctorId;
//        this.doctorName = doctorName;
//        this.overallRating = overallRating;
//        this.location = location;
//        this.reviewList = reviewList;
//    }
//
//    public int getDoctorId() {
//        return doctorId;
//    }
//
//    public void setDoctorId(int doctorId) {
//        this.doctorId = doctorId;
//    }
//
//    public String getDoctorName() {
//        return doctorName;
//    }
//
//    public void setDoctorName(String doctorName) {
//        this.doctorName = doctorName;
//    }
//
//    public float getOverallRating() {
//        return overallRating;
//    }
//
//    public void setOverallRating(int overallRating) {
//        this.overallRating = overallRating;
//    }
//
//    public String getLocation() {
//        return location;
//    }
//
//    public void setLocation(String location) {
//        this.location = location;
//    }
//
//    public ArrayList<Integer> getReviewList() {
//        return reviewList;
//    }
//
//    public void setReviewList(ArrayList<Integer> reviewList) {
//        this.reviewList = reviewList;
//    }
}
