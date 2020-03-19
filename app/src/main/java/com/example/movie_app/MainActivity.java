package com.example.movie_app;

import android.os.Bundle;
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
    private ArrayList<String> movieDescs = new ArrayList<>();
    private ArrayList<String> imgURL = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        try {
            searchMovie("Batman");
        } catch (IOException e) {
            e.printStackTrace();
        }

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
                                        movieNames.add(jObject.getString("title"));
                                        movieDescs.add(jObject.getString("overview"));
                                        imgURL.add("https://image.tmdb.org/t/p/w500" + jObject.getString("poster_path"));
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
                    TextView text = findViewById(R.id.userSearch);
                    this.userInput = text.getText().toString();

                }

                private void initRecyclerView () {
                    RecyclerView rcView = findViewById(R.id.parentContainer);
                    RecyclerViewMovies movies = new RecyclerViewMovies(movieNames, movieDescs, imgURL, this);
                    rcView.setAdapter(movies);
                    rcView.setLayoutManager(new LinearLayoutManager((this)));

                }

   /* private void newPage(View v){
        Intent i = new Intent(this, MoviePage.class);
        startActivity(i);
    }
        private void dummy(){
        movieNames.add("Batman");
            movieDescs.add("Wealthy entrepreneur Bruce Wayne and his ward Dick" +
                    " Grayson lead a double life: they are actually crime fighting duo Batman and " +
                    "Robin. A secret Batpole in the Wayne mansion leads to the Batcave, where Police" +
                    " Commissioner Gordon often calls with the latest emergency threatening Gotham City." +
                    " Racing the the scene of the crime in the Batmobile, Batman and Robin must (with the help" +
                    " of their trusty Bat-utility-belt) thwart the efforts of a variety of master criminals, including" +
                    " The Riddler, The Joker, Catwoman, and The Penguin.");
            imgURL.add("https://image.tmdb.org/t/p/w500/1ZEJuuDh0Zpi5ELM3Zev0GBhQ3R.jpg");
            movieNames.add("Batman");
            movieDescs.add("Wealthy entrepreneur Bruce Wayne and his ward Dick" +
                    " Grayson lead a double life: they are actually crime fighting duo Batman and " +
                    "Robin. A secret Batpole in the Wayne mansion leads to the Batcave, where Police" +
                    " Commissioner Gordon often calls with the latest emergency threatening Gotham City." +
                    " Racing the the scene of the crime in the Batmobile, Batman and Robin must (with the help" +
                    " of their trusty Bat-utility-belt) thwart the efforts of a variety of master criminals, including" +
                    " The Riddler, The Joker, Catwoman, and The Penguin.");
            imgURL.add("https://image.tmdb.org/t/p/w500/1ZEJuuDh0Zpi5ELM3Zev0GBhQ3R.jpg");
        initRecyclerView();
    }*/
            }
