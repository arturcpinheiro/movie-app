/**
 * Assignment 2
 * @author  Kyle Alialy & Artur Pinheiro
 * @version 1.0
 * @since 03/20/2020
 */
package com.example.movie_app;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;


public class MainActivity extends AppCompatActivity {


    @Override
    /**
     * Method that runs when the file is running.
     */
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     * Method will get user input, then send the next page with input data.
     * @param view
     */
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
