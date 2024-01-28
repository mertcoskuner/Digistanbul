package com.example.a310frontend;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.util.Base64;
import android.widget.ImageView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class ImageUtility {

    // Method to load image from a URL that returns a base64 string
    public static void loadImageFromUrl(final ImageView imageView, String url) {
        // Using Volley for the network request
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Convert Base64 string to Bitmap
                        byte[] decodedString = Base64.decode(response, Base64.DEFAULT);
                        Bitmap decodedBitmap = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);

                        // Set Bitmap to ImageView
                        imageView.setImageBitmap(decodedBitmap);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // Error handling
            }
        });

        RequestQueue requestQueue = Volley.newRequestQueue(imageView.getContext());
        requestQueue.add(stringRequest);
    }

    // Method to convert Bitmap to JPEG and return it as a File
    public static File convertBitmapToJpeg(Bitmap bitmap, Context context) {
        // Create a File to save the image
        File jpegFile = new File(context.getExternalFilesDir(Environment.DIRECTORY_PICTURES), "image.jpeg");

        try (FileOutputStream out = new FileOutputStream(jpegFile)) {
            // Compress Bitmap to JPEG
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return jpegFile;
    }
}
