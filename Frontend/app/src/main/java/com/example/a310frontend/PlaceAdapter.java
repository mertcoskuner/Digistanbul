package com.example.a310frontend;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

public class PlaceAdapter extends RecyclerView.Adapter<PlaceAdapter.ViewHolder> {

    private List<Place> places;
    private OnItemClickListener clickListener;
    private String placeType;

    public interface OnItemClickListener {
        void onItemClick(Place place);
    }

    public PlaceAdapter(String placeType) {
        this.placeType = placeType;
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.clickListener = listener;
    }
    public List<Place> getPlaces() {
        return places;
    }
    public void setPlaces(List<Place> places) {
        this.places = places;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final Place place = places.get(position);
        //holder.textView.setText(place.getPlaceName());

        Glide.with(holder.itemView.getContext())
                .load(place.getImagePath()) // Provide the image URL
                .placeholder(R.drawable.placeholder_image) // Placeholder image while loading
                .error(R.drawable.placeholder_image) // Error image if loading fails
                .diskCacheStrategy(DiskCacheStrategy.ALL) // Caching strategy
                .into(holder.imageView); /////////////////

        holder.textView.setText(place.getPlaceName());

        // Set click listener
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (clickListener != null) {
                    clickListener.onItemClick(place);
                }
            }
        });
        holder.ratingTextView.setText(String.valueOf(place.getRating()));
        holder.priceTextView.setText(String.valueOf(place.getPrice()));
    }

    @Override
    public int getItemCount() {
        return places != null ? places.size() : 0;
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView textView;
        ImageView imageView;
        TextView ratingTextView;
        TextView priceTextView;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.textViewItem);
            imageView = itemView.findViewById(R.id.imageViewItem);
            ratingTextView = itemView.findViewById(R.id.ratingTextView);
            priceTextView = itemView.findViewById(R.id.priceTextView);
        }
    }
}
