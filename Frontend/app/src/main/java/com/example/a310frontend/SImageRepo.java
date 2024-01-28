package com.example.a310frontend;/*
package com.example.settingspage;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Handler;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.ExecutorService;

public class ImageRepo {

    private final Context context;

    public ImageRepo(Context context) {
        this.context = context;
    }

    public ImageRepo() {
        this.context = null;
    }

    public interface ImageUploadCallback {
        void onImageUploadSuccess();

        void onImageUploadFailure(String errorMessage);
    }

    public interface ImageDownloadCallback {
        void onImageDownloadSuccess(Bitmap bitmap);

        void onImageDownloadFailure(String errorMessage);
    }

    public void uploadImage(ExecutorService srv, Uri imageUri, ImageUploadCallback callback) {
        srv.execute(() -> {
            try {
                byte[] imageData = convertImageUriToByteArray(imageUri);

                URL url = new URL("http://localhost:8080/image/fileName");
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();

                connection.setRequestMethod("POST");
                connection.setDoOutput(true);
                connection.setRequestProperty("Content-Type", "application/octet-stream");

                try (OutputStream os = connection.getOutputStream()) {
                    os.write(imageData);
                    os.flush();
                }

                if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                    callback.onImageUploadSuccess();
                } else {
                    callback.onImageUploadFailure("Upload failed. Server returned: " + connection.getResponseCode());
                }

                connection.disconnect();

            } catch (IOException e) {
                e.printStackTrace();
                callback.onImageUploadFailure("Upload failed. Exception: " + e.getMessage());
            }
        });
    }

    public void downloadImage(ExecutorService srv, String fileName, ImageDownloadCallback callback) {
        srv.execute(() -> {
            try {
                URL url = new URL("http://localhost:8080/image/" + fileName);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();

                if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                    Bitmap bitmap = convertInputStreamToBitmap(connection.getInputStream());
                    callback.onImageDownloadSuccess(bitmap);
                } else {
                    callback.onImageDownloadFailure("Download failed. Server returned: " + connection.getResponseCode());
                }

                connection.disconnect();

            } catch (IOException e) {
                e.printStackTrace();
                callback.onImageDownloadFailure("Download failed. Exception: " + e.getMessage());
            }
        });
    }

    private byte[] convertImageUriToByteArray(Uri imageUri) throws IOException {
        try (InputStream inputStream = context.getContentResolver().openInputStream(imageUri);
             ByteArrayOutputStream byteBuffer = new ByteArrayOutputStream()) {

            int bufferSize = 1024;
            byte[] buffer = new byte[bufferSize];

            int len;
            while ((len = inputStream.read(buffer)) != -1) {
                byteBuffer.write(buffer, 0, len);
            }

            return byteBuffer.toByteArray();
        }
    }

    private Bitmap convertInputStreamToBitmap(InputStream inputStream) throws IOException {
        BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream);
        return BitmapFactory.decodeStream(bufferedInputStream);
    }
}
*/

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.ExecutorService;

public class SImageRepo {

    public interface ImageDownloadCallback {
        void onImageDownloadSuccess(Bitmap bitmap);

        void onImageDownloadFailure(String errorMessage);
    }

    private final ExecutorService executorService;
    private final Handler uiHandler;

    public SImageRepo(ExecutorService executorService, Handler uiHandler) {
        this.executorService = executorService;
        this.uiHandler = uiHandler;
    }

    public void downloadImage(String imageUrl, ImageDownloadCallback callback) {
        executorService.execute(() -> {
            try {
                URL url = new URL(imageUrl);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();

                if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                    Bitmap bitmap = decodeSampledBitmapFromStream(connection.getInputStream(), 100, 100); // Adjust sample size as needed
                    sendBitmapToUI(bitmap, callback);
                } else {
                    sendFailureToUI("Download failed. Server returned: " + connection.getResponseCode(), callback);
                }

                connection.disconnect();
            } catch (MalformedURLException e) {
                sendFailureToUI("Download failed. Malformed URL.", callback);
            } catch (IOException e) {
                sendFailureToUI("Download failed. Exception: " + e.getMessage(), callback);
            }
        });
    }

    private Bitmap decodeSampledBitmapFromStream(InputStream inputStream, int reqWidth, int reqHeight) {
        // Calculate inSampleSize
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeStream(inputStream, null, options);
        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);

        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;
        try {
            inputStream.reset();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return BitmapFactory.decodeStream(inputStream, null, options);
    }

    private int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {
            final int halfHeight = height / 2;
            final int halfWidth = width / 2;

            // Calculate the largest inSampleSize value that is a power of 2 and keeps both
            // height and width larger than the requested height and width.
            while ((halfHeight / inSampleSize) >= reqHeight && (halfWidth / inSampleSize) >= reqWidth) {
                inSampleSize *= 2;
            }
        }

        return inSampleSize;
    }

    private void sendBitmapToUI(Bitmap bitmap, ImageDownloadCallback callback) {
        uiHandler.post(() -> callback.onImageDownloadSuccess(bitmap));
    }

    private void sendFailureToUI(String errorMessage, ImageDownloadCallback callback) {
        uiHandler.post(() -> callback.onImageDownloadFailure(errorMessage));
    }
}
