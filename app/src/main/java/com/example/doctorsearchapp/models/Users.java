package com.example.doctorsearchapp.models;

import android.util.Log;

import com.parse.ParseFile;
import com.parse.ParseUser;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class Users {

    ParseUser user;
    private static final String KEY_IMAGE = "profilePicture";
    private static final String KEY_FAVORITES = "favoritesList";

    public Users(ParseUser user)
    {
        this.user = user;
    }

    public String getUsername()
    {
        return user.getUsername();
    }

    public ParseFile getProfilePic()
    {
        return user.getParseFile(KEY_IMAGE);
    }

    public String getProfileCreation()
    {
        return new SimpleDateFormat("MMMM dd, yyyy").format(user.getCreatedAt());
    }

    public ArrayList<String> getFavorites()
    {
        return (ArrayList<String>) user.get(KEY_FAVORITES);
    }

    public void addFavorite(String doctorId)
    {
        ArrayList<String> favorites = getFavorites();

        if (favorites == null)
        {
            favorites = new ArrayList<String>();
        }

        if(favorites.contains(doctorId))
        {
            return;
        }

        favorites.add(doctorId);
        user.put(KEY_FAVORITES, favorites);
        user.saveInBackground();
    }

    public void removeFavorite(String doctorId)
    {
        ArrayList<String> favorites = getFavorites();
        favorites.remove(doctorId);
        user.put(KEY_FAVORITES, favorites);
        user.saveInBackground();
    }

}