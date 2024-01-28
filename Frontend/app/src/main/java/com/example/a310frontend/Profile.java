package com.example.a310frontend;

import static kotlin.io.ByteStreamsKt.readBytes;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.OpenableColumns;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import okhttp3.*;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Profile extends AppCompatActivity {
    private EditText editTextUsername;
    private EditText editTextEmail;
    private EditText editTextOldPassword;
    private EditText editTextNewPassword;
    private Button buttonUpdate;
    private ImageButton backButton;
    private Button uploadImageButton;
    private ImageView profileImageView;
    private User_Update_Repo userUpdateRepo;
    private ExecutorService executorService;
    private static final int REQUEST_IMAGE_PICK = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        editTextUsername = findViewById(R.id.username_textbox);
        editTextEmail = findViewById(R.id.email_textbox);
        editTextOldPassword = findViewById(R.id.oldpassword_textbox);
        editTextNewPassword = findViewById(R.id.newpassword_textbox);
        buttonUpdate = findViewById(R.id.button_update);
        backButton = findViewById(R.id.back_button);
        uploadImageButton = findViewById(R.id.uploadImageButton);

        // Initialize the ImageView
        profileImageView = findViewById(R.id.profileImageView);

        executorService = Executors.newFixedThreadPool(4);
        userUpdateRepo = new User_Update_Repo();

        uploadImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
                intent.addCategory(Intent.CATEGORY_OPENABLE);
                intent.setType("image/*");
                startActivityForResult(intent, REQUEST_IMAGE_PICK);
            }
        });
        backButton.setOnClickListener(v -> finish());

        buttonUpdate.setOnClickListener(v -> {
            // Retrieve the information from the input fields
            String username = editTextUsername.getText().toString();
            String email = editTextEmail.getText().toString();
            String oldPassword = editTextOldPassword.getText().toString();
            String newPassword = editTextNewPassword.getText().toString();
            // Directly call the updateUserProfile method from userUpdateRepo
            userUpdateRepo.updateUserProfile(executorService, new Handler(getMainLooper()), username, email, oldPassword, newPassword, new User_Update_Repo.UpdateCallback() {

                public void onUpdateSuccess(User user) {
                    // This is run on the UI thread
                    Toast.makeText(Profile.this, "Profile updated successfully!", Toast.LENGTH_LONG).show();
                }

                @Override
                public void onUpdateFailed(String errorMessage) {
                    // This is run on the UI thread
                    Toast.makeText(Profile.this, "Failed to update profile: " + errorMessage, Toast.LENGTH_LONG).show();
                }
            });
        });

        backButton.setOnClickListener(v -> {
            finish();
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_IMAGE_PICK && resultCode == RESULT_OK && data != null) {
            Uri selectedImageUri = data.getData();

            Log.d("SelectedImageUri", selectedImageUri.toString());

            String fileName = getFileName(selectedImageUri);

            try {
                InputStream inputStream = getContentResolver().openInputStream(selectedImageUri);

                if (inputStream != null) {
                    try {
                        // Read the image bytes
                        byte[] imageBytes = readBytes(inputStream);

                        // Encode image to base64
                        String base64Image = Base64.encodeToString(imageBytes, Base64.DEFAULT);

                        // Decode and set the base64 encoded image to the ImageView
                        byte[] decodedBytes = Base64.decode(base64Image, Base64.DEFAULT);
                        //profileImageView.setImageBitmap(BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.length));

                        // You can also send the base64Image to your server if needed

                        ImageView profileImageView = findViewById(R.id.profileImageView);
                        Picasso.get().load(selectedImageUri).into(profileImageView);

                    } catch (Exception e) {
                        e.printStackTrace();
                    } finally {
                        inputStream.close();
                    }
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private String getFileName(Uri uri) {
        Cursor cursor = getContentResolver().query(uri, null, null, null, null);
        try {
            if (cursor != null && cursor.moveToFirst()) {
                int nameIndex = cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME);
                return cursor.getString(nameIndex);
            }
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return "image.jpg";
    }
}
