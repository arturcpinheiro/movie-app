package com.example.movie_app;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import static android.content.ContentValues.TAG;

public class RecyclerViewMovies extends RecyclerView.Adapter<RecyclerViewMovies.ViewHolder>{

    private ArrayList<String> movieNames;
    private ArrayList<String> movieDescs;
    private ArrayList<String> imgURL;
    private Context mContext;

    /**
     * Constructor
     * @param movieNames
     * @param movieDesc
     * @param url
     * @param mContext
     */
    public RecyclerViewMovies(ArrayList<String> movieNames, ArrayList<String> movieDesc, ArrayList<String> url ,Context mContext) {
        this.movieNames = movieNames;
        this.movieDescs = movieDesc;
        this.imgURL = url;
        this.mContext = mContext;
    }

    /**
     * this method runs when object is created, it will inflate the view.
     * Basically it will put the views in its positions on xml file
     * @param parent
     * @param viewType
     * @return
     */
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.adpt_view_layout, parent, false);
    ViewHolder holder = new ViewHolder(v);
    return holder;
    }

    /**
     *
     * @param holder
     * @param position
     */
    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        Log.d(TAG, "onBindViewHolder: CALLED"); //debugging
        //Get Images
        Glide.with(mContext).asBitmap().load(imgURL.get(position)).into(holder.image);

        holder.movieName.setText(movieNames.get(position));

        holder.movieDesc.setText(movieDescs.get(position));

        holder.lvLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClickL clicked on: " + movieNames.get(position));

                Toast.makeText(mContext, movieNames.get(position), Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * Method tells how many objects will be in the listAdapter.
     * @return
     */
    @Override
    public int getItemCount() {
        return movieNames.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        // holds widget in memory.
        ImageView image;
        TextView movieName;
        TextView movieDesc;
        LinearLayout lvLayout;
        public ViewHolder(View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.image);
            movieDesc = itemView.findViewById(R.id.movieDesc);
            movieName = itemView.findViewById(R.id.movieName);
            lvLayout = itemView.findViewById(R.id.containerLayout);
        }
    }
}
