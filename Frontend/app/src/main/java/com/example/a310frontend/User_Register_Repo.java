package com.example.a310frontend;

import android.os.Handler;
import android.util.Log;

import org.json.JSONObject;

import java.io.BufferedWriter;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.ExecutorService;

public class User_Register_Repo {

    public void registerUser(ExecutorService executorService, Handler handler, String username, String email, String password) {
    }

    public interface RegistrationCallback {
        void onRegistrationSuccess(User user);
        void onRegistrationFailed(String errorMessage);
    }

    public void registerUser(ExecutorService executorService, Handler uiHandler, String username, String email, String password, RegistrationCallback registrationCallback) {
        executorService.submit(() -> {
            HttpURLConnection conn = null;
            try {
                URL url = new URL("http://10.0.2.2:8080/api/users/register");
                conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("POST");
                conn.setRequestProperty("Content-Type", "application/json; utf-8");
                conn.setDoOutput(true);

                JSONObject registrationJson = new JSONObject();
                registrationJson.put("username", username);
                registrationJson.put("email", email);
                registrationJson.put("password", password);

                try (BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(conn.getOutputStream(), StandardCharsets.UTF_8))) {
                    writer.write(registrationJson.toString());
                    writer.flush();
                }

                int responseCode = conn.getResponseCode();
                if (responseCode == HttpURLConnection.HTTP_CREATED) {
                    User user = new User(username, email, password, null, null);
                    uiHandler.post(() -> registrationCallback.onRegistrationSuccess(user));
                } else {
                    String errorMessage = "Registration failed with response code: " + responseCode;
                    Log.e("REGISTRATION", errorMessage);
                    uiHandler.post(() -> registrationCallback.onRegistrationFailed(errorMessage));
                }
            } catch (Exception e) {
                String errorMessage = "Registration failed: " + e.getMessage();
                Log.e("User_Register_Repo", errorMessage, e);
                uiHandler.post(() -> registrationCallback.onRegistrationFailed(errorMessage));
            } finally {
                if (conn != null) {
                    conn.disconnect();
                }
            }
        });
    }
}
