package com.example.a310frontend;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.util.Log;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;

public class PlaceDetailActivity extends AppCompatActivity {

    private ImageButton backButton;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.listed_place);
        backButton = findViewById(R.id.backButton);
        Place place = getIntent().getParcelableExtra("place");
        populateDetailView(place);
        backButton.setOnClickListener(v -> finish());
    }


    private void populateDetailView(Place place) {
        ImageView imgPlace = findViewById(R.id.imgPlace);

        TextView txtListPlaceName = findViewById(R.id.txtListPlaceName);
        TextView txtListDescription = findViewById(R.id.txtListDescription);
        TextView txtListPrice = findViewById(R.id.txtListPrice);

        Log.d("PlaceDetail", "Image Path: " + place.getImagePath());
        Log.d("PlaceDetail", "Description: " + place.getDescription());
        Log.d("PlaceDetail", "Rating: " + place.getRating());
        Log.d("PlaceDetail", "Type: " + place.getPlaceType());
        Log.d("PlaceDetail", "Price: " + place.getPrice());

        // Load the image using Glide from the provided URL
        Glide.with(this)
                .load(place.getPlaceType()) // Load image from URL
                .placeholder(R.drawable.placeholder_image)
                .into(imgPlace);

        txtListPlaceName.setText(place.getPlaceName());
        txtListDescription.setText(place.getImagePath());
        txtListPrice.setText(String.valueOf(place.getPrice()));

    }
}
