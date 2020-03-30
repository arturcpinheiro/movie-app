package com.example.movie_app;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URL;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class movieInfo extends AppCompatActivity {
    String title;
    String rYear;
    String runtime;
    String genre;
    String actors;
    String writers;
    String directors;
    String description;
    String metascore;
    String imdbRating;
    String posterURL;

    @Override
    /**
     * Method that runs when the file is running.
     */
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_info);
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
            String y = getIntent().getStringExtra("year");
            try {
                searchMovie(movieName, y);
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }

    /**
     * this method searches for a movie based on the title and the year
     * @param s - the full title of the movie
     * @param x - the release date (year) of the movie
     * @throws IOException
     */
    public void searchMovie(String s, String x) throws IOException {
        Log.d("searchMovie()", "FOUND, WORKING");
        String data = "";
        final URL url;
        if(x.equals("N/A"))
            url  = new URL("https://www.omdbapi.com/?apikey=974f8ce1&t="+ s.replace(' ', '+'));
        else
            url = new URL("https://www.omdbapi.com/?apikey=974f8ce1&t="+ s.replace(' ', '+')+"&y=" + x);
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(url)
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
                Log.d("searchMovie()", "FOUND, NOT WORKING onFAILURE   " + url);
            }
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Log.d("searchMovie()", "FOUND, ONRESPONSE");
                if (response.isSuccessful()) {
                    Log.d("searchMovie()", "FOUND, ONRESPONSE IFFFF");
                    final String myResponse = response.body().string(); // JSON string
                    movieInfo.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                JSONObject jObject = new JSONObject(myResponse);

                                title = jObject.getString("Title") + " (" + jObject.getString("Year") + ")";
                                //rYear = "Year: " + jObject.getString("Year");
                                runtime = "Runtime: " + jObject.getString("Runtime");
                                genre = "Genre: " + jObject.getString("Genre");
                                actors = "Actors: " + jObject.getString("Actors");
                                writers = "Writers: " + jObject.getString("Writer");
                                directors = "Directors: " + jObject.getString("Director");
                                description = "Plot: " + jObject.getString("Plot");
                                posterURL = jObject.getString("Poster");
                                metascore = "MetaScore: " + jObject.getString("Metascore") + "/100";
                                imdbRating = "IMDBrating: " + jObject.getString("imdbRating") + "/10";
                                setImage(posterURL, title ,description, rYear, actors, writers, imdbRating, metascore);
                                Log.d("SEARCHMOVIEEEEE()", title);

                            } catch (JSONException ex) {
                                ex.printStackTrace();
                            }

                        }
                    });
                }
            }
        });
    }

    /**
     * this method is to set the parameters to an ImageView or TextView
     * used to display the values in the screen
     * @param imageURL
     * @param title
     * @param movieDescription
     * @param year
     * @param actors
     * @param writers
     * @param imbdRating
     * @param metascore
     */
    private void setImage(String imageURL, String title ,String movieDescription, String year, String actors, String writers, String imbdRating, String metascore){
        Log.d("setImage()", "setImage, WORKING");
        TextView name = findViewById(R.id.movieName);
        name.setText(title);
        TextView imbd = findViewById(R.id.imbd);
        imbd.setText(imdbRating);
        TextView meta = findViewById(R.id.meta);
        meta.setText(metascore);
        TextView mvActors = findViewById(R.id.movieActors);
        mvActors.setText(actors);
        TextView mvWriters = findViewById(R.id.movieWriters);
        mvWriters.setText(writers);
        TextView descript = findViewById(R.id.moviePlot);
        descript.setText(movieDescription);
        ImageView img = findViewById(R.id.movieImageSolo);
        Glide.with(this).asBitmap()
                .load(imageURL).into(img);
    }
}
