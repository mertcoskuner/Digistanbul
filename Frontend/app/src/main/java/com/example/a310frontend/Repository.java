package com.example.a310frontend;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Repository {
    public void downloadImage(Handler uiHandler, String path) {

        ExecutorService srv = Executors.newFixedThreadPool(5);
        srv.submit(() -> {


            try {
                URL url = new URL(path);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                Bitmap bmp = BitmapFactory.decodeStream(conn.getInputStream());
                Message msg = new Message();
                msg.obj = bmp;
                uiHandler.sendMessage(msg);

            } catch (MalformedURLException e) {
                Log.e("DEV", e.getMessage());
            } catch (IOException e) {
                Log.e("DEV", e.getMessage());
            }


        });


    }

    public void getPlaceByName(Handler uiHandler, String name) {
        ExecutorService srv = Executors.newFixedThreadPool(5);
        srv.submit(() -> {

            try {
                URL url = new URL("http://localhost:8080/api/place/place-with-placename/" + name);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();

                BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));

                StringBuilder buffer = new StringBuilder();

                String line = "";

                while ((line = reader.readLine()) != null) {

                    buffer.append(line);
                }
                JSONArray arr = new JSONArray(buffer.toString());

                JSONObject current = new JSONObject();

                for (int i = 0; i < arr.length(); i++) {

                    current = arr.getJSONObject(i);

                }


                JSONObject groupOBJ = current.getJSONObject("muscle");


                String group = groupOBJ.getString("group");


                Place place = new Place(
                        current.getString("id"),
                        current.getString("placeName"),
                        current.getString("placeType"),
                        current.getString("imagePath"),
                        current.getString("description"),
                        current.getDouble("price"),
                        current.getDouble("rating")

                );


                Message msg = new Message();

                msg.obj = place;

                uiHandler.sendMessage(msg);


            } catch (MalformedURLException e) {
                Log.e("DEV", e.getMessage());
            } catch (IOException e) {
                Log.e("DEV", e.getMessage());
            } catch (JSONException e) {
                Log.e("DEV", e.getMessage());
            }


        });
    }

    public void postComment(Handler handler, String exerciseName, String comment) {

        ExecutorService srv = Executors.newFixedThreadPool(5);
        srv.submit(() -> {

            try {

                JSONObject obj1 = new JSONObject();
                JSONObject obj2 = new JSONObject();
                JSONObject embed = new JSONObject();
                obj1.put("content", comment);
                obj2.put("name", exerciseName);
                //embed.put("exercise", obj2);
                obj1.put("exercise", obj2);
                URL url = new URL("http://10.0.2.2:8080/comments/post");

                HttpURLConnection conn = (HttpURLConnection) url.openConnection();

                conn.setDoInput(true);
                conn.setDoOutput(true);

                conn.setRequestMethod("POST");
                conn.setRequestProperty("Content-Type", "application/JSON");

                BufferedOutputStream writer = new BufferedOutputStream(conn.getOutputStream());

                writer.write(obj1.toString().getBytes());

                writer.flush();

                BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));

                StringBuilder buffer = new StringBuilder();

                String line = "";

                while ((line = reader.readLine()) != null) {
                    buffer.append(line);
                }

                handler.sendEmptyMessage(0);


            } catch (MalformedURLException e) {
                Log.e("DEV", e.getMessage());
            } catch (IOException e) {
                Log.e("DEV", e.getMessage());
            } catch (JSONException e) {
                Log.e("DEV", e.getMessage());
            }

        });
    }
    public void getPlaceByType(ExecutorService srv, Handler uiHandler, String type) {

        List<Place> data = new ArrayList<>();
        srv.submit(() -> {

            try {

                URL url = new URL("http://localhost:8080/api/place/get-" + type + "s");


                HttpURLConnection conn = (HttpURLConnection) url.openConnection();

                BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));

                StringBuilder buffer = new StringBuilder();

                String line = "";

                while ((line = reader.readLine()) != null) {
                    buffer.append(line);

                }


                JSONArray arr = new JSONArray(buffer.toString());

                for (int i = 0; i < arr.length(); i++) {


                    JSONObject current = arr.getJSONObject(i);
                    Place place = new Place(
                            current.getString("id"),
                            current.getString("placeName"),
                            current.getString("placeType"),
                            current.getString("imagePath"),
                            current.getString("description"),
                            current.getDouble("price"),
                            current.getDouble("rating")
                    );

                    data.add(place);

                }


            } catch (MalformedURLException e) {
                Log.e("DEV", e.getMessage());
            } catch (IOException e) {
                Log.e("DEV", e.getMessage());
            } catch (JSONException e) {
                Log.e("DEV", e.getMessage());
            }
            Message msg = new Message();
            msg.obj = data;

            uiHandler.sendMessage(msg);

        });


    }
}