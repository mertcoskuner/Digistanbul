package com.example.a310frontend;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.ExecutorService;

public class User_Login_Repo {

    public void authenticateUser(ExecutorService srv, Handler uiHandler, String username, String password) {
        srv.submit(() -> {
            try {
                URL url = new URL("http://10.0.2.2:8080/api/users/username/" + username);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();

                BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                StringBuilder buffer = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    buffer.append(line);
                }

                JSONObject jsonObject = new JSONObject(buffer.toString());
                User user = new User(
                        jsonObject.getString("username"),
                        jsonObject.getString("email"),
                        jsonObject.getString("password"),
                        jsonObject.optString("favorites"),
                        jsonObject.optString("favPlace"));

                if(user.getPassword().equals(password)) {
                    Message msg = new Message();
                    msg.obj = user;
                    uiHandler.sendMessage(msg);
                } else {
                    // Handle authentication failure
                    Log.e("AUTH", "Authentication failed");
                }

            } catch (MalformedURLException e) {
                Log.e("DEV", e.getMessage());
            } catch (IOException e) {
                Log.e("DEV", e.getMessage());
            } catch (JSONException e) {
                Log.e("DEV", e.getMessage());
            }
        });
    }

}
