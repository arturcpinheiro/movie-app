package com.example.movie_app;

import android.content.Intent;
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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void useSearch(View view){
        if(view.getId() == R.id.search)
        {
            TextView text = findViewById(R.id.userInput);
            Intent i = new Intent(this, SearchEverything.class);
            i.putExtra("movieName", text.getText().toString() + ";;;search");
            startActivity(i);
        }
        else if(view.getId() == R.id.trending)
        {
            Intent i = new Intent(this, SearchEverything.class);
            i.putExtra("movieName",  "s;;;trending");
            startActivity(i);
        }
        else if(view.getId() == R.id.topRated)
        {
            Intent i = new Intent(this, SearchEverything.class);
            i.putExtra("movieName",  "s;;;topRated");
            startActivity(i);
        }
        else if(view.getId() == R.id.theatres)
        {
            Intent i = new Intent(this, SearchEverything.class);
            i.putExtra("movieName",  "s;;;theatres");
            startActivity(i);
        }
        else
        {
            Intent i = new Intent(this, SearchEverything.class);
            i.putExtra("movieName",  "s;;;upcoming");
            startActivity(i);
        }


    }


}
