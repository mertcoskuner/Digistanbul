package com.example.a310frontend;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class mainrcycle extends AppCompatActivity {

    private Repository repository;
    private RecyclerView recyclerViewRestaurant;
    private RecyclerView recyclerViewCafe;
    private RecyclerView recyclerViewHistorical;
    private ImageButton backButton;
    private Button buttonRestaurant;
    private Button buttonCafe;
    private Button buttonHistorical;
    private ImageButton profile_button;
    private ImageButton homeButton;
    private ImageButton settings_button;
    private RestaurantAdapter restaurantAdapter;
    private CafeAdapter cafeAdapter;
    private HistoricalAdapter historicalAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mainrcycle);

        repository = new Repository();

        homeButton = findViewById(R.id.home_button);

        // RESTAURANTS
        recyclerViewRestaurant = findViewById(R.id.recyclerViewRestaurant);
        //buttonRestaurant = findViewById(R.id.buttonRestaurant);

        restaurantAdapter = new RestaurantAdapter("Restaurant");
        recyclerViewRestaurant.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        recyclerViewRestaurant.setAdapter(restaurantAdapter);
        makeHttpRequestRestaurant("http://10.0.2.2:8080/api/place/get-restaurants", restaurantAdapter);
        recyclerViewRestaurant = findViewById(R.id.recyclerViewRestaurant);
        setupRecyclerViewClickListeners(recyclerViewRestaurant);

        // CAFES
        recyclerViewCafe = findViewById(R.id.recyclerViewCafe);
        //buttonCafe = findViewById(R.id.buttonCafe);

        cafeAdapter = new CafeAdapter("Cafe");
        recyclerViewCafe.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        recyclerViewCafe.setAdapter(cafeAdapter);
        makeHttpRequestCafe("http://10.0.2.2:8080/api/place/get-cafes", cafeAdapter);
        recyclerViewCafe = findViewById(R.id.recyclerViewCafe);
        setupRecyclerViewClickListeners(recyclerViewCafe);

        // HISTORICALS
        recyclerViewHistorical = findViewById(R.id.recyclerViewHistorical);
        //buttonHistorical = findViewById(R.id.buttonHistorical);

        historicalAdapter = new HistoricalAdapter("Historical");
        recyclerViewHistorical.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        recyclerViewHistorical.setAdapter(historicalAdapter);
        makeHttpRequestHistorical("http://10.0.2.2:8080/api/place/get-historicals", historicalAdapter);
        recyclerViewHistorical = findViewById(R.id.recyclerViewHistorical);
        setupRecyclerViewClickListeners(recyclerViewHistorical);

        profile_button = findViewById(R.id.profile_button);
        homeButton = findViewById(R.id.home_button);
        settings_button = findViewById(R.id.settings_button);
        profile_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Start ProfileActivity
                Intent intent = new Intent(mainrcycle.this, Profile.class);
                startActivity(intent);
            }
        });

        settings_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Start SettingsActivity
                Intent intent = new Intent(mainrcycle.this, SettingsActivity.class);
                startActivity(intent);
            }
        });

        homeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Start HomeActivity
                Intent intent = new Intent(mainrcycle.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }

    private void makeHttpRequestRestaurant(String url, final RestaurantAdapter adapter) {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(url)
                .build();

        client.newCall(request).enqueue(new Callback() {
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
                Log.e("MainActivity", "HTTP request failed", e);
            }

            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    final String responseData = response.body().string();
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                List<Place> places = parseJsonResponse(responseData);
                                adapter.setPlaces(places);
                            } catch (JSONException e) {
                                e.printStackTrace();
                                Log.e("MainActivity", "JSON parsing error", e);
                            }
                        }
                    });
                } else {
                    Log.e("MainActivity", "HTTP response unsuccessful: " + response.code());
                }
            }
        });
    }

    private void makeHttpRequestCafe(String url, final CafeAdapter adapter) {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(url)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
                // Handle failure, e.g., show an error message
                Log.e("MainActivity", "HTTP request failed", e);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    final String responseData = response.body().string();
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                List<Place> places = parseJsonResponse(responseData);
                                adapter.setPlaces(places);
                            } catch (JSONException e) {
                                e.printStackTrace();
                                Log.e("MainActivity", "JSON parsing error", e);
                            }
                        }
                    });
                } else {
                    Log.e("MainActivity", "HTTP response unsuccessful: " + response.code());
                }
            }
        });
    }

    private void makeHttpRequestHistorical(String url, final HistoricalAdapter adapter) {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(url)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
                // Handle failure, e.g., show an error message
                Log.e("MainActivity", "HTTP request failed", e);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    final String responseData = response.body().string();
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                // Parse JSON response
                                List<Place> places = parseJsonResponse(responseData);
                                // Update the historical adapter with the parsed data
                                adapter.setPlaces(places);
                            } catch (JSONException e) {
                                e.printStackTrace();
                                // Handle JSON parsing error, e.g., show an error message
                                Log.e("MainActivity", "JSON parsing error", e);
                            }
                        }
                    });
                } else {
                    // Handle unsuccessful response, e.g., show an error message
                    Log.e("MainActivity", "HTTP response unsuccessful: " + response.code());
                }
            }
        });
    }

    private List<Place> parseJsonResponse(String json) throws JSONException {
        List<Place> places = new ArrayList<>();
        JSONArray jsonArray = new JSONArray(json);

        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject placeJson = jsonArray.getJSONObject(i);
            Place place = new Place();
            place.setId(placeJson.getString("id"));
            place.setPlaceName(placeJson.getString("placeName"));
            place.setPlaceType(placeJson.getString("placeType"));
            place.setDescription(placeJson.getString("description"));
            place.setImagePath(placeJson.getString("imagePath"));
            place.setRating(placeJson.getDouble("rating"));
            place.setRating(placeJson.getDouble("price"));
            places.add(place);
        }

        return places;
    }

    private void setupRecyclerViewClickListeners(RecyclerView recyclerView) {
        recyclerView.addOnItemTouchListener(
            new RecyclerItemClickListener(this, recyclerView,
                new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        PlaceAdapter adapter = (PlaceAdapter) recyclerView.getAdapter();
                        if (adapter != null) {
                            List<Place> places = adapter.getPlaces();
                            if (places != null && position >= 0 && position < places.size()) {
                                Place place = places.get(position);
                                openPlaceDetailActivity(place);
                            }
                        }
                    }

                    @Override
                    public void onLongItemClick(View view, int position) {
                        // Handle long item click here if needed
                    }
                })
        );
    }

    private void openPlaceDetailActivity(Place place) {
        Intent intent = new Intent(this, PlaceDetailActivity.class);
        intent.putExtra("place", place);
        startActivity(intent);
    }

    private class RecyclerItemClickListener implements RecyclerView.OnItemTouchListener {
        private OnItemClickListener mListener;
        private GestureDetector mGestureDetector;

        public interface OnItemClickListener {
            void onItemClick(View view, int position);
            void onLongItemClick(View view, int position);
        }

        public RecyclerItemClickListener(
                Context context, final RecyclerView recyclerView, OnItemClickListener listener) {
            mListener = listener;
            mGestureDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener() {
                @Override
                public boolean onSingleTapUp(MotionEvent e) {
                    return true;
                }

                @Override
                public void onLongPress(MotionEvent e) {
                    View child = recyclerView.findChildViewUnder(e.getX(), e.getY());
                    if (child != null && mListener != null) {
                        mListener.onLongItemClick(child, recyclerView.getChildAdapterPosition(child));
                    }
                }
            });
        }

        @Override
        public boolean onInterceptTouchEvent(@NonNull RecyclerView rv, @NonNull MotionEvent e) {
            View child = rv.findChildViewUnder(e.getX(), e.getY());
            if (child != null && mListener != null && mGestureDetector.onTouchEvent(e)) {
                mListener.onItemClick(child, rv.getChildAdapterPosition(child));
                return true;
            }
            return false;
        }

        @Override
        public void onTouchEvent(@NonNull RecyclerView rv, @NonNull MotionEvent e) {
            // Unused, but required by the interface
        }

        @Override
        public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {
            // Unused, but required by the interface
        }
    }

}
