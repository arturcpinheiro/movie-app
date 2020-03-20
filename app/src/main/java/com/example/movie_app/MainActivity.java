package com.example.movie_app;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

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

public class MainActivity extends AppCompatActivity {
    private String userInput = "";
    private ArrayList<String> movieNames = new ArrayList<>();
    //private ArrayList<String> movieDescs = new ArrayList<>();
    private ArrayList<String> year = new ArrayList<>();
    private ArrayList<String> imgURL = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


    }

    public void searchMovie(String s) throws IOException {
        String data = "";
        URL url = new URL("https://api.themoviedb.org/3/search/multi?api_key=c3c32f14e3770749da1f7d3c8d36f976&language=en-US&query="+ s.replace(' ', '+'));
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(url)
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    final String myResponse = response.body().string(); // JSON string
                    MainActivity.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                JSONObject getArray = new JSONObject(myResponse);
                                JSONArray arr = getArray.getJSONArray("results");
                                for (int i = 0; i < arr.length(); ++i) {
                                    JSONObject jObject = arr.getJSONObject(i);
                                    if (jObject.getString("media_type").contentEquals("movie")) {
                                        if(!(jObject.getString("poster_path").length() < 10))
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


                public void userInput (View vi){
                    year.clear();
                    movieNames.clear();
                    imgURL.clear();
                    TextView text = findViewById(R.id.editText);
                    this.userInput = text.getText().toString();
                    Log.d("info", userInput);
                    try {
                        searchMovie(userInput);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

                private void initRecyclerView () {
                    RecyclerView rcView = findViewById(R.id.parentContainer);
                    RecyclerViewMovies movies = new RecyclerViewMovies(movieNames, year, imgURL, this);
                    rcView.setAdapter(movies);
                    rcView.setLayoutManager(new LinearLayoutManager((this)));

                }

            }
