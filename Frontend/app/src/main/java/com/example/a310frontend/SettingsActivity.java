package com.example.a310frontend;

import static kotlin.io.ByteStreamsKt.readBytes;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.OpenableColumns;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import okhttp3.*;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class SettingsActivity extends AppCompatActivity {

    private EditText editTextEmail;
    private EditText editTextUsername;
    private EditText editTextOldPassword;
    private EditText editTextNewPassword;
    private ImageButton backButton;

    private static final int REQUEST_IMAGE_PICK = 2;
    private ImageView selectedImageView;
    //private Button selectImageButton;

    private Button buttonUpdateProfile;

    private ExecutorService executorService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_settings);

        selectedImageView = findViewById(R.id.selectedImageView);
        //selectImageButton = findViewById(R.id.buttonSelectImage);
        backButton = findViewById(R.id.backB);
        editTextEmail = findViewById(R.id.editTextEmail);
        editTextUsername = findViewById(R.id.editTextUsername);
        editTextOldPassword = findViewById(R.id.editTextPassword);
        editTextNewPassword = findViewById(R.id.editTextNewPassword);
        buttonUpdateProfile = findViewById(R.id.buttonUpdateProfile);

        executorService = Executors.newFixedThreadPool(4);

/*        selectImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
                intent.addCategory(Intent.CATEGORY_OPENABLE);
                intent.setType("image/*");
                startActivityForResult(intent, REQUEST_IMAGE_PICK);
            }
        });*/

        backButton.setOnClickListener(v -> finish());

        buttonUpdateProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get user input values
                String email = editTextEmail.getText().toString();
                String username = editTextUsername.getText().toString();
                String oldPassword = editTextOldPassword.getText().toString();
                String newPassword = editTextNewPassword.getText().toString();

                UserProfileRequest userProfileRequest = new UserProfileRequest();
                userProfileRequest.setEmail(email);
                userProfileRequest.setUsername(username);
                userProfileRequest.setOldPassword(oldPassword);
                userProfileRequest.setNewPassword(newPassword);

                executorService.execute(new Runnable() {
                    @Override
                    public void run() {
                        sendUpdateRequest(userProfileRequest);
                    }
                });
            }
        });
    }

    private void sendUpdateRequest(UserProfileRequest request) {
        OkHttpClient client = new OkHttpClient();
        MediaType JSON = MediaType.parse("application/json; charset=utf-8");
        String requestBody = "{\"email\":\"" + request.getEmail() + "\",\"username\":\"" + request.getUsername() + "\",\"oldPassword\":\"" + request.getOldPassword() + "\",\"newPassword\":\"" + request.getNewPassword() + "\"}";
        RequestBody body = RequestBody.create(JSON, requestBody);

        Request httpRequest = new Request.Builder()
                .url("http://10.0.2.2:8080/api/users/update")
                .put(body)
                .build();

        client.newCall(httpRequest).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getApplicationContext(), "Request failed. Check your network connection.", Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getApplicationContext(), "Request sent successfully.", Toast.LENGTH_SHORT).show();
                        }
                    });
                } else {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getApplicationContext(), "Request failed. Server returned an error.", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
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
                        OkHttpClient client = new OkHttpClient();

                        RequestBody requestBody = new MultipartBody.Builder()
                                .setType(MultipartBody.FORM)
                                .addFormDataPart("image", fileName,
                                        RequestBody.create(MediaType.parse("image/jpeg"), readBytes(inputStream)))
                                .build();

                        Request request = new Request.Builder()
                                .url("http://10.0.2.2:8080/image")
                                .post(requestBody)
                                .build();

                        client.newCall(request).enqueue(new Callback() {
                            @Override
                            public void onFailure(Call call, IOException e) {
                                e.printStackTrace();
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast.makeText(getApplicationContext(), "Request failed. Check your network connection.", Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }

                            @Override
                            public void onResponse(Call call, Response response) throws IOException {
                                if (response.isSuccessful()) {
                                    final String fileName = response.body().string();

                                    final String downloadUrl = "http://10.0.2.2:8080/image/" + fileName;

                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            Toast.makeText(getApplicationContext(), "Image uploaded successfully. Download URL: " + downloadUrl, Toast.LENGTH_LONG).show();
                                        }
                                    });
                                } else {
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            Toast.makeText(getApplicationContext(), "Request failed. Server returned an error.", Toast.LENGTH_SHORT).show();
                                        }
                                    });
                                }
                            }
                        });

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


    @Override
    protected void onDestroy() {
        super.onDestroy();

        if (executorService != null) {
            executorService.shutdown();
        }
    }
}
