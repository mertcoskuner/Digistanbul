package com.example.a310frontend;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class SImageFragment extends Fragment {

    private ImageView imgDetails;
    private ExecutorService executorService;
    private SImageRepo imageRepo;

    private static final int PICK_IMAGE_REQUEST = 1;

    private final Handler imgHandler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(@NonNull Message msg) {
            if (msg.obj instanceof Bitmap) {
                imgDetails.setImageBitmap((Bitmap) msg.obj);
            } else {
                // Handle the case when the message object is not a Bitmap
                Toast.makeText(getContext(), "Unexpected message type", Toast.LENGTH_SHORT).show();
            }
            return true;
        }
    });

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_settings, container, false);
        imgDetails = view.findViewById(R.id.profileView);

        executorService = Executors.newFixedThreadPool(4);
        imageRepo = new SImageRepo(executorService, imgHandler);

        // Replace "http://your.server.com/your_image_path.jpg" with the actual image URL
        String imageUrl = "http://10.0.2.2:8080/image/default-profile.jpg";

        imageRepo.downloadImage(imageUrl, new SImageRepo.ImageDownloadCallback() {

            @Override
            public void onImageDownloadSuccess(Bitmap bitmap) {
                // Image downloaded successfully
                // You can update UI or perform any additional actions here

                if (bitmap != null) {
                    // Update the ImageView with the downloaded bitmap
                    imgDetails.setImageBitmap(bitmap);

                    // Show a TextView or any other UI component
                    TextView imageDownloadedText = requireView().findViewById(R.id.profileView);
                    imageDownloadedText.setVisibility(View.VISIBLE);

                    // You can perform additional UI updates or actions here
                } else {
                    // Handle the case when the downloaded bitmap is null
                    Toast.makeText(requireContext(), "Downloaded bitmap is null", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onImageDownloadFailure(String errorMessage) {
                // Image download failed
                Toast.makeText(getContext(), errorMessage, Toast.LENGTH_SHORT).show();
            }
        });

        // Set up click listener for selecting a new image
        imgDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("asdasd", "asdasd");
                openFileChooser();
            }
        });

        return view;
    }

    private void openFileChooser() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK && data != null && data.getData() != null) {
            Uri selectedImageUri = data.getData();

            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(requireActivity().getContentResolver(), selectedImageUri);
                imgDetails.setImageBitmap(bitmap);

                // Now you can upload the selected image using the ImageRepo
                uploadSelectedImage(selectedImageUri);

            } catch (IOException e) {
                e.printStackTrace();
                Toast.makeText(getContext(), "Failed to load selected image", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void uploadSelectedImage(Uri imageUri) {
        // Implement your logic for uploading the selected image to the server using ImageRepo or other methods.
        // ...
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        executorService.shutdown(); // Shutdown the executor service when the fragment is destroyed
    }
}
