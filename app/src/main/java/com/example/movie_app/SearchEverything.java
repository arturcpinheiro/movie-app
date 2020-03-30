/**
 * Assignment 2
 * @author  Kyle Alialy & Artur Pinheiro
 * @version 1.0
 * @since 03/20/2020
 */

package com.example.movie_app;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

import okhttp3.*;

public class SearchEverything extends AppCompatActivity {
    private ArrayList<String> movieNames = new ArrayList<>();
    private ArrayList<String> year = new ArrayList<>();
    private ArrayList<String> imgURL = new ArrayList<>();

    @Override
    /**
     * Method that runs when the file is running.
     */
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_everything);
        getIncomeIntent();
    }
    /**
     * This method is responsible for receiving data from the last page,
     * checking if data exists.
     */
    private void getIncomeIntent() {
        //check if intent has EXTRAS
        if (getIntent().hasExtra("movieName")) {
            Log.d("getIncomeIntent()", "FOUND, WORKING");
            String movieName = getIntent().getStringExtra("movieName");
            try {
                searchMovie(movieName);
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }

    /**
     * This method receive a string, add that to an URL which is a "get" request,
     * then receive a response as json file, and save the important data in ArrayLists.
     * @param s
     * @throws IOException
     */
    public void searchMovie(String s) throws IOException {
        Log.d("searchMovie()", "FOUND, WORKINGssssssssssss");
        URL url = new URL("https://api.themoviedb.org/3/trending/movie/day?api_key=c3c32f14e3770749da1f7d3c8d36f976");
        String [] movieName = s.split(";;;");
        try {
            if(movieName[1].equals("search"))
                url = new URL("https://api.themoviedb.org/3/search/multi?api_key=c3c32f14e3770749da1f7d3c8d36f976&language=en-US&query="+ movieName[0].replace(' ', '+'));
            else if (movieName[1].equals("trending"))
                url = new URL("https://api.themoviedb.org/3/trending/movie/day?api_key=c3c32f14e3770749da1f7d3c8d36f976");
            else if(movieName[1].equals("theatres"))
                url = new URL("https://api.themoviedb.org/3/movie/now_playing?api_key=c3c32f14e3770749da1f7d3c8d36f976&language=en-US&page=1");
            else if(movieName[1].equals("topRated"))
                url = new URL("https://api.themoviedb.org/3/movie/top_rated?api_key=c3c32f14e3770749da1f7d3c8d36f976&language=en-US&page=1");
            else
                url = new URL("https://api.themoviedb.org/3/movie/upcoming?api_key=c3c32f14e3770749da1f7d3c8d36f976&language=en-US&page=1");

        } catch (IOException e) {
            e.printStackTrace();
        }
        Log.d("searchMovie()",url.toString());
        //Flag to check if the url is directed to movies only.
        final boolean flag;
        if(url.toString().contains("movie"))
            flag = true;
        else
            flag = false;


        //Create Requests.
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(url)
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            /**
             * method called when request fails.
             */
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }
            @Override
            /**
             * method called when request works.
             */
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    final String myResponse = response.body().string(); // JSON string
                    SearchEverything.this.runOnUiThread(new Runnable() {
                        @Override
                        /**
                         * Run thread for the response from the web API
                         */
                        public void run() {
                            try {
                                //Create Json object/Array
                                JSONObject getArray = new JSONObject(myResponse);
                                JSONArray arr = getArray.getJSONArray("results");
                                for (int i = 0; i < arr.length(); ++i) {
                                    //Iterate through the array and for each inder, create a json Object
                                    JSONObject jObject = arr.getJSONObject(i);
                                    if(jObject.has("media_type"))
                                    {
                                        //Checks For media type and if it has a title
                                    if (jObject.getString("media_type").contentEquals("movie") && jObject.has("title")) {
                                        if(!(jObject.getString("poster_path").length() < 10))
                                        {
                                            movieNames.add(jObject.getString("title"));
                                            if(jObject.getString("release_date").length() > 4)
                                                year.add(jObject.getString("release_date").substring(0,4));
                                            else
                                                year.add("N/A");
                                            imgURL.add("https://image.tmdb.org/t/p/w500" + jObject.getString("poster_path"));
                                        }
                                    }
                                    //Get person related search
                                    else if(jObject.getString("media_type").contentEquals("person")){
                                        Log.d("searchMovie()SSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSS PERSON CALLED", "Person");
                                        JSONArray innerArray = jObject.getJSONArray("known_for");
                                        for (int j = 0; j < innerArray.length(); ++j) {
                                            JSONObject innerObject = innerArray.getJSONObject(j);
                                            Log.d("searchMovie()SSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSS PERSON CALLED", "ll");
                                            if (innerObject.has("media_type") && innerObject.has("title") && innerObject.has("poster_path") && innerObject.has("release_date")) {

                                                    if (innerObject.getString("media_type").contentEquals("movie")) {
                                                        if (!(innerObject.getString("poster_path").length() < 10)) {
                                                            if (innerObject.getString("title").length() < 1)
                                                                movieNames.add("N/A");
                                                            else
                                                                movieNames.add(innerObject.getString("title"));
                                                            //movieDescs.add(jObject.getString("overview"));
                                                            if (innerObject.getString("release_date").length() > 4)
                                                                year.add(innerObject.getString("release_date").substring(0, 4));
                                                            else
                                                                year.add("N/A");
                                                            imgURL.add("https://image.tmdb.org/t/p/w500" + innerObject.getString("poster_path"));
                                                        }
                                                    }
                                            }
                                        }
                                    }
                                    }
                                    //If everything before not included and flag is up, that means it is directed to movie.
                                    else if(flag == true)
                                    {
                                        if(!(jObject.getString("poster_path").length() < 10) && jObject.has("title"))
                                        {
                                            movieNames.add(jObject.getString("title"));
                                            //movieDescs.add(jObject.getString("overview"));
                                            if(jObject.getString("release_date").length() > 4)
                                                year.add(jObject.getString("release_date").substring(0,4));
                                            else
                                                year.add("N/A");
                                            imgURL.add("https://image.tmdb.org/t/p/w500" + jObject.getString("poster_path"));
                                        }
                                    }
                                }
                                initRecyclerView();
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    });
                }
            }
        });
    }

    /**
     * Initialize the recyclerView class, passing the context and parameters we want to search in the other API.
     */
    private void initRecyclerView () {
        RecyclerView rcView = findViewById(R.id.parentContainer);
        RecyclerViewMovies movies = new RecyclerViewMovies(movieNames, year, imgURL, this);
        rcView.setAdapter(movies);
        rcView.setLayoutManager(new LinearLayoutManager((this)));
    }

}
