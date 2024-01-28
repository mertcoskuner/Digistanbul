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

public class User_Update_Repo {

    public interface UpdateCallback {
        void onUpdateSuccess(User user);
        void onUpdateFailed(String errorMessage);
    }

    public void updateUserProfile(ExecutorService executorService, Handler uiHandler, String username, String email, String oldPassword, String newPassword, UpdateCallback updateCallback) {
        executorService.submit(() -> {
            HttpURLConnection conn = null;
            try {
                URL url = new URL("http://10.0.2.2:8080/api/users/update");
                conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("PUT");
                conn.setRequestProperty("Content-Type", "application/json; utf-8");
                conn.setDoOutput(true);

                JSONObject userJson = new JSONObject();
                userJson.put("username", username);
                userJson.put("email", email);
                userJson.put("oldPassword", oldPassword);
                userJson.put("newPassword", newPassword);

                try (BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(conn.getOutputStream(), StandardCharsets.UTF_8))) {
                    writer.write(userJson.toString());
                    writer.flush();
                }

                int responseCode = conn.getResponseCode();
                if (responseCode == HttpURLConnection.HTTP_OK) {
                    //uiHandler.post(() -> updateCallback.onUpdateSuccess(user));
                } else {
                    String errorMessage = "Update failed with response code: " + responseCode;
                    Log.e("UPDATE", errorMessage);
                    uiHandler.post(() -> updateCallback.onUpdateFailed(errorMessage));
                }
            } catch (Exception e) {
                String errorMessage = "Update failed: " + e.getMessage();
                Log.e("User_Update_Repo", errorMessage, e);
                uiHandler.post(() -> updateCallback.onUpdateFailed(errorMessage));
            } finally {
                if (conn != null) {
                    conn.disconnect();
                }
            }
        });
    }
}
