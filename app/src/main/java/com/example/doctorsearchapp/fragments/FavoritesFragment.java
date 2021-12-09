package com.example.doctorsearchapp.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.doctorsearchapp.R;
import com.example.doctorsearchapp.adapters.DoctorAdapter;
import com.example.doctorsearchapp.models.Doctor;
import com.example.doctorsearchapp.models.Users;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;


public class FavoritesFragment extends Fragment {

    private static final String TAG = "FavoritesFragment";
    RecyclerView rvFavorites;
    Users currUser;
    ArrayList<Doctor> favoritesList;
    DoctorAdapter adapter;

    public FavoritesFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_favorites, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        rvFavorites = view.findViewById(R.id.rvFavorites);
        currUser = new Users(ParseUser.getCurrentUser());
        favoritesList = new ArrayList<>();

        adapter = new DoctorAdapter(getContext(), favoritesList);
        rvFavorites.setAdapter(adapter);

        rvFavorites.setLayoutManager(new LinearLayoutManager(getContext()));

        queryFavorites();
    }

    public void queryFavorites()
    {
        ParseQuery<Doctor> query = new ParseQuery(Doctor.class);

        query.whereContainedIn("objectId", currUser.getFavorites());

        query.findInBackground(new FindCallback<Doctor>() {
            @Override
            public void done(List<Doctor> doctors, ParseException e) {

                if (e != null)
                {
                    Log.i(TAG, "Issue with getting doctors");
                    return;
                }
                for(Doctor doctor: doctors)
                {
                    Log.i(TAG, "Doctor: " + doctor.getDoctorName() + ", " + doctor.getLocation());
                }
                favoritesList.addAll(doctors);
                adapter.notifyDataSetChanged();
            }
        });

    }
}